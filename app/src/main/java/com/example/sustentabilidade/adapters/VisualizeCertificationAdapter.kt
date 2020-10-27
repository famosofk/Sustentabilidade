package com.example.sustentabilidade.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sustentabilidade.R
import com.example.sustentabilidade.databinding.ItemQuestionVisualizeAnswerLayoutBinding
import com.example.sustentabilidade.models.certificationmodels.Answer
import com.example.sustentabilidade.models.certificationmodels.Certification
import com.example.sustentabilidade.models.certificationmodels.Question

class VisualizeCertificationAdapter :
    ListAdapter<Answer, VisualizeCertificationAdapter.ViewHolder>(AnswerDiff()) {

    private lateinit var certification: Certification

    class ViewHolder(
        val binding: ItemQuestionVisualizeAnswerLayoutBinding,
        val certification: Certification
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(answer: Answer) {
            binding.questionTextView.text = certification.getQuestion(answer.index)!!.name
            when (answer.value) {
                Question.ANSWER_NAO_SE_APLICA -> binding.nonApplicableButton.isChecked = true
                Question.ANSWER_NAO_POSSUI -> binding.falseRadioButton.isChecked = true
                Question.ANSWER_POSSUI -> binding.trueRadioButton.isChecked = true
            }

        }

        companion object {
            fun from(parent: ViewGroup, certification: Certification): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding: ItemQuestionVisualizeAnswerLayoutBinding = DataBindingUtil.inflate(
                    inflater,
                    R.layout.item_question_visualize_answer_layout,
                    parent,
                    false
                )
                return ViewHolder(binding, certification)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, certification)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun attachCertification(certification: Certification) {
        this.certification = certification
    }
}

class AnswerDiff : DiffUtil.ItemCallback<Answer>() {
    override fun areItemsTheSame(oldItem: Answer, newItem: Answer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Answer, newItem: Answer): Boolean {
        return true
    }
}
