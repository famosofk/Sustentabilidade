package com.example.sustentabilidade.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sustentabilidade.R
import com.example.sustentabilidade.databinding.DirectListAnswerLayoutBinding
import com.example.sustentabilidade.models.certificationmodels.AnswerList
import com.example.sustentabilidade.models.certificationmodels.components.Answer
import com.example.sustentabilidade.models.certificationmodels.components.Question

class DirectQuestionAdapter :
    ListAdapter<Question, DirectQuestionAdapter.ViewHolder>(QuestionDiff()) {

    val answerList = AnswerList()

    fun setCertificationIdFarmCode(certificationId: String, farmCode: String) {
        answerList.certificationID = certificationId
        answerList.farmCode = farmCode
    }

    class ViewHolder private constructor(
        val binding: DirectListAnswerLayoutBinding,
        val answerList: AnswerList,
        val sizeList: Int
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private var itemPosition = -1

        fun bind(question: Question, position: Int) {
            itemPosition = position

            //todo refactor
            binding.questionTitleRecycler.text = question.name
            binding.trueAnswerRadioButton.setOnClickListener {
                //todo fix parent id
                val answer = Answer()
                answer.index = question.index
                answer.value = Question.ANSWER_POSSUI
                answer.parentID = question.parent
                answerList.answerList.removeIf { x -> x.index == question.index }
                answerList.answerList.add(answer)
                answerList.answeredQuestions = answerList.answerList.size
                if (answerList.answerList.size == sizeList) {
                    answerList.finished = AnswerList.FINISHED
                    answerList.calculatePontuation()
                }

            }
            binding.falseAnswerRadioButton.setOnClickListener {
                val answer = Answer()
                answer.id = question.id
                answer.index = question.index
                answer.value = Question.ANSWER_NAO_POSSUI
                answer.parentID = question.id
                answerList.answerList.removeIf { x -> x.index == question.index }
                answerList.answerList.add(answer)
                answerList.answeredQuestions = answerList.answerList.size
                if (answerList.answerList.size == sizeList) {
                    answerList.finished = AnswerList.FINISHED
                    answerList.calculatePontuation()
                }
            }
            binding.nonApplicableRadioButton.setOnClickListener {
                val answer = Answer()
                answer.id = question.id
                answer.index = question.index
                answer.value = Question.ANSWER_NAO_SE_APLICA
                answer.parentID = question.id
                answerList.answerList.removeIf { x -> x.index == question.index }
                answerList.answerList.add(answer)
                answerList.answeredQuestions = answerList.answerList.size
                if (answerList.answerList.size == sizeList) {
                    answerList.finished = AnswerList.FINISHED
                    answerList.calculatePontuation()
                }

            }
        }

        companion object {
            fun from(parent: ViewGroup, answerList: AnswerList, sizeList: Int): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: DirectListAnswerLayoutBinding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.direct_list_answer_layout, parent, false
                )
                return ViewHolder(binding, answerList, sizeList)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, answerList, itemCount)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
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