package android.example.com.taipeizoointrodemo.networkApi

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

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

@JsonClass(generateAdapter = true)
data class PlantResults(
    @Json(name = "F_Pic01_URL") val imgSrcUrl : String,
    // 因為後台API在F_Name_Ch前面有個0x35的字元，因此這樣設置
    @Json(name = "﻿F_Name_Ch") val f_Name_Ch : String?,

    @Json(name = "F_AlsoKnown") val f_AlsoKnown : String,

    @Json(name = "_id") val _id : Int

//    @Json(name = "F_Name_Latin") val f_Name_Latin : String,
//    @Json(name = "F_pdf02_ALT") val f_pdf02_ALT : String,
//    @Json(name = "F_Location") val f_Location : String,
//    @Json(name = "F_pdf01_ALT") val f_pdf01_ALT : String,
//    @Json(name = "F_Summary") val f_Summary : String,
//    @Json(name = "F_Pic01_URL") val imgSrcUrl : String,
//    @Json(name = "F_pdf02_URL") val f_pdf02_URL : String,
//    @Json(name = "F_Pic02_URL") val f_Pic02_URL : String,
//    @Json(name = "F_Name_Ch") val f_Name_Ch : String?,
//    @Json(name = "F_Keywords") val f_Keywords : String,
//    @Json(name = "F_Code") val f_Code : String,
//    @Json(name = "F_Geo") val f_Geo : String,
//    @Json(name = "F_Pic03_URL") val f_Pic03_URL : String,
//    @Json(name = "F_Voice01_ALT") val f_Voice01_ALT : String,
//    @Json(name = "F_AlsoKnown") val f_AlsoKnown : String,
//    @Json(name = "F_Voice02_ALT") val f_Voice02_ALT : String,
//    @Json(name = "F_Pic04_ALT") val f_Pic04_ALT : String,
//    @Json(name = "F_Name_En") val f_Name_En : String,
//    @Json(name = "F_Brief") val f_Brief : String,
//    @Json(name = "F_Pic04_URL") val f_Pic04_URL : String,
//    @Json(name = "F_Voice01_URL") val f_Voice01_URL : String,
//    @Json(name = "F_Feature") val f_Feature : String,
//    @Json(name = "F_Pic02_ALT") val f_Pic02_ALT : String,
//    @Json(name = "F_Family") val f_Family : String,
//    @Json(name = "F_Voice03_ALT") val f_Voice03_ALT : String,
//    @Json(name = "F_Voice02_URL") val f_Voice02_URL : String,
//    @Json(name = "F_Pic03_ALT") val f_Pic03_ALT : String,
//    @Json(name = "F_Pic01_ALT") val f_Pic01_ALT : String,
//    @Json(name = "F_CID") val f_CID : String,
//    @Json(name = "F_pdf01_URL") val f_pdf01_URL : String,
//    @Json(name = "F_Vedio_URL") val f_Vedio_URL : String,
//    @Json(name = "F_Genus") val f_Genus : String,
//    @Json(name = "F_Function＆Application") val f_Function_Application : String,
//    @Json(name = "F_Voice03_URL") val f_Voice03_URL : String,
//    @Json(name = "F_Update") val f_Update : String,
//    @Json(name = "_id") val _id : Int
)