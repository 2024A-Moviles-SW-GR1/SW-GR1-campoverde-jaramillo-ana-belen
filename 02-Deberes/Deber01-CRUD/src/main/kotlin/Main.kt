package main

import entities.Specialty
import entities.Veterinary
import utils.parseDate
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun main() {
    val veterinaryFileName = "veterinaries.txt"
    val specialtyFileName = "specialties.txt"



    Veterinary.readVeterinariesFromFile(veterinaryFileName)
    Specialty.readSpecialtiesFromFile(specialtyFileName)

    while (true) {
        println(
            "================= VETERINARY PROGRAM =================\n" +
                    "1. Create veterinary\n" +
                    "2. Create speciality\n" +
                    "3. Select veterinary\n" +
                    "4. Select speciality\n" +
                    "5. View all veterinaries\n" +
                    "0. Exit\n"
        )
        when (readLine()?.toIntOrNull()) {
            1 -> {
                print("Enter veterinary name: ")
                val name = readLine() ?: ""
                print("Enter address: ")
                val address = readLine() ?: ""
                print("Enter phone number: ")
                val phoneNumber = readLine() ?: ""
                print("Enter email: ")
                val email = readLine() ?: ""

                val veterinary = Veterinary(name, address, phoneNumber, email)

                while (true) {
                    println("Do you want to add a specialty to this veterinary? (yes/no): ")
                    if (readLine()?.lowercase() != "yes") break

                    println("Choose an option:")
                    println("1. Add a new specialty")
                    println("2. Add an existing specialty")

                    when (readLine()?.toIntOrNull()) {
                        1 -> {
                            print("Enter specialty name: ")
                            val specialtyName = readLine() ?: ""
                            print("Enter description: ")
                            val description = readLine() ?: ""
                            print("Enter start date (yyyy-MM-dd): ")
                            val startDate = readLine()?.parseDate() ?: LocalDate.now()
                            print("Is it active? (true/false): ")
                            val active = readLine()?.toBoolean() ?: true
                            print("Enter duration (years): ")
                            val duration = readLine()?.toIntOrNull() ?: 0

                            val specialty = Specialty(specialtyName, description, startDate, active, duration)
                            veterinary.addSpecialty(specialty)
                        }

                        2 -> {
                            println("List of existing specialties:")
                            Specialty.specialtyList.forEachIndexed { index, specialty ->
                                println("$index. $specialty")
                            }
                            print("Enter the index of the specialty to add: ")
                            val index = readLine()?.toIntOrNull() ?: -1
                            if (index in Specialty.specialtyList.indices) {
                                veterinary.addSpecialty(Specialty.specialtyList[index])
                            } else {
                                println("Invalid index.")
                            }
                        }

                        else -> println("Invalid option.")
                    }
                }

                Veterinary.writeVeterinariesToFile(veterinaryFileName)
            }
            2 -> {
                print("Enter specialty name: ")
                val name = readLine() ?: ""
                print("Enter description: ")
                val description = readLine() ?: ""
                print("Enter start date (yyyy-MM-dd): ")
                val startDate = readLine()?.parseDate() ?: LocalDate.now()
                print("Is it active? (true/false): ")
                val active = readLine()?.toBoolean() ?: true
                print("Enter duration (years): ")
                val duration = readLine()?.toIntOrNull() ?: 0

                Specialty(name, description, startDate, active, duration)
                Specialty.writeSpecialtiesToFile(specialtyFileName)
            }
            3 -> {
                println("Enter the name of the veterinary to select: ")
                val name = readLine() ?: ""
                val vet = Veterinary.veterinaryList.find { it.name == name }
                if (vet != null) {
                    println("Selected Veterinary: $vet")
                    println("What would you like to do?")
                    println("1. Update veterinary")
                    println("2. Delete veterinary")
                    println("3. Add specialty")
                    when (readLine()?.toIntOrNull()) {
                        1 -> {
                            println("Enter the attribute number to update (0: name, 1: address, 2: phone number, 3: email): ")
                            val attr = readLine()?.toIntOrNull() ?: -1
                            if (attr in 0..3) {
                                println("Enter the new value: ")
                                val newValue = readLine() ?: ""
                                println(vet.updateVeterinary(attr, newValue))
                                Veterinary.writeVeterinariesToFile(veterinaryFileName)
                            } else {
                                println("Invalid attribute number.")
                            }
                        }
                        2 -> {
                            if (Veterinary.deleteVeterinary(name)) {
                                println("Veterinary deleted successfully.")
                                Veterinary.writeVeterinariesToFile(veterinaryFileName)
                            } else {
                                println("Veterinary not found.")
                            }
                        }
                        3 -> {
                            println("Adding specialty to Veterinary: ${vet.name}")
                            println("Choose an option:")
                            println("1. Add a new specialty")
                            println("2. Add an existing specialty")

                            when (readLine()?.toIntOrNull()) {
                                1 -> {
                                    print("Enter specialty name: ")
                                    val specialtyName = readLine() ?: ""
                                    print("Enter description: ")
                                    val description = readLine() ?: ""
                                    print("Enter start date (yyyy-MM-dd): ")
                                    val startDate = readLine()?.parseDate() ?: LocalDate.now()
                                    print("Is it active? (true/false): ")
                                    val active = readLine()?.toBoolean() ?: true
                                    print("Enter duration (years): ")
                                    val duration = readLine()?.toIntOrNull() ?: 0

                                    val specialty = Specialty(specialtyName, description, startDate, active, duration)
                                    vet.addSpecialty(specialty)
                                }
                                2 -> {
                                    println("List of existing specialties:")
                                    Specialty.specialtyList.forEachIndexed { index, specialty ->
                                        println("$index. $specialty")
                                    }
                                    print("Enter the index of the specialty to add: ")
                                    val index = readLine()?.toIntOrNull() ?: -1
                                    if (index in Specialty.specialtyList.indices) {
                                        vet.addSpecialty(Specialty.specialtyList[index])
                                    } else {
                                        println("Invalid index.")
                                    }
                                }
                                else -> println("Invalid option.")
                            }
                            Veterinary.writeVeterinariesToFile(veterinaryFileName)
                        }
                        else -> println("Invalid option.")
                    }
                } else {
                    println("Veterinary not found.")
                }
            }
            4 -> {
                println("Enter the name of the specialty to select: ")
                val name = readLine() ?: ""
                val specialty = Specialty.specialtyList.find { it.name == name }
                if (specialty != null) {
                    println("Selected Specialty: $specialty")
                    println("What would you like to do?")
                    println("1. Update specialty")
                    println("2. Delete specialty")
                    when (readLine()?.toIntOrNull()) {
                        1 -> {
                            println("Enter the attribute number to update (0: name, 1: description, 2: start date, 3: active, 4: duration): ")
                            val attr = readLine()?.toIntOrNull() ?: -1
                            if (attr in 0..4) {
                                println("Enter the new value: ")
                                val newValue = readLine() ?: ""
                                println(specialty.updateSpecialty(attr, newValue))
                                Specialty.writeSpecialtiesToFile(specialtyFileName)
                            } else {
                                println("Invalid attribute number.")
                            }
                        }
                        2 -> {
                            if (Specialty.specialtyList.remove(specialty)) {
                                println("Specialty deleted successfully.")
                                Specialty.writeSpecialtiesToFile(specialtyFileName)
                            } else {
                                println("Specialty not found.")
                            }
                        }
                        else -> println("Invalid option.")
                    }
                } else {
                    println("Specialty not found.")
                }
            }
            5 -> {
                println("All Veterinaries:")
                Veterinary.veterinaryList.forEachIndexed { index, veterinary ->
                    println("---------------------------------------------------------------------------------")
                    println(veterinary)
                    if (index < Veterinary.veterinaryList.size - 1) {
                        println()
                    }
                }
                println("=================================================================================\n")
            }
            0 -> {
                println("Exiting program...")
                break
            }
            else -> println("Invalid option. Please try again.")
        }
    }
}