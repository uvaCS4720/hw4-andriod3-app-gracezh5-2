package edu.nd.pmcburne.hello.data

import retrofit2.http.GET

interface UvaApiService {
    @GET("placemarks.json")
    suspend fun getPlacemarks(): List<UvaPlacemarkDto>
}