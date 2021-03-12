package com.example.miamscan.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.miamscan.model.FoodData

@Database(entities = [FoodData::class], version = 1, exportSchema = false)
abstract class FoodDataBase: RoomDatabase() {
    abstract fun foodDAO(): FoodDAO

    companion object{
        @Volatile
        private var INSTANCE: FoodDataBase? = null

        fun getDatabase(context: Context):FoodDataBase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodDataBase::class.java,
                    "food_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}