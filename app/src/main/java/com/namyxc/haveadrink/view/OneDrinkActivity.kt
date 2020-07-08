package com.namyxc.haveadrink.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.palette.graphics.Palette
import com.namyxc.haveadrink.R
import com.namyxc.haveadrink.api.CoctailRetriever
import com.namyxc.haveadrink.data.DrinkDto
import com.nshmura.recyclertablayout.RecyclerTabLayout
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_one_drink.*
import kotlinx.coroutines.*
import org.jetbrains.anko.displayMetrics


class OneDrinkActivity : AppCompatActivity() {

    private lateinit var pagerAdapter: DrinkPagerAdapter
    private lateinit var recyclerTabLayout: RecyclerTabLayout

    private val picassoTarget = object : Target {
        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
            drinkImage.setImageBitmap(bitmap)
            Palette.from(bitmap)
                .generate(Palette.PaletteAsyncListener { palette ->
                    val textSwatch = palette!!.vibrantSwatch ?: return@PaletteAsyncListener
                    drinkName.setBackgroundColor(textSwatch.rgb)
                    drinkName.setTextColor(textSwatch.titleTextColor)
                    recyclerTabLayout.setIndicatorColor(textSwatch.rgb)
                    recyclerTabLayout.invalidate()
                })
        }
        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {  }
        override fun onPrepareLoad(placeHolderDrawable: Drawable?) { }
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT < 23) {
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo != null && (networkInfo.isConnected && (networkInfo.type == ConnectivityManager.TYPE_WIFI || networkInfo.type == ConnectivityManager.TYPE_MOBILE))
        }else {
            val activeNetwork = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            networkCapabilities != null &&
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_drink)



        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        if (isNetworkConnected()) {
            retrieveRandomCoctail()
        } else {
            AlertDialog.Builder(this).setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again")
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
    }

    private fun retrieveRandomCoctail() {
        val oneDrinkActivityJob = Job()
        val errorHandler = CoroutineExceptionHandler { _, exception ->

            AlertDialog.Builder(this).setTitle("Error")
                .setMessage(exception.message)
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
        val coroutineScope = CoroutineScope(oneDrinkActivityJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler){
            val result = CoctailRetriever().getRandomCoctail()
            val width = displayMetrics.widthPixels
            val firstDrink = result.drinks.first()


            val drinkDto = DrinkDto(firstDrink.strDrink, firstDrink.strDrinkThumb, firstDrink.strInstructions, firstDrink.getIngredients())

            pagerAdapter = DrinkPagerAdapter(supportFragmentManager, drinkDto)
            ingredientsInstructions.adapter = pagerAdapter
            ingredientsInstructions.currentItem = 0

            recyclerTabLayout = findViewById(R.id.recyclerTabLayout)
            recyclerTabLayout.setUpWithViewPager(ingredientsInstructions)

            drinkName.text = drinkDto.name

            val picasso = Picasso.get()
            picasso.isLoggingEnabled = true
            picasso
                .load(drinkDto.imageUrl)
                .resize(width, width)
                .into(picassoTarget)
        }
    }
}

