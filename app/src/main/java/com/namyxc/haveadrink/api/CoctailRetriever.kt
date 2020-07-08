package com.namyxc.haveadrink.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CoctailRetriever {
    private val service: CoctailDBService

    companion object{
        const val BASE_URL = "https://www.thecocktaildb.com/"
    }

    init {
        val rerofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = rerofit.create(CoctailDBService::class.java)
    }

    suspend fun getRandomCoctail() = service.retrieveRandomCoctail()

}