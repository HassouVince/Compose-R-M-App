package fr.nadirdev.rickandmortyapp.presentation.ui.views.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fr.nadirdev.rickandmortyapp.R
import fr.nadirdev.rickandmortyapp.domain.models.CharacterResult
import fr.nadirdev.rickandmortyapp.domain.models.Place
import fr.nadirdev.rickandmortyapp.presentation.ui.UNSPECIFIED
import fr.nadirdev.rickandmortyapp.presentation.ui.views.HorizontalDivider
import fr.nadirdev.rickandmortyapp.presentation.ui.views.InfoRowData
import fr.nadirdev.rickandmortyapp.presentation.ui.views.VerticalSpace

@Composable
fun InfosCard(title: String, iconRes: Int, infos : List<InfoRowData>){
    Card(
        shape = RoundedCornerShape(14.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 20.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(all = 10.dp)
        ) {
            Row {
                Image(
                    painterResource(id = iconRes),
                    contentDescription = null,
                    alignment = Alignment.Center
                )
                Spacer(modifier = Modifier.width(3.dp))

                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            VerticalSpace()
            infos.forEach { TextLayout(infoRow = it) }
        }
    }
}

@Composable
fun GeneralInfos(character: CharacterResult){
    val infos = listOf(
        InfoRowData("Genre",character.gender ?: UNSPECIFIED, R.drawable.ic_gender),
        InfoRowData("Status",character.status ?: UNSPECIFIED, R.drawable.ic_medical_information),
        InfoRowData("Type",character.type ?: UNSPECIFIED, R.drawable.ic_type)
    )
    InfosCard(title = "General", iconRes = R.drawable.ic_infos, infos = infos)
}

@Composable
fun PlaceInfos(place: Place, title : String, iconRes : Int){
    val infos = listOf(
        InfoRowData("Name",place.name, R.drawable.ic_name),
        InfoRowData("Dimension",place.dimension ?: UNSPECIFIED, R.drawable.ic_dimension),
        InfoRowData("Type",place.type ?: UNSPECIFIED, R.drawable.ic_type)
    )
    InfosCard(title = title, iconRes = iconRes, infos = infos)
}

@Composable
fun TextLayout(infoRow: InfoRowData){
    Column{
        Row(modifier = Modifier.padding(all = 15.dp)){
            Row (Modifier.weight(5f)){
                Image(
                    painterResource(id = infoRow.iconRes),
                    contentDescription = null,
                    alignment = Alignment.Center)
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = infoRow.title,
                    style = MaterialTheme.typography.body2)
            }

            val text = infoRow.text.ifEmpty { UNSPECIFIED }
            Text(modifier = Modifier.weight(5f),
                text = text,
                textAlign = TextAlign.End,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.body2)
        }
        HorizontalDivider()
    }
}