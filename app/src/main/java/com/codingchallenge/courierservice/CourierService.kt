package com.codingchallenge.courierservice

import com.codingchallenge.courierservice.common.*
import com.codingchallenge.courierservice.common.enums.OfferCode
import com.codingchallenge.courierservice.core.filterParentNodes
import com.codingchallenge.courierservice.core.searchAllPotentialNodes
import com.codingchallenge.courierservice.data.CourierOrderSheet
import com.codingchallenge.courierservice.data.CourierPackage
import com.codingchallenge.courierservice.data.Vehicle
import kotlin.math.roundToInt

const val multiplierWeight = 10
const val multiplierDistance = 5

fun main() {

    //    data fields
    var courierOrderSheet: CourierOrderSheet? = null
    val courierPackages = mutableListOf<CourierPackage>()
    val deliveryVehicles = mutableListOf<Vehicle>()

    //    welcome message
    println("Welcome to Kiki Courier Service")
    println("input the order sheet [sample format: base_delivery_cost<space>no_of_packges]")

    //    INPUT - get order detail from user
    do {
        print(">>> ")
        try {
            getInput().apply { courierOrderSheet = computeCourierOrderSheet(this) }
        } catch (e: Exception) {
            println(e.message)
        }
    } while (courierOrderSheet == null)

    //    INPUT - get packages detail from user
    do {
        println(
            "input the package ${courierPackages.size + 1} detail:"
                    + "\n[sample format: pkg_id1 pkg_weight1_in_kg distance1_in_km offer_code1]"
        )
        print(">>> ")
        try {
            getInput().apply { courierPackages.add(computeCourierPackage(this)) }
        } catch (e: Exception) {
            println(e.message)
        }
    } while (courierPackages.size < courierOrderSheet!!.packageCount)

    //    INPUT - get delivery vehicle detail from user
    do {
        println(
            "input the number of delivery vehicle, max vehicle speed and max carriable weight:"
                    + "\n[sample format: no_of_vehicles max_speed max_carriable_weight]"
        )
        print(">>> ")
        try {
            getInput().apply { deliveryVehicles.addAll(computeDeliveryVehicle(this)) }
        } catch (e: java.lang.Exception) {
            println(e.message)
        }
    } while (deliveryVehicles.isEmpty())

    //    OUTPUT - generate summary of delivery
    println(
        "Order Summary:\n" +
                "Base Delivery Cost: ${courierOrderSheet?.baseDeliveryRate}\n" +
                "No. of Packages: ${courierOrderSheet?.packageCount}\n"
    )

    calcPackagesETA(courierPackages, deliveryVehicles).forEach {
        val beforeDiscount =
            courierOrderSheet!!.baseDeliveryRate + (it.weight * 10) + (it.distance * 5);

        val discAmount = calcDiscountAmount(courierOrderSheet!!.baseDeliveryRate, it).roundToInt()

        val totalCost = calcTotalAmount(
            beforeDiscount,
            calcDiscountAmount(courierOrderSheet!!.baseDeliveryRate, it)
        ).roundToInt()

        println(
            "${it.packageId} $discAmount $totalCost ${it.getFinalDeliveryTime().roundOffDecimal()}"
        )
    }
}

//function to calculate total cost for package delivery, provided [total amount] and [discountAmount]
fun calcTotalAmount(amount: Double, discountAmount: Double): Double {
    return amount - discountAmount
}

/**
 * function to calculate discounted amount based on provided discount code
 * offer code only valid based on [OfferCode], further extend by adding more value to it
 * otherwise return 0 offer amount
 */
fun calcDiscountAmount(
    baseDeliveryRate: Double,
    courierPackage: CourierPackage
): Double {
    val beforeDiscount = calcBeforeDiscountAmount(baseDeliveryRate, courierPackage)
    OfferCode.values().firstOrNull { courierPackage.offerCode?.uppercase() == it.name.uppercase() }
        ?.apply {
            if (courierPackage.weight >= this.offerMinWeight
                && courierPackage.weight < this.offerMaxWeight
                && courierPackage.distance >= this.offerMinDistance
                && courierPackage.distance < this.offerMaxDistance
            ) {
                return this.offerRate * beforeDiscount
            }
        }
    return 0.0;
}

//function to calculate package delivery rate before discount
fun calcBeforeDiscountAmount(baseDeliveryRate: Double, courierPackage: CourierPackage) =
    baseDeliveryRate + (courierPackage.weight * multiplierWeight) + (courierPackage.distance * multiplierDistance)

/**
 * function to calculate ETA for package delivery
 * make use of []Algorithm.kt] class
 */
fun calcPackagesETA(
    packageList: MutableList<CourierPackage>,
    vehicleList: MutableList<Vehicle>
): MutableList<CourierPackage> {
    val maxWeight = vehicleList.firstOrNull()?.maxWeight ?: 0.0
    val maxSpeed = vehicleList.firstOrNull()?.maxSpeed ?: 0.0

    packageList.onEach { it.deliveryTime = it.distance / maxSpeed }
    packageList.sortBy { it.weight }

    val sortedToDeliverList = getAndArrangePackagesCombination(packageList, maxWeight)

    while (sortedToDeliverList.any { it.any { it.fleetWaitingTime == null } }) {
        val packages =
            sortedToDeliverList.firstOrNull { it.any { it.fleetWaitingTime == null } }
                ?: mutableSetOf()

        val earliestAvailableCar =
            vehicleList.minByOrNull { vehicle -> vehicle.getDeliveryStartTime() }

        val deliveryTime =
            ((packages.maxByOrNull { it.deliveryTime ?: 0.0 }?.deliveryTime?.roundOffDecimal()
                ?: 0.0) * 2).roundOffDecimal()
        val deliveryEndTime =
            (earliestAvailableCar?.getDeliveryStartTime()?.plus(deliveryTime) ?: 0.0)
                .roundOffDecimal()

        packages.onEach {
            it.fleetWaitingTime = earliestAvailableCar?.getDeliveryStartTime()?.roundOffDecimal()
        }
        earliestAvailableCar?.jobQueue?.add(Pair(deliveryEndTime, packages))
    }
    return sortedToDeliverList.flatten().sortedBy { it.packageId }.toMutableList()
}

/**
 * function to perform searching for all potential combination
 * also process combination filtering and sorting based on criteria 1,2 and 3
 * condition 1: max packages
 * condition 2: same package count, priority weight
 * condition 3: same package count, same weight, priority shortest delivery time
 */
fun getAndArrangePackagesCombination(
    packageList: MutableList<CourierPackage>,
    maxWeight: Double
): MutableList<MutableSet<CourierPackage>> {
    val recommendedList = mutableListOf<MutableSet<CourierPackage>>()
    //    loop for getting all package combination
    while (packageList.isNotEmpty()) {
        val potentialPackageCombination =
            searchAllPotentialNodes(packageList, mutableSetOf(), maxWeight)
        val finalPackageCombination =
            filterParentNodes(
                potentialPackageCombination.sortedByDescending { it.weight }.toMutableList(),
                maxWeight
            )

        if (finalPackageCombination != null) {
            recommendedList.add(finalPackageCombination)
            packageList.removeAll(finalPackageCombination)
        }
    }

    /**
     * final sorting is at this stage, as we need to get all possible combination first
     * Algorithm is only for searching
     * condition 1: max packages
     * condition 2: same package count, priority weight
     * condition 3: same package count, same weight, priority shortest delivery time
     * final sorted for condition 3:
     * If the weights are also the same, preference should be given to the shipment which can be delivered first.
     */
    val sortedToDeliverList =
        recommendedList.sortedWith(compareByDescending<MutableSet<CourierPackage>> { it.size }
            .thenByDescending { it.sumOf { it.weight } }
            .thenBy { it.sumOf { it.distance } })
    return sortedToDeliverList.toMutableList()
}