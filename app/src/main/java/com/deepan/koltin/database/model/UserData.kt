package com.deepan.koltin.database.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "userData")
data class UserData(@PrimaryKey(autoGenerate = true)var id: Long?,
                    @ColumnInfo(name="username")var username: String){

}
