package com.namyxc.haveadrink.api

import com.namyxc.haveadrink.data.RandomDrinkResponse
import retrofit2.http.GET

interface CoctailDBService {

    @GET("api/json/v1/1//random.php")
    suspend fun retrieveRandomCoctail(): RandomDrinkResponse
}