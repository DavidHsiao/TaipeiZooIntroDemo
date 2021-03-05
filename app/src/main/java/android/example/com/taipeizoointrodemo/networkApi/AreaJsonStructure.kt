package android.example.com.taipeizoointrodemo.networkApi

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetResult(
    val result: AreaJsonStructure

)

data class AreaJsonStructure(
    val limit: Double,
    val offset: Double,
    val count: Double,
    val sort: String?,
    val results: List<EachAreaResults>

)

data class EachAreaResults(
    // name的設定名稱是跟真正JSON裡面的KEY名稱一樣，後面的val則是自己定義，比較易讀。
    @Json(name = "E_Pic_URL") val imgSrcUrl: String,
    val E_Geo: String,
    val E_Info: String,
    val E_no: String,
    val E_Category: String,
    val E_Name: String,
    val E_Memo: String?,
    val _id: Double

)

@JsonClass(generateAdapter = true)
data class NetworkVideoContainer(
    val videos: List<NetworkVideo>
)

/**
 * Videos represent a devbyte that can be played.
 */
@JsonClass(generateAdapter = true)
data class NetworkVideo(
    val title: String,
    val description: String,
    val url: String,
    val updated: String,
    val thumbnail: String,
    val closedCaptions: String?)

@JsonClass(generateAdapter = true)
data class PirateResponse(
    @field:Json(name = "count") val count: Int,
    @field:Json(name = "next") val next: String?,
    @field:Json(name = "previous") val previous: String?,
    @field:Json(name = "results") val results: List<Pirate>
)

@JsonClass(generateAdapter = true)
data class Pirate(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "url") val url: String
)