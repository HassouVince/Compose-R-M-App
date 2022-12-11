package fr.nadirdev.rickandmortyapp.data.datasource.details

import fr.nadirdev.rickandmortyapp.domain.models.CharacterResult

interface DetailsRemoteDataSource {
    suspend fun fetchCharacter(id: Int): CharacterResult
}