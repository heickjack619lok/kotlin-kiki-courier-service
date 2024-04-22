package com.codingchallenge.courierservice.common

import com.codingchallenge.courierservice.data.CourierOrderSheet
import com.codingchallenge.courierservice.data.CourierPackage
import com.codingchallenge.courierservice.data.Vehicle

//function to get input using [readLine()]
fun getInput(): List<String>? {
    return readLine()?.split(' ')?.filter { it.isNotEmpty() }
}

/**
 * function to extract input from readline
 * value[0]: [base_delivery_cost]
 * value[1]: [no_of_packages]
 * other: invalid format with exception
 */
fun computeCourierOrderSheet(inputs: List<String>?): CourierOrderSheet {
    if (inputs?.size != 2 || inputs[0].toDoubleOrNull() == null || inputs[1].toIntOrNull() == null) {
        throw Exception("Invalid input, sample format: base_delivery_cost<space>no_of_packages\n")
    }
    return CourierOrderSheet(inputs[0].toDouble(), inputs[1].toInt())
}

/**
 * function to extract input from readLine
 * value[0]: [package_id]
 * value[1]: [package_weight_in_kg]
 * value[2]: [package_distance_in_km]
 * value[3](optional): [offer_code]
 * other: invalid format with exception
 */
fun computeCourierPackage(inputs: List<String>?): CourierPackage {
    if ((inputs?.size != 3 && inputs?.size != 4) || inputs[1].toDoubleOrNull() == null || inputs[2].toDoubleOrNull() == null) {
        throw Exception(
            "Invalid input, sample format: pkg_id1 pkg_weight1_in_kg distance1_in_km offer_code1\n"
        )
    }
    return CourierPackage(
        inputs[0],
        inputs[1].toDouble(),
        inputs[2].toDouble(),
        if (inputs.size == 4) inputs[3] else null
    )
}

/**
 * function to extract input from readLine
 * value[0]: [no_of_vehicle]
 * value[1]: [vehicle_max_speed]
 * value[2]: [vehicle_max_carriable_weight]
 * other: invalid format with exception
 */
fun computeDeliveryVehicle(inputs: List<String>?): List<Vehicle> {
    val vehicleList = mutableListOf<Vehicle>()
    if (inputs?.size != 3 || inputs[0].toIntOrNull() == null || inputs[1].toDoubleOrNull() == null || inputs[2].toDoubleOrNull() == null) {
        throw Exception("Invalid input, sample format: no_of_vehicles max_speed max_carriable_weight\n")
    }

    val vehicleCount = inputs[0].toInt()
    for (i in 1..vehicleCount) {
        vehicleList.add(
            Vehicle(
                "vehicle_$i",
                maxSpeed = inputs[1].toDouble(),
                maxWeight = inputs[2].toDouble()
            )
        )
    }
    return vehicleList
}