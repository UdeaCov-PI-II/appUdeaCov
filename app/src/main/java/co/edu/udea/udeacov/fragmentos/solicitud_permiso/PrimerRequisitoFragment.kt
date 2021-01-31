package co.edu.udea.udeacov.fragmentos.solicitud_permiso

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import co.edu.udea.udeacov.R
import co.edu.udea.udeacov.activities.SolicitudDeUnPermiso
import com.kofigyan.stateprogressbar.StateProgressBar
import kotlinx.android.synthetic.main.fragment_primer_requisito.*



// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PrimerRequisitoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PrimerRequisitoFragment : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_primer_requisito, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_enviarCoronapp.setOnClickListener {
            loadImage();
        }
        primerReqbtn.setOnClickListener{
            if(SolicitudDeUnPermiso.StaticData.bandera1){
                it.findNavController().navigate(R.id.action_primerRequisitoFragment_to_segundoRequisitoFragment)
            }else{
                Toast.makeText(activity, "Ingresar imagen", Toast.LENGTH_SHORT).show();
            }
        }

    }


    private fun loadImage(){
        val intent = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
        intent.setType("image/");

        startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen"), 10);
    }


    override fun onStart() {
        super.onStart()
        val stateProgressBar = activity?.findViewById<StateProgressBar>(R.id.your_state_progress_bar_id)
        stateProgressBar?.setCurrentStateNumber(StateProgressBar.StateNumber.ONE)
    }
}