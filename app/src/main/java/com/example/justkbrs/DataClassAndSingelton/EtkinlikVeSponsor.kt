package com.example.justkbrs.DataClassAndSingelton

import java.io.Serializable
import java.util.*

data class Etkinlik(
    val mekanName: String = "",
    val activityName: String = "",
    val activityDate: String = "",
    val activityPrice: String = "",
    val activityDescription: String = "",
    val photoURLArray: String = "",
    val isActive: Int = 0,
    val etkinlikEklenisTarihi: Date? = null,
    val activityCategory: String = "",
    val activityDocumentID: String = "",
    val activityPhoneNumber: String = "",
    val activityBarCodeNo: String = ""
)  : Serializable {
    // Firebase Firestore için gereken boş yapıcı metot
    constructor() : this("", "", "", "", "", "", 0, null, "", "", "", "")
}

data class Sponsor(
    val activityDocumentID: String,
    val brandName: String,
    val brandType: String,
    val brandDescription: String,
    val brandPhoneNumber: String,
    val photoURLArray: String,
    val isActive: Int,
    val etkinlikEklenisTarihi: Date,
    val brandDiscound: String,
    val brandDate: String,
    val priceReceived: String
)