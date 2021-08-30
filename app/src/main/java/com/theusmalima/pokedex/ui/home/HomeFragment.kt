package com.theusmalima.pokedex.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.ImageLoader
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import com.theusmalima.pokedex.R
import com.theusmalima.pokedex.data.model.api.PokemonSimpleInfo
import com.theusmalima.pokedex.ui.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            val navController = findNavController()
            PokedexTheme {
                PokemonList(pokemons = viewModel.getPokemons(), navController)
            }
        }
    }
}

@Composable
fun PokemonList(pokemons: Flow<PagingData<PokemonSimpleInfo>>, navController: NavController) {
    val lazyPokemons: LazyPagingItems<PokemonSimpleInfo> = pokemons.collectAsLazyPagingItems()
    LazyColumn {
        items(lazyPokemons) { pokemons ->
            PokemonItem(pokemon = pokemons, navController)
        }

        lazyPokemons.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                }
                loadState.append is LoadState.Loading -> {
                    Log.d("teste", "append LoadState.Loading")
                    item { LoadingItem() }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = lazyPokemons.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            modifier = Modifier.fillParentMaxSize(),
                            onClickRetry = { retry() }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = lazyPokemons.loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            onClickRetry = { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorItem(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            maxLines = 1,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.h6,
            color = Color.Red
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = "Try again")
        }
    }
}

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@OptIn(ExperimentalAnimationApi::class, androidx.compose.material.ExperimentalMaterialApi::class)
@Composable
fun PokemonItem(pokemon: PokemonSimpleInfo?, navController: NavController) {

    Card(
        elevation = 10.dp, modifier = Modifier
            .padding(all = 8.dp)
            .height(80.dp),
        onClick = {
            val bundle = bundleOf("pokeId" to pokemon?.id.toString())
            navController.navigate(R.id.action_home_fragment_to_details_fragment, bundle)
        }
    ) {
        val modifier = Modifier
            .padding(all = 4.dp)
            .fillMaxWidth()
        Row(modifier = modifier) {
            Image(
                painter = rememberImagePainter(pokemon!!.sprites?.bitImage),
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
                    text = "#${pokemon.id} $pokename",
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
                    .crossfade(true)
                    .componentRegistry {
                        add(SvgDecoder(LocalContext.current))
                    }
                    .build()

                    CompositionLocalProvider(LocalImageLoader provides imageLoader) {
                        val painter =
                            rememberImagePainter(pokemon.sprites?.other?.vectorImage?.url)
                        Image(
                            painter = painter,
                            contentDescription = null,
                            modifier = Modifier
                                .width(160.dp)
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