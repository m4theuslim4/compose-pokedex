package com.theusmalima.pokedex.data.model.api

import com.google.gson.annotations.SerializedName

abstract class PokemonBaseInfo {
    abstract val name: String
    abstract val id: Long
    abstract val weight: Long
    abstract val height: Long
    abstract val sprites: PokeSprites?
}

data class PokeSprites(
    @SerializedName("front_default")
    val bitImage: String?,

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