package com.theusmalima.pokedex.data.model

import com.google.gson.annotations.SerializedName

data class PokemonInfo(
    @SerializedName("height")
    val height: Long,

    @SerializedName("weight")
    val weight: Long,

    @SerializedName("name")
    val name: String
)