package fr.nadirdev.rickandmortyapp

import com.apollographql.apollo.api.Response
import fr.nadirdev.rickandmortyapp.data.database.mappers.CharactersMapper as DbCharacterMapper
import fr.nadirdev.rickandmortyapp.data.net.mappers.CharactersMapper as NetCharacterMapper
import fr.nadirdev.rickandmortyapp.data.datasource.characters.CharacterLocalDataSourceImpl
import fr.nadirdev.rickandmortyapp.data.datasource.characters.CharactersRemoteDataSourceImpl
import fr.nadirdev.rickandmortyapp.domain.models.CharacterResult
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import io.reactivex.rxjava3.observers.TestObserver
import org.hamcrest.Matchers

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class CharactersUnitTest : BaseUnitTest() {

    private lateinit var localSource: CharacterLocalDataSourceImpl
    private lateinit var remoteSource: CharactersRemoteDataSourceImpl

    private val characters : List<CharacterResult> by lazy {
        listOf(
            CharacterResult(1,"Character1","",gender = "Male", status = "Alive"),
            CharacterResult(2,"Character2","",gender = "Female", status = "Alive"),
            CharacterResult(3,"Character3","",gender = "Male", status = "Dead"),
            CharacterResult(4,"Character4","",gender = "Female", status = "Dead"),
            CharacterResult(5,"Character5","",gender = "Male", status = "Alive"),
            CharacterResult(6,"Character6","",gender = "Female", status = "Dead"),
            CharacterResult(7,"Character7","",gender = "Male", status = "Alive")
        )
    }

    override fun before() {
        localSource = CharacterLocalDataSourceImpl(db.characterDao(),DbCharacterMapper())
        remoteSource = CharactersRemoteDataSourceImpl(NetCharacterMapper())
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `should post and get all characters from db`() = runBlocking {
        val result = localSource.postAll(characters)
        if(result is fr.nadirdev.rickandmortyapp.domain.utils.Result.Success) {
            var result2 = localSource.getAll(null,null)
            if(result2 is fr.nadirdev.rickandmortyapp.domain.utils.Result.Success){
                assertThat(result2.data, Matchers.equalTo(characters))
                assertTrue(result2.data[2].name == "Character3")
            }else throwResult(result2)

            result2 = localSource.getAll("Male",null)
            if(result2 is fr.nadirdev.rickandmortyapp.domain.utils.Result.Success){
                assertTrue(result2.data.size == 4)
                assertTrue(result2.data[2].name == "Character5")
            }else throwResult(result2)

            result2 = localSource.getAll("Female","Alive")
            if(result2 is fr.nadirdev.rickandmortyapp.domain.utils.Result.Success){
                assertTrue(result2.data.size == 1)
                assertTrue(result2.data[0].name == "Character2")
            }else throwResult(result2)

            result2 = localSource.getAll("Female","unknown")
            if(result2 is fr.nadirdev.rickandmortyapp.domain.utils.Result.Success){
                assertTrue(result2.data.isEmpty())
            }else throwResult(result2)

        }
        else throwResult(result)
    }

    @Test
    fun `should fetch characters`() = runBlocking {
        val testObserver = TestObserver.create<Response<GetAllCharactersQuery.Data>>()
        val obs: @NonNull Observable<Response<GetAllCharactersQuery.Data>> =
            remoteSource.fetchGraphQlCharacters(1, null, null)
        obs.subscribe(testObserver)
        testObserver.await().assertComplete().assertNoErrors()
        val data = testObserver.values()[0].data!!.characters!!.results
        assertTrue(data!!.isNotEmpty())
        testObserver.dispose()
    }

    @Test
    fun `should fetch characters by gender`() = runBlocking {
        val testObserver = TestObserver.create<Response<GetAllCharactersQuery.Data>>()
        val obs = remoteSource.fetchGraphQlCharacters(1, "Male", null)
        obs.subscribe(testObserver)
        testObserver.await().assertComplete().assertNoErrors()
        val data = testObserver.values()[0].data!!.characters!!.results
        assertTrue(data!!.isNotEmpty())
        assertFalse(data.any { it!!.gender != "Male" })
        testObserver.dispose()
    }

    @Test
    fun `should fetch characters by gender and status`() = runBlocking {
        val testObserver = TestObserver.create<Response<GetAllCharactersQuery.Data>>()
        val obs = remoteSource.fetchGraphQlCharacters(1, "Male", "Alive")
        obs.subscribe(testObserver)
        testObserver.await().assertComplete().assertNoErrors()
        val datas = testObserver.values()[0].data!!.characters!!.results
        assertTrue(datas!!.isNotEmpty())
        assertFalse(datas.any { it!!.gender != "Male" })
        assertFalse(datas.any { it!!.status != "Alive" })
        testObserver.dispose()
    }
}