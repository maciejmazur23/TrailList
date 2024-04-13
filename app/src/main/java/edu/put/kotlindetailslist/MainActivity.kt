package edu.put.kotlindetailslist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import edu.put.kotlindetailslist.database.Trail
import edu.put.kotlindetailslist.databinding.ActivityMainBinding
import java.lang.Math.abs

class MainActivity : AppCompatActivity() {
    private val vm by viewModels<MainViewModel>()
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    private var x1: Float = 0f
    private var x2: Float = 0f
    private val MIN_SWIPE_DISTANCE = 100

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        loadSomeDataToDB()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setupWithNavController(navController)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_fragment -> moveToStart()
                R.id.dashboard_fragment -> moveToEasyTrials()
                R.id.profile_fragment -> moveToHardTrials()
                else -> false
            }
        }

        findViewById<View>(android.R.id.content).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> x1 = event.x
                MotionEvent.ACTION_UP -> {
                    x2 = event.x
                    val deltaX = x2 - x1
                    if (abs(deltaX) > MIN_SWIPE_DISTANCE) {
                        if (x2 > x1) swipeRight(bottomNav)
                        else swipeLeft(bottomNav)
                    }
                }
            }
            true
        }

    }

    private fun changeDestinationLight(bottomNav: BottomNavigationView) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val bundle = navController.currentBackStackEntry?.arguments
            val difficulty = bundle?.getString("difficulty")
            if (destination.id == R.id.startFragment) {
                bottomNav.menu.findItem(R.id.home_fragment).isChecked = true
            } else if (destination.id == R.id.listFragment && difficulty == "Łatwy") {
                bottomNav.menu.findItem(R.id.dashboard_fragment).isChecked = true
            } else if (destination.id == R.id.listFragment && difficulty == "Trudny"){
                bottomNav.menu.findItem(R.id.profile_fragment).isChecked = true
            }
        }
    }

    private fun swipeRight(bottomNav: BottomNavigationView) {
        val sourceBundle = navController.currentBackStackEntry?.arguments
        val difficulty = sourceBundle?.getString("difficulty")
        if (navController.currentDestination?.id == R.id.listFragment &&
            difficulty.equals("Łatwy")
        ) {
            navController.navigate(R.id.list_to_start_fragment, sourceBundle)
        } else if (navController.currentDestination?.id == R.id.listFragment) {
            val bundle = Bundle()
            bundle.putString("difficulty", "Łatwy")
            navController.navigate(R.id.action_listFragment_self, bundle)
        }
        changeDestinationLight(bottomNav)
    }

    private fun swipeLeft(bottomNav: BottomNavigationView) {
        val bundle = Bundle()
        if (navController.currentDestination?.id == R.id.startFragment) {
            bundle.putString("difficulty", "Łatwy")
            navController.navigate(R.id.action_startFragment_to_listFragment, bundle)
        } else if (navController.currentDestination?.id == R.id.listFragment) {
            bundle.putString("difficulty", "Trudny")
            navController.navigate(R.id.action_listFragment_self, bundle)
        }
        changeDestinationLight(bottomNav)
    }

    private fun moveToStart(): Boolean {
        if (navController.currentDestination?.id == R.id.startFragment) {
            navController.navigate(R.id.action_startFragment_self)
        } else if (navController.currentDestination?.id == R.id.listFragment) {
            navController.navigate(R.id.list_to_start_fragment)
        } else if (navController.currentDestination?.id == R.id.detailFragment) {
            navController.navigate(R.id.action_detailFragment_to_startFragment)
        }
        return true
    }

    private fun moveToEasyTrials(): Boolean {
        val bundle = Bundle()
        bundle.putString("difficulty", "Łatwy")
        if (navController.currentDestination?.id == R.id.startFragment) {
            navController.navigate(R.id.action_startFragment_to_listFragment, bundle)
        } else if (navController.currentDestination?.id == R.id.listFragment) {
            navController.navigate(R.id.action_listFragment_self, bundle)
        } else if (navController.currentDestination?.id == R.id.detailFragment) {
            navController.navigate(R.id.action_detailFragment_to_listFragment, bundle)
        }
        return true
    }

    private fun moveToHardTrials(): Boolean {
        val bundle = Bundle()
        bundle.putString("difficulty", "Trudny")
        if (navController.currentDestination?.id == R.id.startFragment) {
            navController.navigate(R.id.action_startFragment_to_listFragment, bundle)
        } else if (navController.currentDestination?.id == R.id.listFragment) {
            navController.navigate(R.id.action_listFragment_self, bundle)
        } else if (navController.currentDestination?.id == R.id.detailFragment) {
            navController.navigate(R.id.action_detailFragment_to_listFragment, bundle)
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.go_to_logout -> true
            R.id.go_to_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadSomeDataToDB() {
        val trailService = TrailService()
        trailService.dropTableTrail(this)
        trailService.addTrails(someTrails(), this)
    }

    private fun someTrails(): List<Trail> {
        return listOf(
            Trail(
                name = "Szlak Orlich Gniazd",
                length = 100,
                difficulty = "Łatwy",
                estimatedDuration = 2000,
                description = "Szlak turystyczny wzdłuż zamków na Jurze Krakowsko-Częstochowskiej.",
                image = R.drawable.szlak_orlich_gniazd
            ),
            Trail(
                name = "Szlak Bieszczadzki",
                length = 150,
                difficulty = "Trudny",
                estimatedDuration = 3000,
                description = "Jeden z najpiękniejszych szlaków górskich w Polsce, przecinający Bieszczady Wschodnie i Zachodnie.",
                image = R.drawable.glowny_szlak_bieszczadzki
            ),
            Trail(
                name = "Szlak Pieniński",
                length = 50,
                difficulty = "Łatwy",
                estimatedDuration = 1000,
                description = "Szlak prowadzący przez malownicze Pieniny, m.in. przez Trzy Korony i Sokolicę.",
                image = R.drawable.szaki_turystyczne_pienin
            ),
            Trail(
                name = "Szlak Beskidzki",
                length = 120,
                difficulty = "Trudny",
                estimatedDuration = 2500,
                description = "Szlak o charakterze górskim, przecinający pasmo Beskidów.",
                image = R.drawable.szlak_beskidzki
            ),
            Trail(
                name = "Szlak Gór Stołowych",
                length = 70,
                difficulty = "Łatwy",
                estimatedDuration = 1500,
                description = "Szlak prowadzący przez malownicze formacje skalne Gór Stołowych.",
                image = R.drawable.szlak_gor_stolowych
            ),
            Trail(
                name = "Szlak Babia Góra",
                length = 30,
                difficulty = "Łatwy",
                estimatedDuration = 800,
                description = "Szlak prowadzący na najwyższy szczyt Beskidu Żywieckiego - Babia Góra.",
                image = R.drawable.szaki_turystyczne_babia_gora
            ),
            Trail(
                name = "Szlak Morskie Oko",
                length = 10,
                difficulty = "Łatwy",
                estimatedDuration = 300,
                description = "Szlak prowadzący do jednego z najpiękniejszych jezior w Tatrach - Morskiego Oka.",
                image = R.drawable.szaki_turystyczne_morskie_oko
            ),
            Trail(
                name = "Szlak Rysy",
                length = 20,
                difficulty = "Trudny",
                estimatedDuration = 1200,
                description = "Szlak prowadzący na najwyższy szczyt Polski - Rysy.",
                image = R.drawable.szaki_turystyczne_rysy
            ),
        )
    }

}