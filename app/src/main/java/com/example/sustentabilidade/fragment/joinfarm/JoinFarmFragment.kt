package com.example.sustentabilidade.fragment.joinfarm

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.sustentabilidade.R
import com.example.sustentabilidade.adapters.SelectFarmAdapter
import com.example.sustentabilidade.databinding.JoinFarmFragmentBinding
import com.example.sustentabilidade.helpers.ClickListener

class JoinFarmFragment : Fragment() {

    private lateinit var bind: JoinFarmFragmentBinding
    private lateinit var viewModel: JoinFarmViewModel
    private val adapter = SelectFarmAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = DataBindingUtil.inflate(inflater, R.layout.join_farm_fragment, container, false)
        bind.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(JoinFarmViewModel::class.java)


        attachListeners()
        setObservers(bind)

        arguments?.getString("Program")?.let { viewModel.getFarms(it) }
        return bind.root
    }

    private fun attachListeners() {
        bind.recyclerSelectFarm.adapter = adapter
        val listener = object : ClickListener {
            override fun onClick(id: Int) {
                createAlertDialog(id)
            }
        }
        adapter.attachListener(listener)
    }

    private fun setObservers(bind: JoinFarmFragmentBinding) {

        viewModel.mNavigation.observe(viewLifecycleOwner, {
            if (it) {

                Toast.makeText(
                    context,
                    resources.getString(R.string.success_saving_farm),
                    Toast.LENGTH_SHORT
                ).show()
                bind.root.findNavController().navigate(R.id.action_joinFarmFragment_to_mainFragment)
            }
        })

        viewModel.mFarms.observe(viewLifecycleOwner, {
            if (it) {
                adapter.submitList(viewModel.list)
            }
        })
    }

    private fun createAlertDialog(pos: Int) {
        val mDialogView =
            LayoutInflater.from(context).inflate(R.layout.alert_dialog_layout, null)
        val saveButton: Button = mDialogView.findViewById(R.id.alertDialogSave)
        val body: TextView = mDialogView.findViewById(R.id.alertDialogBody)
        val title: TextView = mDialogView.findViewById(R.id.alertDialogTitle)
        val input: EditText = mDialogView.findViewById(R.id.alertDialogInput)
        body.text = resources.getString(R.string.alertdialog_body_intro_message)
        title.text = resources.getString(R.string.alertdialog_title_intro_message)

        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)
            .create()
        mBuilder.show()
        saveButton.setOnClickListener {
            if (input.text.toString() == viewModel.list[pos].senha) {
                viewModel.copyFarmToDevice(pos)
                mBuilder.dismiss()
            }
        }
    }


}


