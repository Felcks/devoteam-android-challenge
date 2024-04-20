package com.example.desafioemjetpackcompose.movies.ui.pages.movie_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.twotone.Star
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.desafioemjetpackcompose.movies.domain.models.Genre
import com.example.desafioemjetpackcompose.movies.domain.models.Movie
import com.example.desafioemjetpackcompose.movies.ui.models.movieUIModels
import com.example.desafioemjetpackcompose.core.theme.DesafioEmJetpackComposeTheme
import com.example.desafioemjetpackcompose.core.utils.NetworkImage
import com.example.desafioemjetpackcompose.core.utils.ProvideImageLoader
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MovieDetailFragment : Fragment() {

    private val viewModel: MovieDetailViewModel by inject {
        parametersOf(
            MovieDetailFragmentArgs.fromBundle(requireArguments()).movie
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                DesafioEmJetpackComposeTheme {
                    ProvideImageLoader {
                        Scaffold(topBar = { AppBar() }) { paddingValues ->
                            MovieDetailScreen(viewModel.genres, modifier = Modifier.padding(paddingValues))
                        }
                    }
                }

            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AppBar() {
        TopAppBar(
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
            title = {
                Text(text = viewModel.mMovie.title)
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer, titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer),
            actions = {
                IconButton(onClick = { viewModel.favorOrDisfavorMovie() }) {
                    Icon(
                        imageVector = if (viewModel.mMovie.isFavorite)
                            Icons.Outlined.Star
                        else
                            Icons.TwoTone.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        )
    }

    @Composable
    fun MovieDetailScreen(genres: List<Genre>, modifier: Modifier = Modifier) {
        val scrollState = rememberScrollState()

        Column(modifier = modifier.fillMaxSize()) {
            BoxWithConstraints(modifier = Modifier.weight(1f)) {
                Surface {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        MovieHeader(
                            scrollState = scrollState,
                            data = viewModel.mMovie,
                            containerHeight = this@BoxWithConstraints.maxHeight
                        )
                        MovieInfo(data = viewModel.mMovie, genres = genres)
                    }
                }
            }
        }
    }

    @Composable
    fun MovieHeader(
        scrollState: ScrollState,
        data: Movie?,
        containerHeight: Dp,
    ) {
        val offset = (scrollState.value / 2)
        val offsetDp = with(LocalDensity.current) { offset.toDp() }

        data?.getBackdropCompleteUrl()?.let {
            NetworkImage(
                it,
                modifier = Modifier
                    .heightIn(max = containerHeight / 2)
                    .fillMaxWidth()
                    .padding(top = offsetDp),
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
            )
        }
    }

    @Composable
    fun MovieInfo(
        data: Movie?,
        genres: List<Genre>
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = data?.title ?: "",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
            Text(
                text = data?.releaseDate.toString(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = data?.getGenresText(genres) ?: "",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
            Text(
                text = data?.overview ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

    }

    @Preview(widthDp = 640, heightDp = 360)
    @Composable
    fun PreviewLandscapeMode() {
        DesafioEmJetpackComposeTheme {
            ProvideImageLoader {
                Scaffold(topBar = { AppBar() }) { paddingValues ->
                    MovieDetailScreen(listOf(), Modifier.padding(paddingValues))
                }
            }
        }
    }

    @Preview(widthDp = 360, heightDp = 480)
    @Composable
    fun PreviewPortraitMode() {
        DesafioEmJetpackComposeTheme {
            ProvideImageLoader {
                Scaffold(topBar = { AppBar() }) { paddingValues ->
                    MovieDetailScreen(listOf(),  Modifier.padding(paddingValues))
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewMovieInfo() {
        DesafioEmJetpackComposeTheme {
            MovieInfo(data = movieUIModels.first(), genres = listOf())
        }
    }
}