package entities

import java.io.File
import java.time.LocalDate
import utils.parseDate

class Specialty(
    var name: String,
    var description: String,
    var startDate: LocalDate,
    var active: Boolean,
    var duration: Int
) {
    companion object {
        var specialtyList = ArrayList<Specialty>()

        fun readSpecialtiesFromFile(fileName: String) {
            specialtyList.clear()
            File(fileName).forEachLine { line ->
                val data = line.split(";")
                if (data.size == 5) {
                    specialtyList.add(Specialty(
                        data[0],
                        data[1],
                        data[2].parseDate(),
                        data[3].toBoolean(),
                        data[4].toInt()
                    ))
                }
            }
        }

        fun writeSpecialtiesToFile(fileName: String) {
            File(fileName).printWriter().use { writer ->
                specialtyList.forEach { specialty ->
                    writer.println("${specialty.name};${specialty.description};${specialty.startDate};${specialty.active};${specialty.duration}")
                }
            }
        }

        fun addSpecialty(specialty: Specialty) {
            specialtyList.add(specialty)
        }
    }

    fun updateSpecialty(attribute: Int, newValue: String): String {
        return when (attribute) {
            0 -> {
                this.name = newValue
                "Specialty name updated"
            }
            1 -> {
                this.description = newValue
                "Specialty description updated"
            }
            2 -> {
                this.startDate = newValue.parseDate()
                "Specialty start date updated"
            }
            3 -> {
                this.active = newValue.toBoolean()
                "Specialty active status updated"
            }
            4 -> {
                this.duration = newValue.toInt()
                "Specialty duration updated"
            }
            else -> "Invalid attribute number"
        }
    }

    override fun toString(): String {
        return "Name: $name, Description: $description, Start Date: $startDate, Active: $active, Duration: $duration years"
    }
}