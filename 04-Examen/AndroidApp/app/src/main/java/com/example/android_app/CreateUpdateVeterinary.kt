package com.example.android_app

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CreateUpdateVeterinary : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_update_veterinary)

        val name = findViewById<EditText>(R.id.input_veterinary_name)
        val location = findViewById<EditText>(R.id.input_veterinary_location)
        val phone = findViewById<EditText>(R.id.input_veterinary_phone)
        val latitude = findViewById<EditText>(R.id.latitude)
        val longitude = findViewById<EditText>(R.id.longitude)
        val selectedVeterinay = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("selectedVeterinary", VeterinaryEntity::class.java)
        } else {
            intent.getParcelableExtra<VeterinaryEntity>("selectedVeterinary")
        }

        if(selectedVeterinay == null) {
            val btnCreateUpdateVeterinary = findViewById<Button>(R.id.btn_create_update_veterinary)
            btnCreateUpdateVeterinary.setOnClickListener{
                DataBase.tables!!.createVeterinary(
                    name.text.toString(),
                    location.text.toString(),
                    phone.text.toString(),
                    latitude.text.toString(),
                    longitude.text.toString()
                )
                goToActivity(MainActivity::class.java)
            }
        } else {
            name.setText(selectedVeterinay.name)
            location.setText(selectedVeterinay.location)
            phone.setText(selectedVeterinay.phone)
            latitude.setText(selectedVeterinay.latitude)
            longitude.setText(selectedVeterinay.longitude)


            // Update an veterinary
            val btnCreateUpdateVeterinary = findViewById<Button>(R.id.btn_create_update_veterinary)
            btnCreateUpdateVeterinary.setOnClickListener{
                DataBase.tables!!.updateVeterinary(
                    selectedVeterinay.id,
                    name.text.toString(),
                    location.text.toString(),
                    phone.text.toString(),
                    latitude.text.toString(),
                    longitude.text.toString()
                )
                goToActivity(MainActivity::class.java)
            }
        }

    }

    private fun goToActivity(
        activityClass: Class<*>
    ) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}