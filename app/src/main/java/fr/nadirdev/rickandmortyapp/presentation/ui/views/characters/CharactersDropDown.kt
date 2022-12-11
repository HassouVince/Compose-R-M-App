package fr.nadirdev.rickandmortyapp.presentation.ui.views.characters

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import fr.nadirdev.rickandmortyapp.R
import fr.nadirdev.rickandmortyapp.presentation.utils.isInternetAvailable
import fr.nadirdev.rickandmortyapp.presentation.viewmodels.CharactersViewModel
import org.koin.dsl.module.applicationContext

@Composable
fun CharactersDropDown(
    viewModel: CharactersViewModel,
    stateStr: MutableState<String>,
    list: List<String>
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    Surface(
        shape = RoundedCornerShape(30),
        border = BorderStroke(
            1.dp,
            colorResource(id = R.color.light_blue)
        )
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(start = 5.dp), contentAlignment = Alignment.Center
        ) {
            Row(
                Modifier
                    .clickable {
                        expanded = !expanded
                    }
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stateStr.value, style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "")

                DropdownMenu(expanded = expanded, onDismissRequest = {
                    expanded = false
                }) {
                    list.forEach { item ->
                        DropdownMenuItem(onClick = {
                            expanded = false
                            stateStr.value = item
                            viewModel.getCharacters(
                                isInternetAvailable(context)
                            )
                        }) {
                            Text(text = item)
                        }
                    }
                }
            }
        }
    }
}