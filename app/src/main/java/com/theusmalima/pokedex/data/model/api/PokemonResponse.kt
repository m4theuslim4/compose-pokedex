package com.theusmalima.pokedex.data.model.api

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("next")
    val next: String?,

    @SerializedName("previous")
    val previous: String?,

    @SerializedName("results")
    val pokemons: List<Pokemon>
)

data class Pokemon(
    @SerializedName("url")
    val url: String
)