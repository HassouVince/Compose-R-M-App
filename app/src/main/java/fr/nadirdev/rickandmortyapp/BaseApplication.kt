package fr.nadirdev.rickandmortyapp

import android.app.Application
import fr.nadirdev.rickandmortyapp.presentation.di.applicationModule
import fr.nadirdev.rickandmortyapp.presentation.di.databaseModule
import fr.nadirdev.rickandmortyapp.presentation.di.repositoryModule
import fr.nadirdev.rickandmortyapp.presentation.di.useCaseModule
import org.koin.android.ext.android.startKoin

class BaseApplication : Application() {

    private val modules = listOf(
        applicationModule,
        databaseModule,
        repositoryModule,
        useCaseModule
    )

    override fun onCreate() {
        initKoin()
        super.onCreate()
    }

    private fun initKoin(){
        startKoin(this, modules)
    }
}