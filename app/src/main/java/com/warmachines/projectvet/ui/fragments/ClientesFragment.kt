package com.warmachines.projectvet.ui.fragments

import com.warmachines.projectvet.ui.viewmodel.ClientesViewModel
import com.warmachines.projectvet.ui.adapters.ClientesAdapter

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
import com.warmachines.projectvet.databinding.FragmentClientesBinding

class ClientesFragment : Fragment(R.layout.fragment_clientes) {

    private var _b: FragmentClientesBinding? = null
    private val b get() = _b!!

    private val vm: ClientesViewModel by viewModels()
    private val adapter = ClientesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentClientesBinding.bind(view)

        b.rvClientes.layoutManager = LinearLayoutManager(requireContext())
        b.rvClientes.adapter = adapter

        // Observers
        vm.clientes.observe(viewLifecycleOwner) { adapter.submit(it) }
        vm.loading.observe(viewLifecycleOwner) {
            b.progress.visibility = if (it) View.VISIBLE else View.GONE
        }
        vm.error.observe(viewLifecycleOwner) { it?.let { msg ->
            Snackbar.make(b.root, msg, Snackbar.LENGTH_LONG).show()
        }}

        // Carga inicial
        vm.load()

        // 🔹 FAB para agregar nueva enfermedad
        b.fabAddCliente.setOnClickListener {
            mostrarDialogoAgregar()
        }
    }

    private fun mostrarDialogoAgregar() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_add_clientes, null)

        val etCodigocliente = dialogView.findViewById<EditText>(R.id.etCodigoCliente)
        val etNombres = dialogView.findViewById<EditText>(R.id.etNombresCliente)
        val etApellido1 = dialogView.findViewById<EditText>(R.id.etApellido1Cliente)
        val etApellido2 = dialogView.findViewById<EditText>(R.id.etApellido2Cliente)
        val etCorreo = dialogView.findViewById<EditText>(R.id.etCorreoCliente)
        val etTelefono = dialogView.findViewById<EditText>(R.id.etTelefonoCliente)
        val etDireccion = dialogView.findViewById<EditText>(R.id.etDireccionCliente)

        AlertDialog.Builder(requireContext())
            .setTitle("Nuevo Cliente")
            .setView(dialogView)
            .setPositiveButton("Guardar") { d, _ ->
                val codigocliente = etCodigocliente.text.toString().trim()
                val nombres = etNombres.text.toString().trim()
                val apellido1 = etApellido1.text.toString().trim()
                val apellido2 = etApellido2.text.toString().trim()
                val correo = etCorreo.text.toString().trim()
                val telefono = etTelefono.text.toString().trim()
                val direccion = etDireccion.text.toString().trim()

                if (codigocliente.isNotEmpty() && nombres.isNotEmpty() && apellido1.isNotEmpty()
                    && correo.isNotEmpty() && telefono.isNotEmpty() && direccion.isNotEmpty()) {
                    vm.addCliente(codigocliente, nombres, apellido1, apellido2, correo,
                        telefono, direccion)
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