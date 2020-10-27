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
import com.example.sustentabilidade.helpers.ClickListener
import com.example.sustentabilidade.models.certificationmodels.AnswerList

class AnswerListAdapter : ListAdapter<AnswerList, AnswerListAdapter.ViewHolder>(AnswerListDiff()) {
    private lateinit var mClickListener: ClickListener

    class ViewHolder private constructor(
        val binding: ItemlistLayoutBinding,
        val clickListener: ClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(answerList: AnswerList, position: Int) {
            binding.textView.text = answerList.farmCode
            binding.textView.textSize = 16f
            if (answerList.finished == "1") {
                binding.imageView.visibility = View.VISIBLE
            }
            binding.layoutClickListener.setOnClickListener {
                clickListener.onClick(position)
            }
        }

        companion object {
            fun from(parent: ViewGroup, clickListener: ClickListener): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding: ItemlistLayoutBinding =
                    DataBindingUtil.inflate(inflater, R.layout.itemlist_layout, parent, false)
                return ViewHolder(binding, clickListener)
            }
        }
    }

    fun attachListener(clickListener: ClickListener) {
        mClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, mClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
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