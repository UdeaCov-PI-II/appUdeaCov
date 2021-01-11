package co.edu.udea.udeacov.network

import co.edu.udea.udeacov.network.request.AuthRequestDto
import co.edu.udea.udeacov.network.response.AuthResponseDto
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query


private const val BASE_URL = "http://192.168.1.1:8080/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface UdeaCovAuthService{
    @POST("auth/signin")
    fun authenticate(@Query("message") showErrorMessage : Boolean ,@Body authRequest : AuthRequestDto) : Deferred<AuthResponseDto>
}

object udeaCovApiService {
    val authService : UdeaCovAuthService by lazy {
        retrofit.create(UdeaCovAuthService::class.java)
    }
    val converter = moshi
}