package com.warmachines.projectvet.ui.adapters
import com.warmachines.projectvet.models.Enfermedades

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.warmachines.projectvet.R

class EnfermedadAdapter(private var items: List<Enfermedades> = emptyList())
    : RecyclerView.Adapter<EnfermedadAdapter.VH>() {

    fun submit(list: List<Enfermedades>) {
        items = list
        notifyDataSetChanged()
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val tvCodigo: TextView = v.findViewById(R.id.tvCodigo)
        val tvNombre: TextView = v.findViewById(R.id.tvNombre)
    }

    override fun onCreateViewHolder(p: ViewGroup, vt: Int): VH {
        val v = LayoutInflater.from(p.context).inflate(R.layout.item_enfermedades, p, false)
        return VH(v)
    }

    override fun onBindViewHolder(h: VH, pos: Int) {
        val it = items[pos]
        h.tvCodigo.text = it.Codigo_Enfermedades
        h.tvNombre.text = it.Nombre_Enfermedades
    }

    override fun getItemCount() = items.size
}