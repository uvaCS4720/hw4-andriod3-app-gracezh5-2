package edu.nd.pmcburne.hello.data

class LocationRepository(
    private val api: UvaApiService,
    private val dao: LocationDao
) {

    val locations = dao.getAllLocations()

    suspend fun syncLocations() {
        val remote = api.getPlacemarks()

        val entities = remote.map {
            LocationEntity(
                id = it.id,
                name = it.name,
                description = it.description,
                latitude = it.visual_center.latitude,
                longitude = it.visual_center.longitude,
                tags = it.tag_list.joinToString(",")
            )
        }

        dao.upsertAll(entities)
    }
}