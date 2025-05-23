package com.example.contacts.ui.screens

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.contacts.common.CommonAdapter
import com.example.contacts.common.SharedPrefsManager
import com.example.contacts.data.model.Contact
import com.example.contacts.databinding.ContactListItemBinding
import com.example.contacts.databinding.FragmentContactListBinding
import org.koin.android.ext.android.inject

class ContactListFragment : Fragment() {

    // Binding
    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!

    // Adapter
    private lateinit var adapter: CommonAdapter

    // Local Memory
    private val local: SharedPrefsManager by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        listen()
    }

    private fun listen() {
        binding.apply {

            addBtn.setOnClickListener {
                // TODO (Open "Add Contact" fragment)
                Toast.makeText(requireContext(), "Add Button clicked", Toast.LENGTH_SHORT).show()

                // TODO (Remove these codes)
                saveNewContact()
                showContactList()
            }

            syncBtn.setOnClickListener {
                // TODO (Open "Add new Contacts" fragment)
                Toast.makeText(requireContext(), "Sync Button clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveNewContact() {
        local.addContact(
            Contact(
                course = "",
                email = "",
                enrolledOn = "",
                fullName = "SKY${Math.random()}",
                id = "",
                phone = ""
            )
        )
    }

    private fun initView() {
        initAdapter()
    }

    private fun initAdapter() {
        val colors = listOf(
            Color.RED, Color.GREEN, Color.BLUE,
            Color.CYAN, Color.MAGENTA
        )

        adapter = CommonAdapter(ContactListItemBinding::inflate) { itemBinding, item, position ->
            if (itemBinding is ContactListItemBinding && item is Contact) {

                val contact = adapter.currentList[position] as Contact
                val name = contact.fullName

                itemBinding.apply {
                    tag.text = name.firstOrNull()?.uppercase() ?: ""
                    contactName.text = name

                    // set color to background of tag
                    val color = colors[position % colors.size]
                    val drawable = GradientDrawable().apply {
                        shape = GradientDrawable.OVAL
                        setColor(color)
                    }
                    tag.background = drawable
                }
            }
        }

        binding.contactsRv.adapter = adapter
        showContactList()
    }

    private fun showContactList() {
        adapter.submitList(local.getContacts().toList())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ContactListFragment().apply {}
    }
}