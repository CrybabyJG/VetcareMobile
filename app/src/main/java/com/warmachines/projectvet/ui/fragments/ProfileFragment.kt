package com.warmachines.projectvet.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.textview.MaterialTextView
import com.warmachines.projectvet.R

class ProfileFragment : Fragment() {

    private lateinit var profileAvatar: ShapeableImageView
    private lateinit var profileName: MaterialTextView
    private lateinit var btnEditProfile: MaterialButton
    private lateinit var btnLogout: MaterialButton
    private lateinit var switchNotifications: MaterialSwitch
    private lateinit var switchDarkMode: MaterialSwitch
    private lateinit var switchPromos: MaterialSwitch

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Referencias
        profileAvatar = view.findViewById(R.id.profileAvatar)
        profileName = view.findViewById(R.id.profileName)
        btnEditProfile = view.findViewById(R.id.btnEditProfile)
        btnLogout = view.findViewById(R.id.btnLogout)
        switchNotifications = view.findViewById(R.id.switchNotifications)
        switchDarkMode = view.findViewById(R.id.switchDarkMode)
        switchPromos = view.findViewById(R.id.switchPromos)

        // Simulación de datos cargados
        profileName.text = "Mauricio J Gonzalez"

        // Acciones de botones
        btnEditProfile.setOnClickListener {
            // Aquí abrirías un fragment o activity para editar perfil
        }

        btnLogout.setOnClickListener {
            // Aquí cerrarías sesión (ej. limpiar preferencias, volver al login)
        }

        // Listeners de switches
        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            // Guardar preferencia de notificaciones de citas
        }

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            // Guardar preferencia de notificaciones de inventario
        }

        switchPromos.setOnCheckedChangeListener { _, isChecked ->
            // Guardar preferencia de solicitar credenciales
        }

        return view
    }
}