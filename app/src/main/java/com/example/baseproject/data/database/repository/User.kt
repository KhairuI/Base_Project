package com.example.baseproject.data.database.repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(
    @Expose @PrimaryKey @SerializedName("id")
    @ColumnInfo(name = "id") var id: Int? = null,

    @Expose @SerializedName("name")
    @ColumnInfo(name = "name") var name: String? = null,

    @Expose @SerializedName("phone")
    @ColumnInfo(name = "phone") var phone: String? = null,

)