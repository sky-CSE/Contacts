package com.example.contacts.ui.screens

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.contacts.R
import com.example.contacts.common.CommonAdapter
import com.example.contacts.common.SharedPrefsManager
import com.example.contacts.common.Utils
import com.example.contacts.common.Utils.Companion.dp
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
                findNavController().navigate(R.id.action_contactListFragment_to_newContactsScreen)
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
        adapter = CommonAdapter(ContactListItemBinding::inflate) { itemBinding, item, position ->
            if (itemBinding is ContactListItemBinding && item is Contact) {

                val contact = adapter.currentList[position] as Contact
                val name = contact.fullName

                itemBinding.apply {
                    tag.text = name.firstOrNull()?.uppercase() ?: ""
                    contactName.text = name

                    tag.backgroundTintList = ColorStateList.valueOf(Utils.getColorForPosition(position))
                }
            }
        }

        binding.contactsRv.adapter = adapter
        binding.contactsRv.addItemDecoration(object : ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.top = 16.dp
                outRect.left = 32.dp
                outRect.right = 32.dp
            }
        })

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