package com.namyxc.haveadrink.networking

import com.google.gson.Gson
import com.namyxc.haveadrink.data.RandomDrinkResponse
import java.net.URL


class Request(private val url: String) {

    fun run(): RandomDrinkResponse {
        val randomDrinkStr = URL(url).readText()
        return Gson().fromJson(randomDrinkStr, RandomDrinkResponse::class.java)
    }
}