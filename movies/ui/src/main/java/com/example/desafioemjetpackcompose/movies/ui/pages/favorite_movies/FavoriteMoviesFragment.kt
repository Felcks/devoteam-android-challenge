package com.example.desafioemjetpackcompose.movies.ui.pages.favorite_movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
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
import com.example.desafioemjetpackcompose.movies.ui.models.MovieUIModel
import com.example.desafioemjetpackcompose.movies.ui.models.movieUIModels
import com.example.desafioemjetpackcompose.core.theme.DesafioEmJetpackComposeTheme
import com.example.desafioemjetpackcompose.core.utils.NetworkImage
import com.example.desafioemjetpackcompose.core.utils.ProvideImageLoader
import org.koin.android.ext.android.inject
import com.example.desafioemjetpackcompose.core.R

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
                Surface(color = MaterialTheme.colorScheme.background) {
                    Scaffold(
                        topBar = {
                            if (true)
                                AppBar()
                            else
                                Surface(
                                    shadowElevation = 8.dp
                                ) {
                                    Text(text = "")
                                }
                        }
                    ) { padding ->
                        MovieList(
                            movies = viewModel.movies,
                            modifier = Modifier.padding(padding)
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
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
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = { findNavController().navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Sharp.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
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
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }
            )
        else
            Surface(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
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
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.primary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                            focusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary,
                            cursorColor = MaterialTheme.colorScheme.onPrimary,
                            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        placeholder = { Text(stringResource(R.string.search)) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                keyboardController?.hide()
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
        movies: List<MovieUIModel>,
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
                    text = stringResource(R.string.empty_favorite_movies),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }

    @Composable
    private fun MovieItem(
        movie: MovieUIModel,
        modifier: Modifier = Modifier
    ) {
        Card(
            modifier = modifier.clickable {
                val action = FavoriteMoviesFragmentDirections.actionSeeMovieDetail(
                    movie
                )
                findNavController().navigate(action)
            },
            colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
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
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            movie.title,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(6f),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = movie.releaseDate.toString(),
                            maxLines = 1,
                            modifier = Modifier.weight(3f).fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Text(
                        text = movie.overview,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 8,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 16.dp, end = 8.dp)
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
        DesafioEmJetpackComposeTheme {
            AppBar()
        }
    }

    @Preview
    @Composable
    fun PreviewMovieList() {
        DesafioEmJetpackComposeTheme {
            MovieList(movies = movieUIModels)
        }
    }

    @Preview()
    @Composable
    fun PreviewEmptyMovieList() {
        DesafioEmJetpackComposeTheme {
            MovieList(movies = listOf())
        }
    }

    @Preview(widthDp = 360, heightDp = 480)
    @Composable
    fun PreviewMovieItem() {
        DesafioEmJetpackComposeTheme {
            MovieItem(movie = movieUIModels.first())
        }
    }
}