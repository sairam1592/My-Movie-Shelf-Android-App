package com.example.emergetestapplication.emerge

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.emergetestapplication.emerge.data.model.db.UserDao
import com.example.emergetestapplication.emerge.data.model.db.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
