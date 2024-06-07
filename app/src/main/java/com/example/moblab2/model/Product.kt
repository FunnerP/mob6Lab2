package com.example.moblab2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.UUID

@Entity
data class Product(@PrimaryKey val id: UUID =UUID.randomUUID(),
                   var name: String="",
                   var quantity: Int=0,
                   var price: Int=0,
                   var manufacturer: String="",
                   var releaseDate: Int=0): Serializable