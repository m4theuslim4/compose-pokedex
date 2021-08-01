package com.theusmalima.pokedex.data.model

import com.google.gson.annotations.SerializedName

data class PokemonInfo(
    @SerializedName("height")
    val height: Long,

    @SerializedName("weight")
    val weight: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("id")
    val id: Long,

    @SerializedName("sprites")
    val pokeSprites: PokeSprites?
)

data class PokeSprites(
    @SerializedName("front_default")
    val urlImage: String?,

    @SerializedName("other")
    val other: Other
)

data class Other(
    @SerializedName("dream_world")
    val vectorImage: PokeVector?
)

data class PokeVector(
    @SerializedName("front_default")
    val url: String?
)