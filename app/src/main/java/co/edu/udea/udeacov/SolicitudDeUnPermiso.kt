package co.edu.udea.udeacov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import com.shuhart.stepview.StepView

class SolicitudDeUnPermiso : AppCompatActivity() {
    private lateinit var stepView: StepView
    private lateinit var stepTextView: TextView
    private lateinit var descripcionTextView: TextView

    var stepIndex = 0
    var steptsTexts = arrayOf<String>("Paso 1","Paso 2","Paso 3", "Paso 4")
    var descriptionTexts = arrayOf<String>("Link de CoronApp","Link de Medellín Me Cuida",
                                            "Solicitud de Ingreso", "Declaración de Responsabilidad")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitud_de_un_permiso)

        stepTextView = findViewById(R.id.step_txtView);
        descripcionTextView = findViewById(R.id.descripcion_txtView)
        stepView = findViewById(R.id.step_view)

        stepView.state.animationType(StepView.ANIMATION_ALL)
            .stepsNumber(4)
            .animationDuration(resources.getInteger(android.R.integer.config_shortAnimTime))
            .commit()

        siguientePaso()
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