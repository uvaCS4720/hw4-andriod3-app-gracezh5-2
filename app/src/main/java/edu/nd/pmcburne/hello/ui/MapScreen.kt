package edu.nd.pmcburne.hello.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import androidx.compose.runtime.Composable

@Composable
fun MapScreen(viewModel: MapViewModel, modifier: Modifier) {
    val locations by viewModel.filteredLocations.collectAsStateWithLifecycle()
    val tags by viewModel.allTags.collectAsStateWithLifecycle()
    val selectedTag by viewModel.selectedTag.collectAsStateWithLifecycle()

    val cameraPositionState = rememberCameraPositionState {
        position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(
            LatLng(38.0355, -78.5035),
            15f
        )
    }

    Column(modifier = modifier) {
        TagDropdown(tags, selectedTag) {
            viewModel.selectTag(it)
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            locations.forEach { loc ->
                MarkerInfoWindowContent(
                    state = MarkerState(
                        position = LatLng(loc.latitude, loc.longitude)
                    ),
                    title = loc.name
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .widthIn(max = 250.dp)
                    ) {
                        Text(
                            text = loc.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = loc.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagDropdown(tags: List<String>, selected: String, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text("Select Tag") },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            tags.forEach { tag ->
                DropdownMenuItem(
                    text = { Text(tag) },
                    onClick = {
                        onSelect(tag)
                        expanded = false
                    }
                )
            }
        }
    }
}