package com.theusmalima.pokedex.ui.details

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theusmalima.pokedex.data.model.api.PokemonFullInfo
import com.theusmalima.pokedex.data.model.domain.*
import com.theusmalima.pokedex.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    val handle: SavedStateHandle,
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private var _pokeInfo = MutableSharedFlow<PokemonFullInfoDomain>()
    val pokeInfo = _pokeInfo.asSharedFlow()

    fun getPokeInfo(id: String) {
        viewModelScope.launch {
            pokemonRepository.getPokemonInfos(id).map {
                it.toDomain()
            }.collect {
                Log.d("teste",it.toString())
                _pokeInfo.emit(it)
            }
        }
    }

}

fun PokemonFullInfo.toDomain(): PokemonFullInfoDomain {
    val mapStats = mutableMapOf<String, Float>()
    this.stats.forEach {
        when (it.stat.name) {
            HP_KEY -> { mapStats[HP_KEY] = it.value.toFloat()/200 }
            ATK_KEY -> { mapStats[ATK_KEY] = it.value.toFloat()/200 }
            DEF_KEY -> { mapStats[DEF_KEY] = it.value.toFloat()/200 }
            SATK_KEY -> { mapStats[SATK_KEY] = it.value.toFloat()/200 }
            SDEF_KEY -> { mapStats[SDEF_KEY] = it.value.toFloat()/200 }
            SPD_KEY -> { mapStats[SPD_KEY] = it.value.toFloat()/200 }
        }
    }
    return PokemonFullInfoDomain(
        name = this.name.replaceFirstChar { it.uppercaseChar() },
        id = this.id,
        weight = this.weight,
        height = this.height,
        sprites = this.sprites,
        stats = mapStats,
        types = this.types
    )
}