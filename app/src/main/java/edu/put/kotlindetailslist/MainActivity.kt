package edu.put.kotlindetailslist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import edu.put.kotlindetailslist.database.Trail
import edu.put.kotlindetailslist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val vm by viewModels<MainViewModel>()
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadSomeDataToDB()
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

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
                difficulty = "Średni",
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
                difficulty = "Średni",
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
            )
        )
    }

}