package br.com.marquesapps.branchdownloader.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import br.com.marquesapps.branchdownloader.R
import br.com.marquesapps.branchdownloader.databinding.MainActivityBinding
import br.com.marquesapps.branchdownloader.ui.databinding.BottomNavigationData
import br.com.marquesapps.branchdownloader.ui.viewModel.BottomNavigationViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewDataBinding: MainActivityBinding by lazy {
        MainActivityBinding.inflate(layoutInflater)
    }
    private val controller by lazy { findNavController(R.id.main_activity_fragments) }
    private val bottomNavigationViewModel: BottomNavigationViewModel by viewModel()
    private val bottomNavigationData: BottomNavigationData by lazy { BottomNavigationData() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        controller.addOnDestinationChangedListener { _, destination, _ ->
            title = destination.label
        }
        configureBottomNavigation()
    }

    private fun configureBottomNavigation() {
        viewDataBinding.mainActivityBottomNavigation.setupWithNavController(controller)
        viewDataBinding.bottomNavigationData = bottomNavigationData
        bottomNavigationViewModel.visibility.observe(this, { visibility ->
            bottomNavigationData.navVisibility.set(visibility)
        })
    }
}