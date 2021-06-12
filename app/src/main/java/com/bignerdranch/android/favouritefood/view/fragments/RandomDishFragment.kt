package com.bignerdranch.android.favouritefood.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.favouritefood.R
import com.bignerdranch.android.favouritefood.databinding.FragmentRandomDishBinding
import com.bignerdranch.android.favouritefood.viewmodel.NotificationsViewModel
import com.bignerdranch.android.favouritefood.viewmodel.RandomDishViewModel

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
    }

    private fun randomDishViewModelObserver() {
        mRandomDishViewModel.randomDishResponse.observe(viewLifecycleOwner,
            {randomDishResponse -> randomDishResponse?.let {

            }})
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}