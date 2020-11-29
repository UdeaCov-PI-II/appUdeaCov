package co.edu.udea.udeacov

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
import kotlinx.android.synthetic.main.fragment_preingreso1.*
import kotlinx.android.synthetic.main.fragment_preingreso2.*
import kotlinx.android.synthetic.main.fragment_preingreso3.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [preingreso3.newInstance] factory method to
 * create an instance of this fragment.
 */
class preingreso3 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun validate() :Boolean{
        val radioGroup = preingreso3_radioGroup.checkedRadioButtonId
        val radioGroup3 = preingreso3_radioGroup1.checkedRadioButtonId

        if(preingreso3_bloques.text.toString().isEmpty()){
            preingreso3_bloques.error = "campo vacío"
            return false
        }else if(radioGroup == -1){
            Toast.makeText(activity, "Ingresar si perteneces a algún proyecto", Toast.LENGTH_SHORT).show();
            return false
        }else if(preingreso3_municipio.text.toString().isEmpty()){
            preingreso3_municipio.error = "campo vacío"
            return false
        }else if(radioGroup3 == -1){
            Toast.makeText(activity, "Ingresar como te transportas", Toast.LENGTH_SHORT).show();
            return false
        }
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preingreso3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var rg = view.findViewById<RadioGroup>(R.id.preingreso3_radioGroup1)
        var option = ""
        rg.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                var aux = preingreso3_radioGroup1.checkedRadioButtonId
                var radioButton: View = preingreso3_radioGroup1.findViewById(aux)
                var indice: Int = preingreso3_radioGroup1.indexOfChild(radioButton)
                var respuesta: RadioButton = preingreso3_radioGroup1.getChildAt(indice) as RadioButton
                option = respuesta.text.toString()
                var editText1 = view.findViewById<EditText>(R.id.preingreso3_otro)
                if(option == "Otro"){
                    editText1.visibility = View.VISIBLE
                } else{
                    editText1.visibility = View.INVISIBLE
                }
            }
        }
        preingresobtn_siguiente3.setOnClickListener{
            if(validate()){
                Toast.makeText(activity, "Campos diligenciados", Toast.LENGTH_SHORT).show();
                it.findNavController().navigate(R.id.action_preingreso3_to_preingreso4)
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
         * @return A new instance of fragment preingreso3.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            preingreso3().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}