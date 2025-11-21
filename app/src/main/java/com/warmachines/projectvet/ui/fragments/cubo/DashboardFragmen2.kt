package com.warmachines.projectvet.ui.fragments.cubo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.warmachines.projectvet.ui.viewmodel.cubo.DashboardViewModel
import com.warmachines.projectvet.ui.fragments.ui.theme.ProjectVETTheme
import com.warmachines.projectvet.ui.cubo.DashboardScreen

class DashboardFragment2 : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                ProjectVETTheme {       // ← tu theme real
                    DashboardScreen(viewModel)
                }
            }
        }
    }
}