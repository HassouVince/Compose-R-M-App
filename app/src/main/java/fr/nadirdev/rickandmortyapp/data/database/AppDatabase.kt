package fr.nadirdev.rickandmortyapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.nadirdev.rickandmortyapp.data.database.entities.CharacterLocalEntity

@Database(entities = [CharacterLocalEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao() : CharacterDao

    object INSTANCE{
        fun getInstance(context: Context) = buildDatabase(context)

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "rick_and_morty_app.db")
            .build()
    }
}
