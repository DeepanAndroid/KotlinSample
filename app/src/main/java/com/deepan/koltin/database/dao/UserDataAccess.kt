package com.deepan.koltin.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.deepan.koltin.database.model.UserData

@Dao
interface UserDataAccess {

    @Query("SELECT * FROM userData")
    fun getAll(): List<UserData>

    @Insert(onConflict = REPLACE)
    fun insert(userData: UserData)

    @Query("DELETE FROM userData")
    fun deleteAll(): Int

    @Query("DELETE FROM userData WHERE username LIKE :username")
    fun deleteSingleItem(username: String): Int

    @Query("UPDATE userData SET username =:newusername WHERE username LIKE :username")
    fun updateSingleItem(username: String,newusername: String)

}