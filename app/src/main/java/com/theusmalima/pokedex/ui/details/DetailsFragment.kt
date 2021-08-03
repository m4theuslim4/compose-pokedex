package com.theusmalima.pokedex.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {

        setContent {
            val pokeId = arguments?.getString("pokeId")
            PokemonDetail(pokemonId = pokeId)
        }
    }
}

@Composable
fun PokemonDetail(
    pokemonId: String?
) {
    Log.d("teste",pokemonId ?: "-1")
}
