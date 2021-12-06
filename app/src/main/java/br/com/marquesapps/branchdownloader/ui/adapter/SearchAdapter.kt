package br.com.marquesapps.branchdownloader.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.marquesapps.branchdownloader.databinding.RepositoryItemBinding
import br.com.marquesapps.branchdownloader.model.Repository
import br.com.marquesapps.branchdownloader.ui.databinding.RepositoryData

class SearchAdapter(
    private val context: Context?,
    repositories: List<Repository> = listOf()
) : RecyclerView.Adapter<SearchAdapter.RepositoryViewHolder>() {

    private var _repositories = repositories.toMutableList()
    var onRepositoryClickListener: (Repository) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val inflate = LayoutInflater.from(context)
        val viewDataBinding = RepositoryItemBinding.inflate(inflate, parent, false)
        return RepositoryViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val repository = _repositories[position]
        holder.bind(repository)
    }

    override fun getItemCount(): Int = _repositories.size

    fun replaceRepositoryList(newRepositoryList: List<Repository>){
        this._repositories.clear()
        this._repositories.addAll(newRepositoryList)
        notifyDataSetChanged()
    }

    fun addNewRepositories(repositories: List<Repository>){
        val index = itemCount
        this._repositories.addAll(repositories)
        notifyItemRangeInserted(index, repositories.size)
    }

    inner class RepositoryViewHolder(private val viewDataBinding: RepositoryItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        private lateinit var repository: Repository

        fun bind(repository: Repository) {
            this.repository = repository
            viewDataBinding.repositoryData = RepositoryData()
                .also { it.updateRepositoryData(repository) }
            viewDataBinding.onRepositoryItemClickListener = View.OnClickListener {
                onRepositoryClickListener(repository)
            }
        }

    }

}