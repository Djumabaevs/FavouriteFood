package com.bignerdranch.android.favouritefood.view.adapters

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.favouritefood.databinding.ItemCustomListBinding

class CustomListItemAdapter(
    private val activity: Activity,
    private val listItems: List<String>,
    private val selection: String
) : RecyclerView.Adapter<CustomListItemAdapter.ViewHolder>()
{

    class ViewHolder(view: ItemCustomListBinding) : RecyclerView.ViewHolder(view.root) {
        val tvText = view.tvText
    }
}