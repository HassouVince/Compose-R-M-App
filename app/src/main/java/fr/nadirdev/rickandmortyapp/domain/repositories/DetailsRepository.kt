package fr.nadirdev.rickandmortyapp.domain.repositories

import fr.nadirdev.rickandmortyapp.domain.models.CharacterResult
import fr.nadirdev.rickandmortyapp.domain.utils.Result

interface DetailsRepository {
    suspend fun fetchCharacterDetails(id: Int) : Result<CharacterResult>
}