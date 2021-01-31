package co.edu.udea.udeacov.fragmentos.solicitud_permiso

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import co.edu.udea.udeacov.R
import co.edu.udea.udeacov.databinding.FragmentSolicitudIngreso1Binding
import co.edu.udea.udeacov.fragmentos.solicitud_permiso.viewmodels.solicitudingreso1.Solicitud1ViewModel
import co.edu.udea.udeacov.network.request.PermissionRequestDto
import com.kofigyan.stateprogressbar.StateProgressBar
import kotlinx.android.synthetic.main.fragment_solicitud_ingreso1.*


class SolicitudIngreso1 : Fragment() {
    private lateinit var permissionRequestDto: PermissionRequestDto
    private lateinit var viewModel: Solicitud1ViewModel
    private lateinit var binding: FragmentSolicitudIngreso1Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //instanciamos el viewModel
        viewModel = ViewModelProvider(this).get(Solicitud1ViewModel::class.java)

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_solicitud_ingreso1, container, false)

        //unir el binding con el viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.getUnits()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Instanciar el objeto
        permissionRequestDto = PermissionRequestDto()

        //Observar el cambio de unit ID
        viewModel.unitSelected.observe(viewLifecycleOwner, Observer {
            it?.let {
                permissionRequestDto.managerUnitId = it.id
                binding.decano.text = it.manager.fullName
                binding.correoDecano.text = it.manager.email
            }
        })

        SolicitudIngreso1btn_siguiente1.setOnClickListener {
            it.findNavController().navigate(SolicitudIngreso1Directions.actionSolicitudIngreso1ToSolicitudIngreso2(permissionRequestDto))
        }

        val mySpinner = view.findViewById<Spinner>(R.id.SolicitudIngreso1_spinner)

        mySpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                viewModel.unitNameSelected.value = mySpinner.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    override fun onStart() {
        super.onStart()
        val stateProgressBar = activity?.findViewById<StateProgressBar>(R.id.your_state_progress_bar_id)
        stateProgressBar?.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR)
    }
}