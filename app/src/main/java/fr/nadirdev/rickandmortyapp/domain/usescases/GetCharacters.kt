package fr.nadirdev.rickandmortyapp.domain.usescases

import fr.nadirdev.rickandmortyapp.domain.models.CharacterResult
import fr.nadirdev.rickandmortyapp.domain.repositories.CharactersRepository
import fr.nadirdev.rickandmortyapp.domain.utils.Result

class GetCharacters(private val charactersRepository: CharactersRepository) {
    suspend operator fun invoke(
        gender: String?,
        status: String?,
        isInternetAvailable: Boolean,
        onResult: (Result<List<CharacterResult>>) -> Unit
    ) = charactersRepository.getCharacters(gender, status, isInternetAvailable, onResult)
}