package android.example.com.taipeizoointrodemo.networkApi

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

//private const val BASE_URL = "https://data.taipei/api/v1/dataset/"
//private const val BASE_URL = "https://pokeapi.co/api/v2/"
//private const val BASE_URL = "https://devbytes.udacity.com/"
private const val BASE_URL = "https://mars.udacity.com/"


// 用Moshi builder建立moshi object
// KotlinJsonAdapterFactory可讓moshi annotation可以使用在kotlin
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Use Retrofit Builder with ScalarsConverterFactory and BASE_URL
private val retrofit = Retrofit.Builder()
    // MoshiConverterFactory可讓retrofit使用Moshi去convert Json object
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL) // server的url
    .build()
// ScalarsConverterFactory可讓JSON訊息變成String
//    .addConverterFactory(ScalarsConverterFactory.create())


// Implement the MarsApiService interface with @GET getProperties returning a String
interface TaipeiZooAreaApiService {
    // 定義我們希望這個method使用的end point or path
    // The end point for the JSON response is realestate
//    @GET("5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a")
//    fun getProperties(@Query("scope") type: String):
//            Call<GetResult>

//            Call<String> // Call Object is used to start the request

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    @GET("pokemon")
//    fun fetchPirateList(
//        @Query("limit") limit: Int = 10,
//        @Query("offset") offset: Int = 0
//    ): Call<PirateResponse>

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GET("realestate")
    fun getProperties():
    // Call的裡面是要求retrofit回傳的資料類型
            Call<List<MarsProperty>>


}

// Create the MarsApi object using Retrofit to implement the MarsApiService
object TaipeiZooAreaApi {
    val retrofitService: TaipeiZooAreaApiService by lazy {
        retrofit.create(TaipeiZooAreaApiService::class.java)
    }
}