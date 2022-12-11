package fr.nadirdev.rickandmortyapp.domain.usescases

import fr.nadirdev.rickandmortyapp.domain.repositories.DetailsRepository

class GetCharacterDetails (private val detailsRepository: DetailsRepository) {
    suspend operator fun invoke(id: Int) = detailsRepository.fetchCharacterDetails(id)
}