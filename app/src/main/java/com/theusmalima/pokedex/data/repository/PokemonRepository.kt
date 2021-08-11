package com.theusmalima.pokedex.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.theusmalima.pokedex.data.model.api.PokemonFullInfo
import com.theusmalima.pokedex.data.model.api.PokemonSimpleInfo
import com.theusmalima.pokedex.data.service.PokemonApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val service: PokemonApiService
) {

    fun getPokemonList(): Flow<PagingData<PokemonSimpleInfo>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { PokemonDataSource(service) }
        ).flow
    }

    fun getPokemonInfos(id: String): Flow<PokemonFullInfo> {
        return flow {
            val result = service.getPokemonFullData("pokemon/$id")
            emit(result)
        }
    }
}