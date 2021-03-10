package android.example.com.taipeizoointrodemo.networkApi

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
data class GetAreaResult(
    val result: AreaJsonStructure

)

/**
 * * 館別資訊API的資料結構
 */
@JsonClass(generateAdapter = true)
data class AreaJsonStructure(
    val limit: Int,
    val offset: Int,
    val count: Int,
    val sort: String?,
    val results: List<EachAreaResults>

)

@Parcelize
@JsonClass(generateAdapter = true)
data class EachAreaResults(
    // name的設定名稱是跟真正JSON裡面的KEY名稱一樣，後面的val則是自己定義，比較易讀。
    @Json(name = "E_Pic_URL") val imgSrcUrl: String,
    val E_Geo: String,
    val E_Info: String,
    val E_no: String,
    val E_Category: String,
    val E_Name: String,
    val E_Memo: String?,
    val E_URL: String?,
    val _id: Double
) : Parcelable {
    val haveMomoInfo
        get() = E_Memo != ""
}