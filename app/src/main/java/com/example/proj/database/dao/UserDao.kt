package com.example.proj.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.proj.database.models.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE username = :username AND password = :password LIMIT 1")
    fun getUser(username: String, password: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)


}