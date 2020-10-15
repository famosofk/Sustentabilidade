package com.example.sustentabilidade.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sustentabilidade.R
import com.example.sustentabilidade.databinding.DirectListAnswerLayoutBinding
import com.example.sustentabilidade.models.certificationmodels.Question

class DirectQuestionAdapter :
    ListAdapter<Question, DirectQuestionAdapter.ViewHolder>(QuestionDiff()) {

    class ViewHolder private constructor(val binding: DirectListAnswerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(question: Question) {
            binding.questionTitleRecycler.text = question.name
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: DirectListAnswerLayoutBinding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.direct_list_answer_layout, parent, false
                )
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

class QuestionDiff : DiffUtil.ItemCallback<Question>() {
    override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem.index == newItem.index
    }

    override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem.name == newItem.name
    }

}