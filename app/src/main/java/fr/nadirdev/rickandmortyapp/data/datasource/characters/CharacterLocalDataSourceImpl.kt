package fr.nadirdev.rickandmortyapp.data.datasource.characters

import fr.nadirdev.rickandmortyapp.data.database.CharacterDao
import fr.nadirdev.rickandmortyapp.data.database.mappers.CharactersMapper
import fr.nadirdev.rickandmortyapp.domain.models.CharacterResult
import fr.nadirdev.rickandmortyapp.domain.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class CharacterLocalDataSourceImpl(
    private val dao: CharacterDao,
    private val mapper: CharactersMapper
) : CharacterLocalDataSource {

    override suspend fun getAll(gender: String?, status: String?)
            : Result<List<CharacterResult>> =
        withContext(Dispatchers.IO) {
            try {
                Result.Success(
                    mapper.toModelList(
                        dao.getCharacters(gender, status)
                    )
                )
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun postAll(models: List<CharacterResult>): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                Result.Success(
                    dao.postAll(
                        mapper.toEntityList(models)
                    )
                )
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
}