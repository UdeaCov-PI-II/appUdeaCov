package co.edu.udea.udeacov.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.edu.udea.udeacov.databinding.ApprovalItemBinding
import co.edu.udea.udeacov.network.response.ApprovalsResponseDto
import co.edu.udea.udeacov.network.response.RoleResponseDto


class ApprovalAdapter(private val onclick: ApprovalAdapterOnClickListener, val approvals : List<ApprovalsResponseDto>?, val currentRole : String)
    : ListAdapter<RoleResponseDto, ApprovalAdapter.RequestViewHolder> (
    ApprovalDiffCallback()
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        return RequestViewHolder.from(this, parent)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        holder.bind(getItem(position),approvals, currentRole)
    }

    class RequestViewHolder private constructor(val binding: ApprovalItemBinding, private val listener: ApprovalAdapterOnClickListener)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RoleResponseDto, approvals: List<ApprovalsResponseDto>?,currentRole: String) {
            with(binding) {
                role = item
                btnApproval.setOnClickListener {
                    listener.btnApproveOnClick(binding, it)
                }
                try{
                    approvals?.let{
                        val approval = approvals.first{ it.user.role.roleId == item.roleId }
                        txtMotivo.setText(approval.reason)
                        txtMotivo.focusable = View.NOT_FOCUSABLE
                        txtMotivo.isEnabled = false
                        txtMotivo.isCursorVisible=false
                        txtMotivo.setBackgroundColor(Color.TRANSPARENT);
                        chkApproved.isChecked = approval.approved
                        chkApproved.isEnabled = false
                        btnApproval.isEnabled =false
                    }
                }catch (e: NoSuchElementException){
                    Log.d("ApprovalAdapter", "No exists approval from role")
                }
                if(item.roleId != currentRole){
                    txtMotivo.focusable = View.NOT_FOCUSABLE
                    txtMotivo.isEnabled = false
                    txtMotivo.isCursorVisible=false
                    txtMotivo.setBackgroundColor(Color.TRANSPARENT);
                    chkApproved.isEnabled = false
                    btnApproval.isEnabled =false
                }
                executePendingBindings()
            }
        }

        companion object {
            fun from(permissionAdapter: ApprovalAdapter, parent: ViewGroup): RequestViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ApprovalItemBinding.inflate(inflater, parent, false)
                return RequestViewHolder(binding, permissionAdapter.onclick)
            }
        }

    }

    interface ApprovalAdapterOnClickListener {
        fun btnApproveOnClick(binding: ApprovalItemBinding, view: View)
    }


}

class ApprovalDiffCallback : DiffUtil.ItemCallback<RoleResponseDto>() {

    override fun areItemsTheSame(oldItem: RoleResponseDto, newItem: RoleResponseDto): Boolean {
        return  oldItem.modelId == newItem.modelId
    }

    override fun areContentsTheSame(oldItem: RoleResponseDto, newItem: RoleResponseDto): Boolean {
        return  oldItem == newItem
    }

}