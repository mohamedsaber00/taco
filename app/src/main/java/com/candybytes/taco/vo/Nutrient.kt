package com.candybytes.taco.vo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Nutrient(
    /**
     * Nutrient id
     */
    @SerializedName("id")
    val id: Int ,

    /**
     * Nutrient name
     */
    @SerializedName("name")
    var name: String ,

    /**
     *Nutrient unit
     */
    @SerializedName("unit")
    var unit: String = "",

    /**
     *Nutrient quantity
     */
    @SerializedName("qty")
    var qty: String = ""
): Parcelable
