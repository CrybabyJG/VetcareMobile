package com.warmachines.projectvet.ui.adapters

import com.warmachines.projectvet.models.Usuarios

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.warmachines.projectvet.R

class UsuariosAdapter(private var items: List<Usuarios> = emptyList())
    : RecyclerView.Adapter<UsuariosAdapter.VH>() {

    fun submit(list: List<Usuarios>) {
        items = list
        notifyDataSetChanged()
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val tvusername: TextView = v.findViewById(R.id.tvUsername)
        val tvemail: TextView = v.findViewById(R.id.tvMail)
        val tvname: TextView = v.findViewById(R.id.tvNames)
        val tvisstaff: TextView = v.findViewById(R.id.tvIsStaff)
        val tvsuperuser: TextView = v.findViewById(R.id.tvIsSuperuser)
    }

    override fun onCreateViewHolder(p: ViewGroup, vt: Int): VH {
        val v = LayoutInflater.from(p.context).inflate(R.layout.item_usuarios, p, false)
        return VH(v)
    }

    override fun onBindViewHolder(h: VH, pos: Int) {
        val it = items[pos]
        val nombreCompleto = "${it.first_name} ${it.last_name}".trim()
        h.tvusername.text = it.username
        h.tvemail.text = it.email
        h.tvname.text = nombreCompleto
        h.tvisstaff.text = if (it.is_staff) "Staff" else "No Staff"
        h.tvsuperuser.text = if (it.is_superuser) "Superusuario" else "Usuario normal"
    }

    override fun getItemCount() = items.size
}