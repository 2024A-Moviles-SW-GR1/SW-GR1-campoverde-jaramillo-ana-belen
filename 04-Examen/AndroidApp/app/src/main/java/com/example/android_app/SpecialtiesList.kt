package com.example.android_app

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class SpecialtiesList : AppCompatActivity() {

    private var allSpecialties:ArrayList<SpecialtyEntity> = arrayListOf()
    private var adapter:ArrayAdapter<SpecialtyEntity>? = null
    private var selectedRegisterPosition = -1
    private var selectedVeterinary: VeterinaryEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specialties_list)


        selectedVeterinary = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("selectedVeterinary", VeterinaryEntity::class.java)
        } else {
            intent.getParcelableExtra<VeterinaryEntity>("selectedVeterinary")
        }
        val booksAuthor = findViewById<TextView>(R.id.txt_view_veterinary_name)
        booksAuthor.text = selectedVeterinary!!.name

        DataBase.tables = SqliteHelper(this)
        val specialtiesList = findViewById<ListView>(R.id.list_specialties)
        allSpecialties = DataBase.tables!!.getSpecialtiesByVeterinary(selectedVeterinary!!.id)
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            allSpecialties
        )

        specialtiesList.adapter = adapter
        adapter!!.notifyDataSetChanged() // To update the UI

        val btnRedirectCreateSpecialty = findViewById<Button>(R.id.btn_redirect_create_specialty)
        btnRedirectCreateSpecialty.setOnClickListener{
            goToActivity(CreateUpdateSpecialty::class.java, null, selectedVeterinary!!)
        }
        val btnBackToMain = findViewById<Button>(R.id.btn_back_to_main)
        btnBackToMain.setOnClickListener{
            goToActivity(MainActivity::class.java)
        }

        registerForContextMenu(specialtiesList)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        // Set options for the context menu
        val inflater = menuInflater
        inflater.inflate(R.menu.specialty_menu, menu)

        // Get ID of the selected item of the list
        val register = menuInfo as AdapterView.AdapterContextMenuInfo
        selectedRegisterPosition = register.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_edit_specialty -> {
                goToActivity(CreateUpdateSpecialty::class.java, allSpecialties[selectedRegisterPosition], selectedVeterinary!!, false)
                return true
            }
            R.id.mi_delete_specialty -> {
                openDialog(allSpecialties[selectedRegisterPosition].id)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun goToActivity(
        activityClass: Class<*>,
        dataToPass: SpecialtyEntity? = null,
        dataToPass2: VeterinaryEntity? = null,
        create: Boolean = true
    ) {
        val intent = Intent(this, activityClass)
        if(dataToPass != null) {
            intent.apply {
                putExtra("selectedSpecialty", dataToPass)
            }
        }
        if(dataToPass2 != null) {
            intent.apply {
                putExtra("selectedVeterinary", dataToPass2)
            }
        }
        intent.apply {
            putExtra("create", create)
        }
        startActivity(intent)
    }

    private fun openDialog(registerIndex: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Está seguro de eliminar la especialidad?")
        builder.setPositiveButton(
            "Eliminar"
        ) { _, _ ->
            DataBase.tables!!.deleteSpecialty(registerIndex)
            allSpecialties.clear()
            allSpecialties.addAll(DataBase.tables!!.getSpecialtiesByVeterinary(selectedVeterinary!!.id))
            adapter!!.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancelar", null)

        builder.create().show()
    }

}