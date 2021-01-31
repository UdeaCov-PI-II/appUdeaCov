package co.edu.udea.udeacov.fragmentos.preingreso

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import co.edu.udea.udeacov.R
import co.edu.udea.udeacov.databinding.FragmentPreingreso3Binding
import co.edu.udea.udeacov.fragmentos.preingreso.viewmodels.preingreso3.Preingreso3ViewModel
import co.edu.udea.udeacov.network.request.SignUpRequestDto
import kotlinx.android.synthetic.main.fragment_preingreso2.*
import kotlinx.android.synthetic.main.fragment_preingreso3.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Preingreso3.newInstance] factory method to
 * create an instance of this fragment.
 */
class Preingreso3 : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    var bandera: Boolean? = null
    private lateinit var signUpRequestDto: SignUpRequestDto
    private lateinit var viewModel: Preingreso3ViewModel
    private lateinit var binding: FragmentPreingreso3Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Recuperar argumentos anteriores
        val args = arguments?.let { Preingreso3Args.fromBundle(it) }
        signUpRequestDto = args!!.signUpRequest

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_preingreso3, container, false)

        //instanciamos el viewModel
        viewModel = ViewModelProvider(this).get(Preingreso3ViewModel::class.java)

        //unir el binding con el viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.getLocations()
        viewModel.getUnits()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Observar el cambio de location ID seleccionado por el usuario
        viewModel.locationIdSelected.observe(viewLifecycleOwner, Observer{
            it?.let {
                signUpRequestDto.universityInfo.locationId = it
            }
        })

        //Observar el cambio de unit ID
        viewModel.unitIdSelected.observe(viewLifecycleOwner, Observer {
            it?.let {
                signUpRequestDto.universityInfo.unitId = it
            }
        })

        val rgProyectoExtension = view.findViewById<RadioGroup>(R.id.preingreso3_radioGroup)
        var rtaProjectoExtension = ""
        rgProyectoExtension.setOnCheckedChangeListener { _, i ->
            if (i != -1) {
                val aux = preingreso3_radioGroup.checkedRadioButtonId
                val radioButton: View = preingreso3_radioGroup.findViewById(aux)
                val indice: Int = preingreso3_radioGroup.indexOfChild(radioButton)
                val respuesta: RadioButton = preingreso3_radioGroup.getChildAt(indice) as RadioButton
                rtaProjectoExtension = respuesta.text.toString()
                signUpRequestDto.universityInfo.belongToExtensionProject = rtaProjectoExtension == "Si"
            }


            val rg = view.findViewById<RadioGroup>(R.id.preingreso3_radioGroup1)
            var option = ""
            rg.setOnCheckedChangeListener { _, i ->
                if (i != -1) {
                    val aux = preingreso3_radioGroup1.checkedRadioButtonId
                    val radioButton: View = preingreso3_radioGroup1.findViewById(aux)
                    val indice: Int = preingreso3_radioGroup1.indexOfChild(radioButton)
                    val respuesta: RadioButton =
                        preingreso3_radioGroup1.getChildAt(indice) as RadioButton
                    option = respuesta.text.toString()
                    signUpRequestDto.universityInfo.transportationMode = option
                    val editText1 = view.findViewById<EditText>(R.id.preingreso3_otro)
                    if (option == "Otro") {
                        bandera = true
                        editText1.visibility = View.VISIBLE
                    } else {
                        bandera = false
                        editText1.visibility = View.GONE
                    }
                }
            }

            preingresobtn_siguiente3.setOnClickListener {
                if (validate()) {
                    if (preingreso3_otro.text.toString().isNotEmpty()){
                        signUpRequestDto.universityInfo.transportationMode = preingreso3_otro.text.toString()
                    }
                    viewModel.locationNameSelected.value = preingreso3_spinner.selectedItem.toString()
                    signUpRequestDto.universityInfo.buildingAndOffice = preingreso3_bloques.text.toString()
                    signUpRequestDto.universityInfo.extensionProjectName = preingreso3_proyecto.text.toString()
                    viewModel.unitNameSelected.value = preingreso3_spinner1.selectedItem.toString()
                    signUpRequestDto.town = preingreso3_municipio.text.toString()
                    Toast.makeText(activity, "Campos diligenciados", Toast.LENGTH_SHORT).show()
                    it.findNavController().navigate(Preingreso3Directions.actionPreingreso3ToPreingreso4(signUpRequestDto))
                }
            }
        }

    }

    private fun validate() :Boolean{
        val radioGroup = preingreso3_radioGroup.checkedRadioButtonId
        val radioGroup3 = preingreso3_radioGroup1.checkedRadioButtonId

        if(preingreso3_bloques.text.toString().isEmpty()){
            preingreso3_bloques.error = "campo vacío"
            return false
        }else if(radioGroup == -1){
            Toast.makeText(activity, "Ingresar si perteneces a algún proyecto", Toast.LENGTH_SHORT).show()
            return false
        }else if(preingreso3_municipio.text.toString().isEmpty()){
            preingreso3_municipio.error = "campo vacío"
            return false
        }else if(radioGroup3 == -1){
            Toast.makeText(activity, "Ingresar como te transportas", Toast.LENGTH_SHORT).show()
            return false
        }else if(bandera==true && preingreso3_otro.text.toString().isEmpty()){
            preingreso3_otro.error = "campo vacío"
            return false
        }
        return true
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

        fun newInstance(param1: String, param2: String) =
            Preingreso3().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}