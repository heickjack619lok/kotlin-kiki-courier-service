package com.codingchallenge.courierservice

import com.codingchallenge.courierservice.common.computeCourierPackage
import com.codingchallenge.courierservice.data.CourierPackage
import com.codingchallenge.courierservice.data.Vehicle
import org.junit.Assert.*
import org.junit.Test

class CourierServiceTest {

    @Test
    fun calcTotalAmount_success_correctDiscountedAmount() {
        assertTrue(50.0 == calcTotalAmount(100.0, 50.0))
    }

    @Test
    fun calcTotalAmount_failed_incorrectDiscountedAmount() {
        assertFalse(40.0 == calcTotalAmount(100.0, 50.0))
    }

    @Test
    fun calcBeforeDiscountAmount_success_correctBaseRateMultiplier() {
        assertTrue(25.0 == calcBeforeDiscountAmount(10.0, CourierPackage("pkg1", 1.0, 1.0)))
    }

    @Test
    fun calcDiscountAmount_success_OFR001_applied() {
        assertTrue(
            120.0 == calcDiscountAmount(
                100.0,
                CourierPackage("pkg1", 100.0, 20.0, offerCode = "OFR001")
            )
        )
    }

    @Test
    fun calcDiscountAmount_failed_exceed_weight_OFR001_not_applied() {
        assertTrue(
            0.0 == calcDiscountAmount(
                100.0,
                CourierPackage("pkg1", 201.0, 20.0, offerCode = "OFR001")
            )
        )
    }

    @Test
    fun calcDiscountAmount_failed_exceed_distance_OFR001_not_applied() {
        assertTrue(
            0.0 == calcDiscountAmount(
                100.0,
                CourierPackage("pkg1", 200.0, 201.0, offerCode = "OFR001")
            )
        )
    }

    @Test
    fun calcBeforeDiscountAmount_success_full_price_applied() {
        assertTrue(
            1200.0 == calcBeforeDiscountAmount(
                100.0,
                CourierPackage("pkg1", 100.0, 20.0, offerCode = "OFR001")
            )
        )
    }

    @Test
    fun calcBeforeDiscountAmount_failed_full_price_applied() {
        assertFalse(
            1100.0 == calcBeforeDiscountAmount(
                100.0,
                CourierPackage("pkg1", 100.0, 20.0, offerCode = "OFR001")
            )
        )
    }

    @Test
    fun calcPackageETA_success_get_package_sorted_ascending() {
        val vehicleList = mutableListOf<Vehicle>()
        vehicleList.add(Vehicle("vehicle_1", 200.0, 70.0))
        vehicleList.add(Vehicle("vehicle_2", 200.0, 70.0))

        val rawDataList = mutableListOf<CourierPackage>()
        rawDataList.add(computeCourierPackage(listOf("PKG1", "50", "30", "OFR001")))
        rawDataList.add(computeCourierPackage(listOf("PKG2", "75", "125", "OFR008")))
        rawDataList.add(computeCourierPackage(listOf("PKG3", "175", "100", "OFR003")))
        rawDataList.add(computeCourierPackage(listOf("PKG4", "110", "60", "OFR004")))
        rawDataList.add(computeCourierPackage(listOf("PKG5", "155", "95", "NA")))

        val expectedResult = mutableListOf<CourierPackage>()
        expectedResult.add(computeCourierPackage(listOf("PKG1", "50", "30", "OFR001")).apply {
            deliveryTime = 0.42857142857142855
            fleetWaitingTime = 3.56
        })
        expectedResult.add(computeCourierPackage(listOf("PKG2", "75", "125", "OFR008")).apply {
            deliveryTime = 1.7857142857142858
            fleetWaitingTime = 0.0
        })
        expectedResult.add(computeCourierPackage(listOf("PKG3", "175", "100", "OFR003")).apply {
            deliveryTime = 1.4285714285714286
            fleetWaitingTime = 0.0
        })
        expectedResult.add(computeCourierPackage(listOf("PKG4", "110", "60", "OFR004")).apply {
            deliveryTime = 0.8571428571428571
            fleetWaitingTime = 0.0
        })
        expectedResult.add(computeCourierPackage(listOf("PKG5", "155", "95", "NA")).apply {
            deliveryTime = 1.3571428571428572
            fleetWaitingTime = 2.84
        })

        assertEquals(expectedResult, calcPackagesETA(rawDataList, vehicleList))
    }
}