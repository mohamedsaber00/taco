package com.candybytes.taco.ui.util

import com.candybytes.taco.vo.Nutrient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * convert a object to a json string
 */
fun Any.toJson(): String = Gson().toJson(this)

/**
 * convert a json to an object
 */
inline fun <reified T> String?.fromJson() =
    if (this == null) null
    else
        Gson().fromJson<T>(this, object : TypeToken<T>() {}.type)


/**
 * Convert Nutrient HashMap to Mutable List for recyclerView
 * */
fun HashMap<String, Nutrient>.toList(): List<Nutrient> {
    val nutrientsList = mutableListOf<Nutrient>()
    forEach {

        val nutrient = it.value.apply {
            //reformat @qty to avoid empty, "Tr" and NA
            if (qty.isEmpty() || qty.equals("Tr") || qty.equals("NA")){
                //remove unit
                unit = ""
                //set quantity to NA
                qty = "NA"
            }else{
                //It's a number so format it to get only 2 numbers after the decimal
                val qtyDouble =qty.toDouble()
                qty = String.format("%.2f ", qtyDouble)
            }
        }
        /*
        * Capitalize the first letter and remove "_"
        * This may have strange behavior for some special character but it will work will here
        * */
        nutrient.name = it.key.replaceFirstChar(Char::uppercase).replace("_", " ")
        nutrientsList.add(it.value)
    }.let {
        return nutrientsList
    }
}




