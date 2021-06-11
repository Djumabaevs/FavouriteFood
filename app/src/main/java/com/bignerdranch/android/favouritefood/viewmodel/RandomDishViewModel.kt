package com.bignerdranch.android.favouritefood.viewmodel

import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.favouritefood.model.entities.RandomDish
import com.bignerdranch.android.favouritefood.model.network.RandomDishApiService
import io.reactivex.rxjava3.disposables.CompositeDisposable

class RandomDishViewModel {

    private val randomRecipeApiService = RandomDishApiService()
    private val compositeDisposable = CompositeDisposable()

    val  loadRandomDish = MutableLiveData<Boolean>()
    val randomDishResponse = MutableLiveData<RandomDish.Recipes>()
    val randomDishLoadingError = MutableLiveData<Boolean>()



}