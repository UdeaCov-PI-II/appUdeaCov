package co.edu.udea.udeacov.activities.porteria

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import co.edu.udea.udeacov.R
import co.edu.udea.udeacov.activities.main.MainActivity

class PersonalDeSeguridad : AppCompatActivity() {
    private val activityTitle = "Personal de Seguridad"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_de_seguridad)



        //Modificar el titulo del actionBar
        this.supportActionBar?.title = activityTitle



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    // Item Cerrar sesion
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.cerrar_session_item ->{
                removeContentOfPreferencesFile()
                val intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    private fun removeContentOfPreferencesFile() {
        val sharedPref = this.getSharedPreferences(
            getString(R.string.user_settings_file),
            Context.MODE_PRIVATE
        )
        with(sharedPref.edit()) {
            remove(getString(R.string.user_id))
            remove(getString(R.string.user_role))
            remove(getString(R.string.user_token))
            apply()
        }
    }

}



