package co.edu.udea.udeacov.network

import co.edu.udea.udeacov.UdeaCovApplication
import co.edu.udea.udeacov.network.interceptor.AuthTokenInterceptor
import co.edu.udea.udeacov.network.request.AuthRequestDto
import co.edu.udea.udeacov.network.request.PermissionRequestDto
import co.edu.udea.udeacov.network.request.SignUpRequestDto
import co.edu.udea.udeacov.network.response.*
import co.edu.udea.udeacov.network.request.DeviceTokenRequestDto
import co.edu.udea.udeacov.network.response.AuthResponseDto
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

    @POST("auth/signup")
    fun signup(@Query("message") showErrorMessage: Boolean, @Body signUpRequest : SignUpRequestDto) : Deferred<SignUpResponseDto>
}

interface PermissionService {
    @POST("permissions")
    fun createPermission(@Query("message") showErrorMessage: Boolean, @Body permissionRequestDto: PermissionRequestDto): Deferred<CreatePermissionResponseDto>

    @GET("permissions/{permissionId}")
    fun getPermissionById(@Query("message") showErrorMessage: Boolean, @Path("permissionId") permissionId: String): Deferred<PermissionResponseDto>
}

interface LocationService{
    @GET("locations")
    fun getLocations() : Deferred<List<LocationResponseDTO>>
}

interface UnitService {
    @GET ("units")
    fun getUnits() : Deferred<List<UnitResponseDto>>
}



object udeaCovApiService {
    val userService : UserService by lazy {
        retrofit.create(UserService::class.java)
    }

    val authService : UdeaCovAuthService by lazy {
        retrofit.create(UdeaCovAuthService::class.java)
    }

    val permissionService : PermissionService by lazy {
        retrofit.create(PermissionService::class.java)
    }

    val locationService : LocationService by lazy {
        retrofit.create(LocationService::class.java)
    }
    val unitService : UnitService by lazy {
        retrofit.create(UnitService::class.java)
    }

    val converter = moshi
}