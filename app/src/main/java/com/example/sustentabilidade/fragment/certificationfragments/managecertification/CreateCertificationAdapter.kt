package com.example.sustentabilidade.fragment.certificationfragments.managecertification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sustentabilidade.R
import com.example.sustentabilidade.databinding.ProgramItemlistBinding
import com.example.sustentabilidade.models.certificationmodels.Certification

class CreateCertificationAdapter :
    ListAdapter<Certification, CreateCertificationAdapter.ViewHolder>(
        CreateProgramDiff()
    ) {
    private lateinit var clickListener: CreateCertificationItemClickListener

    class ViewHolder private constructor(
        val binding: ProgramItemlistBinding,
        private val clickListener: CreateCertificationItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(certification: Certification, position: Int) {
            binding.certificationItemName.text = certification.name
            binding.certificationItemDomainQuantity.text = certification.dominionNumber.toString()
            binding.certificationLayoutClick.setOnClickListener {
                clickListener.onClick(position)
            }
        }

        companion object {

            fun from(
                parent: ViewGroup,
                clickListener: CreateCertificationItemClickListener
            ): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding: ProgramItemlistBinding =
                    DataBindingUtil.inflate(inflater, R.layout.program_itemlist, parent, false)
                return ViewHolder(binding, clickListener)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    fun attachListener(mListener: CreateCertificationItemClickListener) {
        clickListener = mListener
    }

}

class CreateProgramDiff : DiffUtil.ItemCallback<Certification>() {
    override fun areItemsTheSame(oldItem: Certification, newItem: Certification): Boolean {
        return true
    }

    override fun areContentsTheSame(oldItem: Certification, newItem: Certification): Boolean {
        return true
    }
}