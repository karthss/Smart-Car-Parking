# TORCH

## Introduction

The smart vehicle parking system is an android application that displays the layout of the vehicle parking space, along with the information of the exact location of the free and occupied slots which eliminates the driver to find the free slot by brute-force.

## Design

This smart vehicle parking system can be considered to be an interoperation of two main subsystems – one subsystem that is used to identify the free parking slots (consists of predominantly hardware subsystems - embedded systems) and the other subsystem which presents the information obtained from the first subsystem to the users (consists of predominantly software subsystems - web servers and android application). This can be achieved by using IR sensors in each parking slot, which can be used to determine if there is a vehicle parked in that slot or not. The information from each sensor is collected by a central controller and sent to the cloud through some communication medium (WiFi or Ethernet). Web and Android applications get the data from the cloud and displays it in a format that is required by the users.

## Subsystem 1

**a) Embedded systems:** 

The Ethernet shield and the IR sensors have to be connected to Arduino (we have connected 2 IR sensors to pins 2,3 and 8,9 for pilot implementation). Dump the code "car_parking.ino" to the Arduino and you can see the output through the Serial monitor. (Refer ArduinoProgramming.md)

**b) Python:** 

In order the send the received data to cloud, we are using "telnet_to_arduino.py" which upon running sends the received data to AWS server. (Refer Python.md)

## Subsystem 2

**a) EC2 Service:**

An EC2 service is created in AWS where LAMP Stack (Linux, Apache, MySql, PHP) is deployed onto it. An Apache HTTP server is deployed in the Linux machine with MySql being used to handle the database management activities and PHP acting as the intermediate between Sub-system 1 and Android application that helps in fetching and storing data from/into the MySql database. (Refer PHP.md)

**b) Android Application:**

The application allows only authorised users to login and based on the user query, it fetches the required data from AWS server and displays available/occupied slot info. (Refer Android.md)

## Hardware Requirements:

   - Arduino (type of arduino used?)
   - IR sensors (IR number?)
   - Ethernet Shield HR911105A
   - Connecting wires
   - PC/Laptop

## Software Requirements:

   - Arduino Programming(?)
   - Python 2.7
   - AWS-EC2 service
   - LAMP Stack (Linux, Apache, MySql, PHP)
   - Android SDK

## Project Details:

A more insight on the project can be gained here (Provide wiki link here)