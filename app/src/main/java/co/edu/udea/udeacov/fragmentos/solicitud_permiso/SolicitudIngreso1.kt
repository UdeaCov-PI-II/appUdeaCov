package co.edu.udea.udeacov.fragmentos.solicitud_permiso

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import co.edu.udea.udeacov.R
import com.kofigyan.stateprogressbar.StateProgressBar
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

        var mySpinner = view.findViewById<Spinner>(R.id.SolicitudIngreso1_spinner)
        var text2 = view.findViewById<TextView>(R.id.decano)
        var text3 = view.findViewById<TextView>(R.id.correoDecano)
        mySpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val text = mySpinner.selectedItem.toString()

                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
                if(text == "Facultad de Artes"){
                    text2.setText("Gabriel Velez");
                    text3.setText("gabriel.velez@udea.edu.co");

                }else if(text == "Facultad de Ciencias Agrarias"){
                    text2.setText("Liliana Mahecha Ledesma");
                    text3.setText("decaagrarias@udea.edu.co");

                }else if(text == "Facultad de Ciencias Económicas"){
                    text2.setText("Sergio Iván Restrepo Ochoa");
                    text3.setText("decaeconomicas@udea.edu.co");
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    override fun onStart() {
        super.onStart()
        val stateProgressBar = activity?.findViewById<StateProgressBar>(R.id.your_state_progress_bar_id)
        stateProgressBar?.setCurrentStateNumber(StateProgressBar.StateNumber.THREE)
    }
}