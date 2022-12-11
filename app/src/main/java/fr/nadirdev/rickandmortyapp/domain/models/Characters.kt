package fr.nadirdev.rickandmortyapp.domain.models


class Characters(val info : Info?, val results : List<CharacterResult>)

class Info(val next : Int?)

data class CharacterResult(val id : Int? = null,
                            override val name : String,
                            val image: String,
                            val gender: String? = null,
                            val status: String? = null,
                            val type : String? = null,
                            val species : String? = null,
                            val origin: Place? = null,
                            val location: Place? = null,
                            val episodes : List<Episode>? = null
) : Item(name)

data class Place(override val name : String,
                 val dimension : String? = null,
                 val type : String? = null
                 ) : Item(name)

data class Episode(override val name : String) : Item(name)

abstract class Item(open val name: String)