package gr.edu.samosaed.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gr.edu.samosaed.R
import gr.edu.samosaed.data.AedPoint
import gr.edu.samosaed.data.AedRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(point: AedPoint?, onBack: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(stringResource(R.string.details_title)) },
            navigationIcon = {
                TextButton(onClick = onBack) { Text(stringResource(R.string.back)) }
            }
        )
    }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            TrainingNotice()
            Column(
                Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(stringResource(R.string.training_details), style = MaterialTheme.typography.bodyMedium)
                if (point == null) {
                    Text(stringResource(R.string.missing_point), color = MaterialTheme.colorScheme.error)
                } else {
                    DetailField(stringResource(R.string.field_id), point.id)
                    DetailField(stringResource(R.string.field_name), stringResource(point.nameRes))
                    DetailField(stringResource(R.string.field_area), stringResource(point.area.labelRes))
                    DetailField(stringResource(R.string.field_address), stringResource(R.string.sample_address, point.id))
                    DetailField(stringResource(R.string.field_access), stringResource(R.string.sample_access))
                    DetailField(stringResource(R.string.field_latitude), point.latitude.toString())
                    DetailField(stringResource(R.string.field_longitude), point.longitude.toString())
                }
            }
        }
    }
}

@Composable
private fun DetailField(label: String, value: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
        Text(value, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailsScreenPreview() {
    SamosAedTheme { DetailsScreen(AedRepository.findById("DEMO-V1"), onBack = {}) }
}
