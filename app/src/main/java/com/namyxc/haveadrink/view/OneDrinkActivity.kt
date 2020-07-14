package com.namyxc.haveadrink.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.palette.graphics.Palette
import com.namyxc.haveadrink.R
import com.namyxc.haveadrink.data.DrinkDto
import com.namyxc.haveadrink.viewmodel.DownloadState
import com.namyxc.haveadrink.viewmodel.RandomDrinkViewModel
import com.nshmura.recyclertablayout.RecyclerTabLayout
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_one_drink.*
import org.jetbrains.anko.displayMetrics


class OneDrinkActivity : AppCompatActivity() {

    private lateinit var pagerAdapter: DrinkPagerAdapter
    private lateinit var recyclerTabLayout: RecyclerTabLayout
    private val model: RandomDrinkViewModel by viewModels()


    private lateinit var anim: Animation

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

                    refreshButton.backgroundTintList = ColorStateList.valueOf(textSwatch.rgb)
                    refreshButton.imageTintList = ColorStateList.valueOf(textSwatch.titleTextColor)
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
        anim = AnimationUtils.loadAnimation(this, R.anim.shake)

        val randomDrinkObserver = Observer<DrinkDto> {
            displayDrink(it)
        }

        val randomDrinkStateObserver = Observer<DownloadState> {
            when (it) {
                DownloadState.ERROR -> {
                    AlertDialog.Builder(this).setTitle("Error")
                            .setMessage("valami error message")
                            .setPositiveButton(android.R.string.ok) { _, _ -> }
                            .setIcon(android.R.drawable.ic_dialog_alert).show()
                    refreshButton.isEnabled = true
                    refreshButton.clearAnimation()
                    }
                DownloadState.DONE -> {
                    refreshButton.isEnabled = true
                    refreshButton.clearAnimation()
                }
                DownloadState.IN_PROGRESS -> {
                    refreshButton.isEnabled = false
                    refreshButton.startAnimation(anim)
                }
            }
        }

        model.state.observe(this, randomDrinkStateObserver)
        model.randomDrink.observe(this, randomDrinkObserver)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        getRandomDrink()

        refreshButton.setOnClickListener { getRandomDrink() }
    }

    private fun getRandomDrink(){
        if (isNetworkConnected()) {
            model.getRandomDrink()
        } else {
            AlertDialog.Builder(this).setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again")
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
    }

    private fun displayDrink(drinkDto: DrinkDto) {
        val width = displayMetrics.widthPixels
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

