package co.edu.udea.udeacov.fragmentos.solicitud_permiso

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import co.edu.udea.udeacov.R
import co.edu.udea.udeacov.activities.SolicitudDeUnPermiso
import co.edu.udea.udeacov.network.request.PermissionRequestDto
import com.kofigyan.stateprogressbar.StateProgressBar
import kotlinx.android.synthetic.main.fragment_segundo_requisito.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SegundoRequisitoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SegundoRequisitoFragment : Fragment() {
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

    private fun loadImage(){
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
        intent.setType("image/");

        startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen"), 10);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_segundo_requisito, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        btn_enviarMedellinMeCuida.setOnClickListener {
            loadImage();
        }
        segReqbtn.setOnClickListener{
            if(SolicitudDeUnPermiso.StaticData.bandera2){
                it.findNavController().navigate(R.id.action_segundoRequisitoFragment_to_solicitudIngreso1)
            }else{
                Toast.makeText(activity, "Ingresar imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val stateProgressBar = activity?.findViewById<StateProgressBar>(R.id.your_state_progress_bar_id)
        stateProgressBar?.setCurrentStateNumber(StateProgressBar.StateNumber.TWO)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SegundoRequisitoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SegundoRequisitoFragment()
                .apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}