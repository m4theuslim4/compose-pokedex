package com.theusmalima.pokedex.data.model.api

import com.google.gson.annotations.SerializedName

data class PokemonSimpleInfo(
    @SerializedName("name")
    override val name: String,
    @SerializedName("id")
    override val id: Long,
    @SerializedName("weight")
    override val weight: Long,
    @SerializedName("height")
    override val height: Long,
    @SerializedName("sprites")
    override val sprites: PokeSprites?
): PokemonBaseInfo()