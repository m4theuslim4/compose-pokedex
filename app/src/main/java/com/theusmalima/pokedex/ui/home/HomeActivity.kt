package com.theusmalima.pokedex.ui.home

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.ImageLoader
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
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
            PokemonItem(pokemon = pokemons)
        }
    }

    lazyPokemons.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                Log.d("teste", "refresh LoadState.Loading")
                showLoading(isLoading = true)
            }
            loadState.refresh is LoadState.NotLoading -> {
                showLoading(isLoading = false)
            }
            loadState.append is LoadState.Loading -> {
                Log.d("teste", "append LoadState.Loading")
            }
            loadState.append is LoadState.NotLoading -> {
                Log.d("teste", "append LoadState.NotLoading")
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PokemonItem(pokemon: PokemonInfo?) {

    Card(
        elevation = 10.dp, modifier = Modifier
            .padding(all = 8.dp)
            .height(80.dp)
    ) {
        val modifier = Modifier
            .padding(all = 4.dp)
            .fillMaxWidth()
        Row(modifier = modifier) {
            Image(
                painter = rememberImagePainter(pokemon!!.pokeSprites?.urlImage),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                val pokename = pokemon.name.replaceFirstChar { it.uppercaseChar() }
                Text(
                    text = "${pokemon.id} $pokename",
                    style = MaterialTheme.typography.subtitle2,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentSize(Alignment.BottomCenter)) {
                    Text(text = "Height: ${pokemon.height}", color = Color.Gray, fontSize = 12.sp)
                    Text(text = "Weight: ${pokemon.weight}", color = Color.Gray, fontSize = 12.sp)
                }

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.CenterEnd)
            ) {
                val imageLoader = ImageLoader.Builder(LocalContext.current)
                    .componentRegistry {
                        add(SvgDecoder(LocalContext.current))
                    }
                    .build()

                CompositionLocalProvider(LocalImageLoader provides imageLoader) {
                    val painter =
                        rememberImagePainter(pokemon.pokeSprites?.other?.vectorImage?.url)
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(130.dp)
                            .alpha(.5f)
                            .drawWithCache {
                                val gradient = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.LightGray,
                                        Color.DarkGray
                                    ),
                                )
                                onDrawWithContent {
                                    drawContent()
                                    drawRect(gradient, blendMode = BlendMode.Multiply)
                                }
                            },
                        alignment = Alignment.CenterEnd,
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun showLoading(isLoading: Boolean) {
    AnimatedVisibility(visible = isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }
}