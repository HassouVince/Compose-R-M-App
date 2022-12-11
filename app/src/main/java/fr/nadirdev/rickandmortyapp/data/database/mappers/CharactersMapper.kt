package fr.nadirdev.rickandmortyapp.data.database.mappers

import fr.nadirdev.rickandmortyapp.data.database.entities.CharacterLocalEntity
import fr.nadirdev.rickandmortyapp.domain.models.CharacterResult

object CharactersMapper {

    operator fun invoke() = this

    fun toEntityList(models: List<CharacterResult>): List<CharacterLocalEntity> {
        return models.map { toEntity(it) }
    }

    fun toModelList(models: List<CharacterLocalEntity>): List<CharacterResult> {
        return models.map { toModel(it) }
    }

    private fun toEntity(model: CharacterResult): CharacterLocalEntity =
        CharacterLocalEntity(
            id = model.id ?: 0,
            name = model.name,
            image = model.image,
            gender = model.gender.orEmpty(),
            status = model.status.orEmpty()
        )

    private fun toModel(entity: CharacterLocalEntity): CharacterResult {
        return CharacterResult(
            id = entity.id,
            name = entity.name,
            image = entity.image,
            gender = entity.gender,
            status = entity.status
        )
    }
}