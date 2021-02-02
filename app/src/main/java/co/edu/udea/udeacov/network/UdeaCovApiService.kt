package co.edu.udea.udeacov.network

import co.edu.udea.udeacov.UdeaCovApplication
import co.edu.udea.udeacov.network.interceptor.AuthTokenInterceptor
import co.edu.udea.udeacov.network.request.*
import co.edu.udea.udeacov.network.response.*
import co.edu.udea.udeacov.network.response.AuthResponseDto
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


private const val BASE_URL = "https://rocky-forest-36799.herokuapp.com/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val okHttpClient =  OkHttpClient.Builder()
    .connectTimeout(240, TimeUnit.SECONDS)
    .readTimeout(240,TimeUnit.SECONDS)
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

interface RoleService{
    @GET("roles/approvers")
    fun getApproversRoles() : Deferred<List<RoleResponseDto>>
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
    fun getPermissionById(@Path("permissionId") permissionId: String, @Query("message") showErrorMessage: Boolean): Deferred<PermissionResponseDto>

    @GET("permissions")
    fun getPermissionByUser(@Query("userId") userId: String, @Query("message") showErrorMessage: Boolean): Deferred<List<PermissionCardResponseDto>>

    @GET("permissions")
    fun getPermissionByRole(@Query("userRole") role: String, @Query("message") showErrorMessage: Boolean): Deferred<List<PermissionCardResponseDto>>

    @Multipart
    @PUT("permissions/{id}/medias")
    fun uploadImages(@Path("id") id: String,
                     @Part coronAppEvidence: MultipartBody.Part,
                     @Part medellinMeCuidaEvidence: MultipartBody.Part,
                     @Query("message") showErrorMessage: Boolean): Deferred<CreatePermissionResponseDto>

    @GET("permissions")
    fun getPermissionBydocTypeAndDocNumber(@Query("docType") docType: String, @Query("docNumber") docNumber: String, @Query("showOnlyApproved") showOnlyApproved: Boolean, @Query("message") showErrorMessage: Boolean): Deferred<List<PermissionCardResponseDto>>

    @POST("permissions/{id}/approval")
    fun createApproval(@Path("id") id: String, @Body approvalRequest: ApprovalRequestDto,
                         @Query("message") showErrorMessage: Boolean): Deferred<CreatePermissionResponseDto>
}

interface LocationService{
    @GET("locations")
    fun getLocations() : Deferred<List<LocationResponseDTO>>
}

interface EntranceService{
    @POST("entrances")
    fun createEntrance(@Query("message") showErrorMessage: Boolean, @Body createEntranceRequestDto: CreateEntranceRequestDto): Deferred<CreateEntranceResponseDto>
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
    val entranceService : EntranceService by lazy {
        retrofit.create(EntranceService::class.java)
    }
    val roleService : RoleService by lazy {
        retrofit.create(RoleService::class.java)
    }

    val converter = moshi
}