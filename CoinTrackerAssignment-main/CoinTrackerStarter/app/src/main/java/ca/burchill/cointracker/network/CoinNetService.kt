package ca.burchill.cointracker.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers


private const val BASE_URL = "https://pro-api.coinmarketcap.com/"
private const val YOUR_API_KEY = "6cfea0b3-317e-4d36-9997-d7d8bb8723f2"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

/**
 * A retrofit service to fetch a coins list.
 */
interface CoinApiService {
    @Headers(
        "X-CMC_PRO_API_KEY: ${YOUR_API_KEY}",
        "Accept: application/json"
    )
    @GET("v1/cryptocurrency/listings/latest?start=1&limit=50&convert=USD")
    suspend fun getCoins():  CoinApiResponse
}

object CoinApi {
    val retrofitService : CoinApiService by lazy {
        retrofit.create(CoinApiService::class.java)
    }
}