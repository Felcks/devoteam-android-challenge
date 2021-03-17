package com.example.desafioemjetpackcompose.presentation.pages.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.desafioemjetpackcompose.R.string
import com.example.desafioemjetpackcompose.domain.entities.Movie
import com.example.desafioemjetpackcompose.domain.entities.movies
import com.example.desafioemjetpackcompose.ui.theme.DesafioEmJetpackComposeTheme
import com.example.desafioemjetpackcompose.utils.NetworkImage
import com.example.desafioemjetpackcompose.utils.ProvideImageLoader
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private val viewModel: HomeFragmentViewModel by inject()

    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ScreenContent()
            }
        }
    }

    @ExperimentalFoundationApi
    @Composable
    private fun ScreenContent() {
        DesafioEmJetpackComposeTheme {
            ProvideImageLoader {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        topBar = { AppBar() }
                    ) {
                        MovieList(
                            movieList = viewModel.realMovies,
                            onFavorOrDisfavor = viewModel::favorOrDisfavorMovie
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun AppBar() {
        TopAppBar(
            title = {
                Text(text = stringResource(string.app_name))
            },
            backgroundColor = MaterialTheme.colors.primarySurface,
            actions = {
                IconButton(onClick = {
                    val action = HomeFragmentDirections.actionSeeFavoriteMovies()
                    findNavController().navigate(action)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Bookmark,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        )
    }

    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!")
    }

    @ExperimentalFoundationApi
    @Composable
    fun MovieList(
        modifier: Modifier = Modifier,
        movieList: List<Movie> = movies,
        onFavorOrDisfavor: (Movie) -> Unit
    ) {
        if (movieList.isNotEmpty())
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                modifier = modifier,
            ) {
                itemsIndexed(movieList) { index, movie ->
                    viewModel.onChangeMovieScrollPosition(index)
                    if ((index + 1) >= (viewModel.currentPage * viewModel.pageSize) && !viewModel.loading) {
                        viewModel.nextPage()
                    }
                    MovieItem(
                        movie = movie,
                        onFavorOrDisfavor = onFavorOrDisfavor,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        else
            Box(
                Modifier.fillMaxSize(),
                Alignment.Center
            ) { CircularProgressIndicator() }
    }

    @Composable
    fun MovieItem(
        movie: Movie,
        onFavorOrDisfavor: (Movie) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Card(
            modifier = modifier,
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val action: NavDirections =
                            HomeFragmentDirections.actionSeeMovieDetail(movie)
                        findNavController().navigate(action)
                    }
            ) {
                NetworkImage(
                    movie.getPosterCompleteUrl(),
                    modifier = Modifier
                        .size(width = 180.dp, height = 280.dp)
                        .clip(shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)),
                    contentScale = ContentScale.None,
                    contentDescription = null,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(IntrinsicSize.Min)
                        .padding(start = 8.dp, end = 4.dp, top = 4.dp, bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.subtitle1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(5f)
                    )
                    IconButton(
                        onClick = {
                            onFavorOrDisfavor.invoke(movie)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = if (movie.isFavorite)
                                Icons.Outlined.Star
                            else
                                Icons.Outlined.StarOutline,
                            tint = MaterialTheme.colors.primary,
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        viewModel.updateMoviesFavoriteStatus()
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        DesafioEmJetpackComposeTheme {
            Greeting("Android")
        }
    }

    @ExperimentalFoundationApi
    @Preview
    @Composable
    fun MovieListPreview() {
        MovieList(onFavorOrDisfavor = {})
    }

    @Preview(widthDp = 320, heightDp = 480)
    @Composable
    fun MovieItemPreview() {
        MovieItem(movie = movies.first(), onFavorOrDisfavor = {})
    }

    @Preview
    @Composable
    fun AppBarPreview() {
        AppBar()
    }
}