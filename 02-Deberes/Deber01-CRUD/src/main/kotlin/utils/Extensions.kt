package utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun String.parseDate(): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return LocalDate.parse(this, formatter)
}
