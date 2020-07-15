package com.namyxc.haveadrink.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.namyxc.haveadrink.api.CoctailRetriever
import com.namyxc.haveadrink.data.DrinkDto
import kotlinx.coroutines.*

class RandomDrinkViewModel  : ViewModel(){

    val randomDrink: MutableLiveData<DrinkDto> by lazy {
        MutableLiveData<DrinkDto>()
    }

    val state: MutableLiveData<DownloadState> by lazy {
        MutableLiveData<DownloadState>()
    }

    fun getRandomDrink() {
        val oneDrinkActivityJob = Job()
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            state.value = DownloadState.ERROR
            Log.e("getRandomDrink", exception.message)
        }
        val coroutineScope = CoroutineScope(oneDrinkActivityJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler){
            state.value = DownloadState.IN_PROGRESS
            val result = CoctailRetriever().getRandomCoctail()
            val firstDrink = result.drinks.first()


            val drinkDto = DrinkDto(firstDrink.strDrink, firstDrink.strDrinkThumb, firstDrink.strInstructions, firstDrink.getIngredients())

            randomDrink.value = drinkDto
            state.value = DownloadState.DONE
        }
    }
}