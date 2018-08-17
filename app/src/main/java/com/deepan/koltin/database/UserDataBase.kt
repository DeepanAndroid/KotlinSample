package com.deepan.koltin.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.deepan.koltin.database.dao.UserDataAccess
import com.deepan.koltin.database.model.UserData

@Database(entities = [UserData::class], version = 1,exportSchema = false)
abstract class UserDataBase : RoomDatabase() {
    abstract fun userDataAccess(): UserDataAccess

    companion object {
        private var INSTANCE: UserDataBase? = null

        fun getInstance(context: Context): UserDataBase? {

            if (INSTANCE == null) {
                synchronized(UserDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            UserDataBase::class.java, "user.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}