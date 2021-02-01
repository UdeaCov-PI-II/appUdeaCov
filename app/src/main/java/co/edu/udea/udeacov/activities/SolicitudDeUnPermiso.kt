package co.edu.udea.udeacov.activities

import android.content.Intent
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import co.edu.udea.udeacov.R
import co.edu.udea.udeacov.activities.main.MainActivity
import com.kofigyan.stateprogressbar.StateProgressBar


class SolicitudDeUnPermiso : AppCompatActivity() {
    private lateinit var stepTextView: TextView
    private lateinit var descripcionTextView: TextView
    private lateinit var link_coronapp: TextView

    var stepIndex = 0
    var steptsTexts = arrayOf<String>("Paso 1","Paso 2","Paso 3", "Paso 4")
    var descriptionTexts = arrayOf<String>("Link de CoronApp","Link de Medellín Me Cuida",
                                            "Declaración de Responsabilidad", "Solicitud de Ingreso")

    var descriptionData =
        arrayOf("Paso 1", "Paso 2", "Paso 3", "Paso 4")

    object StaticData {
        var bandera1 = false
        var bandera2 = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitud_de_un_permiso)

        val stateProgressBar = findViewById<StateProgressBar>(R.id.your_state_progress_bar_id)
        stateProgressBar.setStateDescriptionData(descriptionData)
       // link_coronapp = findViewById(R.id.link_enlace_coroapp)
        // link_coronapp.movementMethod = LinkMovementMethod.getInstance()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK && !StaticData.bandera1){
            StaticData.bandera1 = true
            data?.getData()
            Toast.makeText(this, "Imagen subida", Toast.LENGTH_SHORT).show()

        }else if(resultCode== RESULT_OK && StaticData.bandera1){
            StaticData.bandera2 = true
            data?.getData()
            Toast.makeText(this, "Imagen subida", Toast.LENGTH_SHORT).show()
        }
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