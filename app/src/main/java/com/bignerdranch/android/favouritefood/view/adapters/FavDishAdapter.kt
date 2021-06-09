package com.bignerdranch.android.favouritefood.view.adapters

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.favouritefood.databinding.ItemDishLayoutBinding
import com.bignerdranch.android.favouritefood.model.entities.FavDish

class FavDishAdapter(private val fragment: Fragment) :
RecyclerView.Adapter<FavDishAdapter.ViewHolder>()
{
private var dishes: List<FavDish> = listOf()

    class ViewHolder(view: ItemDishLayoutBinding) : RecyclerView.ViewHolder(view.root) {
        val ivDishImage = view.ivDishImage
        val tvTitle = view.tvDishTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return dishes.size
    }

    fun dishesList(list: List<FavDish>) {
        dishes = list
        notifyDataSetChanged()
    }
}