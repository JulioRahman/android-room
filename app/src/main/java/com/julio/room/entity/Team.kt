package com.julio.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team")
data class Team(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "team")
    var team: String?,

    @ColumnInfo(name = "year")
    var year: String?,

    @ColumnInfo(name = "website")
    var website: String?,

    @ColumnInfo(name = "gender")
    var gender: String?,

    @ColumnInfo(name = "country")
    var country: String?
)