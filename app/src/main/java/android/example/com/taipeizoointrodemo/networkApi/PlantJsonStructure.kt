package android.example.com.taipeizoointrodemo.networkApi

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 * 植物API的資料結構
 */
@JsonClass(generateAdapter = true)
data class GetPlantResult(
    val result: PlantJsonStructure

)

@JsonClass(generateAdapter = true)
data class PlantJsonStructure(
    val limit: Int,
    val offset: Int,
    val count: Int,
    val sort: String?,
    val results: List<PlantResults>

)

@Parcelize
@JsonClass(generateAdapter = true)
data class PlantResults(
    @Json(name = "F_Pic01_URL") val imgSrcUrl: String,
    // 因為後台API在F_Name_Ch前面有個0x35的字元，因此這樣設置
    @Json(name = "﻿F_Name_Ch") val f_Name_Ch: String?,

    @Json(name = "F_AlsoKnown") val f_AlsoKnown: String,

    @Json(name = "F_Name_En") val f_Name_En: String?,

    @Json(name = "F_Brief") val f_Brief: String?,

    @Json(name = "F_Feature") val f_Feature: String?,

    @Json(name = "F_Function＆Application") val f_Function_Application: String?,

    @Json(name = "F_Update") val f_Update: String?,

    @Json(name = "_id") val _id: Int

) : Parcelable