package fr.nadirdev.rickandmortyapp.presentation.ui.views.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import fr.nadirdev.rickandmortyapp.domain.models.CharacterResult

@ExperimentalCoilApi
@Composable
fun CharacterCardTemplate(character: CharacterResult) {
    Image(
        modifier = Modifier
            .height(128.dp)
            .fillMaxWidth(),
        painter = rememberImagePainter(character.image),
        contentScale = ContentScale.FillBounds,
        contentDescription = null
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = character.name,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}