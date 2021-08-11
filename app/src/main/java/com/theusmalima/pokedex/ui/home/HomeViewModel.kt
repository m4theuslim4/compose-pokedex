package com.theusmalima.pokedex.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.theusmalima.pokedex.data.model.api.PokemonSimpleInfo
import com.theusmalima.pokedex.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val handle: SavedStateHandle,
    private val pokemonRepository: PokemonRepository
): ViewModel() {

    fun getPokemons(): Flow<PagingData<PokemonSimpleInfo>> {
        return pokemonRepository.getPokemonList().cachedIn(viewModelScope)
    }
}