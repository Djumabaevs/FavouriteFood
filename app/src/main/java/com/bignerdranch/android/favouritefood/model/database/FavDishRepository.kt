package com.bignerdranch.android.favouritefood.model.database

import androidx.annotation.WorkerThread
import com.bignerdranch.android.favouritefood.model.entities.FavDish

class FavDishRepository(private val favDishDao: FavDishDAO) {

    @WorkerThread
    suspend fun insertFavDishData(favDish: FavDish) {
        favDishDao.insertFavDishDetails(favDish)
    }
}