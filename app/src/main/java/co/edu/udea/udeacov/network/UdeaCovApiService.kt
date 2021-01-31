package co.edu.udea.udeacov.network

import co.edu.udea.udeacov.network.request.AuthRequestDto
import co.edu.udea.udeacov.network.request.PermissionRequestDto
import co.edu.udea.udeacov.network.request.SignUpRequestDto
import co.edu.udea.udeacov.network.response.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


private const val BASE_URL = "https://rocky-forest-36799.herokuapp.com/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface UdeaCovAuthService{
    @POST("auth/signin")
    fun authenticate(@Query("message") showErrorMessage : Boolean ,@Body authRequest : AuthRequestDto) : Deferred<AuthResponseDto>

    @POST("auth/signup")
    fun signup(@Query("message") showErrorMessage: Boolean, @Body signUpRequest : SignUpRequestDto) : Deferred<SignUpResponseDto>
}

interface PermissionService {
    @POST("permissions")
    fun createPermission(@Query("message") showErrorMessage: Boolean, @Body permissionRequestDto: PermissionRequestDto): Deferred<PermissionResponseDto>
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