package co.edu.udea.udeacov.network.interceptor

import android.content.Context
import co.edu.udea.udeacov.R
import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor(val context: Context) : Interceptor {
    private  val sharedPref = context.getSharedPreferences(context.getString(R.string.user_settings_file),Context.MODE_PRIVATE)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val authToken = sharedPref.getString(context.getString(R.string.user_token),null)
        authToken?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}