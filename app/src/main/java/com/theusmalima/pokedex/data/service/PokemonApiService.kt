package com.theusmalima.pokedex.data.service

import com.theusmalima.pokedex.data.model.PokemonInfo
import com.theusmalima.pokedex.data.model.PokemonResponse
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
    suspend fun getPokemonData(@Url index: String): PokemonInfo
}