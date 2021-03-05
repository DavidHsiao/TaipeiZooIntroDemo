package android.example.com.taipeizoointrodemo.networkApi

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://data.taipei/api/v1/dataset/"

// Use Retrofit Builder with ScalarsConverterFactory and BASE_URL
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL) // server的url
    .build()

// Implement the MarsApiService interface with @GET getProperties returning a String
interface TaipeiZooAeraApiService {
    // 定義我們希望這個method使用的end point or path
    // The end point for the JSON response is realestate
    @GET("5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a")
    fun getProperties(@Query("scope") type: String):
            Call<String> // Call Object is used to start the request
}

// Create the MarsApi object using Retrofit to implement the MarsApiService
object TaipeiZooAeraApi {
    val retrofitService : TaipeiZooAeraApiService by lazy {
        retrofit.create(TaipeiZooAeraApiService::class.java)
    }
}