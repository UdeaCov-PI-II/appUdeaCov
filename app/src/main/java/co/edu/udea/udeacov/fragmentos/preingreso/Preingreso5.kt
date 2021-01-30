package co.edu.udea.udeacov.fragmentos.preingreso

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import co.edu.udea.udeacov.R
import co.edu.udea.udeacov.databinding.FragmentPreingreso4Binding
import co.edu.udea.udeacov.databinding.FragmentPreingreso5Binding
import co.edu.udea.udeacov.fragmentos.preingreso.viewmodels.preingreso5.Preingreso5ViewModel
import co.edu.udea.udeacov.network.request.SignUpRequestDto
import kotlinx.android.synthetic.main.fragment_preingreso5.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Preingreso5.newInstance] factory method to
 * create an instance of this fragment.
 */
class Preingreso5 : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    var bandera: Boolean? = null
    private lateinit var viewModel : Preingreso5ViewModel
    private lateinit var signUpRequestDto: SignUpRequestDto
    lateinit var binding: FragmentPreingreso5Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun validate() :Boolean{
        if(!preingreso5_checkBox1.isChecked && !preingreso5_checkBox2.isChecked && !preingreso5_checkBox3.isChecked && !preingreso5_checkBox4.isChecked && !preingreso5_checkBox5.isChecked && !preingreso5_checkBox6.isChecked && !preingreso5_checkBox7.isChecked && !preingreso5_checkBox8.isChecked && !preingreso5_checkBox9.isChecked && !preingreso5_checkBox10.isChecked && !preingreso5_checkBox11.isChecked){
            Toast.makeText(activity, "Esta pregunta es obligatoria", Toast.LENGTH_SHORT).show()
            return false
        }else if(bandera==true && preingreso5_otro.text.toString().isEmpty()){
            preingreso5_otro.error = "campo vacío"
            return false
        }
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Recuperar argumentos anteriores
        val args = arguments?.let { Preingreso5Args.fromBundle(it) }
        signUpRequestDto = args!!.signUpRequest

        //instanciamos el viewModel
        viewModel = ViewModelProvider(this).get(Preingreso5ViewModel::class.java)

           // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_preingreso5, container, false )
        //Instanciamos la variable del fragment
        binding.fragmentPreingreso5 = this

        //unir el binding con el viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

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
                view.findNavController().navigate(R.id.action_preingreso5_to_mainActivity)
                viewModel.navigationIsCompleted()
            }
        })

        preingreso5_checkBox11.setOnClickListener {

            val editText1 = view.findViewById<EditText>(R.id.preingreso5_otro)
            if(preingreso5_checkBox11.isChecked){
                bandera = true
                editText1.visibility = View.VISIBLE
            }else{
                bandera = false
                editText1.visibility = View.GONE
            }
        }

         btn_enviarFormulario.setOnClickListener{
            if(validate()){
                if (preingreso5_otro.text.toString().isNotEmpty()){
                    signUpRequestDto.healthInfo.roomatesConditions.add(preingreso5_otro.text.toString())
                }
                Toast.makeText(activity, "Formulario enviado exitosamente", Toast.LENGTH_SHORT).show()
                viewModel.signup(signUpRequestDto)
            }
        }

    }

    fun seleccionarCondicionesConvivencia(view: View) {
        val checked = view as CheckBox
        if (checked.text.toString()=="Otro"){
            return
        }
        if (checked.isChecked) {
            signUpRequestDto.healthInfo.roomatesConditions.add(checked.text.toString())
        } else {
            signUpRequestDto.healthInfo.roomatesConditions.remove(checked.text.toString())
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment preingreso5.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Preingreso5().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}