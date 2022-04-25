package com.mydigitalschoolendrick.myapplicationmuseum.data

import retrofit2.Call
import retrofit2.http.GET

interface MuseumInterface {
    @GET("api/records/1.0/search/?dataset=musees-de-france-base-museofile&q=&facet=dompal&facet=region")
    fun getMuseums(): Call<Records>
}

