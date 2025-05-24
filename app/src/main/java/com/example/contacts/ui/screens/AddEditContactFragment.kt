package com.example.contacts.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.contacts.common.SharedPrefsManager
import com.example.contacts.data.model.Contact
import com.example.contacts.databinding.FragmentAddEditContactBinding
import org.koin.android.ext.android.inject


class AddEditContactFragment : Fragment() {

    // Binding
    private var _binding: FragmentAddEditContactBinding? = null
    private val binding get() = _binding!!

    // Local Memory
    private val local: SharedPrefsManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditContactBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listen()
    }

    private fun listen() {
        binding.apply {

            saveBtn.setOnClickListener {

                if (firstName.editText!!.text.toString().isBlank()) {
                    firstName.error = "Mandatory"
                    return@setOnClickListener
                }
                if (phone.editText!!.text.toString().isBlank()) {
                    phone.error = "Mandatory"
                    return@setOnClickListener
                }

                val firstName = firstName.editText!!.text.toString()
                val surname = surname.editText!!.text.toString()
                val phone = phone.editText!!.text.toString()

                local.addContact(
                    Contact(
                        course = "",
                        email = "",
                        enrolledOn = "",
                        fullName = "$firstName $surname",
                        id = Math.random().toString(),
                        phone = phone,
                    )
                )

                Toast.makeText(requireContext(), "Contact Added Successfully", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddEditContactFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}