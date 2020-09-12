package com.example.sustentabilidade.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sustentabilidade.databinding.ItemlistLayoutBinding
import com.example.sustentabilidade.helpers.ClickListener
import com.example.sustentabilidade.models.gestaomodels.Farm


class SelectFarmAdapter : ListAdapter<Farm, SelectFarmViewHolder>(SelectFarmDiff()) {

    private lateinit var listener: ClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectFarmViewHolder {
        return SelectFarmViewHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: SelectFarmViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    fun attachListener(mlistener: ClickListener) {
        listener = mlistener
    }
}

class SelectFarmViewHolder(val binding: ItemlistLayoutBinding, val listener: ClickListener) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(farm: Farm, position: Int) {
        binding.textView.text = farm.codigoFazenda
        binding.layoutClickListener.setOnClickListener {
            listener.onClick(position)
        }
    }

    companion object {
        fun from(parent: ViewGroup, listener: ClickListener): SelectFarmViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding: ItemlistLayoutBinding =
                ItemlistLayoutBinding.inflate(inflater, parent, false)
            return SelectFarmViewHolder(binding, listener)
        }
    }
}

class SelectFarmDiff : DiffUtil.ItemCallback<Farm>() {
    override fun areItemsTheSame(oldItem: Farm, newItem: Farm): Boolean {
        return oldItem.codigoFazenda == newItem.codigoFazenda
    }

    override fun areContentsTheSame(oldItem: Farm, newItem: Farm): Boolean {
        return true
    }

}