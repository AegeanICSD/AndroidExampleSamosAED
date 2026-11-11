package gr.edu.samosaed.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import gr.edu.samosaed.R
import gr.edu.samosaed.data.AedPoint
import gr.edu.samosaed.data.AedRepository
import gr.edu.samosaed.data.Area

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// mapConfigured: καθορίζει αν θα εμφανιστεί ο χάρτης Google ή μήνυμα ότι δεν έχει ρυθμιστεί.
// onShowDetails: συνάρτηση που δέχεται ένα AedPoint. Καλείται όταν ο χρήστης ζητήσει λεπτομέρειες καταχώρισης.
fun MainScreen(mapConfigured: Boolean, onShowDetails: (AedPoint) -> Unit) {
    // Η επιλογή στο μενού εφαρμόζεται όταν ο χρήστης πατήσει το κουμπί.
    var pendingArea by rememberSaveable { mutableStateOf(Area.ALL.name) }
    var appliedArea by rememberSaveable { mutableStateOf(Area.ALL.name) }
    var selectedId by rememberSaveable { mutableStateOf<String?>(null) }
    var menuExpanded by remember { mutableStateOf(false) }
    val visiblePoints = AedRepository.filterByArea(Area.valueOf(appliedArea))
    val selectedPoint = visiblePoints.find { it.id == selectedId }

    Scaffold(topBar = { TopAppBar(title = { Text(stringResource(R.string.app_name)) }) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            TrainingNotice()
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(Modifier.weight(1f)) {
                    OutlinedButton(onClick = { menuExpanded = true }, modifier = Modifier.fillMaxWidth()) {
                        Text(stringResource(Area.valueOf(pendingArea).labelRes))
                    }
                    DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                        Area.entries.forEach { area ->
                            DropdownMenuItem(
                                text = { Text(stringResource(area.labelRes)) },
                                onClick = { pendingArea = area.name; menuExpanded = false }
                            )
                        }
                    }
                }
                Button(onClick = {
                    appliedArea = pendingArea
                    selectedId = null
                    val results = AedRepository.filterByArea(Area.valueOf(appliedArea))
                    Log.i("AED_ACTION", "Filter applied: $appliedArea, results=${results.size}")
                }) { Text(stringResource(R.string.show_points)) }
            }

            Box(Modifier.fillMaxWidth().weight(1f)) {
                if (mapConfigured) {
                    AedMap(
                        points = visiblePoints,
                        areaKey = appliedArea,
                        onSelect = { point ->
                            selectedId = point.id
                            Log.d("AED_ACTION", "Marker selected: ${point.id}")
                        },
                        onShowDetails = onShowDetails
                    )
                } else {
                    Box(
                        Modifier.fillMaxSize().background(MaterialTheme.colorScheme.secondaryContainer)
                            .padding(24.dp), contentAlignment = Alignment.Center
                    ) { Text(stringResource(R.string.map_not_configured)) }
                }
            }

            if (selectedPoint != null) {
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(stringResource(selectedPoint.nameRes), Modifier.weight(1f))
                    TextButton(onClick = { onShowDetails(selectedPoint) }) {
                        Text(stringResource(R.string.details))
                    }
                }
            }
            Text(
                stringResource(R.string.point_count, visiblePoints.size),
                Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.titleSmall
            )
            LazyColumn(Modifier.fillMaxWidth().weight(1f)) {
                if (visiblePoints.isEmpty()) {
                    item { Text(stringResource(R.string.empty_results), Modifier.padding(16.dp)) }
                }
                items(visiblePoints, key = { it.id }) { point ->
                    ListItem(
                        headlineContent = { Text(stringResource(point.nameRes)) },
                        supportingContent = { Text("${point.id} · ${stringResource(point.area.labelRes)}") },
                        trailingContent = { Text(stringResource(R.string.details)) },
                        modifier = Modifier.clickable { onShowDetails(point) }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun TrainingNotice() {
    Text(
        stringResource(R.string.training_notice),
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        style = MaterialTheme.typography.labelMedium
    )
}

@Composable
private fun AedMap(
    points: List<AedPoint>,
    areaKey: String,
    onSelect: (AedPoint) -> Unit,
    onShowDetails: (AedPoint) -> Unit
) {
    val samos = LatLng(37.75, 26.82)
    val cameraState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(samos, 10f)
    }
    var mapLoaded by remember { mutableStateOf(false) }
    var showLoadingHint by remember { mutableStateOf(false) }
    var lastAppliedArea by rememberSaveable { mutableStateOf(areaKey) }

    LaunchedEffect(mapLoaded) {
        if (!mapLoaded) {
            kotlinx.coroutines.delay(12000)
            showLoadingHint = true
        }
    }
    LaunchedEffect(areaKey) {
        if (lastAppliedArea == areaKey) return@LaunchedEffect
        // Η αλλαγή φίλτρου εστιάζει στην πρώτη καταχώριση της περιοχής.
        val first = points.firstOrNull()
        val target = if (areaKey != Area.ALL.name && first != null) {
            LatLng(first.latitude, first.longitude)
        } else samos
        cameraState.position = CameraPosition.fromLatLngZoom(target, if (target == samos) 10f else 14f)
        lastAppliedArea = areaKey
    }

    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraState,
            uiSettings = MapUiSettings(mapToolbarEnabled = false),
            onMapLoaded = { mapLoaded = true; Log.i("AED_ACTION", "Map loaded") }
        ) {
            points.forEach { point ->
                key(point.id) {
                    val markerState = remember { MarkerState(position = LatLng(point.latitude, point.longitude)) }
                    Marker(
                        state = markerState,
                        title = stringResource(point.nameRes),
                        snippet = stringResource(point.area.labelRes),
                        onClick = {
                            onSelect(point)
                            cameraState.move(CameraUpdateFactory.newLatLng(it.position))
                            false // Επιτρέπει και την εμφάνιση του τίτλου του marker.
                        },
                        onInfoWindowClick = { onShowDetails(point) }
                    )
                }
            }
        }
        if (!mapLoaded) {
            Surface(Modifier.align(Alignment.TopCenter).padding(8.dp), tonalElevation = 3.dp) {
                Text(
                    stringResource(if (showLoadingHint) R.string.map_loading_hint else R.string.map_loading),
                    Modifier.padding(12.dp), style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 820)
@Composable
private fun MainScreenPreview() {
    SamosAedTheme { MainScreen(mapConfigured = false, onShowDetails = {}) }
}
