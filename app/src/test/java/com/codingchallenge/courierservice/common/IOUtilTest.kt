package com.codingchallenge.courierservice.common

import com.codingchallenge.courierservice.data.CourierOrderSheet
import com.codingchallenge.courierservice.data.CourierPackage
import com.codingchallenge.courierservice.data.Vehicle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class IOUtilTest {

    @Test
    fun computeCourierOrderSheet_success_orderSheetCreated() {
        assertEquals(
            CourierOrderSheet(100.0, 5),
            computeCourierOrderSheet(listOf("100", "5"))
        )
    }

    @Test
    fun computeCourierOrderSheet_failed_incorrect_length_exceptionThrown() {
        assertThrows(
            "Invalid input, sample format: base_delivery_cost<space>no_of_packages\n",
            Exception::class.java
        ) { computeCourierOrderSheet(listOf("100")) }
    }

    @Test
    fun computeCourierOrderSheet_failed_incorrect_datatype_base_cost_exceptionThrown() {
        assertThrows(
            "Invalid input, sample format: base_delivery_cost<space>no_of_packages\n",
            Exception::class.java
        ) { computeCourierOrderSheet(listOf("x", "5")) }
    }

    @Test
    fun computeCourierOrderSheet_failed_incorrect_datatype_package_count_exceptionThrown() {
        assertThrows(
            "Invalid input, sample format: base_delivery_cost<space>no_of_packages\n",
            Exception::class.java
        ) { computeCourierOrderSheet(listOf("100", "y")) }
    }

    @Test
    fun computeCourierPackage_success_orderSheetCreated() {
        val expectedResult = CourierPackage("PKG1", 30.0, 75.0, "OFR001")
        assertEquals(
            expectedResult,
            computeCourierPackage(listOf("PKG1", "30", "75", "OFR001"))
        )
    }

    @Test
    fun computeCourierPackage_success_withoutOfferCodeOrderSheetCreated() {
        val expectedResult = CourierPackage("PKG1", 30.0, 75.0)
        assertEquals(
            expectedResult,
            computeCourierPackage(listOf("PKG1", "30", "75"))
        )
    }

    @Test
    fun computeCourierPackage_failed_incorrect_length_exceptionThrown() {
        assertThrows(
            "Invalid input, sample format: pkg_id1 pkg_weight1_in_kg distance1_in_km offer_code1\n",
            Exception::class.java
        ) { computeCourierPackage(listOf("PKG1", "OFR001")) }
    }

    @Test
    fun computeCourierPackage_failed_incorrect_datatype_weight_exceptionThrown() {
        assertThrows(
            "Invalid input, sample format: pkg_id1 pkg_weight1_in_kg distance1_in_km offer_code1\n",
            Exception::class.java
        ) { computeCourierPackage(listOf("PKG1", "x", "75", "OFR001")) }
    }

    @Test
    fun computeCourierPackage_failed_incorrect_datatype_distance_exceptionThrown() {
        assertThrows(
            "Invalid input, sample format: pkg_id1 pkg_weight1_in_kg distance1_in_km offer_code1\n",
            Exception::class.java
        ) { computeCourierPackage(listOf("PKG1", "30", "Y", "OFR001")) }
    }

    @Test
    fun computeDeliveryVehicle_success_vehicleListCreated() {
        val expectedResult = mutableListOf<Vehicle>()
        expectedResult.add(Vehicle("vehicle_1", 200.0, 70.0))
        expectedResult.add(Vehicle("vehicle_2", 200.0, 70.0))
        assertEquals(
            expectedResult,
            computeDeliveryVehicle(listOf("2", "70", "200"))
        )
    }

    @Test
    fun computeDeliveryVehicle_failed_incorrect_length_exceptionThrown() {
        assertThrows(
            "Invalid input, sample format: no_of_vehicles max_speed max_carriable_weight\n",
            Exception::class.java
        ) { computeDeliveryVehicle(listOf("2", "70")) }
    }

    @Test
    fun computeDeliveryVehicle_failed_incorrect_datatype_vehicle_count_exceptionThrown() {
        assertThrows(
            "Invalid input, sample format: no_of_vehicles max_speed max_carriable_weight\n",
            Exception::class.java
        ) { computeDeliveryVehicle(listOf("x", "70", "200")) }
    }

    @Test
    fun computeDeliveryVehicle_failed_incorrect_datatype_vehicle_max_speed_exceptionThrown() {
        assertThrows(
            "Invalid input, sample format: no_of_vehicles max_speed max_carriable_weight\n",
            Exception::class.java
        ) { computeDeliveryVehicle(listOf("2", "y", "200")) }
    }

    @Test
    fun computeDeliveryVehicle_failed_incorrect_datatype_vehicle_max_weight_exceptionThrown() {
        assertThrows(
            "Invalid input, sample format: no_of_vehicles max_speed max_carriable_weight\n",
            Exception::class.java
        ) { computeCourierPackage(listOf("2", "70", "z")) }
    }
}