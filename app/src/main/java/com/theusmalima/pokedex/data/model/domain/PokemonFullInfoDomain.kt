package com.theusmalima.pokedex.data.model.domain

import com.theusmalima.pokedex.data.model.api.PokeSprites
import com.theusmalima.pokedex.data.model.api.PokemonBaseInfo
import com.theusmalima.pokedex.data.model.api.Type

const val HP_KEY = "hp"
const val ATK_KEY = "attack"
const val DEF_KEY = "defense"
const val SATK_KEY = "special-attack"
const val SDEF_KEY = "special-defense"
const val SPD_KEY = "speed"

data class PokemonFullInfoDomain(
    override val name: String,
    override val id: Long,
    override val weight: Long,
    override val height: Long,
    override val sprites: PokeSprites?,
    val stats: Map<String,Float>,
    val types: List<Type>
): PokemonBaseInfo() {

    fun getMainType(): Type? {
        var type: Type? = null
        types.forEach {
            if(it.slot == 1L) type = it
        }
        return type
    }
}