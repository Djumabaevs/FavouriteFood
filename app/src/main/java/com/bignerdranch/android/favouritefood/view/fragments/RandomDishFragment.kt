package com.bignerdranch.android.favouritefood.view.fragments

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.favouritefood.R
import com.bignerdranch.android.favouritefood.databinding.FragmentRandomDishBinding
import com.bignerdranch.android.favouritefood.model.entities.RandomDish
import com.bignerdranch.android.favouritefood.viewmodel.NotificationsViewModel
import com.bignerdranch.android.favouritefood.viewmodel.RandomDishViewModel
import com.bumptech.glide.Glide
import kotlin.math.log

class RandomDishFragment : Fragment() {

    private var mBinding: FragmentRandomDishBinding? = null
    private lateinit var mRandomDishViewModel: RandomDishViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = FragmentRandomDishBinding.inflate(inflater, container, false)


        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRandomDishViewModel = ViewModelProvider(this).get(RandomDishViewModel::class.java)
        mRandomDishViewModel.getRandomRecipeFromApi()

        randomDishViewModelObserver()
    }

    private fun randomDishViewModelObserver() {

        mRandomDishViewModel.randomDishResponse.observe(viewLifecycleOwner,
            {randomDishResponse -> randomDishResponse?.let {
                Log.i("Random response", "${randomDishResponse.recipes[0]}")
                setRandomDishResponseInUI(randomDishResponse.recipes[0])
            }})
        mRandomDishViewModel.randomDishLoadingError.observe(viewLifecycleOwner,
            {dataError -> dataError?.let {
                Log.e("Random error", "$dataError")
            }})
        mRandomDishViewModel.loadRandomDish.observe(viewLifecycleOwner,
            {loadRandomDish -> loadRandomDish?.let {
                Log.i("Random loading", "$loadRandomDish")
            }})
    }

    private fun setRandomDishResponseInUI(recipe: RandomDish.Recipe) {
        Glide
            .with(requireActivity())
            .load(recipe.image)
            .centerCrop()
            .into(mBinding!!.ivDishImage)

        mBinding!!.tvTitle.text = recipe.title

        var dishType: String = "other"
        if(recipe.dishTypes.isNotEmpty()) {
            dishType = recipe.dishTypes[0]
            mBinding!!.tvType.text = dishType
        }

        mBinding!!.tvCategory.text = "Other"

        var ingredients = ""
        for(value in recipe.extendedIngredients) {
            if(ingredients.isEmpty()) {
                ingredients = value.original
            } else {
                ingredients = ingredients + " , \n" + value.original
            }
        }
        mBinding!!.tvIngredients.text = ingredients

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            mBinding!!.tvCookingDirection.text = Html.fromHtml(
                recipe.instructions,
                Html.FROM_HTML_MODE_COMPACT
            )
        } else {
            @Suppress("DEPRECATION")
            mBinding!!.tvCookingDirection.text = Html.fromHtml(recipe.instructions)
        }

        mBinding!!.tvCookingTime.text =
            resources.getString(R.string.lbl_estimate_cooking_time,
            recipe.readyInMinutes.toString())
    }


    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}