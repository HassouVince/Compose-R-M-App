package fr.nadirdev.rickandmortyapp.data.database

import androidx.room.*
import fr.nadirdev.rickandmortyapp.data.database.entities.CharacterLocalEntity

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characters WHERE (:pGender IS NULL OR gender = :pGender) AND (:pStatus IS NULL OR status = :pStatus)")
    fun getCharacters(pGender : String?, pStatus : String?) : List<CharacterLocalEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun post(characterLocalEntity: CharacterLocalEntity) : Long

    @Transaction
    fun postAll(entities: List<CharacterLocalEntity>) = entities.forEach {post(it)}
}