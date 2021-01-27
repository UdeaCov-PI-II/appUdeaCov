package co.edu.udea.udeacov.fragmentos.lista_solicitudes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.github.chrisbanes.photoview.PhotoView
import co.edu.udea.udeacov.R
import kotlinx.android.synthetic.main.fragment_detalle_solicitud.*
import kotlinx.android.synthetic.main.fragment_preingreso1.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetalleSolicitud.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetalleSolicitud : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle_solicitud, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photoView = view.findViewById(R.id.photo_view) as PhotoView
        photoView.setImageResource(R.drawable.coronapp_captura)

        val photoView1 = view.findViewById(R.id.photo_view1) as PhotoView
        photoView1.setImageResource(R.drawable.medellin_me_cuida_captura)

        btn_registra_ingreso.setOnClickListener{
            it.findNavController().navigate(R.id.action_detalleSolicitud_to_ingreso)
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetalleSolicitud.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetalleSolicitud().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}