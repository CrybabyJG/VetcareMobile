package com.warmachines.projectvet.ui.fragments

import com.warmachines.projectvet.ui.viewmodel.EnfermedadesViewModel
import com.warmachines.projectvet.ui.adapters.EnfermedadAdapter

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
import com.warmachines.projectvet.databinding.FragmentEnfermedadesBinding

class EnfermedadesFragment : Fragment(R.layout.fragment_enfermedades) {

    private var _b: FragmentEnfermedadesBinding? = null
    private val b get() = _b!!

    private val vm: EnfermedadesViewModel by viewModels()
    private val adapter = EnfermedadAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentEnfermedadesBinding.bind(view)

        b.rvEnfermedades.layoutManager = LinearLayoutManager(requireContext())
        b.rvEnfermedades.adapter = adapter

        // Observers
        vm.enfermedades.observe(viewLifecycleOwner) { adapter.submit(it) }
        vm.loading.observe(viewLifecycleOwner) {
            b.progress.visibility = if (it) View.VISIBLE else View.GONE
        }
        vm.error.observe(viewLifecycleOwner) { it?.let { msg ->
            Snackbar.make(b.root, msg, Snackbar.LENGTH_LONG).show()
        }}

        // Carga inicial
        vm.load()

        // 🔹 FAB para agregar nueva enfermedad
        b.fabAddEnfermedad.setOnClickListener {
            mostrarDialogoAgregar()
        }
    }

    private fun mostrarDialogoAgregar() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_add_enfermedad, null)

        val etCodigo = dialogView.findViewById<EditText>(R.id.etCodigoEnfermedad)
        val etNombre = dialogView.findViewById<EditText>(R.id.etNombreEnfermedad)

        AlertDialog.Builder(requireContext())
            .setTitle("Nueva Enfermedad")
            .setView(dialogView)
            .setPositiveButton("Guardar") { d, _ ->
                val codigo = etCodigo.text.toString().trim()
                val nombre = etNombre.text.toString().trim()

                if (codigo.isNotEmpty() && nombre.isNotEmpty()) {
                    vm.addEnfermedad(codigo, nombre)
                } else {
                    Snackbar.make(b.root, "Completa todos los campos", Snackbar.LENGTH_LONG).show()
                }
                d.dismiss()
            }
            .setNegativeButton("Cancelar") { d, _ -> d.dismiss() }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}