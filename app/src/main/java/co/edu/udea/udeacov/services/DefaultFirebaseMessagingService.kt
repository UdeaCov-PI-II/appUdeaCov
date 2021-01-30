package co.edu.udea.udeacov.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class DefaultFirebaseMessagingService : FirebaseMessagingService(){

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("DefaultFirebaseMessagingService", "Refreshed token: $token")
    }
}