package co.edu.udea.udeacov.fragmentos.lista_solicitudes

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TableLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.edu.udea.udeacov.R
import co.edu.udea.udeacov.adapter.ApprovalAdapter
import co.edu.udea.udeacov.databinding.ApprovalItemBinding
import co.edu.udea.udeacov.databinding.FragmentDetalleSolicitudBinding
import co.edu.udea.udeacov.fragmentos.lista_solicitudes.viewmodels.viewmodels.DetalleSolicitudViewModel
import co.edu.udea.udeacov.fragmentos.porteria.FiltroUsuarioPorteriaDirections
import co.edu.udea.udeacov.fragmentos.preingreso.Preingreso5Args
import co.edu.udea.udeacov.network.request.ApprovalRequestDto
import co.edu.udea.udeacov.network.request.CreateEntranceRequestDto
import com.kofigyan.stateprogressbar.StateProgressBar
import kotlinx.android.synthetic.main.fragment_detalle_solicitud.*
import kotlinx.android.synthetic.main.fragment_filtro_usuario_porteria.view.*
import kotlinx.android.synthetic.main.layout_datos_porteria.*
import kotlinx.android.synthetic.main.layout_datos_porteria.view.*
import kotlinx.android.synthetic.main.lista_solicitudes_cards.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class DetalleSolicitud : Fragment(), ApprovalAdapter.ApprovalAdapterOnClickListener {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewModel: DetalleSolicitudViewModel
    private lateinit var binding: FragmentDetalleSolicitudBinding
    private lateinit var permissionId: String
    private lateinit var alertDialogView : View
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter : ApprovalAdapter
    private lateinit var userId : String

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

        //Recuperar argumentos enviados
        val args = arguments?.let { DetalleSolicitudArgs.fromBundle(it) }
        permissionId = args!!.permissionId

        //Instanciamos el viewModel
        viewModel = ViewModelProvider(this).get(DetalleSolicitudViewModel::class.java)

        // Inflate the layout for this fragment
        binding = FragmentDetalleSolicitudBinding.inflate(inflater)

        //unir el binding con el viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        //Capturo el rol
        val sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.user_settings_file),
            Context.MODE_PRIVATE
        )
        userId = sharedPref.getString(requireContext().getString(R.string.user_id),null)!!
        viewModel.role.value = sharedPref.getString(getString(R.string.user_role), null)

        viewModel.getPermissionById(permissionId)

        val stateProgressBar =
            requireActivity().findViewById<StateProgressBar>(R.id.your_state_progress_bar_id)
        stateProgressBar?.let {
            it.visibility = View.GONE
        }
        alertDialogView  = inflater.inflate(R.layout.layout_datos_porteria,null, false)
        viewManager = LinearLayoutManager(context)

        viewAdapter = ApprovalAdapter(this, null, viewModel.role.value!!)
        recyclerView = binding.recyclerViewApprovals.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.permissionResponse.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.getApproverRoles()
                it.id?.let{ id->
                    binding.txtFechainicio.text = it.startTimeStr
                    binding.txtFechafinal.text = it.endTimeStr
                    binding.txtLugarPermanencia.text = it.location
                    binding.txtEstado.text = it.status?.displayName
                    binding.txtReasonValue.text = it.reason
                }
                //faltan imagenes y aprobaciones
            }
        })

        viewModel.roleResponse.observe(viewLifecycleOwner, Observer {
            it?.let{
                viewAdapter = ApprovalAdapter(this, viewModel.permissionResponse.value?.approvals, viewModel.role.value!!)
                recyclerView.adapter = viewAdapter
                viewAdapter.submitList(it)
            }
        })

        viewModel.entranceResponse.observe(viewLifecycleOwner, Observer {
            it?.let{
                findNavController().navigate(R.id.action_detalleSolicitud4_to_filtroUsuarioPorteria)
            }
        })

        binding.btnRegistrarIngreso.setOnClickListener {
            //mostrar el Dialog


            val mAlertDialog = AlertDialog.Builder(this.view?.context)
                .setView(alertDialogView)
                .setNegativeButton("Cancelar") { dialog: DialogInterface?, _: Int ->
                    Log.d("Tag", "Prueba")
                }
                .setPositiveButton("Agregar") { _: DialogInterface?, _: Int ->
                    val temperature = alertDialogView.input_temperatura_usuario.text.toString()
                    val response = viewModel.permissionResponse.value
                    val isEntry = response != null && response.entrance == null
                    viewModel.createEntrance(CreateEntranceRequestDto(permissionId,temperature,isEntry))
                }
            // Create the AlertDialog object and return it
            mAlertDialog.show()
        }

        viewModel.approvalResponse.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_detalleSolicitud_to_listaSolicitudes)
            viewModel.navigateToListIsDone()
        })



/*
        val photoView = view.findViewById(R.id.photo_view) as PhotoView
        photoView.setImageResource(R.drawable.coronapp_captura)

        val photoView1 = view.findViewById(R.id.photo_view1) as PhotoView
        photoView1.setImageResource(R.drawable.medellin_me_cuida_captura)


         btn_registra_ingreso.setOnClickListener{
            it.findNavController().navigate(R.id.action_detalleSolicitud_to_ingreso)
        }*/



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

    override fun btnApproveOnClick(binding: ApprovalItemBinding, view: View) {
      val action = viewModel.permissionResponse.value?.status?.action
        action?.let{ act ->
            val request = ApprovalRequestDto(userId,binding.chkApproved.isChecked,binding.txtMotivo.text.toString(),act)
            viewModel.createApproval(permissionId, request)

        }
    }
}