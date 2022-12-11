package fr.nadirdev.rickandmortyapp.presentation.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import fr.nadirdev.rickandmortyapp.domain.models.CharacterResult
import fr.nadirdev.rickandmortyapp.presentation.utils.ScreensRoutes
import fr.nadirdev.rickandmortyapp.presentation.viewmodels.CharactersViewModel
import fr.nadirdev.rickandmortyapp.domain.utils.Result
import fr.nadirdev.rickandmortyapp.presentation.ui.views.characters.CharacterCardTemplate
import fr.nadirdev.rickandmortyapp.presentation.ui.views.characters.CharactersDropDown
import fr.nadirdev.rickandmortyapp.presentation.utils.isInternetAvailable

val characters = mutableStateOf<List<CharacterResult>>(listOf())

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun CharactersScreen(
    viewModel: CharactersViewModel,
    navController: NavController
) {
    Column {
        InitFilters(viewModel = viewModel)
        DisplayCharacters(navController)
        ShowCard(null, navController)
        ObserveCharactersViewModel(viewModel = viewModel)
        if (characters.value.isEmpty())
            viewModel.getCharacters(
                isInternetAvailable(LocalContext.current)
            )
    }
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun ObserveCharactersViewModel(viewModel: CharactersViewModel) {
    val charactersState by viewModel.characters.observeAsState(Result.Loading)
    when (charactersState) {
        is Result.Success -> {
            (charactersState as Result.Success).data.let {
                characters.value = it
            }
        }
        is Result.Error -> showError(
            msg = (charactersState as Result.Error)
                .exception.toString()
        )
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
fun InitFilters(viewModel: CharactersViewModel) {
    Row {
        val modifier = Modifier
            .fillMaxWidth()
            .weight(5f)
            .padding(all = 3.dp)
        Box(modifier, contentAlignment = Alignment.Center) {
            CharactersDropDown(viewModel, viewModel.genderState, viewModel.genders)
        }
        Box(modifier, contentAlignment = Alignment.Center) {
            CharactersDropDown(viewModel, viewModel.statusState, viewModel.statusList)
        }
    }
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun DisplayCharacters(navController: NavController? = null) {
    if (characters.value.isNotEmpty()) {
        Box(Modifier.padding(2.dp, 5.dp)) {
            LazyVerticalGrid(
                cells = GridCells.Adaptive(minSize = 100.dp)
            ) {
                items(characters.value) { character ->
                    ShowCard(character, navController)
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun ShowCard(character: CharacterResult?, navController: NavController? = null) {
    character?.let {
        Box(Modifier.padding(all = 3.dp)) {
            Card(
                shape = RoundedCornerShape(12.dp),
                backgroundColor = MaterialTheme.colors.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .clickable {
                            navController!!
                                .navigate(ScreensRoutes.DETAILS.route + "/${character.id}")
                        }
                ) {
                    CharacterCardTemplate(character = character)
                }
            }
        }
    }
}