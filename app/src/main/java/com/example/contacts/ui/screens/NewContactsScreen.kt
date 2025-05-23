package com.example.contacts.ui.screens

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.contacts.common.CommonAdapter
import com.example.contacts.common.SharedPrefsManager
import com.example.contacts.common.UIState
import com.example.contacts.common.Utils
import com.example.contacts.common.Utils.Companion.dp
import com.example.contacts.data.model.Contact
import com.example.contacts.data.model.ContactListResponse
import com.example.contacts.databinding.FragmentNewContactsScreenBinding
import com.example.contacts.databinding.NewContactCardBinding
import com.example.contacts.ui.viewmodel.ContactViewModel
import org.koin.android.ext.android.inject

class NewContactsScreen : Fragment() {

    // Binding
    private var _binding: FragmentNewContactsScreenBinding? = null
    private val binding get() = _binding!!

    // Adapter
    private lateinit var adapter: CommonAdapter

    // ViewModel
    private val contactViewModel: ContactViewModel by inject()

    // Local Memory
    private val local: SharedPrefsManager by inject()

    // local variables
    private var contacts = emptyList<Contact>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewContactsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        fetchNewContacts()
        listen()
        observe()
    }

    private fun listen() {
        binding.apply {

            syncBtn.setOnClickListener {
                local.addContactsBulk(contacts)

                Toast.makeText(requireContext(), "Contacts synced Successfully", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }

        }
    }

    private fun initAdapter() {
        adapter = CommonAdapter(NewContactCardBinding::inflate) { itemBinding, item, position ->
            if (itemBinding is NewContactCardBinding && item is Contact) {
                itemBinding.apply {
                    root.setCardBackgroundColor(Utils.getColorForPosition(position))

                    name.text = item.fullName
                    role.text = item.course
                    phone.text = item.phone
                    email.text = item.email

                    editBtn.setOnClickListener {
                        // TODO(Navigate to Add/Edit Contact)
                        Toast.makeText(requireContext(), "Edit", Toast.LENGTH_SHORT).show()
                    }
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
                outRect.top = 32.dp
            }
        })
    }

    private fun observe() {
        contactViewModel.newContacts.observe(viewLifecycleOwner) { response ->
            when (response) {
                UIState.Loading -> {
                    showProgressBar(true)
                }

                is UIState.Success<ContactListResponse> -> {
                    showProgressBar(false)

                    response.data?.data?.users?.let {
                        contacts = it
                        adapter.submitList(it)
                    } ?: run {
                        Toast.makeText(requireContext(), "No Data", Toast.LENGTH_SHORT).show()
                    }
                }

                is UIState.Failure<*> -> {
                    showProgressBar(false)
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun showProgressBar(show: Boolean) {
        binding.progressBar.isVisible = show
    }

    private fun fetchNewContacts() {
        contactViewModel.fetchContacts()
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewContactsScreen().apply {
                arguments = Bundle().apply {

                }
            }
    }
}