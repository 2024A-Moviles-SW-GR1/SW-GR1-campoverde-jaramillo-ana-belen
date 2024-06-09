package entities

import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Veterinary(
    var name: String,
    var address: String,
    var phoneNumber: String,
    var email: String,
    var specialties: ArrayList<Specialty> = ArrayList()
) {

    init {
        veterinaryList.add(this)
    }


    fun addSpecialty(specialty: Specialty) {
        specialties.add(specialty)
    }

    fun updateVeterinary(attribute: Int, newValue: String): String {
        return when (attribute) {
            0 -> {
                this.name = newValue
                "Veterinary name updated"
            }
            1 -> {
                this.address = newValue
                "Veterinary address updated"
            }
            2 -> {
                this.phoneNumber = newValue
                "Veterinary phone number updated"
            }
            3 -> {
                this.email = newValue
                "Veterinary email updated"
            }
            else -> "Invalid attribute number"
        }
    }

    override fun toString(): String {
        val specialtiesInfo = specialties.joinToString("\n    ") { it.toString() }
        return  "Name: $name\n" +
                "Address: $address\n" +
                "Phone Number: $phoneNumber\n" +
                "Email: $email\n" +
                "Specialties:\n    $specialtiesInfo"
    }

    companion object {
        var veterinaryList = ArrayList<Veterinary>()

        fun readVeterinariesFromFile(fileName: String) {
            veterinaryList.clear()
            File(fileName).forEachLine { line ->
                val data = line.split(";")
                if (data.size >= 4) {
                    val veterinary = Veterinary(data[0], data[1], data[2], data[3])
                    val specialties = data.drop(4)
                    specialties.forEach { specialtyData ->
                        val specialtyFields = specialtyData.split(",")
                        if (specialtyFields.size == 5) {
                            veterinary.addSpecialty(Specialty(
                                specialtyFields[0],
                                specialtyFields[1],
                                LocalDate.parse(specialtyFields[2], DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                specialtyFields[3].toBoolean(),
                                specialtyFields[4].toInt()
                            ))
                        }
                    }
                }
            }
        }

        fun writeVeterinariesToFile(fileName: String) {
            File(fileName).printWriter().use { writer ->
                veterinaryList.forEach { vet ->
                    val specialtiesString = vet.specialties.joinToString(";") { specialty ->
                        "${specialty.name},${specialty.description},${specialty.startDate},${specialty.active},${specialty.duration}"
                    }
                    writer.println("${vet.name};${vet.address};${vet.phoneNumber};${vet.email};$specialtiesString")
                }
            }
        }

        fun deleteVeterinary(name: String): Boolean {
            val vet = veterinaryList.find { it.name == name }
            return if (vet != null) {
                veterinaryList.remove(vet)
                true
            } else {
                false
            }
        }
    }
}
