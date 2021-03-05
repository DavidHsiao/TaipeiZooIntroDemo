package android.example.com.taipeizoointrodemo.networkApi

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://data.taipei/api/v1/dataset/"


// 用Moshi builder建立moshi object
// KotlinJsonAdapterFactory可讓moshi annotation可以使用在kotlin
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Use Retrofit Builder with ScalarsConverterFactory and BASE_URL
private val retrofit = Retrofit.Builder()
    // MoshiConverterFactory可讓retrofit使用Moshi去convert Json object
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    // Enable Retrofit creates Coroutine based api
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL) // server的url
    .build()
// ScalarsConverterFactory可讓JSON訊息變成String
//    .addConverterFactory(ScalarsConverterFactory.create())


// Implement the MarsApiService interface with @GET getProperties returning a String
interface TaipeiZooAreaApiService {
    // 定義我們希望這個method使用的end point or path
    // The end point for the JSON response is realestate
    @GET("5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a")
    fun getProperties(@Query("scope") type: String):
            Deferred<GetResult>
}

// Create the MarsApi object using Retrofit to implement the MarsApiService
object TaipeiZooAreaApi {
    val retrofitService: TaipeiZooAreaApiService by lazy {
        retrofit.create(TaipeiZooAreaApiService::class.java)
    }
}