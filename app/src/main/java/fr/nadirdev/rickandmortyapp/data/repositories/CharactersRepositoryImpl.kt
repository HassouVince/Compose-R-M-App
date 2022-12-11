package fr.nadirdev.rickandmortyapp.data.repositories

import fr.nadirdev.rickandmortyapp.data.datasource.characters.CharacterLocalDataSource
import fr.nadirdev.rickandmortyapp.data.datasource.characters.CharacterRemoteDataSource
import fr.nadirdev.rickandmortyapp.domain.models.CharacterResult
import fr.nadirdev.rickandmortyapp.domain.utils.Result
import kotlinx.coroutines.*
import fr.nadirdev.rickandmortyapp.domain.repositories.CharactersRepository

class CharactersRepositoryImpl(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val localDataSource: CharacterLocalDataSource
) : CharactersRepository {

    private var stored = false
    private val characters = mutableListOf<CharacterResult>()

    override suspend fun getCharacters(
        gender: String?,
        status: String?,
        isInternetAvailable: Boolean,
        onResult: (Result<List<CharacterResult>>) -> Unit
    ) {
        characters.clear()
        if(isInternetAvailable){
            try {
                remoteDataSource.getCharacters(gender, status) { list ->
                    characters.addAll(list)
                    onResult(Result.Success(characters.toList()))
                    if (gender == null && status == null) // no filter selected
                        storeCharactersOnDb(list)
                }
            } catch (e: Exception) {
                onResult(Result.Error(e))
            }
        }else{
            onResult(localDataSource.getAll(gender, status))
        }


    }

    private fun storeCharactersOnDb(list: List<CharacterResult>) {
        CoroutineScope(Dispatchers.IO).launch {
            if (localDataSource.postAll(list) is Result.Error)
                stored = false
        }
    }
}