package com.example.android_app

import android.os.Parcel
import android.os.Parcelable

class VeterinaryEntity(
    var id: Int,
    var name: String,
    var location: String,
    var phone: String,
    var latitude: String,
    var longitude: String
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(location)
        parcel.writeString(phone)
        parcel.writeString(latitude)
        parcel.writeString(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VeterinaryEntity> {
        override fun createFromParcel(parcel: Parcel): VeterinaryEntity {
            return VeterinaryEntity(parcel)
        }

        override fun newArray(size: Int): Array<VeterinaryEntity?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString():String {
        return "$id - $name"
    }

}