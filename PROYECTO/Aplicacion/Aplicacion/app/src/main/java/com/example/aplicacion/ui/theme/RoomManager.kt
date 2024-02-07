package com.example.aplicacion.ui.theme

object RoomManager {
    val rooms = mutableListOf<Room>()

    fun initializeRooms(numRooms: Int) {
        for (i in 1..numRooms) {
            rooms.add(Room(i, false, 20))
        }
    }

    fun turnOnHeating(roomId: Int) {
        rooms.find { it.id == roomId }?.apply {
            isHeatingOn = true
        }
    }

    fun turnOffHeating(roomId: Int) {
        rooms.find { it.id == roomId }?.apply {
            isHeatingOn = false
        }
    }

    fun controlTemperature(roomId: Int, temperature: Int) {
        rooms.find { it.id == roomId }?.apply {
            isHeatingOn = true
            configuredTemperature = temperature
        }
    }
}