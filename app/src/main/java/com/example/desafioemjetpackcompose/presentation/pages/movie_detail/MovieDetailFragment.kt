package com.example.desafioemjetpackcompose.presentation.pages.movie_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.desafioemjetpackcompose.R
import com.example.desafioemjetpackcompose.domain.entities.Genre
import com.example.desafioemjetpackcompose.domain.entities.Movie
import com.example.desafioemjetpackcompose.domain.entities.movies
import com.example.desafioemjetpackcompose.utils.NetworkImage
import com.example.desafioemjetpackcompose.utils.ProvideImageLoader
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
                ProvideImageLoader {
                    Scaffold(topBar = { AppBar() }) {
                        MovieDetailScreen(viewModel.genres)
                    }
                }
            }
        }
    }

    @Composable
    private fun AppBar() {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { findNavController().navigateUp() }) {
                    Icon(
                        imageVector = Icons.Sharp.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 12.dp),
                    )
                }
            },
            title = {
                Text(text = viewModel.mMovie.title)
            },
            backgroundColor = MaterialTheme.colors.primarySurface,
            actions = {
                IconButton(onClick = { viewModel.favorOrDisfavorMovie() }) {
                    Icon(
                        imageVector = if (viewModel.mMovie.isFavorite)
                            Icons.Outlined.Star
                        else
                            Icons.Outlined.StarOutline,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary,
                    )
                }
            }
        )
    }

    @Composable
    fun MovieDetailScreen(genres: List<Genre>) {
        val scrollState = rememberScrollState()

        Column(modifier = Modifier.fillMaxSize()) {
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
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
            Text(
                text = data?.releaseDate.toString(),
                style = MaterialTheme.typography.overline,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = data?.getGenresText(genres) ?: "",
                style = MaterialTheme.typography.overline,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
            Text(
                text = data?.overview ?: "",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )
        }

    }

    @Preview(widthDp = 640, heightDp = 360)
    @Composable
    fun PreviewLandscapeMode() {
        ProvideImageLoader {
            Scaffold(topBar = { AppBar() }) {
                MovieDetailScreen(listOf())
            }
        }
    }

    @Preview(widthDp = 360, heightDp = 480)
    @Composable
    fun PreviewPortraitMode() {
        ProvideImageLoader {
            Scaffold(topBar = { AppBar() }) {
                MovieDetailScreen(listOf())
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewMovieInfo() {
        MovieInfo(data = movies.first(), genres = listOf())
    }
}