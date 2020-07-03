package com.namyxc.haveadrink.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.palette.graphics.Palette
import com.namyxc.haveadrink.R
import com.namyxc.haveadrink.data.DrinkDto
import com.nshmura.recyclertablayout.RecyclerTabLayout
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_one_drink.*


class OneDrinkActivity : AppCompatActivity() {

    private lateinit var pagerAdapter: DrinkPagerAdapter
    private lateinit var recyclerTabLayout: RecyclerTabLayout

    private val drinkDto = DrinkDto("Random drink", "https://www.thecocktaildb.com/images/media/drink/qtwxwr1483387647.jpg")


    private val picassoTarget = object : Target {
        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
            drinkImage.setImageBitmap(bitmap)
            Palette.from(bitmap)
                .generate(Palette.PaletteAsyncListener { palette ->
                    val textSwatch = palette!!.vibrantSwatch
                    if (textSwatch == null) {
                        return@PaletteAsyncListener
                    }
                    drinkName.setBackgroundColor(textSwatch.rgb)
                    drinkName.setTextColor(textSwatch.titleTextColor)
                })
        }
        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {  }
        override fun onPrepareLoad(placeHolderDrawable: Drawable?) { }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_drink)

        pagerAdapter = DrinkPagerAdapter(supportFragmentManager, drinkDto)
        ingredientsInstructions.adapter = pagerAdapter
        ingredientsInstructions.currentItem = 0

        recyclerTabLayout = findViewById(R.id.recyclerTabLayout)
        recyclerTabLayout.setUpWithViewPager(ingredientsInstructions)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels

        drinkName.text = drinkDto.name

        val picasso = Picasso.get()
        picasso.setLoggingEnabled(true)
        picasso
            .load(drinkDto.imageUrl)
            .resize(width, width)
            .into(picassoTarget)
    }
}

