package co.edu.udea.udeacov.services

import android.content.Context
import android.util.Log
import co.edu.udea.udeacov.R
import co.edu.udea.udeacov.UdeaCovApplication
import com.google.firebase.messaging.FirebaseMessagingService

class DefaultFirebaseMessagingService : FirebaseMessagingService(){

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("DefaultFirebaseMessagingService", "Refreshed token: $token")
        val context = UdeaCovApplication.getAppContext()!!
        val sharedPref = context.
            getSharedPreferences(context.getString(R.string.user_settings_file), Context.MODE_PRIVATE)
        with(sharedPref.edit()){
            putString(context.getString(R.string.user_device_token),token)
            apply()
        }
    }
}