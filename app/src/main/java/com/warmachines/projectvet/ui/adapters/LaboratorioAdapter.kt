package com.warmachines.projectvet.ui.adapters
import com.warmachines.projectvet.models.MostrarLaboratorio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.warmachines.projectvet.R

class LaboratorioAdapter(private var items: List<MostrarLaboratorio> = emptyList())
    : RecyclerView.Adapter<LaboratorioAdapter.VH>() {

    fun submit(list: List<MostrarLaboratorio>) {
        items = list
        notifyDataSetChanged()
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val tvCodigoLab: TextView = v.findViewById(R.id.tvCodigoLab)

        val tvNombreLab: TextView = v.findViewById(R.id.tvNombreLab)

        val tvDireccionLab: TextView = v.findViewById(R.id.tvDireccionLab)
    }

    override fun onCreateViewHolder(p: ViewGroup, vt: Int): VH {
        val v = LayoutInflater.from(p.context).inflate(R.layout.item_laboratorios, p, false)
        return VH(v)
    }

    override fun onBindViewHolder(h: VH, pos: Int) {
        val it = items[pos]
        h.tvCodigoLab.text = it.Codigo_Laboratorio
        h.tvNombreLab.text = it.Nombre_Laboratorio
        h.tvDireccionLab.text = it.Direccion_Laboratorio
    }

    override fun getItemCount() = items.size
}