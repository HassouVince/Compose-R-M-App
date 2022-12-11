package fr.nadirdev.rickandmortyapp.presentation.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import fr.nadirdev.rickandmortyapp.R
import fr.nadirdev.rickandmortyapp.domain.models.CharacterResult
import fr.nadirdev.rickandmortyapp.presentation.viewmodels.DetailsViewModel
import fr.nadirdev.rickandmortyapp.domain.utils.Result
import fr.nadirdev.rickandmortyapp.presentation.ui.views.VerticalSpace
import fr.nadirdev.rickandmortyapp.presentation.ui.views.details.EpisodeItem
import fr.nadirdev.rickandmortyapp.presentation.ui.views.details.GeneralInfos
import fr.nadirdev.rickandmortyapp.presentation.ui.views.details.PlaceInfos
import fr.nadirdev.rickandmortyapp.presentation.utils.isInternetAvailable

const val UNSPECIFIED = "Unspecified"
private val toolbarTitle = mutableStateOf("")

@ExperimentalCoilApi
@Composable
fun DetailsScreen(
    navController: NavController,
    viewModel: DetailsViewModel,
    id: Int
) {
    if (isInternetAvailable(LocalContext.current)) {
        toolbarTitle.value = stringResource(id = R.string.app_name)
        Column {
            DetailsAppBar(navController)
            ObserveDetailsViewModel(viewModel)
        }
        viewModel.getCharacter(id)
    } else
        showError("Veuillez verifier votre connection a Internet")
}

@ExperimentalCoilApi
@Composable
fun ObserveDetailsViewModel(viewModel: DetailsViewModel) {
    val characterState by viewModel.character.observeAsState(Result.Loading)
    when (characterState) {
        is Result.Success -> {
            ShowDetails(character = (characterState as Result.Success).data)
        }
        is Result.Error -> showError(msg = (characterState as Result.Error).exception.toString())
        else -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
                    .size(35.dp)
                    .wrapContentSize(Alignment.Center)
            )
        }
    }
}

@Composable
fun DetailsAppBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(text = toolbarTitle.value)
        },
        navigationIcon = if (navController.previousBackStackEntry != null) {
            {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        } else {
            null
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        elevation = 10.dp
    )
}

@ExperimentalCoilApi
@Composable
fun ShowDetails(character: CharacterResult) {
    toolbarTitle.value = character.name
    LazyColumn(
        Modifier
            .fillMaxWidth()
    ) {
        item {
            Column {
                Image(
                    modifier = Modifier//.weight(8f)
                        .height(300.dp)
                        .fillMaxWidth(),
                    painter = rememberImagePainter(character.image),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null
                )
                Column(
                    Modifier
                        .padding(all = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = character.name,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    VerticalSpace()
                    Text(
                        text = character.species ?: UNSPECIFIED,
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Center
                    )
                    VerticalSpace(15.dp)

                    GeneralInfos(character = character)

                    character.origin?.let {
                        VerticalSpace()
                        PlaceInfos(
                            place = character.origin,
                            title = "Origin",
                            iconRes = R.drawable.ic_flag
                        )
                    }
                    character.location?.let {
                        VerticalSpace()
                        PlaceInfos(
                            place = character.location,
                            title = "Location",
                            iconRes = R.drawable.ic_location
                        )
                    }

                    character.episodes?.let {
                        VerticalSpace(10.dp)
                        Text(
                            text = "Episodes",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        character.episodes?.let {
            items(character.episodes) { ep ->
                EpisodeItem(ep)
            }
        }
    }
}