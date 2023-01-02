package com.example.proj.database.dao

import androidx.room.*
import com.example.proj.database.models.Position
import kotlinx.coroutines.flow.Flow

@Dao
interface PositionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(position: Position)

    @Update
    fun update(position: Position)

    @Delete
    fun delete(position: Position)

    @Query("SELECT * FROM Position")
    fun getAll(): Flow<List<Position>>

    @Query("SELECT * FROM Position WHERE id = :id")
    fun getPosition(id: Int): Flow<Position>
}