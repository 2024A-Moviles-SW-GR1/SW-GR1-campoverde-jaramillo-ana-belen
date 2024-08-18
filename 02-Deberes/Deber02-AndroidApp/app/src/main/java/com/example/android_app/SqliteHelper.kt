package com.example.android_app

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper(
    context: Context? /* this */
) : SQLiteOpenHelper(context, "AndroidApp", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createVeterinaryTable = """
            CREATE TABLE VETERINARY(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name VARCHAR(100),
                location VARCHAR(100),
                phone VARCHAR(50)
            );
        """.trimIndent()

        val createSpecialtyTable = """
            CREATE TABLE SPECIALTY(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name VARCHAR(100),
                description VARCHAR(200),
                veterinary_id INTEGER,
                FOREIGN KEY (veterinary_id) REFERENCES VETERINARY(id) ON DELETE CASCADE
            );
        """.trimIndent()

        db?.execSQL(createVeterinaryTable)
        db?.execSQL(createSpecialtyTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun getAllVeterinaries():ArrayList<VeterinaryEntity> {
        val lectureDB =readableDatabase
        val queryScript ="""
            SELECT * FROM VETERINARY
        """.trimIndent()
        val queryResult = lectureDB.rawQuery(
            queryScript,
            emptyArray()
        )
        val response = arrayListOf<VeterinaryEntity>()

        if(queryResult.moveToFirst()) {
            do {
                response.add(
                    VeterinaryEntity(
                        queryResult.getInt(0),
                        queryResult.getString(1),
                        queryResult.getString(2),
                        queryResult.getString(3)
                    )
                )
            } while(queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return response
    }

    fun getSpecialtiesByVeterinary(veterinary_id: Int):ArrayList<SpecialtyEntity> {
        val lectureDB =readableDatabase
        val queryScript ="""
            SELECT * FROM SPECIALTY WHERE veterinary_id=?
        """.trimIndent()
        val queryResult = lectureDB.rawQuery(
            queryScript,
            arrayOf(veterinary_id.toString())
        )
        val response = arrayListOf<SpecialtyEntity>()

        if(queryResult.moveToFirst()) {
            do {
                response.add(
                    SpecialtyEntity(
                        queryResult.getInt(0),
                        queryResult.getString(1),
                        queryResult.getString(2),
                        queryResult.getInt(3)
                    )
                )
            } while(queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return response
    }

    fun createVeterinary(
        name: String,
        location: String,
        phone: String
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToStore = ContentValues()
        valuesToStore.put("name", name)
        valuesToStore.put("location", location)
        valuesToStore.put("phone", phone)

        val storeResult = writeDB.insert(
            "VETERINARY", // Table name
            null,
            valuesToStore // Values to insert
        )
        writeDB.close()

        return storeResult.toInt() != -1
    }

    fun createSpecialty(
        title: String,
        description: String,
        veterinary_id: Int
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToStore = ContentValues()
        valuesToStore.put("name", title)
        valuesToStore.put("description", description)
        valuesToStore.put("veterinary_id", veterinary_id)

        val storeResult = writeDB.insert(
            "SPECIALTY", // Table name
            null,
            valuesToStore // Values to insert
        )
        writeDB.close()

        return storeResult.toInt() != -1
    }

    fun updateVeterinary(
        id: Int,
        name: String,
        location: String,
        phone: String
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToUpdate = ContentValues()
        valuesToUpdate.put("name", name)
        valuesToUpdate.put("location", location)
        valuesToUpdate.put("phone", phone)

        val parametersUpdateQuery = arrayOf(id.toString())
        val updateResult = writeDB.update(
            "VETERINARY", // Table name
            valuesToUpdate,
            "id=?",
            parametersUpdateQuery
        )
        writeDB.close()

        return updateResult != -1
    }

    fun updateSpecialty(
        id: Int,
        title: String,
        description: String,
        veterinary_id: Int
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToUpdate = ContentValues()
        valuesToUpdate.put("name", title)
        valuesToUpdate.put("description", description)
        valuesToUpdate.put("veterinary_id", veterinary_id)

        val parametersUpdateQuery = arrayOf(id.toString())
        val updateResult = writeDB.update(
            "SPECIALTY", // Table name
            valuesToUpdate,
            "id=?",
            parametersUpdateQuery
        )
        writeDB.close()

        return updateResult != -1
    }

    fun deleteVeterinary(id:Int): Boolean {
        val writeDB = writableDatabase
        // SQL query example: where .... ID=? AND NAME=? [?=1, ?=2]
        val parametersDeleteQuery = arrayOf(id.toString())
        val deleteResult = writeDB.delete(
            "VETERINARY",
            "id=?",
            parametersDeleteQuery
        )
        writeDB.close()

        return deleteResult != -1
    }

    fun deleteSpecialty(id:Int): Boolean {
        val writeDB = writableDatabase
        // SQL query example: where .... ID=? AND NAME=? [?=1, ?=2]
        val parametersDeleteQuery = arrayOf(id.toString())
        val deleteResult = writeDB.delete(
            "SPECIALTY",
            "id=?",
            parametersDeleteQuery
        )
        writeDB.close()

        return deleteResult != -1
    }

}