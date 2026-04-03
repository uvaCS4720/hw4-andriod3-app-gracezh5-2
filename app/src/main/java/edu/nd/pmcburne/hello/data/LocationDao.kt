package edu.nd.pmcburne.hello.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Upsert
    suspend fun upsertAll(locations: List<LocationEntity>)

    @Query("SELECT * FROM locations")
    fun getAllLocations(): Flow<List<LocationEntity>>
}