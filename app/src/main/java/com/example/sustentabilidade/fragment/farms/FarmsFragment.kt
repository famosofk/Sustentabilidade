package com.example.sustentabilidade.fragment.farms


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.sustentabilidade.R
import com.example.sustentabilidade.databinding.FragmentFarmsBinding
import com.example.sustentabilidade.helpers.NetworkHelper
import com.example.sustentabilidade.helpers.ScreenHelper
import com.example.sustentabilidade.models.Program
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.realm.Realm
import io.realm.kotlin.where


class FarmsFragment : Fragment() {

    private lateinit var binding: FragmentFarmsBinding
    private lateinit var viewModel: FarmsViewModel
    private lateinit var realm: Realm

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bind: FragmentFarmsBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_farms, container, false)
        bind.lifecycleOwner = this
        binding = bind
        viewModel = ViewModelProvider(this).get(FarmsViewModel::class.java)
        realm = Realm.getDefaultInstance()

        startDownload()
        enableNavigation()

        return bind.root
    }

    private fun startDownload() {

        if (NetworkHelper.isOnline(requireNotNull(context))) {
            ScreenHelper.enableLoading(requireActivity())
            Toast.makeText(context, "iniciou", Toast.LENGTH_SHORT).show()

            val db = Firebase.database.reference.child("programas")
            val listener = object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    realm.beginTransaction()
                    val result = realm.where<Program>().findAll()

                    snapshot.children.forEach {
                        val program = it.getValue(Program::class.java)
                        if (program != null && (result.where().contains("name", program.name)
                                .count() == 0.toLong())
                        ) {
                            realm.copyToRealm(program)
                        }
                    }
                    realm.commitTransaction()
                    ScreenHelper.disableLoading(requireActivity())
                    Toast.makeText(context, "terminou", Toast.LENGTH_SHORT).show()
                }
            }
            db.addListenerForSingleValueEvent(listener)
        }
    }

    private fun enableNavigation() {
        binding.createFarmNavigation.setOnClickListener {
            binding.root.findNavController()
                .navigate(R.id.action_farmsFragment_to_createFarmFragment)
        }
        binding.createProgramNavigation.setOnClickListener {
            binding.root.findNavController()
                .navigate(R.id.action_farmsFragment_to_createProgramFragment)
        }
        binding.joinFarmNavigation.setOnClickListener {
            binding.root.findNavController()
                .navigate(R.id.action_farmsFragment_to_selectProgramFragment)
        }
    }

}





