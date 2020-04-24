package com.example.android.hive_assignment.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.hive_assignment.model.Restaurant


@Dao
interface RestrauntDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(restraunt: Restaurant)

    @Query("SELECT * FROM offline_restraunts")
    fun getAllRestraunts(): List<Restaurant>

    @Query("DELETE  FROM offline_restraunts WHERE id = :id ")
    fun delete(id: String)


}