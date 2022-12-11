package fr.nadirdev.rickandmortyapp.data.net.mappers

import fr.nadirdev.rickandmortyapp.GetCharacterDetailsQuery
import fr.nadirdev.rickandmortyapp.domain.models.*

object DetailsMapper {

    operator fun invoke() = this

    fun toModel(entity : GetCharacterDetailsQuery.Data) : CharacterResult {
        val character = entity.character
        return CharacterResult(
            name = character?.name.orEmpty(),
            gender = character?.gender.orEmpty(),
            status = character?.status.orEmpty(),
            image = character?.image.orEmpty(),
            type = character?.type.orEmpty(),
            species = character?.species.orEmpty(),
            origin = Place(
                character?.origin?.name.orEmpty(),
                character?.origin?.dimension.orEmpty(),
                character?.origin?.type.orEmpty()
            ),
            location = Place(
                character?.location?.name.orEmpty(),
                character?.location?.dimension.orEmpty(),
                character?.location?.type.orEmpty()
            ),
            episodes = character?.episode?.map { Episode(it?.name.orEmpty()) }.orEmpty()
        )
    }
}