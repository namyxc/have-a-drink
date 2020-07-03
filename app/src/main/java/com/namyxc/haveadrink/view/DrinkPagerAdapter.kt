package com.namyxc.haveadrink.view

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.namyxc.haveadrink.data.DrinkDto
import java.lang.IllegalArgumentException

class DrinkPagerAdapter(fragmentManager: FragmentManager, private val drinkDto: DrinkDto) :
    FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int) = when (position) {
        0 -> IngerdientsFragment.newInstance(drinkDto.ingredients)
        1 -> InstructionsFragment.newInstance(drinkDto.instructions)
        else -> throw IllegalArgumentException()
    }

    override fun getPageTitle(position: Int) = when (position) {
        0 -> "Ingredients"
        1 -> "Instructions"
        else -> throw IllegalArgumentException()
    }

    override fun getCount() = 2
}