package co.edu.udea.udeacov.fragmentos.lista_solicitudes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.edu.udea.udeacov.R
import co.edu.udea.udeacov.activities.adapter.AdapterSolicitud
import kotlinx.android.synthetic.main.fragment_preingreso1.*
import kotlinx.android.synthetic.main.lista_solicitudes_cards.*
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.udea.udeacov.fragmentos.lista_solicitudes.viewmodels.ListaSolicitudViewModel
import co.edu.udea.udeacov.fragmentos.lista_solicitudes.viewmodels.viewmodels.DetalleSolicitudViewModel
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
class ListaSolicitudes : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewModel: ListaSolicitudViewModel
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerView.Adapter<AdapterSolicitud.ViewHolder>


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
        return inflater.inflate(R.layout.fragment_lista_solicitudes, container, false)

        //Instanciamos el viewModel
        viewModel = ViewModelProvider(this).get(ListaSolicitudViewModel::class.java)

        viewModel.getList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = LinearLayoutManager(context)
        recycler_view_solicitudes.layoutManager = layoutManager
        adapter = AdapterSolicitud ()
        recycler_view_solicitudes.adapter = adapter

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
}