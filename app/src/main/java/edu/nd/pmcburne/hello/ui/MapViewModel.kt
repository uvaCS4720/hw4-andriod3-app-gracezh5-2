package edu.nd.pmcburne.hello.ui

import edu.nd.pmcburne.hello.data.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

class MapViewModel(
    private val repository: LocationRepository
) : ViewModel() {

    private val _selectedTag = MutableStateFlow("core")
    val selectedTag = _selectedTag

    val locations = repository.locations

    val filteredLocations = combine(locations, selectedTag) { locs, tag ->
        locs.filter { it.tags.split(",").contains(tag) }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val allTags = locations.map { locs ->
        locs.flatMap { it.tags.split(",") }
            .toSet()
            .toList()
            .sorted()
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun selectTag(tag: String) {
        _selectedTag.value = tag
    }

    init {
        viewModelScope.launch {
            repository.syncLocations()
        }
    }
}