package com.theusmalima.pokedex.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.theusmalima.pokedex.data.model.PokemonInfo
import com.theusmalima.pokedex.data.service.PokemonApiService

class PokemonDataSource(
    private val service: PokemonApiService
) : PagingSource<Int, PokemonInfo>() {

    override fun getRefreshKey(state: PagingState<Int, PokemonInfo>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonInfo> {

        return try {
            val page = params.key ?: 1

            val listPokemons = service.getPokemons()

            val newList = mutableListOf<PokemonInfo>()
            listPokemons.pokemons.forEach { pokemon ->
                val indexOfPoke = pokemon.url.substring(34)
                val pokeInfo = service.getPokemonData("pokemon/$indexOfPoke")
                newList.add(pokeInfo)
            }

            LoadResult.Page(
                data = newList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = null
            )

        } catch (ex: Throwable) {
            LoadResult.Error(ex)
        }
    }
}