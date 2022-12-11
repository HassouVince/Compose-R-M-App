package fr.nadirdev.rickandmortyapp.data.repositories

import fr.nadirdev.rickandmortyapp.data.datasource.details.DetailsRemoteDataSource
import fr.nadirdev.rickandmortyapp.domain.repositories.DetailsRepository
import fr.nadirdev.rickandmortyapp.domain.utils.Result

class DetailsRepositoryImpl(private val remoteDataSource: DetailsRemoteDataSource)
    : DetailsRepository {

    override suspend fun fetchCharacterDetails(id: Int) = try{
            remoteDataSource.fetchCharacter(id)
                .let { Result.Success(it) }
        }catch (e : Exception){
            Result.Error(e)
        }
}