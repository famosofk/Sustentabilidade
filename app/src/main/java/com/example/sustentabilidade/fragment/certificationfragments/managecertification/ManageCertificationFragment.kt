package com.example.sustentabilidade.fragment.certificationfragments.managecertification

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.sustentabilidade.R
import com.example.sustentabilidade.databinding.FragmentManageCertificationBinding
import com.example.sustentabilidade.models.certificationmodels.Certification
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import io.realm.kotlin.where


class ManageCertificationFragment : Fragment() {

    private lateinit var binding: FragmentManageCertificationBinding
    private lateinit var viewModel: ManageCertificationViewModel
    private val adapter = CreateCertificationAdapter()
    private val elements = mutableListOf<Certification>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_manage_certification,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(ManageCertificationViewModel::class.java)
        initializingRecyclerView()
        enableListeners()
        enableObservers()
        viewModel.getList()

        return binding.root
    }

    private fun initializingRecyclerView() {
        val listener = object : CreateCertificationItemClickListener {
            override fun onClick(p: Int) {
                createAlertDialog(p)
            }
        }
        adapter.attachListener(listener)
        binding.certificationRecycler.adapter = adapter
        getElements()


    }

    private fun getBundle(p: Int): Bundle {
        val bundle = Bundle()
        bundle.putString("certificationName", elements[p].name)
        bundle.putString("certificationID", elements[p].id)
        return bundle
    }

    private fun createAlertDialog(p: Int) {

        val dialogView =
            LayoutInflater.from(context).inflate(R.layout.dialog_view_alert_dialog, null)
        val manageButton: FloatingActionButton = dialogView.findViewById(R.id.manageattributes)
        val addButton: FloatingActionButton = dialogView.findViewById(R.id.createNewAttribute)
        val mBuilder = AlertDialog.Builder(context).setView(dialogView).create()
        mBuilder.show();
        mBuilder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        manageButton.setOnClickListener {
            binding.root.findNavController()
                .navigate(R.id.manageCertificationItemsFragment3, getBundle(p))
            mBuilder.dismiss()
        }
        addButton.setOnClickListener {
            binding.root.findNavController()
                .navigate(R.id.action_manageCertificationFragment_to_signinFragment, getBundle(p))
            mBuilder.dismiss()
        }


    }

    private fun getElements() {
        elements.clear()
        val realm = Realm.getDefaultInstance()
        elements.addAll(realm.where<Certification>().findAll())
        viewModel.getList()
    }

    private fun enableObservers() {
        viewModel.mSavedItem.observe(viewLifecycleOwner, {
            if (it) {
                binding.editTextTitleCreateCertificate.text.clear()
                viewModel.turnBackSavedToFalse()
                getElements()

            }
        })
        viewModel.mUpdatedList.observe(viewLifecycleOwner, {
            if (it) {
                adapter.submitList(null)
                adapter.submitList(elements)
                viewModel.turnBackUpdatedToFalse()
            }
        })
    }



    private fun enableListeners() {
        binding.createCertificationButton.setOnClickListener {
            viewModel.createCertification(binding.editTextTitleCreateCertificate.text.toString())
        }
    }




}