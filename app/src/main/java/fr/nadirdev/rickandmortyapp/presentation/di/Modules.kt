package fr.nadirdev.rickandmortyapp.presentation.di

import android.content.Context
import android.content.SharedPreferences
import com.apollographql.apollo.ApolloClient
import fr.nadirdev.rickandmortyapp.data.database.AppDatabase
import fr.nadirdev.rickandmortyapp.data.database.CharacterDao
import fr.nadirdev.rickandmortyapp.data.datasource.characters.CharacterLocalDataSource
import fr.nadirdev.rickandmortyapp.data.datasource.characters.CharacterLocalDataSourceImpl
import fr.nadirdev.rickandmortyapp.data.datasource.characters.CharacterRemoteDataSource
import fr.nadirdev.rickandmortyapp.data.datasource.characters.CharactersRemoteDataSourceImpl
import fr.nadirdev.rickandmortyapp.data.datasource.details.DetailsRemoteDataSource
import fr.nadirdev.rickandmortyapp.data.datasource.details.DetailsRemoteDataSourceImpl
import fr.nadirdev.rickandmortyapp.data.net.ApolloService
import fr.nadirdev.rickandmortyapp.data.net.mappers.DetailsMapper
import fr.nadirdev.rickandmortyapp.data.repositories.CharactersRepositoryImpl
import fr.nadirdev.rickandmortyapp.data.repositories.DetailsRepositoryImpl
import fr.nadirdev.rickandmortyapp.domain.repositories.CharactersRepository
import fr.nadirdev.rickandmortyapp.domain.repositories.DetailsRepository
import fr.nadirdev.rickandmortyapp.domain.usescases.GetCharacterDetails
import fr.nadirdev.rickandmortyapp.domain.usescases.GetCharacters
import fr.nadirdev.rickandmortyapp.presentation.viewmodels.CharactersViewModel
import fr.nadirdev.rickandmortyapp.presentation.viewmodels.DetailsViewModel
import org.koin.android.ext.koin.androidContext
import fr.nadirdev.rickandmortyapp.data.database.mappers.CharactersMapper as DbCharacterMapper
import fr.nadirdev.rickandmortyapp.data.net.mappers.CharactersMapper as NetCharacterMapper
import org.koin.dsl.module.module

val applicationModule = module(override = true) {
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            androidContext().packageName,
            Context.MODE_PRIVATE
        )
    }
    single<ApolloClient>{ ApolloService.INSTANCE.getInstance() }
    factory { CharactersViewModel(get()) }
    factory { DetailsViewModel(get()) }
}

val databaseModule = module(override = true) {
    single<AppDatabase> { AppDatabase.INSTANCE.getInstance(androidContext()) }
    single<CharacterDao> { get<AppDatabase>().characterDao() }
}

val repositoryModule = module(override = true) {
    single<DetailsMapper> { DetailsMapper() }
    single<DbCharacterMapper> { DbCharacterMapper() }
    single<NetCharacterMapper> { NetCharacterMapper() }

    single<CharacterLocalDataSource> { CharacterLocalDataSourceImpl(get(), get()) }
    single<CharacterRemoteDataSource> { CharactersRemoteDataSourceImpl(get()) }
    single<DetailsRemoteDataSource> { DetailsRemoteDataSourceImpl(get()) }

    single<CharactersRepository> { CharactersRepositoryImpl(get(), get()) }
    single<DetailsRepository> { DetailsRepositoryImpl(get()) }
}

val useCaseModule = module(override = true) {
    single<GetCharacters> { GetCharacters(get()) }
    single<GetCharacterDetails> { GetCharacterDetails(get()) }
}