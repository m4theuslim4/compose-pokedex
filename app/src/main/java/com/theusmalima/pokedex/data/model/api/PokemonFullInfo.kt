package com.theusmalima.pokedex.data.model.api

import com.google.gson.annotations.SerializedName

data class PokemonFullInfo(
    @SerializedName("name")
    override val name: String,
    @SerializedName("id")
    override val id: Long,
    @SerializedName("weight")
    override val weight: Long,
    @SerializedName("height")
    override val height: Long,
    @SerializedName("sprites")
    override val sprites: PokeSprites?,
    @SerializedName("stats")
    val stats: List<StatItem>,
    @SerializedName("types")
    val types: List<Type>
): PokemonBaseInfo()

data class Type(
    @SerializedName("slot")
    val slot: Long,
    @SerializedName("type")
    val typeName: TypeName
)

data class TypeName(
    @SerializedName("name")
    val name: String
)

data class StatItem(
    @SerializedName("base_stat")
    val value: Long,
    @SerializedName("stat")
    val stat: StatName
)

data class StatName(
    @SerializedName("name")
    val name: String
)