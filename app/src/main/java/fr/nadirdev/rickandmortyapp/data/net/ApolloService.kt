package fr.nadirdev.rickandmortyapp.data.net

import com.apollographql.apollo.ApolloClient

abstract class ApolloService {
    object INSTANCE{
        fun getInstance(): ApolloClient = ApolloClient.builder()
            .serverUrl(API_URL)
            .build()
        private const val API_URL = "https://rickandmortyapi.com/graphql"
    }
}