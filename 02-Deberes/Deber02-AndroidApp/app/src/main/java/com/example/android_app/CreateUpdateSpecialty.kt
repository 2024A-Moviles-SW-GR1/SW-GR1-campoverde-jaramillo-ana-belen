package com.example.android_app

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CreateUpdateSpecialty : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_update_specialty)

        val name = findViewById<EditText>(R.id.input_specialty_name)
        val description = findViewById<EditText>(R.id.input_specialty_description)
        val veterinaryId = findViewById<EditText>(R.id.input_veterinary_id)
        val selectedSpecialty = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("selectedSpecialty", SpecialtyEntity::class.java)
        } else {
            intent.getParcelableExtra<SpecialtyEntity>("selectedSpecialty")
        }
        val selectedVeterinary = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("selectedVeterinary", VeterinaryEntity::class.java)
        } else {
            intent.getParcelableExtra<VeterinaryEntity>("selectedVeterinary")
        }
        val create = intent.getBooleanExtra("create", true)

        if(create) {
            veterinaryId.setText(selectedVeterinary!!.id.toString())

            // Create a book
            val btnCreateUpdateSpecialty = findViewById<Button>(R.id.btn_create_update_specialty)
            btnCreateUpdateSpecialty.setOnClickListener{
                DataBase.tables!!.createSpecialty(
                    name.text.toString(),
                    description.text.toString(),
                    veterinaryId.text.toString().toInt()
                )
                goToActivity(SpecialtiesList::class.java, selectedVeterinary)
            }
        } else {
            name.setText(selectedSpecialty!!.name)
            description.setText(selectedSpecialty.description)
            veterinaryId.setText(selectedSpecialty.veterinary_id.toString())

            // Update a book
            val btnCreateUpdateSpecialty = findViewById<Button>(R.id.btn_create_update_specialty)
            btnCreateUpdateSpecialty.setOnClickListener{
                DataBase.tables!!.updateSpecialty(
                    selectedSpecialty.id,
                    name.text.toString(),
                    description.text.toString(),
                    veterinaryId.text.toString().toInt()
                )
                goToActivity(SpecialtiesList::class.java, selectedVeterinary!!)
            }
        }
    }

    private fun goToActivity(
        activityClass: Class<*>,
        dataToPass: VeterinaryEntity
    ) {
        val intent = Intent(this, activityClass)
        intent.apply {
            putExtra("selectedVeterinary", dataToPass)
        }
        startActivity(intent)
    }

}