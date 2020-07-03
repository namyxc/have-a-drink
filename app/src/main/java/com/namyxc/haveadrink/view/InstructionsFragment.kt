package com.namyxc.haveadrink.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.namyxc.haveadrink.R

class InstructionsFragment: Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View? {
        return inflater.inflate(R.layout.fragment_instructions, container, false)
    }

    companion object {

        // Method for creating new instances of the fragment
        fun newInstance(): InstructionsFragment {

            // Store the movie data in a Bundle object
            /*val args = Bundle()
            args.putString(MovieHelper.KEY_TITLE, movie.title)
            args.putInt(MovieHelper.KEY_RATING, movie.rating)
            args.putString(MovieHelper.KEY_POSTER_URI, movie.posterUri)
            args.putString(MovieHelper.KEY_OVERVIEW, movie.overview)*/

            // Create a new MovieFragment and set the Bundle as the arguments
            // to be retrieved and displayed when the view is created
            val fragment = InstructionsFragment()
            //fragment.arguments = args
            return fragment
        }
    }
}