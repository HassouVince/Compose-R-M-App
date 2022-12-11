package fr.nadirdev.rickandmortyapp.domain.repositories

import fr.nadirdev.rickandmortyapp.domain.models.CharacterResult
import fr.nadirdev.rickandmortyapp.domain.utils.Result

interface CharactersRepository {
    suspend fun getCharacters(
        gender : String? = null,
        status : String? = null,
        isInternetAvailable: Boolean,
        onResult: (Result<List<CharacterResult>>) -> Unit,
    )
}