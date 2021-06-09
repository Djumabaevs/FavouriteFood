package com.bignerdranch.android.favouritefood.model.database

import androidx.room.Dao
import androidx.room.Insert
import com.bignerdranch.android.favouritefood.model.entities.FavDish

@Dao
interface FavDishDAO {

    @Insert
    suspend fun insertFavDishDetails(favDish: FavDish)

}