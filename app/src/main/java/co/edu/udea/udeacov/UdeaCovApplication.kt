package co.edu.udea.udeacov

import android.app.Application
import android.content.Context

class UdeaCovApplication: Application() {

    companion object{
        private var context: Context? = null

        fun getAppContext() : Context? {
            return context;
        }
    }

    override fun onCreate() {
        super.onCreate()
        UdeaCovApplication.context = applicationContext
    }
}