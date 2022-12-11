package fr.nadirdev.rickandmortyapp.data.datasource.characters

import fr.nadirdev.rickandmortyapp.domain.models.CharacterResult

interface CharacterRemoteDataSource {
    fun getCharacters(
        gender: String?,
        status: String?,
        onResult: (List<CharacterResult>) -> Unit
    )
}