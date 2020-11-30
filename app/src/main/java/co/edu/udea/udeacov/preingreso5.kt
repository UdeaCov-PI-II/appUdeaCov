package co.edu.udea.udeacov

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_preingreso2.*
import kotlinx.android.synthetic.main.fragment_preingreso4.*
import kotlinx.android.synthetic.main.fragment_preingreso5.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [preingreso5.newInstance] factory method to
 * create an instance of this fragment.
 */
class preingreso5 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var bandera: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun validate() :Boolean{
        if(preingreso5_checkBox1.isChecked == false && preingreso5_checkBox2.isChecked == false && preingreso5_checkBox3.isChecked == false && preingreso5_checkBox4.isChecked == false &&
            preingreso5_checkBox5.isChecked == false && preingreso5_checkBox6.isChecked == false && preingreso5_checkBox7.isChecked == false && preingreso5_checkBox8.isChecked == false &&
            preingreso5_checkBox9.isChecked == false && preingreso5_checkBox10.isChecked == false && preingreso5_checkBox11.isChecked == false ){
            Toast.makeText(activity, "Esta pregunta es obligatoria", Toast.LENGTH_SHORT).show();
            return false
        }else if(bandera==true && preingreso5_otro.text.toString().isEmpty()){
            preingreso5_otro.error = "campo vacío"
            return false
        }
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preingreso5, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preingreso5_checkBox11.setOnClickListener {
            var editText1 = view.findViewById<EditText>(R.id.preingreso5_otro)
            if(preingreso5_checkBox11.isChecked == true){
                bandera = true
                editText1.visibility = View.VISIBLE
            }else{
                bandera = false
                editText1.visibility = View.INVISIBLE
            }
        }
        preingresobtn_siguiente5.setOnClickListener{
            if(validate()){
                Toast.makeText(activity, "Campos diligenciados", Toast.LENGTH_SHORT).show();
                it.findNavController().navigate(R.id.action_preingreso5_to_preingreso62)
            }
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment preingreso5.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            preingreso5().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}