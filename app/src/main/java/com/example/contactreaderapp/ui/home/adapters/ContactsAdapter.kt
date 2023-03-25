package com.example.contactreaderapp.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactreaderapp.R
import com.example.contactreaderapp.data.ContactData
import com.example.contactreaderapp.databinding.ItemContactBinding

class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {
    inner class ContactViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val d = models[position]

            val nameAndSurname = d.name
            binding.tvNameSurname.text = nameAndSurname
            binding.tvNameSurname.isSelected = true
            binding.tvPhone.text = d.phone
            if (d.image != null) {
                binding.icProfile.setImageBitmap(d.image)
            }else{
                binding.icProfile.setImageResource(R.drawable.ic_launcher_background)
            }

            binding.root.setOnClickListener {
                onItemClickListener?.invoke(d)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            ItemContactBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = models.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(position)
    }

    var models = listOf<ContactData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    private var onItemClickListener: ((ContactData) -> Unit)? = null
    fun setOnItemClickListener(block: ((ContactData) -> Unit)) {
        onItemClickListener = block
    }
}