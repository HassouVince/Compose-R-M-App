package fr.nadirdev.rickandmortyapp.data.net.mappers

import fr.nadirdev.rickandmortyapp.GetAllCharactersQuery
import fr.nadirdev.rickandmortyapp.domain.models.CharacterResult
import fr.nadirdev.rickandmortyapp.domain.models.Characters
import fr.nadirdev.rickandmortyapp.domain.models.Info

object CharactersMapper {

    operator fun invoke() = this

    fun toModel(entity : GetAllCharactersQuery.Data) : Characters {
        val info = entity.characters?.info
        val results = entity.characters?.results
        return Characters(
            Info(
                next = info?.next
            ),
            convertResultEntitiesToModelList(results)
        )
    }

    private fun convertResultEntitiesToModelList(results : List<GetAllCharactersQuery.Result?>?)
            : List<CharacterResult>{
        return results!!.map{convertResultEntityToModel(it!!)}
    }

    private fun convertResultEntityToModel(result : GetAllCharactersQuery.Result) : CharacterResult {
        return CharacterResult(result.id!!.toInt(),
            result.name!!,
            result.image!!,
            result.gender,
            result.status
        )
    }
}