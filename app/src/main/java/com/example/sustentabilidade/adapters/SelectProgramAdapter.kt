package com.example.sustentabilidade.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sustentabilidade.databinding.ItemlistLayoutBinding
import com.example.sustentabilidade.helpers.ClickListener
import com.example.sustentabilidade.models.gestaomodels.Program

class SelectProgramAdapter : ListAdapter<Program, SelectProgramViewHolder>(SelectProgramDiff()) {

    private lateinit var listener: ClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectProgramViewHolder {
        return SelectProgramViewHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: SelectProgramViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    fun attachListener(mlistener: ClickListener) {
        listener = mlistener
    }
}

class SelectProgramViewHolder(val binding: ItemlistLayoutBinding, val listener: ClickListener) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(program: Program, position: Int) {
        binding.textView.text = program.name
        binding.layoutClickListener.setOnClickListener {
            listener.onClick(position)
        }
    }

    companion object {
        fun from(parent: ViewGroup, listener: ClickListener): SelectProgramViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding: ItemlistLayoutBinding =
                ItemlistLayoutBinding.inflate(inflater, parent, false)
            return SelectProgramViewHolder(binding, listener)
        }
    }
}

class SelectProgramDiff : DiffUtil.ItemCallback<Program>() {
    override fun areItemsTheSame(oldItem: Program, newItem: Program): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Program, newItem: Program): Boolean {
        return true
    }

}