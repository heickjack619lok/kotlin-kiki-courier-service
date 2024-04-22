package com.codingchallenge.courierservice.data

import com.codingchallenge.courierservice.common.computeCourierPackage
import org.junit.Assert.assertTrue
import org.junit.Test

class CourierPackageTest {

    @Test
    fun getFinalDeliveryTime_success_correct_time() {
        val packageData = computeCourierPackage(listOf("PKG1", "50", "30", "OFR001")).apply {
            deliveryTime = 2.0
            fleetWaitingTime = 3.0
        }
        assertTrue(5.0 == packageData.getFinalDeliveryTime())
    }
}