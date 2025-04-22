package com.orderpos

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.orderpos.base.BaseActivity
import com.orderpos.base.FeedbackApplication
import com.orderpos.data.local.AppDatabase
import com.orderpos.data.local.entities.Category
import com.orderpos.data.local.entities.MenuItem
import com.orderpos.data.local.entities.MenuItemOption
import com.orderpos.data.local.entities.Restaurant
import com.orderpos.data.local.entities.User
import com.orderpos.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment?)!!
        val navController = navHostFragment.navController
        binding.appBarMain.contentMain.navView?.let {
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_dashboard,
                    R.id.nav_new_order,
                    R.id.nav_active_orders,
                    R.id.nav_tables,
                    R.id.nav_reports,
                    R.id.nav_settings,
                    R.id.nav_logout,
                )
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            it.setupWithNavController(navController)
        }

        supportActionBar?.hide()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}