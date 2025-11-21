package com.warmachines.projectvet.ui.fragments
import com.warmachines.projectvet.ui.viewmodel.LaboratorioViewModel


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
import com.warmachines.projectvet.databinding.FragmentLaboratoriosBinding
import com.warmachines.projectvet.ui.adapters.LaboratorioAdapter

class LaboratorioFragment : Fragment(R.layout.fragment_laboratorios) {

    private var _b: FragmentLaboratoriosBinding? = null
    private val b get() = _b!!

    private val vm: LaboratorioViewModel by viewModels()
    private val adapter = LaboratorioAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentLaboratoriosBinding.bind(view)

        b.rvLaboratorios.layoutManager = LinearLayoutManager(requireContext())
        b.rvLaboratorios.adapter = adapter

        // Observers
        vm.laboratorio.observe(viewLifecycleOwner) { adapter.submit(it) }
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