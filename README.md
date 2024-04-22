# kotlin-kiki-courier-service

## Background

Kiki, a first-time entrepreneur from the city of Koriko has decided to open a small distance courier service to deliver packages, with her friend Tombo and cat Joji.
Kiki has invested in N no. of vehicles and have driver partners to drive each vehicle & deliver packages.

## Problem
### Delivery Cost Estimation with Offers
Since it’s a new business, the team has decided to pass coupons around the town which will help them attract more customers.

#### Challenge
Required to build a command line application to estimate the total delivery cost of each package with an offer code (if applicable).

##### Notes:
  1. Base Delivery Cost + (Package Total Weight * 10) + (Distance to Destination * 5) = Delivery Cost
  2. Input format:
       * base_delivery_cost no_of_packges
       * input format: pkg_id1 pkg_weight1_in_kg distance1_in_km offer_code1
  3. Output format:
       * pkg_id1 discount1 total_cost1


### Delivery Time Estimation
Now all these packages should be delivered to their destinations in the fleet of vehicles Kiki owns. She has N no. of vehicles available for delivering the packages.

#### Challenge
You are required to build a command line application to calculate the estimated delivery time for every package by maximizing no. of packages in every shipment.

###### Delivering criteria:
The delivery can be made using the criteria illustrated below.
* Shipment should contain max packages vehicle can carry in a trip.
* We should prefer heavier packages when there are multiple shipments with the same no. of packages.
* If the weights are also the same, preference should be given to the shipment which can be delivered first.
* Always travelling at max speed.


## Solving Problem

### Outcome example:
<img width="570" alt="Screenshot 2024-04-22 at 5 01 47 PM" src="https://github.com/heickjack619lok/kotlin-kiki-courier-service/assets/35556458/16d22e2c-f33b-4406-8abc-18abb24e42f6">

#### INPUT Data:<br>
100 5<br>
PKG1 50 30 OFR001<br>
PKG2 75 125 OFR008<br>
PKG3 175 100 OFR003<br>
PKG4 110 60 OFR002<br>
PKG5 155 95 NA<br>
2 70 200<br>
<br>
#### OUTPUT Data:<br>
PKG1 0 750 3.98<br>
PKG2 0 1475 1.78<br>
PKG3 0 2350 1.42<br>
PKG4 105 1395 0.85<br>
PKG5 0 2125 4.19<br>

### Approach:
The core function of the delivery time estimation is based on searching algorithm in [class: Algorithm.kt](app/src/main/java/com/codingchallenge/courierservice/core/Algorithm.kt)
There is two steps in order to get an accurate delivery shipment combination:
1. Rough Search or Branch/Node Search to find out all the potential packages that make up of different combination <br>ref:[Algorithm.kt.searchAllPotentialNodes()](app/src/main/java/com/codingchallenge/courierservice/core/Algorithm.kt)
2. Fine Search or Node Search which will conduct filtering to remove parent nodes that is not useful anymore. (can be replaced with child nodes)<br>ref:[Algorithm.kt.filterParentNodes()](app/src/main/java/com/codingchallenge/courierservice/core/Algorithm.kt)

<b>Step 1 and 2</b> is use to achieve the criteria 1 and 2, which is  
* Shipment should contain max packages vehicle can carry in a trip.
* We should prefer heavier packages when there are multiple shipments with the same no. of packages.

for <b>Criteria 3</b>, we use a sorting function to bring the package with least delivery time up to the front, so when there is condition have multiple shipment with same No.of Packages and same Weight, priority will be given to the one that can be delivery earliest. [class: CourierService.kt.getAndArrangePackagesCombination()](app/src/main/java/com/codingchallenge/courierservice/CourierService.kt)

<img width="695" alt="Screenshot 2024-04-22 at 5 15 50 PM" src="https://github.com/heickjack619lok/kotlin-kiki-courier-service/assets/35556458/571b0524-ad82-45dc-9b91-542eef264b1a">


