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
import co.edu.udea.udeacov.databinding.FragmentPreingreso4Binding
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
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var bandera: Boolean? = null
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
            Toast.makeText(activity, "Debes introducir una respuesta por fila", Toast.LENGTH_SHORT).show();
            return false
        }else if(!cb_ningunaDiscapacidad.isChecked && !cb_visualPermanente.isChecked && !cb_motrizPermanente.isChecked && !cb_auditivaPermanente.isChecked && !cb_otra_discapacidad.isChecked){
            Toast.makeText(activity, "Registrar si tiene algún tipo de incapacidad", Toast.LENGTH_SHORT).show();
            return false
        }else if(bandera==true && input_otraDiscapacidad.text.toString().isEmpty()){
            input_otraDiscapacidad.error = "campo vacío"
            return false
        }
        else if(rgGrupoSanguineo == -1){
            Toast.makeText(activity, "Ingresar grupo sanguíneo", Toast.LENGTH_SHORT).show();
            return false
        }else if(rgConvivencia == -1){
            Toast.makeText(activity, "Ingresar si convives actualmente con otras personas", Toast.LENGTH_SHORT).show();
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        leerCondicionesDeSalud()

        var rgGrupoSanguineo = view.findViewById<RadioGroup>(R.id.rg_grupo_sanguineo)
        var rtaGrupoSanguineo = ""
        rgGrupoSanguineo.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                var aux = rg_grupo_sanguineo.checkedRadioButtonId
                var radioButton: View = rg_grupo_sanguineo.findViewById(aux)
                var indice: Int = rg_grupo_sanguineo.indexOfChild(radioButton)
                var respuesta: RadioButton = rg_grupo_sanguineo.getChildAt(indice) as RadioButton
                rtaGrupoSanguineo = respuesta.text.toString()
                signUpRequestDto.healthInfo.bloodType = rtaGrupoSanguineo
            }
        }


        var rg = view.findViewById<RadioGroup>(R.id.rg_convivesConOtros)
        var option = ""
        rg.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                var aux = rg_convivesConOtros.checkedRadioButtonId
                var radioButton: View = rg_convivesConOtros.findViewById(aux)
                var indice: Int = rg_convivesConOtros.indexOfChild(radioButton)
                var respuesta: RadioButton = rg_convivesConOtros.getChildAt(indice) as RadioButton
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
                signUpRequestDto.healthInfo.weight = preingreso4_peso.text.toString().toFloat()
                signUpRequestDto.healthInfo.height = preingreso4_altura.text.toString().toFloat()
                if(option == "Si"){
                    signUpRequestDto.healthInfo.hasRoomates = true
                    Toast.makeText(activity, "Campos diligenciados", Toast.LENGTH_SHORT).show();
                    it.findNavController().navigate(Preingreso4Directions.actionPreingreso4ToPreingreso5(signUpRequestDto))
                } else{
                    signUpRequestDto.healthInfo.hasRoomates = false
                    Toast.makeText(activity, "Campos diligenciados", Toast.LENGTH_SHORT).show();
                    it.findNavController().navigate(R.id.action_preingreso4_to_preingreso62)
                }
            }
        }

    }

    fun seleccionarDiscapacidadPermanente (view: View) {
        var checked = view as CheckBox
        if (checked.isChecked) {
            signUpRequestDto.healthInfo.hasPermanentDisability = checked.text.toString()
        }
    }

    fun leerCondicionesDeSalud(){

        var rg_presionArterial = requireActivity().findViewById<RadioGroup>(R.id.rg_presion_arterial)
        var rta_presionArterial = ""
        rg_presionArterial.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                var aux = rg_presionArterial.checkedRadioButtonId
                var radioButton: View = rg_presionArterial.findViewById(aux)
                var indice: Int = rg_presionArterial.indexOfChild(radioButton)
                var respuesta: RadioButton = rg_presionArterial.getChildAt(indice) as RadioButton
                rta_presionArterial = respuesta.text.toString()
                signUpRequestDto.healthInfo.hasHighBloodPressure = rta_presionArterial=="Si"
            }
        }


        var rgEnfermCorazon = requireActivity().findViewById<RadioGroup>(R.id.rg_enferm_corazon)
        var rtaEnfermCorazon = ""
        rgEnfermCorazon.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                var aux = rgEnfermCorazon.checkedRadioButtonId
                var radioButton: View = rgEnfermCorazon.findViewById(aux)
                var indice: Int = rgEnfermCorazon.indexOfChild(radioButton)
                var respuesta: RadioButton = rgEnfermCorazon.getChildAt(indice) as RadioButton
                rtaEnfermCorazon = respuesta.text.toString()
                signUpRequestDto.healthInfo.hasHeartDisease = rtaEnfermCorazon=="Si"

            }
        }

        var rgColesterol = requireActivity().findViewById<RadioGroup>(R.id.rg_colesterol)
        var rtaColesterol = ""
        rgColesterol.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                var aux = rgColesterol.checkedRadioButtonId
                var radioButton: View = rgColesterol.findViewById(aux)
                var indice: Int = rgColesterol.indexOfChild(radioButton)
                var respuesta: RadioButton = rgColesterol.getChildAt(indice) as RadioButton
                rtaColesterol = respuesta.text.toString()
                signUpRequestDto.healthInfo.hasHighCholesterol = rtaColesterol=="Si"
            }
        }

        var rgEnfermRenal = requireActivity().findViewById<RadioGroup>(R.id.rg_enfermedadRenal)
        var rtaEnfermRenal = ""
        rgEnfermRenal.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                var aux = rgEnfermRenal.checkedRadioButtonId
                var radioButton: View = rgEnfermRenal.findViewById(aux)
                var indice: Int = rgEnfermRenal.indexOfChild(radioButton)
                var respuesta: RadioButton = rgEnfermRenal.getChildAt(indice) as RadioButton
                rtaEnfermRenal = respuesta.text.toString()
                signUpRequestDto.healthInfo.haskidneyDisease = rtaEnfermRenal=="Si"
            }
        }

        var rgDiabetes = requireActivity().findViewById<RadioGroup>(R.id.rg_diabetes)
        var rtaDiabetes = ""
        rgDiabetes.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                var aux = rgDiabetes.checkedRadioButtonId
                var radioButton: View = rgDiabetes.findViewById(aux)
                var indice: Int = rgDiabetes.indexOfChild(radioButton)
                var respuesta: RadioButton = rgDiabetes.getChildAt(indice) as RadioButton
                rtaDiabetes = respuesta.text.toString()
                signUpRequestDto.healthInfo.hasDiabetes = rtaDiabetes=="Si"
            }
        }

        var rgEnfermPulmonar = requireActivity().findViewById<RadioGroup>(R.id.rg_enfermedadPulmonar)
        var rtaEnfermPulmonar = ""
        rgEnfermPulmonar.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                var aux = rgEnfermPulmonar.checkedRadioButtonId
                var radioButton: View = rgEnfermPulmonar.findViewById(aux)
                var indice: Int = rgEnfermPulmonar.indexOfChild(radioButton)
                var respuesta: RadioButton = rgEnfermPulmonar.getChildAt(indice) as RadioButton
                rtaEnfermPulmonar = respuesta.text.toString()
                signUpRequestDto.healthInfo.hasEPOC = rtaEnfermPulmonar=="Si"
            }
        }

        var rgAsma = requireActivity().findViewById<RadioGroup>(R.id.rg_asma)
        var rtaAsma = ""
        rgAsma.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                var aux = rgAsma.checkedRadioButtonId
                var radioButton: View = rgAsma.findViewById(aux)
                var indice: Int = rgAsma.indexOfChild(radioButton)
                var respuesta: RadioButton = rgAsma.getChildAt(indice) as RadioButton
                rtaAsma = respuesta.text.toString()
                signUpRequestDto.healthInfo.hasAsthma = rtaAsma=="Si"
            }
        }

        var rgEnfermAlteracionInmunidad = requireActivity().findViewById<RadioGroup>(R.id.rg_alteracionInmunidad)
        var rtaAlteracionInmunidad = ""
        rgEnfermAlteracionInmunidad.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                var aux = rgEnfermAlteracionInmunidad.checkedRadioButtonId
                var radioButton: View = rgEnfermAlteracionInmunidad.findViewById(aux)
                var indice: Int = rgEnfermAlteracionInmunidad.indexOfChild(radioButton)
                var respuesta: RadioButton = rgEnfermAlteracionInmunidad.getChildAt(indice) as RadioButton
                rtaAlteracionInmunidad = respuesta.text.toString()
                signUpRequestDto.healthInfo.hasAlterationImmunityDisease = rtaAlteracionInmunidad=="Si"
            }
        }

        var rgCancer = requireActivity().findViewById<RadioGroup>(R.id.rg_cancer)
        var rtaCancer = ""
        rgCancer.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                var aux = rgCancer.checkedRadioButtonId
                var radioButton: View = rgCancer.findViewById(aux)
                var indice: Int = rgCancer.indexOfChild(radioButton)
                var respuesta: RadioButton = rgCancer.getChildAt(indice) as RadioButton
                rtaCancer = respuesta.text.toString()
                signUpRequestDto.healthInfo.hasCancer = rtaCancer=="Si"
            }
        }

        var rgMedicacionEsteroides = requireActivity().findViewById<RadioGroup>(R.id.rg_esteroides)
        var rtaMedicacionEsteroides = ""
        rgMedicacionEsteroides.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                var aux = rgMedicacionEsteroides.checkedRadioButtonId
                var radioButton: View = rgMedicacionEsteroides.findViewById(aux)
                var indice: Int = rgMedicacionEsteroides.indexOfChild(radioButton)
                var respuesta: RadioButton = rgMedicacionEsteroides.getChildAt(indice) as RadioButton
                rtaMedicacionEsteroides = respuesta.text.toString()
                signUpRequestDto.healthInfo.useOralSteroids = rtaMedicacionEsteroides=="Si"
            }
        }

        var rgEnfermHepatica = requireActivity().findViewById<RadioGroup>(R.id.rg_enfermadadHepatica)
        var rtaEnfermHepatica = ""
        rgEnfermHepatica.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                var aux = rgEnfermHepatica.checkedRadioButtonId
                var radioButton: View = rgEnfermHepatica.findViewById(aux)
                var indice: Int = rgEnfermHepatica.indexOfChild(radioButton)
                var respuesta: RadioButton = rgEnfermHepatica.getChildAt(indice) as RadioButton
                rtaEnfermHepatica = respuesta.text.toString()
                signUpRequestDto.healthInfo.hasHepaticDisease = rtaEnfermHepatica=="Si"
            }
        }

        var rgObesidad = requireActivity().findViewById<RadioGroup>(R.id.rg_obesidad)
        var rtaObesidad = ""
        rgObesidad.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                var aux = rgObesidad.checkedRadioButtonId
                var radioButton: View = rgObesidad.findViewById(aux)
                var indice: Int = rgObesidad.indexOfChild(radioButton)
                var respuesta: RadioButton = rgObesidad.getChildAt(indice) as RadioButton
                rtaObesidad = respuesta.text.toString()
                signUpRequestDto.healthInfo.hasObesity = rtaObesidad=="Si"
            }
        }

        var rgFumador = requireActivity().findViewById<RadioGroup>(R.id.rg_fumador)
        var rtaFumador = ""
        rgFumador.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                var aux = rgFumador.checkedRadioButtonId
                var radioButton: View = rgFumador.findViewById(aux)
                var indice: Int = rgFumador.indexOfChild(radioButton)
                var respuesta: RadioButton = rgFumador.getChildAt(indice) as RadioButton
                rtaFumador = respuesta.text.toString()
                signUpRequestDto.healthInfo.isSmoker = rtaFumador=="Si"
            }
        }

        var rgEnfermRara = requireActivity().findViewById<RadioGroup>(R.id.rg_enfermedadRara)
        var rtaEnfermRara = ""
        rgEnfermRara.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                var aux = rgEnfermRara.checkedRadioButtonId
                var radioButton: View = rgEnfermRara.findViewById(aux)
                var indice: Int = rgEnfermRara.indexOfChild(radioButton)
                var respuesta: RadioButton = rgEnfermRara.getChildAt(indice) as RadioButton
                rtaEnfermRara = respuesta.text.toString()
                signUpRequestDto.healthInfo.hasStrangeDisease = rtaEnfermRara=="Si"
            }
        }

        var rgEmbarazo = requireActivity().findViewById<RadioGroup>(R.id.rg_embarazo)
        var rtaEmbarazo = ""
        rgEmbarazo.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                var aux = rgEmbarazo.checkedRadioButtonId
                var radioButton: View = rgEmbarazo.findViewById(aux)
                var indice: Int = rgEmbarazo.indexOfChild(radioButton)
                var respuesta: RadioButton = rgEmbarazo.getChildAt(indice) as RadioButton
                rtaEmbarazo = respuesta.text.toString()
                signUpRequestDto.healthInfo.isPregnant = rtaEmbarazo=="Si"
            }
        }

        var rgPartoReciente = requireActivity().findViewById<RadioGroup>(R.id.rg_parto)
        var rtaPartoReciente = ""
        rgPartoReciente.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                var aux = rgPartoReciente.checkedRadioButtonId
                var radioButton: View = rgPartoReciente.findViewById(aux)
                var indice: Int = rgPartoReciente.indexOfChild(radioButton)
                var respuesta: RadioButton = rgPartoReciente.getChildAt(indice) as RadioButton
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