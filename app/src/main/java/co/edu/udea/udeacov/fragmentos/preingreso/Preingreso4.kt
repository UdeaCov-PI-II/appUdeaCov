package co.edu.udea.udeacov.fragmentos.preingreso

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import co.edu.udea.udeacov.R
import co.edu.udea.udeacov.databinding.FragmentPreingreso4Binding
import co.edu.udea.udeacov.fragmentos.preingreso.viewmodels.preingreso4.Preingreso4ViewModel
import co.edu.udea.udeacov.fragmentos.preingreso.viewmodels.preingreso5.Preingreso5ViewModel
import co.edu.udea.udeacov.network.request.SignUpRequestDto
import kotlinx.android.synthetic.main.fragment_preingreso2.*
import kotlinx.android.synthetic.main.fragment_preingreso4.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Preingreso4.newInstance] factory method to
 * create an instance of this fragment.
 */
class Preingreso4 : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    var bandera: Boolean? = null
    private lateinit var viewModel: Preingreso4ViewModel
    private lateinit var signUpRequestDto: SignUpRequestDto
    lateinit var binding : FragmentPreingreso4Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun validate() :Boolean{
        val rgPresionArterial = rg_presion_arterial.checkedRadioButtonId
        val rgEnfermCorazon = rg_enferm_corazon.checkedRadioButtonId
        val rgColesterol = rg_colesterol.checkedRadioButtonId
        val rgEnfermRenal = rg_enfermedadRenal.checkedRadioButtonId
        val rgDiabetes = rg_diabetes.checkedRadioButtonId
        val rgEnfermPulmonar = rg_enfermedadPulmonar.checkedRadioButtonId
        val rgAsma = rg_asma.checkedRadioButtonId
        val rgAlteracionInmunidad = rg_alteracionInmunidad.checkedRadioButtonId
        val rgCancer = rg_cancer.checkedRadioButtonId
        val rgEsteroides = rg_esteroides.checkedRadioButtonId
        val rgEnfermHepatica = rg_enfermadadHepatica.checkedRadioButtonId
        val rgObesidad = rg_obesidad.checkedRadioButtonId
        val rgFumador = rg_fumador.checkedRadioButtonId
        val rgEnfermedadRara = rg_enfermedadRara.checkedRadioButtonId
        val rgEmbarazo = rg_embarazo.checkedRadioButtonId
        val rgParto = rg_parto.checkedRadioButtonId
        val rgGrupoSanguineo = rg_grupo_sanguineo.checkedRadioButtonId
        val rgConvivencia = rg_convivesConOtros.checkedRadioButtonId

        if(preingreso4_peso.text.toString().isEmpty()){
            preingreso4_peso.error = "campo vacío"
            return false
        }else if(preingreso4_altura.text.toString().isEmpty()){
            preingreso4_altura.error = "campo vacío"
            return false
        }else if(rgPresionArterial == -1 || rgEnfermCorazon == -1 || rgColesterol == -1 || rgEnfermRenal == -1 || rgDiabetes == -1 || rgEnfermPulmonar == -1 || rgAsma == -1 || rgAlteracionInmunidad == -1 ||
            rgCancer == -1 || rgEsteroides == -1 || rgEnfermHepatica == -1 || rgObesidad == -1 || rgFumador == -1 || rgEnfermedadRara == -1 || rgEmbarazo == -1 || rgParto == -1 ){
            Toast.makeText(activity, "Debes introducir una respuesta por fila", Toast.LENGTH_SHORT).show()
            return false
        }else if(!cb_ningunaDiscapacidad.isChecked && !cb_visualPermanente.isChecked && !cb_motrizPermanente.isChecked && !cb_auditivaPermanente.isChecked && !cb_otra_discapacidad.isChecked){
            Toast.makeText(activity, "Registrar si tiene algún tipo de incapacidad", Toast.LENGTH_SHORT).show()
            return false
        }else if(bandera==true && input_otraDiscapacidad.text.toString().isEmpty()){
            input_otraDiscapacidad.error = "campo vacío"
            return false
        }
        else if(rgGrupoSanguineo == -1){
            Toast.makeText(activity, "Ingresar grupo sanguíneo", Toast.LENGTH_SHORT).show()
            return false
        }else if(rgConvivencia == -1){
            Toast.makeText(activity, "Ingresar si convives actualmente con otras personas", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Recuperar argumentos anteriores
        val args = arguments?.let { Preingreso4Args.fromBundle(it) }
        signUpRequestDto = args!!.signUpRequest

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_preingreso4, container, false )

        //Instanciamos la variable del fragment
        binding.fragmentPreingreso4 = this

        //instanciamos el ViewModel
        viewModel = ViewModelProvider(this).get(Preingreso4ViewModel::class.java)

        //conectamos el binding con el viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel= viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.responseError.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                viewModel.showErrorIsCompleted()
            }
        })

        viewModel.signUpResponse.observe(viewLifecycleOwner, Observer {
            it?.let {
                view.findNavController().navigate(R.id.action_preingreso4_to_mainActivity)
                viewModel.navigationIsCompleted()
            }
        })

        leerCondicionesDeSalud()

        val rgGrupoSanguineo = view.findViewById<RadioGroup>(R.id.rg_grupo_sanguineo)
        var rtaGrupoSanguineo = ""
        rgGrupoSanguineo.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                val aux = rg_grupo_sanguineo.checkedRadioButtonId
                val radioButton: View = rg_grupo_sanguineo.findViewById(aux)
                val indice: Int = rg_grupo_sanguineo.indexOfChild(radioButton)
                val respuesta: RadioButton = rg_grupo_sanguineo.getChildAt(indice) as RadioButton
                rtaGrupoSanguineo = respuesta.text.toString()
                signUpRequestDto.healthInfo.bloodType = rtaGrupoSanguineo
            }
        }


        val rg = view.findViewById<RadioGroup>(R.id.rg_convivesConOtros)
        var option = ""
        rg.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                val aux = rg_convivesConOtros.checkedRadioButtonId
                val radioButton: View = rg_convivesConOtros.findViewById(aux)
                val indice: Int = rg_convivesConOtros.indexOfChild(radioButton)
                val respuesta: RadioButton = rg_convivesConOtros.getChildAt(indice) as RadioButton
                option = respuesta.text.toString()
            }
        }

        cb_otra_discapacidad.setOnClickListener {
            var editText1 = view.findViewById<EditText>(R.id.input_otraDiscapacidad)
            if(cb_otra_discapacidad.isChecked){
                bandera = true
                editText1.visibility = View.VISIBLE
            }else{
                bandera = false
                editText1.visibility = View.GONE
            }
        }

        preingresobtn_siguiente4.setOnClickListener{
            if(validate()){
                if (input_otraDiscapacidad.text.toString().isNotEmpty()){
                    signUpRequestDto.healthInfo.hasPermanentDisability = input_otraDiscapacidad.text.toString()
                }
                signUpRequestDto.healthInfo.weight = preingreso4_peso.text.toString().toFloat()
                signUpRequestDto.healthInfo.height = preingreso4_altura.text.toString().toFloat()
                if(option == "Si"){
                    signUpRequestDto.healthInfo.hasRoomates = true
                    Toast.makeText(activity, "Campos diligenciados", Toast.LENGTH_SHORT).show()
                    it.findNavController().navigate(Preingreso4Directions.actionPreingreso4ToPreingreso5(signUpRequestDto))
                } else{
                    signUpRequestDto.healthInfo.hasRoomates = false
                    Toast.makeText(activity, "Formulario enviado exitosamente", Toast.LENGTH_SHORT).show()
                    viewModel.signup(signUpRequestDto)
                }
            }
        }

    }

    fun seleccionarDiscapacidadPermanente (view: View) {
        val checked = view as CheckBox
        if (checked.isChecked) {
            signUpRequestDto.healthInfo.hasPermanentDisability = checked.text.toString()
        }
    }

    fun leerCondicionesDeSalud(){

        val rg_presionArterial = requireActivity().findViewById<RadioGroup>(R.id.rg_presion_arterial)
        var rta_presionArterial = ""
        rg_presionArterial.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                val aux = rg_presionArterial.checkedRadioButtonId
                val radioButton: View = rg_presionArterial.findViewById(aux)
                val indice: Int = rg_presionArterial.indexOfChild(radioButton)
                val respuesta: RadioButton = rg_presionArterial.getChildAt(indice) as RadioButton
                rta_presionArterial = respuesta.text.toString()
                signUpRequestDto.healthInfo.hasHighBloodPressure = rta_presionArterial=="Si"
            }
        }


        val rgEnfermCorazon = requireActivity().findViewById<RadioGroup>(R.id.rg_enferm_corazon)
        var rtaEnfermCorazon = ""
        rgEnfermCorazon.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                val aux = rgEnfermCorazon.checkedRadioButtonId
                val radioButton: View = rgEnfermCorazon.findViewById(aux)
                val indice: Int = rgEnfermCorazon.indexOfChild(radioButton)
                val respuesta: RadioButton = rgEnfermCorazon.getChildAt(indice) as RadioButton
                rtaEnfermCorazon = respuesta.text.toString()
                signUpRequestDto.healthInfo.hasHeartDisease = rtaEnfermCorazon=="Si"

            }
        }

        val rgColesterol = requireActivity().findViewById<RadioGroup>(R.id.rg_colesterol)
        var rtaColesterol = ""
        rgColesterol.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                val aux = rgColesterol.checkedRadioButtonId
                val radioButton: View = rgColesterol.findViewById(aux)
                val indice: Int = rgColesterol.indexOfChild(radioButton)
                val respuesta: RadioButton = rgColesterol.getChildAt(indice) as RadioButton
                rtaColesterol = respuesta.text.toString()
                signUpRequestDto.healthInfo.hasHighCholesterol = rtaColesterol=="Si"
            }
        }

        val rgEnfermRenal = requireActivity().findViewById<RadioGroup>(R.id.rg_enfermedadRenal)
        var rtaEnfermRenal = ""
        rgEnfermRenal.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                val aux = rgEnfermRenal.checkedRadioButtonId
                val radioButton: View = rgEnfermRenal.findViewById(aux)
                val indice: Int = rgEnfermRenal.indexOfChild(radioButton)
                val respuesta: RadioButton = rgEnfermRenal.getChildAt(indice) as RadioButton
                rtaEnfermRenal = respuesta.text.toString()
                signUpRequestDto.healthInfo.haskidneyDisease = rtaEnfermRenal=="Si"
            }
        }

        val rgDiabetes = requireActivity().findViewById<RadioGroup>(R.id.rg_diabetes)
        var rtaDiabetes = ""
        rgDiabetes.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                val aux = rgDiabetes.checkedRadioButtonId
                val radioButton: View = rgDiabetes.findViewById(aux)
                val indice: Int = rgDiabetes.indexOfChild(radioButton)
                val respuesta: RadioButton = rgDiabetes.getChildAt(indice) as RadioButton
                rtaDiabetes = respuesta.text.toString()
                signUpRequestDto.healthInfo.hasDiabetes = rtaDiabetes=="Si"
            }
        }

        val rgEnfermPulmonar = requireActivity().findViewById<RadioGroup>(R.id.rg_enfermedadPulmonar)
        var rtaEnfermPulmonar = ""
        rgEnfermPulmonar.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                val aux = rgEnfermPulmonar.checkedRadioButtonId
                val radioButton: View = rgEnfermPulmonar.findViewById(aux)
                val indice: Int = rgEnfermPulmonar.indexOfChild(radioButton)
                val respuesta: RadioButton = rgEnfermPulmonar.getChildAt(indice) as RadioButton
                rtaEnfermPulmonar = respuesta.text.toString()
                signUpRequestDto.healthInfo.hasEPOC = rtaEnfermPulmonar=="Si"
            }
        }

        val rgAsma = requireActivity().findViewById<RadioGroup>(R.id.rg_asma)
        var rtaAsma = ""
        rgAsma.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                val aux = rgAsma.checkedRadioButtonId
                val radioButton: View = rgAsma.findViewById(aux)
                val indice: Int = rgAsma.indexOfChild(radioButton)
                val respuesta: RadioButton = rgAsma.getChildAt(indice) as RadioButton
                rtaAsma = respuesta.text.toString()
                signUpRequestDto.healthInfo.hasAsthma = rtaAsma=="Si"
            }
        }

        val rgEnfermAlteracionInmunidad = requireActivity().findViewById<RadioGroup>(R.id.rg_alteracionInmunidad)
        var rtaAlteracionInmunidad = ""
        rgEnfermAlteracionInmunidad.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                val aux = rgEnfermAlteracionInmunidad.checkedRadioButtonId
                val radioButton: View = rgEnfermAlteracionInmunidad.findViewById(aux)
                val indice: Int = rgEnfermAlteracionInmunidad.indexOfChild(radioButton)
                val respuesta: RadioButton = rgEnfermAlteracionInmunidad.getChildAt(indice) as RadioButton
                rtaAlteracionInmunidad = respuesta.text.toString()
                signUpRequestDto.healthInfo.hasAlterationImmunityDisease = rtaAlteracionInmunidad=="Si"
            }
        }

        val rgCancer = requireActivity().findViewById<RadioGroup>(R.id.rg_cancer)
        var rtaCancer = ""
        rgCancer.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                val aux = rgCancer.checkedRadioButtonId
                val radioButton: View = rgCancer.findViewById(aux)
                val indice: Int = rgCancer.indexOfChild(radioButton)
                val respuesta: RadioButton = rgCancer.getChildAt(indice) as RadioButton
                rtaCancer = respuesta.text.toString()
                signUpRequestDto.healthInfo.hasCancer = rtaCancer=="Si"
            }
        }

        val rgMedicacionEsteroides = requireActivity().findViewById<RadioGroup>(R.id.rg_esteroides)
        var rtaMedicacionEsteroides = ""
        rgMedicacionEsteroides.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                val aux = rgMedicacionEsteroides.checkedRadioButtonId
                val radioButton: View = rgMedicacionEsteroides.findViewById(aux)
                val indice: Int = rgMedicacionEsteroides.indexOfChild(radioButton)
                val respuesta: RadioButton = rgMedicacionEsteroides.getChildAt(indice) as RadioButton
                rtaMedicacionEsteroides = respuesta.text.toString()
                signUpRequestDto.healthInfo.useOralSteroids = rtaMedicacionEsteroides=="Si"
            }
        }

        val rgEnfermHepatica = requireActivity().findViewById<RadioGroup>(R.id.rg_enfermadadHepatica)
        var rtaEnfermHepatica = ""
        rgEnfermHepatica.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                val aux = rgEnfermHepatica.checkedRadioButtonId
                val radioButton: View = rgEnfermHepatica.findViewById(aux)
                val indice: Int = rgEnfermHepatica.indexOfChild(radioButton)
                val respuesta: RadioButton = rgEnfermHepatica.getChildAt(indice) as RadioButton
                rtaEnfermHepatica = respuesta.text.toString()
                signUpRequestDto.healthInfo.hasHepaticDisease = rtaEnfermHepatica=="Si"
            }
        }

        val rgObesidad = requireActivity().findViewById<RadioGroup>(R.id.rg_obesidad)
        var rtaObesidad = ""
        rgObesidad.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                val aux = rgObesidad.checkedRadioButtonId
                val radioButton: View = rgObesidad.findViewById(aux)
                val indice: Int = rgObesidad.indexOfChild(radioButton)
                val respuesta: RadioButton = rgObesidad.getChildAt(indice) as RadioButton
                rtaObesidad = respuesta.text.toString()
                signUpRequestDto.healthInfo.hasObesity = rtaObesidad=="Si"
            }
        }

        val rgFumador = requireActivity().findViewById<RadioGroup>(R.id.rg_fumador)
        var rtaFumador = ""
        rgFumador.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                val aux = rgFumador.checkedRadioButtonId
                val radioButton: View = rgFumador.findViewById(aux)
                val indice: Int = rgFumador.indexOfChild(radioButton)
                val respuesta: RadioButton = rgFumador.getChildAt(indice) as RadioButton
                rtaFumador = respuesta.text.toString()
                signUpRequestDto.healthInfo.isSmoker = rtaFumador=="Si"
            }
        }

        val rgEnfermRara = requireActivity().findViewById<RadioGroup>(R.id.rg_enfermedadRara)
        var rtaEnfermRara = ""
        rgEnfermRara.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                val aux = rgEnfermRara.checkedRadioButtonId
                val radioButton: View = rgEnfermRara.findViewById(aux)
                val indice: Int = rgEnfermRara.indexOfChild(radioButton)
                val respuesta: RadioButton = rgEnfermRara.getChildAt(indice) as RadioButton
                rtaEnfermRara = respuesta.text.toString()
                signUpRequestDto.healthInfo.hasStrangeDisease = rtaEnfermRara=="Si"
            }
        }

        val rgEmbarazo = requireActivity().findViewById<RadioGroup>(R.id.rg_embarazo)
        var rtaEmbarazo = ""
        rgEmbarazo.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                val aux = rgEmbarazo.checkedRadioButtonId
                val radioButton: View = rgEmbarazo.findViewById(aux)
                val indice: Int = rgEmbarazo.indexOfChild(radioButton)
                val respuesta: RadioButton = rgEmbarazo.getChildAt(indice) as RadioButton
                rtaEmbarazo = respuesta.text.toString()
                signUpRequestDto.healthInfo.isPregnant = rtaEmbarazo=="Si"
            }
        }

        val rgPartoReciente = requireActivity().findViewById<RadioGroup>(R.id.rg_parto)
        var rtaPartoReciente = ""
        rgPartoReciente.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                val aux = rgPartoReciente.checkedRadioButtonId
                val radioButton: View = rgPartoReciente.findViewById(aux)
                val indice: Int = rgPartoReciente.indexOfChild(radioButton)
                val respuesta: RadioButton = rgPartoReciente.getChildAt(indice) as RadioButton
                rtaPartoReciente = respuesta.text.toString()
                signUpRequestDto.healthInfo.hasChildBirthRecently = rtaPartoReciente=="Si"
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
            Preingreso4().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}