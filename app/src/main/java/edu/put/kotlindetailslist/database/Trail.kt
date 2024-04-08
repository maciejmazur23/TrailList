package edu.put.kotlindetailslist.database

import androidx.annotation.DrawableRes


class Trail {
    var id: Int = 0
    val name: String
    val length: Int
    val difficulty: String
    val estimatedDuration: Int
    val description: String
    @DrawableRes var image: Int

    constructor(
        name: String,
        length: Int,
        difficulty: String,
        estimatedDuration: Int,
        description: String,
        image: Int
    ) {
        this.name = name
        this.length = length
        this.difficulty = difficulty
        this.estimatedDuration = estimatedDuration
        this.description = description
        this.image = image
    }

    constructor(
        id: Int,
        name: String,
        length: Int,
        difficulty: String,
        estimatedDuration: Int,
        description: String,
        image: Int
    ) {
        this.id = id
        this.name = name
        this.length = length
        this.difficulty = difficulty
        this.estimatedDuration = estimatedDuration
        this.description = description
        this.image = image
    }

    override fun toString(): String {
        return "Trial(" +
                "name='$name', " +
                "length=$length, " +
                "difficulty='$difficulty', " +
                "estimatedDuration=$estimatedDuration, " +
                "description='$description'" +
                ")"
    }

}
