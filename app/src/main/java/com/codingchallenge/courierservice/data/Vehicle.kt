package com.codingchallenge.courierservice.data

data class Vehicle(
    val vehicleId: String,
    val maxWeight: Double,
    val maxSpeed: Double,
    val jobQueue: MutableList<Pair<Double, MutableSet<CourierPackage>>> = mutableListOf()
) {
    fun getDeliveryStartTime(): Double {
        return jobQueue.sumOf { it.first }
    }
}