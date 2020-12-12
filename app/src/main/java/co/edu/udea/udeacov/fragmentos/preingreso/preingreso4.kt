package co.edu.udea.udeacov.fragmentos.preingreso

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.navigation.findNavController
import co.edu.udea.udeacov.R
import kotlinx.android.synthetic.main.fragment_preingreso4.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [preingreso4.newInstance] factory method to
 * create an instance of this fragment.
 */
class preingreso4 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var bandera: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun validate() :Boolean{
        val radioGroup1 = preingreso4_radioGroup1.checkedRadioButtonId
        val radioGroup2 = preingreso4_radioGroup2.checkedRadioButtonId
        val radioGroup3 = preingreso4_radioGroup3.checkedRadioButtonId
        val radioGroup4 = preingreso4_radioGroup4.checkedRadioButtonId
        val radioGroup5 = preingreso4_radioGroup5.checkedRadioButtonId
        val radioGroup6 = preingreso4_radioGroup6.checkedRadioButtonId
        val radioGroup7 = preingreso4_radioGroup7.checkedRadioButtonId
        val radioGroup8 = preingreso4_radioGroup8.checkedRadioButtonId
        val radioGroup9 = preingreso4_radioGroup9.checkedRadioButtonId
        val radioGroup10 = preingreso4_radioGroup10.checkedRadioButtonId
        val radioGroup11 = preingreso4_radioGroup11.checkedRadioButtonId
        val radioGroup12 = preingreso4_radioGroup12.checkedRadioButtonId
        val radioGroup13 = preingreso4_radioGroup13.checkedRadioButtonId
        val radioGroup14 = preingreso4_radioGroup14.checkedRadioButtonId
        val radioGroup15 = preingreso4_radioGroup15.checkedRadioButtonId
        val radioGroup16 = preingreso4_radioGroup16.checkedRadioButtonId
        val radioGroup17 = preingreso4_radioGroup17.checkedRadioButtonId
        val radioGroup18 = preingreso4_radioGroup18.checkedRadioButtonId

        if(preingreso4_peso.text.toString().isEmpty()){
            preingreso4_peso.error = "campo vacío"
            return false
        }else if(preingreso4_altura.text.toString().isEmpty()){
            preingreso4_altura.error = "campo vacío"
            return false
        }else if(radioGroup1 == -1 || radioGroup2 == -1 || radioGroup3 == -1 || radioGroup4 == -1 || radioGroup5 == -1 || radioGroup6 == -1 || radioGroup7 == -1 || radioGroup8 == -1 ||
            radioGroup9 == -1 || radioGroup10 == -1 || radioGroup11 == -1 || radioGroup12 == -1 || radioGroup13 == -1 || radioGroup14 == -1 || radioGroup15 == -1 || radioGroup16 == -1 ){
            Toast.makeText(activity, "Debes introducir una respuesta por fila", Toast.LENGTH_SHORT).show();
            return false
        }else if(preingreso4_checkBox1.isChecked == false && preingreso4_checkBox2.isChecked == false && preingreso4_checkBox3.isChecked == false && preingreso4_checkBox4.isChecked == false
            && preingreso4_checkBox5.isChecked == false){
            Toast.makeText(activity, "Registrar si tiene algún tipo de incapacidad", Toast.LENGTH_SHORT).show();
            return false
        }else if(bandera==true && preingreso4_otro.text.toString().isEmpty()){
            preingreso4_otro.error = "campo vacío"
            return false
        }
        else if(radioGroup17 == -1){
            Toast.makeText(activity, "Ingresar grupo sanguíneo", Toast.LENGTH_SHORT).show();
            return false
        }else if(radioGroup18 == -1){
            Toast.makeText(activity, "Ingresar si convives actualmente con otras personas", Toast.LENGTH_SHORT).show();
            return false
        }
        return true
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preingreso4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var rg = view.findViewById<RadioGroup>(R.id.preingreso4_radioGroup18)
        var option = ""
        rg.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                var aux = preingreso4_radioGroup18.checkedRadioButtonId
                var radioButton: View = preingreso4_radioGroup18.findViewById(aux)
                var indice: Int = preingreso4_radioGroup18.indexOfChild(radioButton)
                var respuesta: RadioButton = preingreso4_radioGroup18.getChildAt(indice) as RadioButton
                option = respuesta.text.toString()

            }
        }
        preingreso4_checkBox5.setOnClickListener {
            var editText1 = view.findViewById<EditText>(R.id.preingreso4_otro)
            if(preingreso4_checkBox5.isChecked == true){
                bandera = true
                editText1.visibility = View.VISIBLE
            }else{
                bandera = false
                editText1.visibility = View.INVISIBLE
            }
        }

        preingresobtn_siguiente4.setOnClickListener{
            if(validate()){
                if(option == "Si"){
                    Toast.makeText(activity, "Campos diligenciados", Toast.LENGTH_SHORT).show();
                    it.findNavController().navigate(R.id.action_preingreso4_to_preingreso5)
                } else{
                    Toast.makeText(activity, "Campos diligenciados", Toast.LENGTH_SHORT).show();
                    it.findNavController().navigate(R.id.action_preingreso4_to_preingreso62)
                }
            }
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment preingreso4.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            preingreso4().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}