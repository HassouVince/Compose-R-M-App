package fr.nadirdev.rickandmortyapp

import android.content.Context
import androidx.room.Room
import fr.nadirdev.rickandmortyapp.data.database.AppDatabase
import fr.nadirdev.rickandmortyapp.domain.utils.Result
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
abstract class BaseUnitTest {

    open fun before() {}
    open fun after() {}

    protected lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val context = androidx.test.core.app.ApplicationProvider.getApplicationContext() as Context
        val dbInstance = mock(AppDatabase.INSTANCE::class.java)
        Mockito.`when`(dbInstance.getInstance(context)).thenReturn(
            Room.inMemoryDatabaseBuilder(
                context, AppDatabase::class.java
            ).build()
        )
        db = dbInstance.getInstance(context)
        before()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
        after()
    }

    open fun throwResult(result: Result<Any>) {
        if (result is Result.Error)
            throw result.exception
    }
}