package co.edu.udea.udeacov.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.edu.udea.udeacov.R
import com.kofigyan.stateprogressbar.StateProgressBar


class SolicitudDeUnPermiso : AppCompatActivity() {
    private lateinit var stepTextView: TextView
    private lateinit var descripcionTextView: TextView
    private lateinit var link_coronapp: TextView

    var stepIndex = 0
    var steptsTexts = arrayOf<String>("Paso 1","Paso 2","Paso 3", "Paso 4")
    var descriptionTexts = arrayOf<String>("Link de CoronApp","Link de Medellín Me Cuida",
                                            "Solicitud de Ingreso", "Declaración de Responsabilidad")

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
        if(resultCode== RESULT_OK && StaticData.bandera1 == false ){
            StaticData.bandera1 = true
            val Uri = data?.getData();
            Toast.makeText(this, "Imagen subida", Toast.LENGTH_SHORT).show();

        }else if(resultCode== RESULT_OK && StaticData.bandera1 == true){
            StaticData.bandera2 = true
            val Uri = data?.getData();
            Toast.makeText(this, "Imagen subida", Toast.LENGTH_SHORT).show();
        }

    }





}