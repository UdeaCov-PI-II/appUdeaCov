package co.edu.udea.udeacov.activities.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.edu.udea.udeacov.R
import kotlinx.android.synthetic.main.lista_solicitudes_cards.view.*

class AdapterSolicitud(): RecyclerView.Adapter<AdapterSolicitud.ViewHolder>(){

    private val itemName = arrayOf("Paula Andrea Maldonado Pamplona", "Dolly Jimenez")
    private val itemCedula = arrayOf("123456", "456789")
    private val itemFechaI = arrayOf("06/10/23", "06/10/23")
    private val itemFechaF = arrayOf("06/10/23", "06/10/23")

    inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){

        lateinit var namess:TextView
        lateinit var cedula:TextView
        lateinit var fecha_inicio:TextView
        lateinit var fecha_fin:TextView


        init{
            namess=itemView.findViewById(R.id.txt_nombre)
            cedula=itemView.findViewById(R.id.txt_cc)
            fecha_inicio=itemView.findViewById(R.id.txt_fechaini)
            fecha_fin=itemView.findViewById(R.id.txt_fechafin)
        }

        // Cantidad de elementos del RecyclerView
        // Puede ser más complejo (por ejm, si implementamos filtros o búsquedas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.lista_solicitudes_cards, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.namess.text = itemName[position]
        holder.cedula.text = itemCedula[position]
        holder.fecha_inicio.text = itemFechaI[position]
        holder.fecha_fin.text = itemFechaF[position]

        holder.itemView.setOnClickListener{v:View ->
            v.findNavController().navigate(R.id.action_listaSolicitudes_to_detalleSolicitud)
        }

    }

    override fun getItemCount(): Int {
        return itemName.size
    }



}