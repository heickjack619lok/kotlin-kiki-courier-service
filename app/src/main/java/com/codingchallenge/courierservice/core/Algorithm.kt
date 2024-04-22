package com.codingchallenge.courierservice.core

import com.codingchallenge.courierservice.data.CourierPackage

/**
 * function to perform delivery ETA calculation
 * perform rough search to find all possible combination of packages arrangement
 *                  10
 *      |           |                |                      4
 *      1           2                3              |               |
 *              |       |       |       |           1               3
 *              1       3       2       3                   |               |
 *                   |     |        |       |               1               2
 *                   1     2        1       2
 * not sure what the exact name this called, kind of algorithm like tree searching
 * just think of get the best result, come out with this logic
 */
fun searchAllPotentialNodes(
    dataList: MutableList<CourierPackage>,
    finalDataList: MutableSet<CourierPackage>,
    targetWeight: Double
): MutableSet<CourierPackage> {

    dataList.sortBy { it.weight }

    //    searching for maximum number of branches for a given [targetWeight]
    val branch = searchBranchNodes(dataList, targetWeight)

    finalDataList.addAll(branch?.toSet() ?: emptySet())

    //    check for any child branch/nodes below
    val hasBranch: Boolean =
        branch?.any { (searchBranchNodes(dataList, it.weight)?.size ?: 0) > 1 } ?: false

    //    proceed with further searching downward for more potential child nodes, which mean more package can be included in a shipment
    //    searching will be growing with method looping, without taking care much on memory growth
    //    there shall have better approach for searching algorithm
    return if (hasBranch) {
        branch?.sortedBy { it.weight }

        //        filter to NOT include already added node
        branch?.filter { (searchBranchNodes(dataList, it.weight)?.size ?: 0) > 1 }?.map {
            val nodes = searchAllPotentialNodes(dataList.filter { list -> list.packageId != it.packageId }
                .toMutableList(), finalDataList, it.weight)
            finalDataList.addAll(nodes)
        }
        finalDataList
    } else {
        finalDataList
    }
}

/**
 * function to search for a SINGLE layer branch
 * provided with available data [dataList] and node to search [targetWeight]
 * will always return set that have most number of packages or node
 * [mark] is value calculated as a x value for at most 100, serve as a limit point for weight limit
 * [markList] is a set of data, pair of [mark] and [markSet], it is the raw data to process and get best set of nodes later
 * [markSet] is a set of data that combined to get [mark]
 */
fun searchBranchNodes(
    dataList: MutableList<CourierPackage>,
    targetWeight: Double
): MutableSet<CourierPackage>? {
    val markList = mutableListOf<MutableSet<CourierPackage>>()

    dataList.forEachIndexed { i, a ->
        var mark = a.weight.div(targetWeight).times(100)
        val markSet: MutableSet<CourierPackage> = mutableSetOf()

        if (mark <= 100) {
            markSet.add(a)
        }

        do {
//            filtering to remove already added nodes, exclude from next searching
            val nextPackageList =
                dataList.subList(i, dataList.size).filter { !markSet.contains(it) }
                    .filter { mark + (it.weight / targetWeight * 100) <= 100 }

            val nextPackage = nextPackageList.minByOrNull { it.weight }

            nextPackage?.let {
                nextPackage.weight.div(targetWeight).times(100).plus(mark).apply {
                    if (this <= 100) {
                        mark = this
                        markSet.add(nextPackage)
                    }
                }
            }
        } while (nextPackage != null)

        if (mark <= 100) {
            markList.add(markSet)
        }
    }

//    get the set with most number of packages
    markList.sortedByDescending { it.size }
    val maxPackageNumberList = markList.filter { it.size == markList.maxByOrNull { it.size }?.size }


//    condition check where if more than one combination that is same number of packages, the heavier one is preferred
    return if (maxPackageNumberList.size > 1) {
        maxPackageNumberList.maxByOrNull { it.sumOf { it.weight } }
    } else {
        maxPackageNumberList.firstOrNull()
    }
}

/**
 * function to do fine filtering based on provided possible combination
 * this fine search will eliminate parent nodes when child nodes is available in the combination
 * will return the set that has most number of packages and heavier one
 */
fun filterParentNodes(
    dataList: MutableList<CourierPackage>,
    targetWeight: Double
): MutableSet<CourierPackage>? {
    val markList = mutableListOf<MutableSet<CourierPackage>>()

    dataList.forEach { a ->
        var mark = a.weight / targetWeight * 100
        val markSet: MutableSet<CourierPackage> = mutableSetOf()

        if (mark <= 100) markSet.add(a)

        dataList.filter { it.packageId != a.packageId }.forEach { b ->
            val tempMark = (mark + (b.weight / targetWeight * 100))
            if (tempMark <= 100) mark = tempMark
            if (tempMark <= 100) markSet.add(b)
        }

        markList.add(markSet)
    }

    markList.sortedByDescending { it.size }
    val filterList = markList.filter { it.size == markList.maxByOrNull { it.size }?.size }

    return if (filterList.size > 1) {
        filterList.maxByOrNull { it.sumOf { it.weight } }
    } else {
        filterList.firstOrNull()
    }
}