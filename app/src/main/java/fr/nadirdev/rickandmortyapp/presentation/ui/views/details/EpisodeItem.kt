package fr.nadirdev.rickandmortyapp.presentation.ui.views.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fr.nadirdev.rickandmortyapp.domain.models.Episode
import fr.nadirdev.rickandmortyapp.presentation.ui.views.HorizontalDivider
import fr.nadirdev.rickandmortyapp.presentation.ui.views.VerticalSpace

@Composable
fun EpisodeItem(episode: Episode){
    Column(
        Modifier
            .fillMaxWidth()
            .padding(2.dp),
        verticalArrangement = Arrangement.Center) {
        Text(
            text = episode.name,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic
        )
        VerticalSpace()
        HorizontalDivider()
    }
}