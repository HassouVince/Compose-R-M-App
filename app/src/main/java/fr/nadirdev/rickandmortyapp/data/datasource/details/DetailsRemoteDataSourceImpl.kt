package fr.nadirdev.rickandmortyapp.data.datasource.details

import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.ApolloClient
import fr.nadirdev.rickandmortyapp.GetCharacterDetailsQuery
import fr.nadirdev.rickandmortyapp.data.net.mappers.DetailsMapper
import fr.nadirdev.rickandmortyapp.domain.models.CharacterResult
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class DetailsRemoteDataSourceImpl(private val mapper: DetailsMapper) :
    DetailsRemoteDataSource,
    KoinComponent {

    private val client: ApolloClient by inject()

    override suspend fun fetchCharacter(id: Int): CharacterResult =
        try {
            client.query(GetCharacterDetailsQuery(id.toString()))
                .await()
                .data
                ?.let {
                    mapper.toModel(it)
                } ?: throw Exception("Une erreur est survenue")
        } catch (e: Exception) {
            throw e
        }
}