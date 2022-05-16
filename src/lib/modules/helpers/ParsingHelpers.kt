package modules.helpers

import kotlin.math.pow
import kotlin.math.round

fun roundToDigits(number: Double, digits: Int): Double = round(number * (10.0).pow(digits)) / (10.0).pow(digits)
