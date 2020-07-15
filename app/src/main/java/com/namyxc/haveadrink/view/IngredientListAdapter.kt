package com.namyxc.haveadrink.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.namyxc.haveadrink.R
import com.namyxc.haveadrink.data.Ingredient

class IngredientListAdapter(private val ingredients: List<Ingredient>): RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder>()  {

    class IngredientViewHolder(itemView: View): RecyclerView.ViewHolder(
        itemView
    ){
        private val nameView: TextView = itemView.findViewById(R.id.ingredientName)
        private val amountView: TextView = itemView.findViewById(R.id.ingredientAmount)


        fun bind(ingredient: Ingredient) {
            nameView.text = ingredient.name
            amountView.text = ingredient.amount
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_list_item, parent, false)
        return IngredientViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.bind(ingredient)
    }
}