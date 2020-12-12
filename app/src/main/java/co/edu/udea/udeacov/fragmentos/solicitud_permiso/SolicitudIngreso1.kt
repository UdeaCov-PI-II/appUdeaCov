package co.edu.udea.udeacov.fragmentos.solicitud_permiso

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import co.edu.udea.udeacov.R
import kotlinx.android.synthetic.main.fragment_solicitud_ingreso1.*


class SolicitudIngreso1 : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_solicitud_ingreso1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SolicitudIngreso1btn_siguiente1.setOnClickListener {
            it.findNavController().navigate(R.id.action_solicitudIngreso1_to_solicitudIngreso2)
        }
    }
}