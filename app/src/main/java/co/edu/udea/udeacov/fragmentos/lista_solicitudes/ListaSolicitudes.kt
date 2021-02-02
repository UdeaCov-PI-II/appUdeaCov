package co.edu.udea.udeacov.fragmentos.lista_solicitudes

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.edu.udea.udeacov.R
import co.edu.udea.udeacov.activities.adapter.AdapterSolicitud
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.udea.udeacov.adapter.PermissionAdapter
import co.edu.udea.udeacov.databinding.FragmentListaSolicitudesBinding
import co.edu.udea.udeacov.fragmentos.lista_solicitudes.viewmodels.ListaSolicitudesViewModel
import co.edu.udea.udeacov.network.response.PermissionCardResponseDto
import kotlinx.android.synthetic.main.fragment_lista_solicitudes.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListaSolicitudes.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaSolicitudes : Fragment(),PermissionAdapter.PermissionAdapterOnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : FragmentListaSolicitudesBinding
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ListaSolicitudesViewModel


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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_lista_solicitudes,container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewManager = LinearLayoutManager(context)
        val viewAdapter = PermissionAdapter(this)
        recyclerView = binding.recyclerViewSolicitudes.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
        viewModel = ViewModelProvider(this).get(ListaSolicitudesViewModel::class.java)

        viewModel.permissionCardResponse.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewAdapter.submitList(it)
            }
        })

        val sharedPref = requireActivity().getSharedPreferences(getString(R.string.user_settings_file), Context.MODE_PRIVATE)
        val role = sharedPref.getString(requireActivity().getString(R.string.user_role),null)
        role?.let{
            viewModel.getPermissionByRole(it)
        }

        viewModel.responseError.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListaSolicitudes.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListaSolicitudes().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun btnVerOnClick(permission: PermissionCardResponseDto, view: View) {
      permission.id?.let{
          view.findNavController().navigate(ListaSolicitudesDirections.actionListaSolicitudesToDetalleSolicitud(it))
          viewModel.navToDetailFragmentIsDone()
      }
    }
}