package com.codingchallenge.courierservice.data

import com.codingchallenge.courierservice.common.computeCourierPackage
import org.junit.Assert.assertTrue
import org.junit.Test

class VehicleTest {

    @Test
    fun getDeliveryStartTime_success_get_start_time() {
        val packageData = computeCourierPackage(listOf("PKG1", "50", "30", "OFR001")).apply {
            deliveryTime = 2.0
            fleetWaitingTime = 0.0
        }
        val vehicle = Vehicle("vehicle_1", 200.0, 70.0)
        vehicle.apply {
            jobQueue.add(Pair(2.0, mutableSetOf(packageData)))
        }

        assertTrue(2.0 == vehicle.getDeliveryStartTime())
    }

}