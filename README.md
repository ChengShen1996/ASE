# Hotel Management System [![Build Status](https://travis-ci.org/ChengShen1996/ASE.svg?branch=iteration1-test-wen)](https://travis-ci.org/ChengShen1996/ASE) [![Coverage Status](https://coveralls.io/repos/github/ChengShen1996/ASE/badge.svg?branch=iteration2-chaiquan)](https://coveralls.io/github/ChengShen1996/ASE)

Hotel management is a critical part of the hotel operation chain. Our goal is to create an efficient, secure, and user-friendly hotel management system to provide excellent service to the customers. Our hotel management system is an all-in -one application, which is designed to accommodate the needs of different staff members in the hotel. Front desk staffs are able to make the most suitable room arrangement and recommend places of interests to the customers. Managers are able to view and analyze the revenue over a certain period of time. We believe that our hotel management is capable of facilitating the service and helping the hotel to develop at a fast pace.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Download [JavaFX IDE](https://www.oracle.com/technetwork/java/javafx2-archive-download-1939373.html)


### Installing

First clone the repository

```
git clone
```

And open the repository in JavaFX IDE and run the project


## Running the tests

We have unit and integration tests in [tests](https://github.com/ChengShen1996/ASE/tree/master/Hotel/src/test/java) fold

### To run unit and integration tests

In JavaFx, click run test

In terminal, run
```
  maven clean
  maven install
  maven test
```

### And pre-commit tests

run the following command to setup pre-commit tests

```
./setup_pre-commit.sh
```

### And post-commit tests


Travis post-commit CI is in [.travis.yml](https://github.com/ChengShen1996/ASE/blob/master/.travis.yml)
```
./setup_pre-commit.sh
```
### And coverage tests

Tested by [Coveralls](https://coveralls.io/github/ChengShen1996/ASE)


## Built With

* [Derby](https://db.apache.org/derby/docs/10.0/manuals/develop/develop13.html) - The database used
* [Maven](https://maven.apache.org/) - Dependency Management





