package co.edu.udea.udeacov.fragmentos.preingreso


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import co.edu.udea.udeacov.R
import co.edu.udea.udeacov.network.request.SignUpRequestDto
import kotlinx.android.synthetic.main.fragment_preingreso1.*
import kotlinx.android.synthetic.main.fragment_preingreso5.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Preingreso1.newInstance] factory method to
 * create an instance of this fragment.
 */
class Preingreso1 : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var bandera: Boolean? = null
    private lateinit var signUpRequestDto: SignUpRequestDto


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun validate() :Boolean{
        val radioGroup1 = preingreso1_radioGroup.checkedRadioButtonId

        if(preingreso1_username.text.toString().isEmpty()){
            preingreso1_username.error = "campo vacío"
            return false
        }else if(preingreso1_password.text.toString().isEmpty()){
            preingreso1_password.error = "campo vacío"
            return false
        }else if(preingreso1_correo.text.toString().isEmpty()){
            preingreso1_correo.error = "campo vacío"
            return false
        }else if(radioGroup1 == -1){
            Toast.makeText(activity, "Ingresar el tipo de documento", Toast.LENGTH_SHORT).show()
            return false
        }else if(bandera==true && preingreso1_otro.text.toString().isEmpty()){
            preingreso1_otro.error = "campo vacío"
            return false
        }else if(preingreso1_doc.text.toString().isEmpty()){
            preingreso1_doc.error = "campo vacío"
            return false
        }else if(preingreso1_nombre.text.toString().isEmpty()){
            preingreso1_nombre.error = "campo vacío"
            return false
        }else if(!preingreso1_autorizo.isChecked){
            preingreso1_autorizo.error = "Es importante tu información para que la Institución pueda dar cumplimiento a la Resolución 0666 del 24 de abril de 2010 expedida por el Ministerio de Salud y poder orientar acciones preventivas frente al COVID-19."
            return false
        }
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preingreso1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpRequestDto = SignUpRequestDto()

        //Tipo de Documento
        val rg = view.findViewById<RadioGroup>(R.id.preingreso1_radioGroup)
        var option = ""
        rg.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                val aux = preingreso1_radioGroup.checkedRadioButtonId
                val radioButton: View = preingreso1_radioGroup.findViewById(aux)
                val indice: Int = preingreso1_radioGroup.indexOfChild(radioButton)
                val respuesta: RadioButton = preingreso1_radioGroup.getChildAt(indice) as RadioButton
                option = respuesta.text.toString()
                signUpRequestDto.documentType = option
                val editText1 = view.findViewById<EditText>(R.id.preingreso1_otro)
                if(option == "Otro"){
                    bandera = true
                    editText1.visibility = View.VISIBLE
                } else{
                    bandera = false
                    editText1.visibility = View.GONE
                }
            }
        }

        preingresobtn_siguiente1.setOnClickListener{
            if(validate()){
                if (preingreso1_otro.text.toString().isNotEmpty()){
                    signUpRequestDto.documentType = preingreso1_otro.text.toString()
                }
                signUpRequestDto.roleId = "ROLE_USER"
                signUpRequestDto.username = preingreso1_username.text.toString()
                signUpRequestDto.password = preingreso1_password.text.toString()
                signUpRequestDto.email = preingreso1_correo.text.toString()
                signUpRequestDto.documentNumber = preingreso1_doc.text.toString()
                signUpRequestDto.fullName = preingreso1_nombre.text.toString()
                Toast.makeText(activity, "Campos diligenciados", Toast.LENGTH_SHORT).show()
                it.findNavController().navigate(Preingreso1Directions.actionPreingreso1ToPreingreso2(signUpRequestDto))
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
         * @return A new instance of fragment preingreso1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Preingreso1().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}