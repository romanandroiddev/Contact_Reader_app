package com.example.contactreaderapp.ui.home

import android.content.ContentResolver
import android.content.ContentUris
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.contactreaderapp.MainActivity
import com.example.contactreaderapp.R
import com.example.contactreaderapp.data.ContactData
import com.example.contactreaderapp.databinding.FragmentHomeBinding
import com.example.contactreaderapp.ui.home.adapters.ContactsAdapter
import com.example.contactreaderapp.ui.info.InfoFragment
import java.io.InputStream


class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private var _adapter: ContactsAdapter? = null
    private val adapter get() = _adapter!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        initData()
        initListeners()

    }

    private fun initData() {
        _adapter = ContactsAdapter()
        binding.rvContacts.adapter = adapter
        adapter.models = getContacts()
    }

    private fun initListeners() {
        adapter.setOnItemClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToInfoFragment(
                    it.name, it.phone
                )
            )

        }
    }


    private fun getContacts(): List<ContactData> {

        val contactList = mutableListOf<ContactData>()

        val contentResolver: ContentResolver = requireActivity().contentResolver
        val cursor =
            contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        if (cursor!!.count > 0) {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    val cursorInfo = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )
                    val inputStream: InputStream? =
                        ContactsContract.Contacts.openContactPhotoInputStream(
                            requireActivity().contentResolver, ContentUris.withAppendedId(
                                ContactsContract.Contacts.CONTENT_URI, id.toLong()
                            )
                        )
                    var photo: Bitmap? = null
                    if (inputStream != null) {
                        photo = BitmapFactory.decodeStream(inputStream)
                    }
                    while (cursorInfo!!.moveToNext()) {
                        val name: String =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                        val surname =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_ALTERNATIVE))

                        val mobileNumber =
                            cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        contactList.add(ContactData(name, surname, mobileNumber, photo))
                    }
                    cursorInfo.close()
                }
            }
            cursor.close()
        }
        return contactList
    }
}