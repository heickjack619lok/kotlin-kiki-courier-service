package com.codingchallenge.courierservice.common.enums

enum class OfferCode(
    val offerRate: Double,
    val offerMinDistance: Int,
    val offerMaxDistance: Int,
    val offerMinWeight: Int,
    val offerMaxWeight: Int
) {
    OFR001(0.1, 0, 200, 70, 200),
    OFR002(0.07, 50, 150, 100, 250),
    OFR003(0.05, 50, 250, 10, 150)
}