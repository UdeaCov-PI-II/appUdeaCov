package co.edu.udea.udeacov.fragmentos.solicitud_permiso

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import co.edu.udea.udeacov.R
import co.edu.udea.udeacov.databinding.FragmentSolicitudIngreso2Binding
import co.edu.udea.udeacov.fragmentos.solicitud_permiso.viewmodels.solicitudingreso2.Solicitud2ViewModel
import co.edu.udea.udeacov.network.request.PermissionRequestDto
import co.edu.udea.udeacov.ui.DatePickerFragment
import com.kofigyan.stateprogressbar.StateProgressBar
import kotlinx.android.synthetic.main.fragment_solicitud_ingreso2.*

class SolicitudIngreso2 : Fragment() {
    private lateinit var viewModel: Solicitud2ViewModel
    private lateinit var binding: FragmentSolicitudIngreso2Binding
    private lateinit var permissionRequestDto: PermissionRequestDto
    var bandera: Boolean? = null

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
        viewModel = ViewModelProvider(this).get(Solicitud2ViewModel::class.java)

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_solicitud_ingreso2, container, false)

        //unir el binding con el viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.getLocations()

        //Instanciamos la variable del fragment
        binding.fragmentSolicitudIngreso2 = this

        //Recuperar argumentos anteriores
        val args = arguments?.let { SolicitudIngreso2Args.fromBundle(it) }
        permissionRequestDto = args!!.permissionRequest

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        fecha_inicio_permiso.setOnClickListener{
            showDatePickerDialog(it as EditText)
        }

        fecha_finalizacion.setOnClickListener{
            showDatePickerDialog(it as EditText)
        }


        //Observar el cambio de location ID seleccionado por el usuario
        viewModel.locationIdSelected.observe(viewLifecycleOwner, Observer{
            it?.let {
                permissionRequestDto.locationId = it
            }
        })

        viewModel.permissionResponse.observe(viewLifecycleOwner, Observer {
            it?.let {
                view.findNavController().navigate(R.id.action_solicitudIngreso2_to_detalleSolicitud2)
                viewModel.navigationIsCompleted()
            }
        })

        viewModel.responseError.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                viewModel.showErrorIsCompleted()
            }
        })


        val rg = view.findViewById<RadioGroup>(R.id.rg_jornadaPermanencia)
        var option = ""
        rg.setOnCheckedChangeListener { _, i ->
            if (i != -1) {
                val aux = rg_jornadaPermanencia.checkedRadioButtonId
                val radioButton: View = rg_jornadaPermanencia.findViewById(aux)
                val indice: Int = rg_jornadaPermanencia.indexOfChild(radioButton)
                val respuesta: RadioButton =
                    rg_jornadaPermanencia.getChildAt(indice) as RadioButton
                option = respuesta.text.toString()
                permissionRequestDto.requestedWorkingDay = option
                val editText1 = view.findViewById<EditText>(R.id.input_otraJornada)
                if (option == "Otro") {
                    option = ""
                    editText1.visibility = View.VISIBLE
                }
            }
        }


        SolicitudIngreso2_siguiente2.setOnClickListener {
            if (input_otraJornada.text.toString().isNotEmpty() && option=="Otro"){
                permissionRequestDto.requestedWorkingDay = input_otraJornada.text.toString()
            }
            if (fecha_inicio_permiso.text.toString().isNotEmpty()){
                permissionRequestDto.startTimeStr = fecha_inicio_permiso.text.toString().plus(" 05:00")
            }
            if (fecha_finalizacion.text.toString().isNotEmpty()){
                permissionRequestDto.endTimeStr = fecha_finalizacion.text.toString().plus(" 05:00")
            }
            permissionRequestDto.reason = SolicitudIngreso2_motivo.text.toString()
            viewModel.locationNameSelected.value = SolicitudIngreso2_spinner.selectedItem.toString()
            permissionRequestDto.building = preingreso3_bloque.text.toString()
            permissionRequestDto.officeNumber = input_numeroficina.text.toString()
            permissionRequestDto.locationOutOfUniversity = SolicitudIngreso2_lugar.text.toString()
            permissionRequestDto.statusId = "LZoClJxzXghTxcyiQXw6"
            val sharedPref = requireActivity().getSharedPreferences(getString(R.string.user_settings_file),Context.MODE_PRIVATE)
            val userId = sharedPref.getString(getString(R.string.user_id),"")
            userId?.let{
                permissionRequestDto.userId = it
            }
            viewModel.createPermission(permissionRequestDto)
        }


    }

    private fun showDatePickerDialog(view:EditText) {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = day.toString() + "-" + (month + 1) + "-" + year
            view.setText(selectedDate)
        })

        newFragment.show(requireActivity().supportFragmentManager, "datePicker")
    }

    fun onCheckboxClicked(view: View) {
        val checked = view as CheckBox
        if (checked.isChecked) {
            permissionRequestDto.requestedDays.add(checked.text.toString())
        } else {
            permissionRequestDto.requestedDays.remove(checked.text.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        val stateProgressBar = activity?.findViewById<StateProgressBar>(R.id.your_state_progress_bar_id)
        stateProgressBar?.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR)
    }


}