package com.example.android.hive_assignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android.hive_assignment.model.HighLightTypeConverter
import com.example.android.hive_assignment.model.OffersTypeConverter
import com.example.android.hive_assignment.model.Restaurant
import com.example.android.hive_assignment.model.ReviewsTypeConverter

@Database(entities = [Restaurant::class], version = 1, exportSchema = false)
@TypeConverters(HighLightTypeConverter::class, OffersTypeConverter::class, ReviewsTypeConverter::class)
abstract class RestrauntDataBase : RoomDatabase() {

    abstract val databaseDao: RestrauntDao

    companion object {

        @Volatile
        private var INSTANCE: RestrauntDataBase? = null

        fun getInstance(context: Context): RestrauntDataBase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            RestrauntDataBase::class.java,
                            "offline_restraunt"
                    )
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}