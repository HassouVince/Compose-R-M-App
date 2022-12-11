package fr.nadirdev.rickandmortyapp.presentation.ui.views

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fr.nadirdev.rickandmortyapp.R

class DialogData(
    val title : String? = "Alerte",
    val message : String = "",
    val confirmBtnText : String? = null,
    val dismissBtnText : String = "Ok",
    val onConfirmClick : () -> Unit = {},
    val onDismissClick : () -> Unit = {},
)

class InfoRowData(val title : String, val text : String, val iconRes : Int)

@Composable
fun VerticalSpace(height : Dp = 5.dp) = Spacer(modifier = Modifier.height(height))

@Composable
fun HorizontalDivider() = Divider(color = colorResource(id = R.color.light_blue), thickness = 0.5.dp)