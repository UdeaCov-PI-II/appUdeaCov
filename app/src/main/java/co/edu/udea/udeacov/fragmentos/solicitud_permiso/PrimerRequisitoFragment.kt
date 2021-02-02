package co.edu.udea.udeacov.fragmentos.solicitud_permiso

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import co.edu.udea.udeacov.R
import co.edu.udea.udeacov.activities.SolicitudDeUnPermiso
import co.edu.udea.udeacov.fragmentos.solicitud_permiso.viewmodels.primerrequisito.PrimerRequisitoViewModel
import co.edu.udea.udeacov.network.response.PermissionResponseDto
import com.kofigyan.stateprogressbar.StateProgressBar
import kotlinx.android.synthetic.main.fragment_primer_requisito.*



// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PrimerRequisitoFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    var bandera: Boolean? = null
    private lateinit var viewModel: PrimerRequisitoViewModel

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
        viewModel = ViewModelProvider(this).get(PrimerRequisitoViewModel::class.java)

        val sharedPref = requireActivity().getSharedPreferences(getString(R.string.user_settings_file),
            Context.MODE_PRIVATE)

        //capturamos el id del usuario que ingresa
        viewModel.userId.value = sharedPref.getString(getString(R.string.user_role), null)
        val userId = sharedPref.getString(getString(R.string.user_id), null)
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_primer_requisito, container, false)

        viewModel.isUser.observe(viewLifecycleOwner, Observer{
            if(it != null && it == true){
                viewModel.getPermissionByUser(userId!!)
            }
        })

        viewModel.permissionCardResponse.observe(viewLifecycleOwner,Observer{
            it?.let{
                val permissionId = it[0].id
                permissionId?.let{ id ->
                    findNavController().navigate(PrimerRequisitoFragmentDirections
                        .actionPrimerRequisitoFragmentToDetalleSolicitud2(id))
                    viewModel.navToDetailFragmentIsDone()
                }
            }
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_enviarCoronapp.setOnClickListener {
            loadImage()
        }
        primerReqbtn.setOnClickListener{
            if(SolicitudDeUnPermiso.StaticData.bandera1){
                it.findNavController().navigate(R.id.action_primerRequisitoFragment_to_segundoRequisitoFragment)
            }else{
                Toast.makeText(activity, "Ingresar imagen", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun loadImage(){
        val intent = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        intent.setType("image/")

        startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen"), 10)
    }


    override fun onStart() {
        super.onStart()
        //Paso numero 1 de la barra de progreso
        val stateProgressBar = activity?.findViewById<StateProgressBar>(R.id.your_state_progress_bar_id)
        stateProgressBar?.setCurrentStateNumber(StateProgressBar.StateNumber.ONE)
    }
}