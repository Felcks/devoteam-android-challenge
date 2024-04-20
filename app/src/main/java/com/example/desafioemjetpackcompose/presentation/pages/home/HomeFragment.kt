package com.example.desafioemjetpackcompose.presentation.pages.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
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
                Surface(color = MaterialTheme.colorScheme.background) {
                    Scaffold(
                        topBar = { AppBar() }
                    ) { padding ->
                        MovieList(
                            movieList = viewModel.realMovies,
                            onFavorOrDisfavor = viewModel::favorOrDisfavorMovie,
                            modifier = Modifier.padding(padding)
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AppBar() {
        TopAppBar(
            title = {
                Text(text = stringResource(string.app_name))
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer, titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer),
            actions = {
                IconButton(onClick = {
                    val action = HomeFragmentDirections.actionSeeFavoriteMovies()
                    findNavController().navigate(action)
                }) {
                    Icon(
                        painter = painterResource(com.example.desafioemjetpackcompose.R.drawable.ic_bookmark), //Icons.Filled.Bookmark
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        )
    }

    @ExperimentalFoundationApi
    @Composable
    fun MovieList(
        movieList: List<Movie>,
        modifier: Modifier = Modifier,
        onFavorOrDisfavor: (Movie) -> Unit
    ) {
        if (movieList.isNotEmpty())
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
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
            elevation = CardDefaults.cardElevation(4.dp)
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
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
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
                        style = MaterialTheme.typography.titleMedium,
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
                                Icons.Filled.Star
                            else
                                Icons.TwoTone.Star,
                            tint = MaterialTheme.colorScheme.primary,
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

    @Preview
    @Composable
    fun AppBarPreview() {
        DesafioEmJetpackComposeTheme {
            AppBar()
        }
    }

    @ExperimentalFoundationApi
    @Preview
    @Composable
    fun MovieListPreview() {
        DesafioEmJetpackComposeTheme {
            MovieList(movieList = movies, onFavorOrDisfavor = {})
        }
    }

    @Preview(widthDp = 320, heightDp = 480)
    @Composable
    fun MovieItemPreview() {
        DesafioEmJetpackComposeTheme {
            MovieItem(movie = movies.first(), onFavorOrDisfavor = {})
        }
    }

    @Preview(widthDp = 320, heightDp = 480)
    @Composable
    fun MovieItemFavPreview() {
        DesafioEmJetpackComposeTheme {
            MovieItem(movie = movies.last(), onFavorOrDisfavor = {})
        }
    }
}