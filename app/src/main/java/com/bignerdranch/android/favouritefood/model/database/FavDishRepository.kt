package com.bignerdranch.android.favouritefood.model.database

import androidx.annotation.WorkerThread
import com.bignerdranch.android.favouritefood.model.entities.FavDish
import kotlinx.coroutines.flow.Flow

class FavDishRepository(private val favDishDao: FavDishDAO) {

    @WorkerThread
    suspend fun insertFavDishData(favDish: FavDish) {
        favDishDao.insertFavDishDetails(favDish)
    }

    val allDishesList: Flow<List<FavDish>> = favDishDao.getAllDishesList()

    @WorkerThread
    suspend fun updateFavDishData(favDish: FavDish) {
        favDishDao.updateFavDishDetails(favDish)
    }

    val favDishList: Flow<List<FavDish>> = favDishDao.getFavoriteDishList()

    @WorkerThread
    suspend fun deleteFavDishData(favDish: FavDish) {
        favDishDao.deleteDish(favDish)
    }

    fun filteredListDishes(value: String) : Flow<List<FavDish>> =
        favDishDao.getFilteredDishList(value)
}