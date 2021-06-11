package com.bignerdranch.android.favouritefood.model.network

import com.bignerdranch.android.favouritefood.model.entities.RandomDish
import com.bignerdranch.android.favouritefood.utils.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomDishApi {

    @GET(Constants.API_ENDPOINT)
    fun getDishes(

        @Query(Constants.API_KEY) apiKey: String,


    ): Single<RandomDish.Recipes>
}