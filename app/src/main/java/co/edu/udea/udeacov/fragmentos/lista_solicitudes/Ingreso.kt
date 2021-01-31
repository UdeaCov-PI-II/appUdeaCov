package co.edu.udea.udeacov.fragmentos.lista_solicitudes

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.edu.udea.udeacov.R
import co.edu.udea.udeacov.TablaDinamica
import com.github.chrisbanes.photoview.PhotoView
import kotlinx.android.synthetic.main.fragment_ingreso.*
import kotlinx.android.synthetic.main.fragment_preingreso1.*
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Ingreso.newInstance] factory method to
 * create an instance of this fragment.
 */
class Ingreso : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var tableLayout: TableLayout
    private val header =arrayOf("  No  ","  Nombre  ","  T° entrada  ","  Hora entrada  ", "  Lugar  ", "  T° salida  ", "  Hora salida  ")
    internal var row = ArrayList<Array<String>>()
    internal lateinit var tablaDinamica:TablaDinamica
    var contador=0


    private lateinit var txtName:EditText
    //private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingreso, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tableLayout=view.findViewById<TableLayout>(R.id.table_ingreso)
        tablaDinamica = TablaDinamica(tableLayout, context)
        tablaDinamica.addHeader(header);
        tablaDinamica.addData(getUsers())
        tablaDinamica.backgroundHeader(Color.rgb(61,174,43));
        tablaDinamica.backgroundData(Color.WHITE, Color.rgb(141,213,0));
        val txtName = view.findViewById(R.id.txt_ingreso_nombre) as EditText
        val txtTempIn = view.findViewById(R.id.txt_ingreso_tempIn) as EditText
        val txtHoraIn = view.findViewById(R.id.txt_ingreso_horaIn) as EditText
        val txtLugar = view.findViewById(R.id.txt_ingreso_lugar) as EditText
        val txtTemoOut = view.findViewById(R.id.txt_ingreso_tempOut) as EditText
        val txtHoraOut = view.findViewById(R.id.txt_ingreso_horaOut) as EditText

        buttoniii.setOnClickListener{
            contador= contador+1
            val item = arrayOf<String>("$contador", txtName.getText().toString(), txtTempIn.getText().toString(),txtHoraIn.getText().toString(),
                txtLugar.getText().toString(),txtTemoOut.getText().toString(),txtHoraOut.getText().toString())
            tablaDinamica.addItems(item)
            Toast.makeText(activity, "Ingresar usuario", Toast.LENGTH_SHORT).show();
        }
    }

    private fun getUsers():ArrayList<Array<String>> {
        row.add(arrayOf<String>(""))
        return row

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Ingreso.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                Ingreso().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}