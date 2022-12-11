package fr.nadirdev.rickandmortyapp.data.datasource.characters

import fr.nadirdev.rickandmortyapp.domain.models.CharacterResult
import fr.nadirdev.rickandmortyapp.domain.utils.Result

interface CharacterLocalDataSource {
    suspend fun getAll(gender : String?,status : String?)
            : Result<List<CharacterResult>>
    suspend fun postAll(models: List<CharacterResult>) : Result<Unit>
}