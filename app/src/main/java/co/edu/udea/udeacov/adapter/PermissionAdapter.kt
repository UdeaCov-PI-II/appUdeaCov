package co.edu.udea.udeacov.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.edu.udea.udeacov.databinding.ListaSolicitudesCardsBinding
import co.edu.udea.udeacov.network.response.PermissionCardResponseDto

class PermissionAdapter(private val onclick: PermissionAdapterOnClickListener)
    : ListAdapter<PermissionCardResponseDto, PermissionAdapter.RequestViewHolder> (
    PermissionDiffCallback()
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        return RequestViewHolder.from(this, parent)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RequestViewHolder private constructor(val binding: ListaSolicitudesCardsBinding, private val listener: PermissionAdapterOnClickListener)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PermissionCardResponseDto) {
            with(binding) {
                permission = item
                btnVerSolicitud.setOnClickListener {
                    listener.btnVerOnClick(item, it)
                }
                executePendingBindings()
            }
        }

        companion object {
            fun from(permissionAdapter: PermissionAdapter, parent: ViewGroup): RequestViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListaSolicitudesCardsBinding.inflate(inflater, parent, false)
                return RequestViewHolder(binding, permissionAdapter.onclick)
            }
        }

    }

    interface PermissionAdapterOnClickListener {
        fun btnVerOnClick(permission: PermissionCardResponseDto, view: View)
    }


}

class PermissionDiffCallback : DiffUtil.ItemCallback<PermissionCardResponseDto>() {

    override fun areItemsTheSame(oldItem: PermissionCardResponseDto, newItem: PermissionCardResponseDto): Boolean {
        return  oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PermissionCardResponseDto, newItem: PermissionCardResponseDto): Boolean {
        return  oldItem == newItem
    }

}