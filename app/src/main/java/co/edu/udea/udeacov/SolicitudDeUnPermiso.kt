package co.edu.udea.udeacov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.LinkMovementMethod
import android.widget.Button
import android.widget.TextView
import com.shuhart.stepview.StepView

class SolicitudDeUnPermiso : AppCompatActivity() {
    private lateinit var stepView: StepView
    private lateinit var stepTextView: TextView
    private lateinit var descripcionTextView: TextView
    private lateinit var link_coronapp: TextView


    var stepIndex = 0
    var steptsTexts = arrayOf<String>("Paso 1","Paso 2","Paso 3", "Paso 4")
    var descriptionTexts = arrayOf<String>("Link de CoronApp","Link de Medellín Me Cuida",
                                            "Solicitud de Ingreso", "Declaración de Responsabilidad")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitud_de_un_permiso)

       // link_coronapp = findViewById(R.id.link_enlace_coroapp)
        // link_coronapp.movementMethod = LinkMovementMethod.getInstance()

    }





    private fun siguientePaso(){
        Handler(Looper.getMainLooper()).postDelayed({
            /* Create an Intent that will start the Menu-Activity. */
            stepIndex++
            if (stepIndex <= steptsTexts.size-1){
                this.stepTextView.text = steptsTexts[stepIndex]
                this.descripcionTextView.text = descriptionTexts[stepIndex]
                this.stepView.go(stepIndex, true)
                siguientePaso()
            }
        }, 3000)
    }

}