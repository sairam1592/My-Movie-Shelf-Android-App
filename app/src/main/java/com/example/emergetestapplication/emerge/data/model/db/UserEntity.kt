package com.example.emergetestapplication.emerge.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.emergetestapplication.emerge.common.AppConstants

@Entity(tableName = AppConstants.USERS_TABLE_NAME)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = AppConstants.USERNAME) val username: String,
    @ColumnInfo(name = AppConstants.PASSWORD) val password: String,
)
