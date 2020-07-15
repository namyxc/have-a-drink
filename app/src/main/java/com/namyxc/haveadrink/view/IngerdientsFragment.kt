package com.namyxc.haveadrink.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.namyxc.haveadrink.R
import com.namyxc.haveadrink.data.Ingredient
import kotlinx.android.synthetic.main.fragment_ingredients.*
import java.io.Serializable

class IngerdientsFragment: Fragment() {

    private var ingredients: List<Ingredient>? = null
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View? {

        ingredients = arguments?.getSerializable(BundleKeys.INGREDIENTS.toString()) as List<Ingredient>
        return inflater.inflate(R.layout.fragment_ingredients, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (ingredients != null) {
            viewAdapter = IngredientListAdapter(ingredients!!)
            ingredientsList.adapter = viewAdapter
            ingredientsList.layoutManager = LinearLayoutManager(context)
        }
    }


    companion object {

        fun newInstance(ingredients: List<Ingredient>): IngerdientsFragment {

            val args = Bundle()
            args.putSerializable(BundleKeys.INGREDIENTS.toString(), ingredients as Serializable)
            val fragment = IngerdientsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}