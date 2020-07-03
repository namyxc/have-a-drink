package com.namyxc.haveadrink.data

data class DrinkResponseDto (
    val idDrink: Int,
    val strDrink: String,
    val strDrinkThumb: String,
    val strInstructions: String,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strIngredient10: String?,
    val strIngredient11: String?,
    val strIngredient12: String?,
    val strIngredient13: String?,
    val strIngredient14: String?,
    val strIngredient15: String?,
    
    val strMeasure1: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?,
    val strMeasure6: String?,
    val strMeasure7: String?,
    val strMeasure8: String?,
    val strMeasure9: String?,
    val strMeasure10: String?,
    val strMeasure11: String?,
    val strMeasure12: String?,
    val strMeasure13: String?,
    val strMeasure14: String?,
    val strMeasure15: String?

){
    fun getIngredients(): List<Ingredient>{
        val ingredients = mutableListOf<Ingredient>()
        if (strIngredient1 != null && strMeasure1 != null){
            ingredients.add(Ingredient(strIngredient1, strMeasure1))
        }
        if (strIngredient2 != null && strMeasure2 != null){
            ingredients.add(Ingredient(strIngredient2, strMeasure2))
        }
        if (strIngredient3 != null && strMeasure3 != null){
            ingredients.add(Ingredient(strIngredient3, strMeasure3))
        }
        if (strIngredient4 != null && strMeasure4 != null){
            ingredients.add(Ingredient(strIngredient4, strMeasure4))
        }
        if (strIngredient5 != null && strMeasure5 != null){
            ingredients.add(Ingredient(strIngredient5, strMeasure5))
        }
        if (strIngredient6 != null && strMeasure6 != null){
            ingredients.add(Ingredient(strIngredient6, strMeasure6))
        }
        if (strIngredient7 != null && strMeasure7 != null){
            ingredients.add(Ingredient(strIngredient7, strMeasure7))
        }
        if (strIngredient8 != null && strMeasure8 != null){
            ingredients.add(Ingredient(strIngredient8, strMeasure8))
        }
        if (strIngredient9!= null && strMeasure9 != null){
            ingredients.add(Ingredient(strIngredient9, strMeasure9))
        }
        if (strIngredient10 != null && strMeasure10 != null){
            ingredients.add(Ingredient(strIngredient10, strMeasure10))
        }
        if (strIngredient11 != null && strMeasure11 != null){
            ingredients.add(Ingredient(strIngredient11, strMeasure11))
        }
        if (strIngredient12 != null && strMeasure12 != null){
            ingredients.add(Ingredient(strIngredient12, strMeasure12))
        }
        if (strIngredient13 != null && strMeasure13 != null){
            ingredients.add(Ingredient(strIngredient13, strMeasure13))
        }
        if (strIngredient14 != null && strMeasure14 != null){
            ingredients.add(Ingredient(strIngredient14, strMeasure14))
        }
        if (strIngredient15 != null && strMeasure15 != null){
            ingredients.add(Ingredient(strIngredient15, strMeasure15))
        }

        return ingredients
    }
}