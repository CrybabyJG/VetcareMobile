package com.warmachines.projectvet.ui.fragments


import com.warmachines.projectvet.ui.viewmodel.UsuariosViewModel
import com.warmachines.projectvet.ui.adapters.UsuariosAdapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.warmachines.projectvet.R
import com.warmachines.projectvet.databinding.FragmentUsuariosBinding

class UsuariosFragment : Fragment(R.layout.fragment_usuarios) {

    private var _b: FragmentUsuariosBinding? = null
    private val b get() = _b!!

    private val vm: UsuariosViewModel by viewModels()
    private val adapter = UsuariosAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentUsuariosBinding.bind(view)

        b.rvUsuarios.layoutManager = LinearLayoutManager(requireContext())
        b.rvUsuarios.adapter = adapter

        // Observers
        vm.usuarios.observe(viewLifecycleOwner) { adapter.submit(it) }
        vm.loading.observe(viewLifecycleOwner) {
            b.progress.visibility = if (it) View.VISIBLE else View.GONE
        }
        vm.error.observe(viewLifecycleOwner) { it?.let { msg ->
            Snackbar.make(b.root, msg, Snackbar.LENGTH_LONG).show()
        }}

        // Carga inicial
        vm.load()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}