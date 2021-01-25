package co.edu.udea.udeacov.fragmentos.preingreso

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import co.edu.udea.udeacov.R
import co.edu.udea.udeacov.databinding.FragmentPreingreso2Binding
import co.edu.udea.udeacov.network.request.SignUpRequestDto
import kotlinx.android.synthetic.main.fragment_preingreso2.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Preingreso2.newInstance] factory method to
 * create an instance of this fragment.
 */
class Preingreso2 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var bandera: Boolean? = null
    private lateinit var signUpRequestDto: SignUpRequestDto
    lateinit var binding : FragmentPreingreso2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun validate() :Boolean{
        if(preingreso2_correoN.text.toString().isEmpty()){
            preingreso2_correoN.error = "campo vacío"
            return false
        }else if(!preingreso2_checkBox1.isChecked && !preingreso2_checkBox2.isChecked && !preingreso2_checkBox3.isChecked && !preingreso2_checkBox4.isChecked && !preingreso2_checkBox5.isChecked && !preingreso2_checkBox6.isChecked && !preingreso2_checkBox7.isChecked && !preingreso2_checkBox8.isChecked){
            Toast.makeText(activity, "Ingresar vínculo con la universidad", Toast.LENGTH_SHORT).show();
            return false
        }else if(preingreso2_cargo.text.toString().isEmpty()){
            preingreso2_cargo.error = "campo vacío"
            return false
        }else if(bandera==true && preingreso2_otro.text.toString().isEmpty()){
            preingreso2_otro.error = "campo vacío"
            return false
        }else if(preingreso2_telefono.text.toString().isEmpty()){
            preingreso2_telefono.error = "campo vacío"
            return false
        }
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Recuperar argumentos anteriores
        val args = arguments?.let { Preingreso2Args.fromBundle(it) }
        signUpRequestDto = args!!.signUpRequest

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_preingreso2, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var rg = view.findViewById<RadioGroup>(R.id.rgRiesgosLaborales)
        var option = ""
        rg.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                var aux = rgRiesgosLaborales.checkedRadioButtonId
                var radioButton: View = rgRiesgosLaborales.findViewById(aux)
                var indice: Int = rgRiesgosLaborales.indexOfChild(radioButton)
                var respuesta: RadioButton = rgRiesgosLaborales.getChildAt(indice) as RadioButton
                option = respuesta.text.toString()
                signUpRequestDto.arlName = option
                var editText1 = view.findViewById<EditText>(R.id.preingreso2_otro)
                if(option == "Otro"){
                    bandera = true
                    editText1.visibility = View.VISIBLE
                    signUpRequestDto.arlName = preingreso2_otro.text.toString()
                } else{
                    bandera = false
                    editText1.visibility = View.GONE
                }
            }
        }

        preingresobtn_siguiente2.setOnClickListener{
            if(validate()){
                signUpRequestDto.birthday = preingreso2_fecha.text.toString()
                signUpRequestDto.personalEmail = preingreso2_correoN.text.toString()
                signUpRequestDto.birthday = preingreso2_fecha.text.toString()
                signUpRequestDto.universityInfo.detailUniversityRelation = preingreso2_vinculo.text.toString()
                signUpRequestDto.universityInfo.occupation = preingreso2_cargo.text.toString()
                signUpRequestDto.phoneContact = preingreso2_telefono.text.toString()
                Toast.makeText(activity, "Campos diligenciados", Toast.LENGTH_SHORT).show();
                it.findNavController().navigate(Preingreso2Directions.actionPreingreso2ToPreingreso3(signUpRequestDto))
            }
        }

    }

    fun onCheckboxClicked(view: View) {
        var checked = view as CheckBox
        if (checked.isChecked) {
            signUpRequestDto.universityInfo.universityRelation.add(checked.text.toString())
        } else {
            signUpRequestDto.universityInfo.universityRelation.remove(checked.text.toString())
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment preingreso2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Preingreso2().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}