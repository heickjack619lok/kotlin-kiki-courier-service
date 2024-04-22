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

