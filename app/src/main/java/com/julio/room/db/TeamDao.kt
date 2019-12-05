package com.julio.room.db

import androidx.room.*
import com.julio.room.entity.Team

@Dao
interface TeamDao {

    @Insert
    fun insert(teamList: List<Team>)

    @Query("SELECT * FROM team ORDER BY team ASC")
    fun select(): List<Team>

    @Update
    fun update(team: Team)

    @Delete
    fun delete(team: Team)
}