package com.namyxc.haveadrink.data

data class DrinkDto(
    val name: String,
    val imageUrl: String,
    val instructions: String,
    val ingredients: List<Ingredient>
)