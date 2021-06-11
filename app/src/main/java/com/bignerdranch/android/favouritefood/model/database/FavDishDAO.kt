package com.bignerdranch.android.favouritefood.model.database

import androidx.room.*
import com.bignerdranch.android.favouritefood.model.entities.FavDish
import kotlinx.coroutines.flow.Flow

@Dao
interface FavDishDAO {

    @Insert
    suspend fun insertFavDishDetails(favDish: FavDish)

    @Query("SELECT * FROM FAV_DISHES_TABLE ORDER BY ID")
    fun getAllDishesList(): Flow<List<FavDish>>

    @Update
    suspend fun updateFavDishDetails(favDish: FavDish)

    @Query("SELECT * FROM FAV_DISHES_TABLE where favorite_dish = 1")
    fun getFavoriteDishList(): Flow<List<FavDish>>

    @Delete
    suspend fun deleteDish(favDish: FavDish)

    @Query("SELECT * FROM FAV_DISHES_TABLE WHERE type = :filterType")
    fun getFilteredDishList(filterType: String): Flow<List<FavDish>>

}