package com.theusmalima.pokedex.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.theusmalima.pokedex.data.model.PokemonInfo
import com.theusmalima.pokedex.data.service.PokemonApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val service: PokemonApiService
) {

    val teste = "ddddd".substringAfter("offset=").substringBefore("&")

    fun getPokemons(): Flow<PagingData<PokemonInfo>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { PokemonDataSource(service) }
        ).flow
    }
}