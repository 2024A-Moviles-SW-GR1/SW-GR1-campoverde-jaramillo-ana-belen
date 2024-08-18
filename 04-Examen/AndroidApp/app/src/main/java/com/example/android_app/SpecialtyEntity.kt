package com.example.android_app

import android.os.Parcel
import android.os.Parcelable

class SpecialtyEntity(
    var id: Int,
    var name: String,
    var description: String,
    var veterinary_id: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(veterinary_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SpecialtyEntity> {
        override fun createFromParcel(parcel: Parcel): SpecialtyEntity {
            return SpecialtyEntity(parcel)
        }

        override fun newArray(size: Int): Array<SpecialtyEntity?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString():String {
        return "$id - $name"
    }

}