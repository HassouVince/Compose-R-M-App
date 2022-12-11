package fr.nadirdev.rickandmortyapp.presentation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import fr.nadirdev.rickandmortyapp.R
import fr.nadirdev.rickandmortyapp.presentation.ui.views.DialogData
import fr.nadirdev.rickandmortyapp.ui.theme.RickAndMortyAppTheme
import fr.nadirdev.rickandmortyapp.presentation.utils.ScreensRoutes
import fr.nadirdev.rickandmortyapp.presentation.viewmodels.CharactersViewModel
import fr.nadirdev.rickandmortyapp.presentation.viewmodels.DetailsViewModel
import org.koin.android.ext.android.inject
import org.koin.standalone.KoinComponent

val dialInfos = mutableStateOf<DialogData?>(null)

@ExperimentalCoilApi
@ExperimentalFoundationApi
class MainActivity : ComponentActivity(), KoinComponent {

    private val charactersViewModel : CharactersViewModel by inject()
    private val detailsViewModel : DetailsViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermissions()
        setContent {
            RickAndMortyAppTheme {
                Surface(color = colorResource(id = R.color.bg),
                    modifier = Modifier.fillMaxHeight()
                ) {
                    InitAlertDialog()
                    InitNavigation(charactersViewModel,detailsViewModel)
                }
            }
        }
    }

    private fun checkPermissions() {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M)
            return
        val permission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            permissionsResultCallback.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    private val permissionsResultCallback = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){
        if(!it){
            dialInfos.value = DialogData("Permission requise","Si vous voulez utiliser" +
                    " l'application en mode hors-connection, vous devez accepter " +
                    "les permissions requises.", "Accepter",
                "Ignorer", onConfirmClick = {checkPermissions()})
        }
    }
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun InitNavigation(charactersViewModel : CharactersViewModel,
                      detailsViewModel : DetailsViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ScreensRoutes.CHARACTERS.route
    ) {
        composable(ScreensRoutes.CHARACTERS.route) {
                CharactersScreen(
                    navController = navController,
                    viewModel = charactersViewModel
                )
        }
        composable(ScreensRoutes.DETAILS.route + "/{id}") {
            DetailsScreen(
                navController = navController,
                viewModel = detailsViewModel,
                it.arguments?.getString("id")!!.toInt()
            )
        }
    }
}

@Composable
fun InitAlertDialog(){
    if(dialInfos.value != null) {
        val infos = dialInfos.value!!
        Box(Modifier.padding(horizontal = 20.dp)) {
            AlertDialog(
                onDismissRequest = {
                    dialInfos.value = null
                },
                title = {
                    infos.title?.let {
                        Text(text = it)
                    }
                },
                text = {
                    Text(infos.message)
                },
                confirmButton = {
                    infos.confirmBtnText?.let {
                        Button(onClick = {
                            dialInfos.value = null
                            infos.onConfirmClick()
                        }) {
                            Text(text = infos.confirmBtnText)
                        }
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        dialInfos.value = null
                        infos.onDismissClick()
                    }) {
                        Text(text = infos.dismissBtnText)
                    }
                }, properties = DialogProperties(
                    dismissOnClickOutside = false
                )
            )
        }
    }
}

fun showError(msg : String){
    dialInfos.value = DialogData(title = "Error", message = msg)
}