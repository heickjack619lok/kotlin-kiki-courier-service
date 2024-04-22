package com.codingchallenge.courierservice.core

import com.codingchallenge.courierservice.common.computeCourierPackage
import com.codingchallenge.courierservice.data.CourierPackage
import org.junit.Assert.assertEquals
import org.junit.Test

class AlgorithmTest {

    @Test
    fun roughSearch_success_min_5_returnMaxPackagesCombination() {
        val rawDataList = mutableListOf<CourierPackage>()
        rawDataList.add(computeCourierPackage(listOf("PKG1", "1", "75")))
        rawDataList.add(computeCourierPackage(listOf("PKG2", "2", "75")))
        rawDataList.add(computeCourierPackage(listOf("PKG3", "3", "75")))
        rawDataList.add(computeCourierPackage(listOf("PKG4", "4", "75")))
        rawDataList.add(computeCourierPackage(listOf("PKG5", "5", "75")))

        val expectedResult = mutableSetOf<CourierPackage>()
        expectedResult.add(computeCourierPackage(listOf("PKG1", "1", "75")))
        expectedResult.add(computeCourierPackage(listOf("PKG2", "2", "75")))
        expectedResult.add(computeCourierPackage(listOf("PKG3", "3", "75")))

        assertEquals(expectedResult, searchAllPotentialNodes(rawDataList, mutableSetOf(), 5.0))
    }

    @Test
    fun roughSearch_success_min_10_returnMaxPackagesCombination() {
        val rawDataList = mutableListOf<CourierPackage>()
        rawDataList.add(computeCourierPackage(listOf("PKG1", "1", "75")))
        rawDataList.add(computeCourierPackage(listOf("PKG2", "2", "75")))
        rawDataList.add(computeCourierPackage(listOf("PKG3", "3", "75")))
        rawDataList.add(computeCourierPackage(listOf("PKG4", "4", "75")))
        rawDataList.add(computeCourierPackage(listOf("PKG5", "5", "75")))
        rawDataList.add(computeCourierPackage(listOf("PKG6", "6", "75")))
        rawDataList.add(computeCourierPackage(listOf("PKG7", "7", "75")))
        rawDataList.add(computeCourierPackage(listOf("PKG8", "8", "75")))
        rawDataList.add(computeCourierPackage(listOf("PKG9", "9", "75")))
        rawDataList.add(computeCourierPackage(listOf("PKG10", "10", "75")))

        val expectedResult = mutableSetOf<CourierPackage>()
        expectedResult.add(computeCourierPackage(listOf("PKG1", "1", "75")))
        expectedResult.add(computeCourierPackage(listOf("PKG2", "2", "75")))
        expectedResult.add(computeCourierPackage(listOf("PKG3", "3", "75")))
        expectedResult.add(computeCourierPackage(listOf("PKG4", "4", "75")))

        assertEquals(expectedResult, searchAllPotentialNodes(rawDataList, mutableSetOf(), 10.0))
    }

    @Test
    fun roughSearch_success_min_15_returnMaxPackagesCombination() {
        val rawDataList = mutableListOf<CourierPackage>()
        for(i in 1..15) {
            rawDataList.add(computeCourierPackage(listOf("PKG$i", "$i", "75")))
        }

        val expectedResult = mutableSetOf<CourierPackage>()
        expectedResult.add(computeCourierPackage(listOf("PKG1", "1", "75")))
        expectedResult.add(computeCourierPackage(listOf("PKG2", "2", "75")))
        expectedResult.add(computeCourierPackage(listOf("PKG3", "3", "75")))
        expectedResult.add(computeCourierPackage(listOf("PKG4", "4", "75")))
        expectedResult.add(computeCourierPackage(listOf("PKG5", "5", "75")))

        assertEquals(expectedResult, searchAllPotentialNodes(rawDataList, mutableSetOf(), 15.0))
    }

    /**
     * group of test for exact scenario as provided
     * PKG1 50 30 OFR001
     * PKG2 75 125 OFR008
     * PKG3 175 100 OFR003
     * PKG4 110 60 OFR002
     * PKG5 155 95 NA
     */
    @Test
    fun roughSearch_success_1st_combination_returnMaxPackagesCombination() {
        val rawDataList = mutableListOf<CourierPackage>()
        rawDataList.add(computeCourierPackage(listOf("PKG1", "50", "30", "OFR001")))
        rawDataList.add(computeCourierPackage(listOf("PKG2", "75", "125", "OFR008")))
        rawDataList.add(computeCourierPackage(listOf("PKG3", "175", "100", "OFR003")))
        rawDataList.add(computeCourierPackage(listOf("PKG4", "110", "60", "OFR004")))
        rawDataList.add(computeCourierPackage(listOf("PKG5", "155", "95", "NA")))

        val expectedResult = mutableSetOf<CourierPackage>()
        expectedResult.add(computeCourierPackage(listOf("PKG2", "75", "125", "OFR008")))
        expectedResult.add(computeCourierPackage(listOf("PKG4", "110", "60", "OFR004")))

        assertEquals(expectedResult, searchAllPotentialNodes(rawDataList, mutableSetOf(), 200.0))
    }

    @Test
    fun roughSearch_success_2nd_combination_returnMaxPackagesCombination() {
        val rawDataList = mutableListOf<CourierPackage>()
        rawDataList.add(computeCourierPackage(listOf("PKG1", "50", "30", "OFR001")))
        rawDataList.add(computeCourierPackage(listOf("PKG3", "175", "100", "OFR003")))
        rawDataList.add(computeCourierPackage(listOf("PKG5", "155", "95", "NA")))

        val expectedResult = mutableSetOf<CourierPackage>()
        expectedResult.add(computeCourierPackage(listOf("PKG3", "175", "100", "OFR003")))

        assertEquals(expectedResult, searchAllPotentialNodes(rawDataList, mutableSetOf(), 200.0))
    }

    @Test
    fun roughSearch_success_3rd_combination_returnMaxPackagesCombination() {
        val rawDataList = mutableListOf<CourierPackage>()
        rawDataList.add(computeCourierPackage(listOf("PKG1", "50", "30", "OFR001")))
        rawDataList.add(computeCourierPackage(listOf("PKG5", "155", "95", "NA")))

        val expectedResult = mutableSetOf<CourierPackage>()
        expectedResult.add(computeCourierPackage(listOf("PKG5", "155", "95", "NA")))

        assertEquals(expectedResult, searchAllPotentialNodes(rawDataList, mutableSetOf(), 200.0))
    }

    @Test
    fun roughSearch_success_4th_combination_returnMaxPackagesCombination() {
        val rawDataList = mutableListOf<CourierPackage>()
        rawDataList.add(computeCourierPackage(listOf("PKG1", "50", "30", "OFR001")))

        val expectedResult = mutableSetOf<CourierPackage>()
        expectedResult.add(computeCourierPackage(listOf("PKG1", "50", "30", "OFR001")))

        assertEquals(expectedResult, searchAllPotentialNodes(rawDataList, mutableSetOf(), 200.0))
    }

    /**
     * group of test for scenario with same maxPackageNo, maxWeight
     * PKG1 75 30 OFR001
     * PKG2 75 125 OFR008
     * PKG3 175 100 OFR003
     * PKG4 110 60 OFR002
     * PKG5 110 95 NA
     */
    @Test
    fun roughSearch_success_1st_combination_pkg2_pkg4_returnMaxPackagesCombination() {
        val rawDataList = mutableListOf<CourierPackage>()
        rawDataList.add(computeCourierPackage(listOf("PKG1", "75", "30", "OFR001")))
        rawDataList.add(computeCourierPackage(listOf("PKG2", "75", "125", "OFR008")))
        rawDataList.add(computeCourierPackage(listOf("PKG3", "175", "100", "OFR003")))
        rawDataList.add(computeCourierPackage(listOf("PKG4", "110", "60", "OFR004")))
        rawDataList.add(computeCourierPackage(listOf("PKG5", "110", "95", "NA")))

        val expectedResult = mutableSetOf<CourierPackage>()
        expectedResult.add(computeCourierPackage(listOf("PKG2", "75", "125", "OFR008")))
        expectedResult.add(computeCourierPackage(listOf("PKG4", "110", "60", "OFR004")))

        assertEquals(expectedResult, searchAllPotentialNodes(rawDataList, mutableSetOf(), 200.0))
    }

    @Test
    fun roughSearch_success_2nd_combination_pkg1_pkg5_returnMaxPackagesCombination() {
        val rawDataList = mutableListOf<CourierPackage>()
        rawDataList.add(computeCourierPackage(listOf("PKG1", "75", "30", "OFR001")))
        rawDataList.add(computeCourierPackage(listOf("PKG3", "175", "100", "OFR003")))
        rawDataList.add(computeCourierPackage(listOf("PKG5", "110", "95", "NA")))

        val expectedResult = mutableSetOf<CourierPackage>()
        expectedResult.add(computeCourierPackage(listOf("PKG1", "75", "30", "OFR001")))
        expectedResult.add(computeCourierPackage(listOf("PKG5", "110", "95", "NA")))

        assertEquals(expectedResult, searchAllPotentialNodes(rawDataList, mutableSetOf(), 200.0))
    }

    @Test
    fun roughSearch_success_2nd_combination_pkg3_returnMaxPackagesCombination() {
        val rawDataList = mutableListOf<CourierPackage>()
        rawDataList.add(computeCourierPackage(listOf("PKG3", "175", "100", "OFR003")))

        val expectedResult = mutableSetOf<CourierPackage>()
        expectedResult.add(computeCourierPackage(listOf("PKG3", "175", "100", "OFR003")))

        assertEquals(expectedResult, searchAllPotentialNodes(rawDataList, mutableSetOf(), 200.0))
    }

    @Test
    fun fineSearch_success_eliminate_pkg2_pkg4_returnMaxPackagesCombination() {
        val rawDataList = mutableListOf<CourierPackage>()
        rawDataList.add(computeCourierPackage(listOf("PKG2", "75", "125", "OFR008")))
        rawDataList.add(computeCourierPackage(listOf("PKG3", "175", "100", "OFR003")))
        rawDataList.add(computeCourierPackage(listOf("PKG4", "110", "60", "OFR004")))
        rawDataList.add(computeCourierPackage(listOf("PKG5", "110", "95", "NA")))
        rawDataList.add(computeCourierPackage(listOf("PKG1", "75", "30", "OFR001")))

        val expectedResult = mutableSetOf<CourierPackage>()
        expectedResult.add(computeCourierPackage(listOf("PKG2", "75", "125", "OFR008")))
        expectedResult.add(computeCourierPackage(listOf("PKG4", "110", "60", "OFR004")))

        assertEquals(expectedResult, filterParentNodes(rawDataList, 200.0))
    }

    @Test
    fun fineSearch_success_eliminate_pkg1_pkg5_returnMaxPackagesCombination() {
        val rawDataList = mutableListOf<CourierPackage>()
        rawDataList.add(computeCourierPackage(listOf("PKG3", "175", "100", "OFR003")))
        rawDataList.add(computeCourierPackage(listOf("PKG5", "110", "95", "NA")))
        rawDataList.add(computeCourierPackage(listOf("PKG1", "75", "30", "OFR001")))

        val expectedResult = mutableSetOf<CourierPackage>()
        expectedResult.add(computeCourierPackage(listOf("PKG5", "110", "95", "NA")))
        expectedResult.add(computeCourierPackage(listOf("PKG1", "75", "30", "OFR001")))

        assertEquals(expectedResult, filterParentNodes(rawDataList, 200.0))
    }

    @Test
    fun fineSearch_success_eliminate_pkg3_returnMaxPackagesCombination() {
        val rawDataList = mutableListOf<CourierPackage>()
        rawDataList.add(computeCourierPackage(listOf("PKG1", "75", "30", "OFR001")))

        val expectedResult = mutableSetOf<CourierPackage>()
        expectedResult.add(computeCourierPackage(listOf("PKG1", "75", "30", "OFR001")))

        assertEquals(expectedResult, filterParentNodes(rawDataList, 200.0))
    }

    /**
     * test for combinations with parent and child nodes together
     */
    @Test
    fun fineSearch_success_eliminate_pkg1_returnMaxPackagesCombination() {
        val rawDataList = mutableListOf<CourierPackage>()
        rawDataList.add(computeCourierPackage(listOf("PKG3", "3", "75")))
        rawDataList.add(computeCourierPackage(listOf("PKG2", "2", "75")))
        rawDataList.add(computeCourierPackage(listOf("PKG1", "1", "75")))

        val expectedResult = mutableSetOf<CourierPackage>()
        expectedResult.add(computeCourierPackage(listOf("PKG2", "2", "75")))
        expectedResult.add(computeCourierPackage(listOf("PKG3", "3", "75")))

        assertEquals(expectedResult, filterParentNodes(rawDataList, 5.0))
    }
}