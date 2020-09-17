package com.example.sustentabilidade.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sustentabilidade.R
import com.example.sustentabilidade.databinding.ProgramItemlistBinding
import com.example.sustentabilidade.fragment.certificationfragments.managecertification.CreateCertificationItemClickListener

class StringAdapter : ListAdapter<String, StringAdapter.ViewHolder>(StringDiff()) {

    private lateinit var listener: CreateCertificationItemClickListener

    class ViewHolder private constructor(
        val binding: ProgramItemlistBinding,
        val list: CreateCertificationItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String, position: Int) {
            binding.certificationItemDomainQuantity.visibility = View.GONE
            binding.certificationItemName.text = item
            binding.certificationLayoutClick.setOnClickListener {
                list.onClick(position)
            }

        }

        companion object {
            fun from(
                parent: ViewGroup,
                listener: CreateCertificationItemClickListener
            ): StringAdapter.ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding: ProgramItemlistBinding =
                    DataBindingUtil.inflate(inflater, R.layout.program_itemlist, parent, false);

                return ViewHolder(binding, listener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    fun attachListener(mListener: CreateCertificationItemClickListener) {
        listener = mListener
    }

}

class StringDiff : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return true
    }

}