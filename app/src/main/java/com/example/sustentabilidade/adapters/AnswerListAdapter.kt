package com.example.sustentabilidade.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sustentabilidade.R
import com.example.sustentabilidade.databinding.ItemlistLayoutBinding
import com.example.sustentabilidade.models.certificationmodels.AnswerList

class AnswerListAdapter : ListAdapter<AnswerList, AnswerListAdapter.ViewHolder>(AnswerListDiff()) {

    class ViewHolder private constructor(val binding: ItemlistLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(answerList: AnswerList) {
            binding.textView.text = answerList.id
            binding.textView.textSize = 16f
            if (answerList.finished == "1") {
                binding.imageView.visibility = View.VISIBLE
            }

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding: ItemlistLayoutBinding =
                    DataBindingUtil.inflate(inflater, R.layout.itemlist_layout, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class AnswerListDiff : DiffUtil.ItemCallback<AnswerList>() {
    override fun areItemsTheSame(oldItem: AnswerList, newItem: AnswerList): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AnswerList, newItem: AnswerList): Boolean {
        return true
    }
}