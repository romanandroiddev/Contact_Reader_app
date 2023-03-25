package com.example.contactreaderapp.ui.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.contactreaderapp.R
import com.example.contactreaderapp.data.ContactData
import com.example.contactreaderapp.databinding.FragmentInfoBinding

class InfoFragment : Fragment(R.layout.fragment_info) {
    private lateinit var binding: FragmentInfoBinding
    private val args: InfoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoBinding.bind(view)

        initData()
    }

    private fun initData() {
        binding.tvNameSurname.text = args.name
        binding.tvNameSurname.isSelected = true
        binding.tvPhone.text = args.phone


        binding.btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${args.phone}"))
            startActivity(intent)
        }

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}