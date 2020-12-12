package co.edu.udea.udeacov.fragmentos.solicitud_permiso

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import co.edu.udea.udeacov.R
import kotlinx.android.synthetic.main.fragment_solicitud_ingreso2.*

class SolicitudIngreso2 : Fragment() {
    // TODO: Rename and change types of parameters

    var bandera: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_solicitud_ingreso2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        var rg = view.findViewById<RadioGroup>(R.id.SolicitudIngreso2_radioGroup2)
        var option = ""
        rg.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                var aux = SolicitudIngreso2_radioGroup2.checkedRadioButtonId
                var radioButton: View = SolicitudIngreso2_radioGroup2.findViewById(aux)
                var indice: Int = SolicitudIngreso2_radioGroup2.indexOfChild(radioButton)
                var respuesta: RadioButton = SolicitudIngreso2_radioGroup2.getChildAt(indice) as RadioButton
                option = respuesta.text.toString()
                var editText1 = view.findViewById<EditText>(R.id.SolicitudIngreso2_otro)
                if(option == "Otro"){
                    bandera = true
                    editText1.visibility = View.VISIBLE
                } else{
                    bandera = false
                    editText1.visibility = View.INVISIBLE
                }
            }
        }


    }


}