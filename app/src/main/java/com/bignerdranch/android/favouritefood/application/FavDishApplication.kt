package com.bignerdranch.android.favouritefood.application

import android.app.Application
import com.bignerdranch.android.favouritefood.model.database.FavDishRepository
import com.bignerdranch.android.favouritefood.model.database.FavDishRoomDatabase

class FavDishApplication : Application() {

    private val database by lazy { FavDishRoomDatabase.getDatabase(this@FavDishApplication) }

    val repository by lazy { FavDishRepository(database.favDishDao()) }

}