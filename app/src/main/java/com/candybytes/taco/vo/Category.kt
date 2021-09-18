package com.candybytes.taco.vo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "categories")
data class Category(

    /**
     * Category unique ID.
     */
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int = -1,

    /**
     * Category description.
     */
    @SerializedName("category")
    val name: String = "",

    /**
     * Number of foods available.
     */
    @SerializedName("foodCount")
    var foodCount: Int? = null

) : Parcelable
