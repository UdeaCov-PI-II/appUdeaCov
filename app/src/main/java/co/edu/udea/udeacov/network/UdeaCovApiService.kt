package co.edu.udea.udeacov.network

import co.edu.udea.udeacov.UdeaCovApplication
import co.edu.udea.udeacov.network.interceptor.AuthTokenInterceptor
import co.edu.udea.udeacov.network.request.AuthRequestDto
import co.edu.udea.udeacov.network.request.DeviceTokenRequestDto
import co.edu.udea.udeacov.network.response.AuthResponseDto
import co.edu.udea.udeacov.network.response.DeviceTokenResponseDto
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


private const val BASE_URL = "https://rocky-forest-36799.herokuapp.com/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val okHttpClient =  OkHttpClient.Builder()
    .addInterceptor(AuthTokenInterceptor(UdeaCovApplication.getAppContext()!!))
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

interface UserService{
    @PUT("users/{userId}/deviceToken")
    fun updateTokenForUser(@Path("userId") userId : String, @Body deviceTokenRequest : DeviceTokenRequestDto) : Deferred<DeviceTokenResponseDto>
}

interface UdeaCovAuthService{
    @POST("auth/signin")
    fun authenticate(@Query("message") showErrorMessage : Boolean ,@Body authRequest : AuthRequestDto) : Deferred<AuthResponseDto>
}

object udeaCovApiService {
    val userService : UserService by lazy {
        retrofit.create(UserService::class.java)
    }

    val authService : UdeaCovAuthService by lazy {
        retrofit.create(UdeaCovAuthService::class.java)
    }
    val converter = moshi
}