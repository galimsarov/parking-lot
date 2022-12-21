fun main() {
    val parking = mutableMapOf<Int, Car>()
    var parkingSize = 0
    while (true) {
        val command = readln()
        val commandElements = command.split(" ")
        when (commandElements[0]) {
            "create" -> {
                if (commandElements.size == 2) {
                    val size = try {
                        commandElements[1].toInt()
                    } catch (_: NumberFormatException) {
                        0
                    }
                    if (size > 0) {
                        if (parkingSize > 0) {
                            parking.clear()
                        }
                        parkingSize = createParking(size)
                    }
                }
            }

            "park" -> {
                if (parkingSize == 0) {
                    println("Sorry, a parking lot has not been created.")
                } else if (commandElements.size == 3) {
                    parking.parkCar(number = commandElements[1], color = commandElements[2], parkingSize = parkingSize)
                }
            }

            "leave" -> {
                if (parkingSize == 0) {
                    println("Sorry, a parking lot has not been created.")
                } else if (commandElements.size == 2) {
                    parking.leaveCar(spotNumber = commandElements[1])
                }
            }

            "status" -> {
                if (parkingSize == 0) {
                    println("Sorry, a parking lot has not been created.")
                } else {
                    parking.status(parkingSize)
                }
            }

            "reg_by_color" -> {
                if (parkingSize == 0) {
                    println("Sorry, a parking lot has not been created.")
                } else if (commandElements.size == 2) {
                    parking.regByColor(color = commandElements[1], parkingSize)
                }
            }

            "spot_by_color" -> {
                if (parkingSize == 0) {
                    println("Sorry, a parking lot has not been created.")
                } else if (commandElements.size == 2) {
                    parking.spotByColor(color = commandElements[1], parkingSize)
                }
            }

            "spot_by_reg" -> {
                if (parkingSize == 0) {
                    println("Sorry, a parking lot has not been created.")
                } else if (commandElements.size == 2) {
                    parking.spotByReg(reg = commandElements[1])
                }
            }

            "exit" -> break
        }
    }
}

data class Car(val number: String, val color: String)

fun createParking(size: Int): Int {
    println("Created a parking lot with $size spots.")
    return size
}

fun MutableMap<Int, Car>.parkCar(number: String, color: String, parkingSize: Int) {
    if (size >= parkingSize) {
        println("Sorry, the parking lot is full.")
    } else {
        for (i in 1..parkingSize) {
            if (!keys.contains(i)) {
                this[i] = Car(number, color)
                println("$color car parked in spot $i.")
                break
            }
        }
    }
}

fun MutableMap<Int, Car>.leaveCar(spotNumber: String) {
    try {
        val key = spotNumber.toInt()
        if (contains(key)) {
            remove(key)
            println("Spot $spotNumber is free.")
        }
    } catch (_: NumberFormatException) {
    }
}

fun Map<Int, Car>.status(parkingSize: Int) {
    if (isEmpty()) {
        println("Parking lot is empty.")
    } else {
        for (spot in 1..parkingSize) {
            if (containsKey(spot)) {
                println("$spot ${this[spot]?.number} ${this[spot]?.color}")
            }
        }
    }
}

fun Map<Int, Car>.regByColor(color: String, parkingSize: Int) {
    val numbers = mutableListOf<String>()
    for (key in 1..parkingSize) {
        if (this[key]?.color?.lowercase() == color.lowercase()) {
            this[key]?.number?.let { numbers.add(it) }
        }
    }
    println(
        if (numbers.isEmpty()) {
            "No cars with color $color were found."
        } else {
            numbers.joinToString(", ")
        }
    )
}

fun Map<Int, Car>.spotByColor(color: String, parkingSize: Int) {
    val spots = mutableListOf<Int>()
    for (key in 1..parkingSize) {
        if (this[key]?.color?.lowercase() == color.lowercase()) {
            spots.add(key)
        }
    }
    println(
        if (spots.isEmpty()) {
            "No cars with color $color were found."
        } else {
            spots.joinToString(", ")
        }
    )
}

fun Map<Int, Car>.spotByReg(reg: String) {
    for ((spot, car) in this) {
        if (car.number == reg) {
            println(spot)
            return
        }
    }
    println("No cars with registration number $reg were found.")
}