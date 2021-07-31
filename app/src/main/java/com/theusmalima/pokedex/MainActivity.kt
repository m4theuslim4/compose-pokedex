package com.theusmalima.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.theusmalima.pokedex.data.model.PokemonInfo
import com.theusmalima.pokedex.ui.home.HomeViewModel
import com.theusmalima.pokedex.ui.theme.PokedexTheme
import kotlinx.coroutines.flow.Flow

class MainActivity : ComponentActivity() {

    val viewModel = HomeViewModel()
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