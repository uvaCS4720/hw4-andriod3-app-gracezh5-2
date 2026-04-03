package edu.nd.pmcburne.hello.ui


import androidx.compose.runtime.Composable

@Composable
fun MapScreen(viewModel: MapViewModel) {
    val locations by viewModel.filteredLocations.collectAsState()
    val tags by viewModel.allTags.collectAsState()
    val selectedTag by viewModel.selectedTag.collectAsState()

    Column {
        TagDropdown(tags, selectedTag) {
            viewModel.selectTag(it)
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = rememberCameraPositionState()
        ) {
            locations.forEach { loc ->
                Marker(
                    state = MarkerState(
                        position = LatLng(loc.latitude, loc.longitude)
                    ),
                    title = loc.name,
                    snippet = loc.description
                )
            }
        }
    }
}