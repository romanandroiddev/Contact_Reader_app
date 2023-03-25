package com.example.contactreaderapp.ui.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.contactreaderapp.R
import com.example.contactreaderapp.data.ContactData
import com.example.contactreaderapp.databinding.FragmentInfoBinding

class InfoFragment : Fragment(R.layout.fragment_info) {
    private lateinit var binding: FragmentInfoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoBinding.bind(view)

        initData()


    }

    private fun initData() {
        val nameAndSurname = requireArguments().getString(NAME_OF_CONTACT)
        binding.tvNameSurname.text = nameAndSurname
        binding.tvNameSurname.isSelected = true
        binding.tvPhone.text = requireArguments().getString(PHONE_NUMBER_OF_CONTACT)
        val phoneNumber = requireArguments().getString(PHONE_NUMBER_OF_CONTACT)




        binding.btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            startActivity(intent)
        }

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    companion object {
        private const val NAME_OF_CONTACT = "name"
        private const val PHONE_NUMBER_OF_CONTACT = "phone"
        fun newInstance(contactData: ContactData): InfoFragment {
            val fragment = InfoFragment()
            val bundle = Bundle()
            bundle.putString(NAME_OF_CONTACT, contactData.name)
            bundle.putString(PHONE_NUMBER_OF_CONTACT, contactData.phone)
            fragment.arguments = bundle
            return fragment
        }
    }
}