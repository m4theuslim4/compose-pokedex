package com.theusmalima.pokedex.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.theusmalima.pokedex.data.model.PokemonInfo
import com.theusmalima.pokedex.data.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val repo = PokemonRepository()

    fun getPokemons(): Flow<PagingData<PokemonInfo>> {
        return repo.getPokemons().cachedIn(viewModelScope)
    }
}