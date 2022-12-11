package fr.nadirdev.rickandmortyapp.data.datasource.characters

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.Rx3Apollo
import fr.nadirdev.rickandmortyapp.GetAllCharactersQuery
import fr.nadirdev.rickandmortyapp.data.net.mappers.CharactersMapper
import fr.nadirdev.rickandmortyapp.domain.models.CharacterResult
import fr.nadirdev.rickandmortyapp.presentation.utils.isInternetAvailable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class CharactersRemoteDataSourceImpl(private val mapper: CharactersMapper) :
    CharacterRemoteDataSource, KoinComponent {

    private val apolloClient: ApolloClient by inject()
    private var disposable: DisposableObserver<Response<GetAllCharactersQuery.Data>>? = null

    override fun getCharacters(
        gender: String?,
        status: String?,
        onResult: (List<CharacterResult>) -> Unit
    ) {
        initDisposable()
        object : DisposableObserver<Response<GetAllCharactersQuery.Data>>() {
            override fun onNext(t: Response<GetAllCharactersQuery.Data>) {
                t.data?.let {
                    onResult(mapper.toModel(it).results)
                }
            }

            override fun onError(e: Throwable) {
                throw e
            }

            override fun onComplete() {}
        }.let {
            disposable = it
            fetchGraphQlCharacters(gender = gender, status = status)
                .flatMap { response -> getNextPage(response, gender, status) }
                .subscribe(it)
        }
    }

    fun fetchGraphQlCharacters(
        page: Int = 1,
        gender: String? = null,
        status: String? = null,
    ): @NonNull Observable<Response<GetAllCharactersQuery.Data>> {
        return Rx3Apollo.from(getQuery(page, gender, status))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter { it.data != null }
    }

    private fun initDisposable() {
        if (disposable != null && !disposable!!.isDisposed)
            disposable!!.dispose()
    }

    private fun getQuery(page: Int, gender: String?, status: String?) =
        apolloClient.query(
            GetAllCharactersQuery(
                page,
                Input.fromNullable(gender),
                Input.fromNullable(status)
            )
        )

    private fun getNextPage(
        response: Response<GetAllCharactersQuery.Data>,
        gender: String? = null,
        status: String? = null,
    ): @NonNull Observable<Response<GetAllCharactersQuery.Data>> {
        return response.data?.characters?.info?.next
            ?.let { next ->
                Observable.merge(
                    Observable.just(response),
                    Observable.just(response)
                        .flatMap { fetchGraphQlCharacters(next, gender, status) }
                        .flatMap { resp: Response<GetAllCharactersQuery.Data> ->
                            getNextPage(
                                resp,
                                gender,
                                status
                            )
                        }
                )
            } ?: Observable.just(response)
    }
}