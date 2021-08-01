package com.theusmalima.pokedex.ui.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.theusmalima.pokedex.data.model.PokemonInfo
import com.theusmalima.pokedex.ui.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PokedexTheme {
                PokemonList(pokemons = viewModel.getPokemons())
            }
        }
    }
}

@Composable
fun PokemonList(pokemons: Flow<PagingData<PokemonInfo>>) {
    val lazyPokemons: LazyPagingItems<PokemonInfo> = pokemons.collectAsLazyPagingItems()

    LazyColumn {
        items(lazyPokemons) { pokemons ->
            Row {
                Text(text = pokemons!!.name)
            }
        }
    }
}