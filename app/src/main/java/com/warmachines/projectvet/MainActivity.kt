package com.warmachines.projectvet

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.warmachines.projectvet.ui.fragments.ClientesFragment
import com.warmachines.projectvet.ui.fragments.DashboardFragment
import com.warmachines.projectvet.ui.fragments.EnfermedadesFragment
import com.warmachines.projectvet.ui.fragments.LaboratorioFragment
import com.warmachines.projectvet.ui.fragments.ProfileFragment
import com.warmachines.projectvet.ui.fragments.UsuariosFragment
import com.warmachines.projectvet.ui.fragments.HolaMundoFragment

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        bottomNav = findViewById(R.id.bottom_navigation)

        // Toolbar
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Bottom navigation
        bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_home -> replaceFragment(DashboardFragment(), "Dashboard")
                R.id.nav_users -> replaceFragment(fragment = UsuariosFragment(), title="Usuarios")
                R.id.nav_account -> replaceFragment(ProfileFragment(), "Perfil")
            }
            true
        }

        // Drawer navigation
        navView.setNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.drawer_diseases -> replaceFragment(EnfermedadesFragment(), "Enfermedades")
                R.id.drawer_laboratories -> replaceFragment(LaboratorioFragment(), "Laboratorios")
                R.id.drawer_clients -> replaceFragment(ClientesFragment(), "Clientes")
                R.id.drawer_compose -> replaceFragment(HolaMundoFragment(), "Compose")
                // Agrega otros items si los tienes
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Fragment inicial
        replaceFragment(DashboardFragment(), "Dashboard", addToBackStack = false)

        // Listener para actualizar título al retroceder
        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            when(currentFragment) {
                is DashboardFragment -> supportActionBar?.title = "Dashboard"
                is EnfermedadesFragment -> supportActionBar?.title = "Enfermedades"
                is LaboratorioFragment -> supportActionBar?.title = "Laboratorios"
                is ClientesFragment -> supportActionBar?.title = "Clientes"
                is ProfileFragment -> supportActionBar?.title = "Perfil"
                is UsuariosFragment -> supportActionBar?.title = "Usuarios"
                is HolaMundoFragment -> supportActionBar?.title = "Compose Demo"
            }
        }

        onBackPressedDispatcher.addCallback(this) {
            if (supportFragmentManager.backStackEntryCount > 1) {
                supportFragmentManager.popBackStack()
            } else {
                finish()
            }
        }
    }

    // Función para reemplazar fragmentos y agregar al back stack
    private fun replaceFragment(fragment: Fragment, title: String = "", addToBackStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null) // Solo agrega a la pila si es true
        }
        transaction.commit()
        supportActionBar?.title = title
    }
}