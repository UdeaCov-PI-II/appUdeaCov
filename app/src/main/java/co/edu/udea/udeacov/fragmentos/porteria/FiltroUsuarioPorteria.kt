package co.edu.udea.udeacov.fragmentos.porteria

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import co.edu.udea.udeacov.R
import co.edu.udea.udeacov.databinding.FragmentFiltroUsuarioPorteriaBinding
import kotlinx.android.synthetic.main.fragment_filtro_usuario_porteria.*
import kotlinx.android.synthetic.main.fragment_preingreso1.*
import kotlinx.android.synthetic.main.fragment_preingreso4.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FiltroUsuarioPorteria.newInstance] factory method to
 * create an instance of this fragment.
 */
class FiltroUsuarioPorteria : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel: FiltroUsuarioPorteriaViewModel
    private lateinit var binding: FragmentFiltroUsuarioPorteriaBinding
    var bandera: Boolean? = null


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

        //instanciamos el viewModel
        viewModel = ViewModelProvider(this).get(FiltroUsuarioPorteriaViewModel::class.java)

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_filtro_usuario_porteria, container, false)

        //conectamos el binding con el viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel= viewModel

        binding.fragmentFiltroUsuario = this


        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.permissionCardResponse.observe(viewLifecycleOwner, Observer{
            it?.let{
                val permissionId = it[0].id
                permissionId?.let{ id ->
                    findNavController().navigate(FiltroUsuarioPorteriaDirections.actionFiltroUsuarioPorteriaToDetalleSolicitud4(id))
                    viewModel.navToDetailFragmentIsDone()
                }
            }
        })

        //Tipo de Documento
        val rg = requireView().findViewById<RadioGroup>(R.id.rg_tipoDocumento)
        var option = ""
        rg.setOnCheckedChangeListener { _, i ->
            if(i != -1){
                val aux = rg.checkedRadioButtonId
                val radioButton: View = rg.findViewById(aux)
                val indice: Int = rg.indexOfChild(radioButton)
                val respuesta: RadioButton = rg.getChildAt(indice) as RadioButton
                option = respuesta.text.toString()
                val editText1 = view?.findViewById<EditText>(R.id.otroDoc_value)
                if(option == "Otro"){
                    bandera = true
                    editText1!!.visibility = View.VISIBLE
                } else{
                    bandera = false
                    editText1!!.visibility = View.GONE
                }
            }
        }
        binding.btnBuscarSolicitud.setOnClickListener {
            if (otroDoc_value.text.toString().isNotEmpty()){
                option = otroDoc_value.text.toString()
            }
            var numDoc = inputNumeroDocumento.text.toString()
            viewModel.getPermissionBydocTypeAndDocNumber(option, numDoc)
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FiltroUsuarioPorteria.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FiltroUsuarioPorteria().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}