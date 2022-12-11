package fr.nadirdev.rickandmortyapp

import fr.nadirdev.rickandmortyapp.data.datasource.details.DetailsRemoteDataSourceImpl
import fr.nadirdev.rickandmortyapp.data.net.mappers.DetailsMapper
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class DetailsUnitTest : BaseUnitTest() {

    private lateinit var remoteSource: DetailsRemoteDataSourceImpl

    override fun before() {
        remoteSource = DetailsRemoteDataSourceImpl(DetailsMapper())
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun getCharacterDetailsFromNet() = runBlocking {
        val result = remoteSource.fetchCharacter(1)
        Assert.assertNotNull(result)
        Assert.assertTrue(result.name.isNotEmpty())
    }
}