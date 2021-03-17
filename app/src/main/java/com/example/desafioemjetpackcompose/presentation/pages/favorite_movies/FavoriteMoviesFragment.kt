package com.example.desafioemjetpackcompose.presentation.pages.favorite_movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.desafioemjetpackcompose.R
import com.example.desafioemjetpackcompose.domain.entities.Movie
import com.example.desafioemjetpackcompose.domain.entities.movies
import com.example.desafioemjetpackcompose.ui.theme.DesafioEmJetpackComposeTheme
import com.example.desafioemjetpackcompose.utils.NetworkImage
import com.example.desafioemjetpackcompose.utils.ProvideImageLoader
import org.koin.android.ext.android.inject
import java.time.format.TextStyle

class FavoriteMoviesFragment : Fragment() {

    private val viewModel: FavoriteMoviesViewModel by inject()

    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                FavoriteMoviesScreenContent()
            }
        }
    }

    @ExperimentalComposeUiApi
    @Composable
    private fun FavoriteMoviesScreenContent() {
        DesafioEmJetpackComposeTheme {
            ProvideImageLoader {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        topBar = {
                            if (true)
                                AppBar()
                            else
                                Surface(
                                    elevation = 8.dp
                                ) {
                                    Text(text = "")
                                }
                        }
                    ) {
                        MovieList(
                            movies = viewModel.movies
                        )
                    }
                }
            }
        }
    }

    @ExperimentalComposeUiApi
    @Composable
    private fun AppBar() {

        val (searching, setSearch) = remember { mutableStateOf(false) }
        val (query, setQuery) = remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current

        if (!searching)
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.favorite_movies))
                },
                backgroundColor = MaterialTheme.colors.primarySurface,
                navigationIcon = {
                    IconButton(onClick = { findNavController().navigateUp() }) {
                        Icon(
                            imageVector = Icons.Sharp.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.padding(horizontal = 12.dp),
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        setSearch(true)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onPrimary,
                        )
                    }
                }
            )
        else
            Surface(
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                    )
                    TextField(
                        modifier = Modifier.focusModifier(),
                        value = query,
                        onValueChange = { value ->
                            setQuery(value)
                            viewModel.fetchFavoriteMovies(value)
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            focusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            placeholderColor = MaterialTheme.colors.onPrimary,
                            cursorColor = MaterialTheme.colors.onPrimary,
                        ),
                        placeholder = { Text("Pesquisar") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                keyboardController?.hideSoftwareKeyboard()
                            }
                        )
                    )
                    IconButton(
                        onClick = {
                            setQuery("")
                            setSearch(false)
                            viewModel.fetchFavoriteMovies("")
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = null,
                        )
                    }
                }
            }
    }

    @Composable
    private fun MovieList(
        movies: List<Movie>,
        modifier: Modifier = Modifier
    ) {
        if (movies.isNotEmpty()) {
            LazyColumn(modifier = modifier) {
                itemsIndexed(movies) { _, movie ->
                    MovieItem(
                        movie = movie,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        } else {
            Box(
                Modifier.fillMaxSize(),
                Alignment.Center
            ) {
                Text(
                    text = "Nenhum filme favorito encontrado.",
                    style = MaterialTheme.typography.h6,
                )
            }
        }
    }

    @Composable
    private fun MovieItem(
        movie: Movie,
        modifier: Modifier = Modifier
    ) {
        Card(
            modifier = modifier.clickable {
                val action = FavoriteMoviesFragmentDirections.actionSeeMovieDetail(
                    movie
                )
                findNavController().navigate(action)
            },
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                NetworkImage(
                    modifier = Modifier
                        .weight(3f)
                        .height(225.dp),
                    url = movie.getPosterCompleteUrl(),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
                Column(
                    modifier = Modifier
                        .weight(5f)
                        .padding(start = 8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            movie.title,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(6f),
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = movie.releaseDate.toString(),
                            maxLines = 1,
                            modifier = Modifier.weight(4f),
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.secondary
                        )
                    }
                    Text(
                        text = movie.overview,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 8,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchFavoriteMovies()
    }

    @ExperimentalComposeUiApi
    @Preview
    @Composable
    fun AppBarPreview() {
        AppBar()
    }

    @Preview
    @Composable
    fun PreviewMovieList() {
        MovieList(movies = movies)
    }

    @Preview
    @Composable
    fun PreviewEmptyMovieList() {
        MovieList(movies = listOf())
    }

    @Preview(widthDp = 360, heightDp = 480)
    @Composable
    fun PreviewMovieItem() {
        MovieItem(movie = movies.first())
    }
}