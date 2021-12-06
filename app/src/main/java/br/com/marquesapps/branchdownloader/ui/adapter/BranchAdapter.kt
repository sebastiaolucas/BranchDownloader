package br.com.marquesapps.branchdownloader.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.marquesapps.branchdownloader.databinding.BranchItemBinding
import br.com.marquesapps.branchdownloader.model.Branch
import br.com.marquesapps.branchdownloader.ui.databinding.BranchData

class BranchAdapter(
    private val context: Context?,
    branches: List<Branch> = listOf(),
) : RecyclerView.Adapter<BranchAdapter.BranchViewHolder>() {

    private val _branches: MutableList<Branch> = branches.toMutableList()
    var onBranchClickListener: (branch: Branch) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val inflater = LayoutInflater.from(context)
        val viewDataBinding = BranchItemBinding.inflate(inflater, parent, false)
        return BranchViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        val branch = _branches[position]
        holder.bind(branch)
    }

    override fun getItemCount(): Int = _branches.size

    fun replaceBranchList(newBranchList: List<Branch>){
        _branches.clear()
        _branches.addAll(newBranchList)
        notifyDataSetChanged()
    }

    fun addNewBranches(branches: List<Branch>){
        val index = itemCount
        this._branches.addAll(branches)
        notifyItemRangeInserted(index, branches.size)
    }

    inner class BranchViewHolder(private val viewDataBinding: BranchItemBinding)
        : RecyclerView.ViewHolder(viewDataBinding.root){

        private lateinit var branch: Branch

            fun bind(branch: Branch){
                this.branch = branch
                viewDataBinding.branchData = BranchData().also { it.updateBranchData(branch) }
                viewDataBinding.onBranchButtonClickListener = View.OnClickListener {
                    onBranchClickListener(this.branch)
                }
            }

    }
}