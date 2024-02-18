package com.example.justkbrs.DataClassAndSingelton

class UserInfoSingleton private constructor() {
    companion object {
        val instance = UserInfoSingleton()
    }

    var userID: String = ""
    var username: String = ""
    var email: String = ""
    var password: String = ""
    var name: String = ""
    var surname: String = ""
    var phoneNumber: String = ""
    var age: String = ""
}