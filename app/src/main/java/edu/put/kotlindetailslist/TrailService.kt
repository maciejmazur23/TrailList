package edu.put.kotlindetailslist

import android.content.Context
import edu.put.kotlindetailslist.database.DBHandler
import edu.put.kotlindetailslist.database.Trail

class TrailService {

    fun createTableTrail(context: Context) {
        val dbHandler = DBHandler(context, null)
        dbHandler.createTrialTable()
    }

    fun dropTableTrail(context: Context) {
        val dbHandler = DBHandler(context, null)
        dbHandler.dropTrialTable()
    }

    fun findAllTrails(context: Context?): List<Trail> {
        val dbHandler = DBHandler(context!!, null)
        return dbHandler.findAll()
    }

    fun addTrail(trail: Trail, context: Context) {
        val dbHandler = DBHandler(context, null)
        val name = trail.name
        val length = trail.length
        val difficulty = trail.difficulty
        val estimatedDuration = trail.estimatedDuration
        val description = trail.description
        val image = trail.image

        dbHandler.addTrail(Trail(name, length, difficulty, estimatedDuration, description, image))
    }

    fun addTrails(trails: List<Trail>, context: Context){
        for (t in trails){
            addTrail(t, context)
        }
    }

}