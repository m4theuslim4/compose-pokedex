package com.theusmalima.pokedex.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import com.theusmalima.pokedex.data.model.domain.*
import com.theusmalima.pokedex.ui.theme.BottomSheetShape
import com.theusmalima.pokedex.ui.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {

        setContent {
            PokedexTheme {
                val pokeId = arguments?.getString("pokeId")
                viewModel.getPokeInfo(pokeId!!)
                PokemonDetail(
                    pokemonId = pokeId,
                    viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun PokemonDetail(
    pokemonId: String?,
    viewModel: DetailsViewModel
) {
    val pokemonInfo = viewModel.pokeInfo.collectAsState(null)
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxSize()
                    .weight(1f)
            ) {

            }
            Surface(
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxSize()
                    .weight(1f), shape = BottomSheetShape, color = Color.White
            ) {
                Row(verticalAlignment = Alignment.Bottom){
                Column(
                    modifier = Modifier
                        .padding(start = 24.dp, top = 20.dp, end = 24.dp)
                ) {
                    Spacer(modifier = Modifier.height(50.dp))

                    AnimateTeste(max = pokemonInfo.value?.stats?.get(HP_KEY) ?: 0f, "HP", pokemonInfo)
                    Spacer(modifier = Modifier.height(12.dp))

                    AnimateTeste(max = pokemonInfo.value?.stats?.get(ATK_KEY) ?: 0f, "ATK", pokemonInfo)
                    Spacer(modifier = Modifier.height(12.dp))

                    AnimateTeste(max = pokemonInfo.value?.stats?.get(DEF_KEY) ?: 0f, "DEF", pokemonInfo)
                    Spacer(modifier = Modifier.height(12.dp))

                    AnimateTeste(max = pokemonInfo.value?.stats?.get(SATK_KEY) ?: 0f, "SATK", pokemonInfo)
                    Spacer(modifier = Modifier.height(12.dp))

                    AnimateTeste(max = pokemonInfo.value?.stats?.get(SDEF_KEY) ?: 0f, "SDEF", pokemonInfo)
                    Spacer(modifier = Modifier.height(12.dp))

                    AnimateTeste(max = pokemonInfo.value?.stats?.get(SPD_KEY) ?: 0f, "SPD", pokemonInfo)

                }
                }
            }
        }
        val imageLoader = ImageLoader.Builder(LocalContext.current)
            .crossfade(true)
            .componentRegistry {
                add(SvgDecoder(LocalContext.current))
            }
            .build()
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 64.dp)
                .wrapContentSize(Alignment.Center)
        ) {
            CompositionLocalProvider(LocalImageLoader provides imageLoader) {
                val painter =
                    rememberImagePainter("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/$pokemonId.svg")
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.size(200.dp),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Inside,
                )
            }
        }

    }

}

@ExperimentalAnimationApi
@Composable
fun AnimateTeste(max: Float, title: String, state: State<PokemonFullInfoDomain?>) {
    var progress by remember { mutableStateOf(false) }
    val animatedProgress = animateFloatAsState(
        if (!progress) 0f else max,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    ).value

    Column(
        horizontalAlignment = Alignment.Start, modifier = Modifier
            .fillMaxWidth()
    ) {
        Row {
            Text(
                text = title, color = Color.Black, modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(30.dp),
                fontSize = 12.sp
            )
            LinearProgressIndicator(
                progress = animatedProgress,
                modifier = Modifier
                    .padding(8.dp)
                    .height(12.dp)
                    .fillMaxWidth(),
                color = Color.Blue,
                backgroundColor = Color.Gray
            )
            LaunchedEffect(state.value != null) {
                progress = true
            }
        }

    }
}

//@Preview
//@Composable
//fun PreviewPokemon() {
//    PokemonDetail(pokemonId = "")
//}