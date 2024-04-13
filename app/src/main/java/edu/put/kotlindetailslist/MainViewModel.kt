package edu.put.kotlindetailslist

import android.content.Context
import androidx.lifecycle.ViewModel
import edu.put.kotlindetailslist.database.Trail

interface Server {
    fun loadDataFromDB(context: Context?, difficulty: String): List<Trail>
}

class MainViewModel : ViewModel(), Server {
    private var trail: Trail? = null
    private var service = TrailService()

    override fun loadDataFromDB(context: Context?, difficulty: String): List<Trail> {
        return service.findAllTrails(context)
            .filter {
                it.difficulty == difficulty
            }
    }

    fun setTrail(trail: Trail) {
        this.trail = trail
    }

    fun getTrail() = trail

}
