package com.theusmalima.pokedex.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.theusmalima.pokedex.data.model.PokemonInfo
import com.theusmalima.pokedex.data.model.PokemonResponse
import com.theusmalima.pokedex.data.service.PokemonApiService
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class PokemonRepository {
    private val service = Network.getService()

    fun getPokemons(): Flow<PagingData<PokemonInfo>> {
        Log.d("teste","aqui")
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { PokemonDataSource(service) }
        ).flow
    }
}


object Network {
    fun getService(): PokemonApiService {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonApiService::class.java)
    }
}