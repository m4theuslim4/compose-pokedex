package com.theusmalima.pokedex.data.service

import com.theusmalima.pokedex.data.model.api.PokemonFullInfo
import com.theusmalima.pokedex.data.model.api.PokemonSimpleInfo
import com.theusmalima.pokedex.data.model.api.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonApiService {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("pokemon/")
    suspend fun getPokemons(
        @Query("offset") offset: String,
        @Query("limit") limit: String = "20"): PokemonResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET
    suspend fun getPokemonSimpleData(@Url index: String): PokemonSimpleInfo

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET
    suspend fun getPokemonFullData(@Url index: String): PokemonFullInfo
}