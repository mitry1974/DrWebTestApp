package com.example.drwebtestapp.packagesInfo.presentation.packagesScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drwebtestapp.databinding.ItemPackageInfoBinding

class ApplicationsInfoAdapter(val onClick: ((String) -> Unit)) :
    RecyclerView.Adapter<ApplicationsInfoAdapter.ApplicationsInfoViewHolder>() {
    private var items = emptyList<AppInfoUiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationsInfoViewHolder {
        return ApplicationsInfoViewHolder(
            ItemPackageInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onClick
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ApplicationsInfoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ApplicationsInfoViewHolder(
        private val binding: ItemPackageInfoBinding,
        private val onClick: (String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(appInfo: AppInfoUiModel) {
            binding.appName.text = appInfo.label
            binding.appIcon.setImageDrawable(appInfo.icon)
            binding.root.setOnClickListener { onClick(appInfo.packageName) }
        }
    }

    fun setItems(newItems: List<AppInfoUiModel>) {
        items = newItems
    }
}