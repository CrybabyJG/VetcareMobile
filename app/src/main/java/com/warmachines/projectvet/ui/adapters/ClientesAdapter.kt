package com.warmachines.projectvet.ui.adapters

import com.warmachines.projectvet.models.Clientes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.warmachines.projectvet.R

class ClientesAdapter(private var items: List<Clientes> = emptyList())
    : RecyclerView.Adapter<ClientesAdapter.VH>() {

    fun submit(list: List<Clientes>) {
        items = list
        notifyDataSetChanged()
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val tvCodigoCliente: TextView = v.findViewById(R.id.tvCodigoCliente)
        val tvNombres: TextView = v.findViewById(R.id.tvNombreCliente)
        val tvCorreo: TextView = v.findViewById(R.id.tvCorreoCliente)
        val tvTelefono: TextView = v.findViewById(R.id.tvTelefonoCliente)
        val tvDireccion: TextView = v.findViewById(R.id.tvDireccionCliente)
    }

    override fun onCreateViewHolder(p: ViewGroup, vt: Int): VH {
        val v = LayoutInflater.from(p.context).inflate(R.layout.item_clientes, p, false)
        return VH(v)
    }

    override fun onBindViewHolder(h: VH, pos: Int) {
        val it = items[pos]
        val nombreCompleto = "${it.Nombres} ${it.Apellido1} ${it.Apellido2}".trim()

        h.tvCodigoCliente.text = it.Codigo_Cliente
        h.tvNombres.text = nombreCompleto
        h.tvCorreo.text = it.Correo
        h.tvTelefono.text = it.Telefono
        h.tvDireccion.text = it.Direccion
    }

    override fun getItemCount() = items.size
}