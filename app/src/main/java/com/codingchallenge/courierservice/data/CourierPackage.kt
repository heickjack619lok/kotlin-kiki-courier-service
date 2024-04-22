package com.codingchallenge.courierservice.data

data class CourierPackage(
    val packageId: String,
    val weight: Double,
    val distance: Double,
    val offerCode: String? = null,
    var deliveryTime: Double? = null,
    var fleetWaitingTime: Double? = null,
    var deliveryQueue: Int? = null
) {
    fun getFinalDeliveryTime(): Double {
        return fleetWaitingTime?.plus(deliveryTime ?: 0.0) ?: 0.0
    }
}