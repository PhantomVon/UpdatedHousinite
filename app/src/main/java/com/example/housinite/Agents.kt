package com.example.housinite
class Agents {
    var name:String = ""
    var email:String = ""
    var phoneNumber:String = ""
    var password:String = ""
    var confirmPassword:String = ""
    var id:String = ""

    constructor(name: String, email: String, phoneNumber:String, password:String,
                confirmPassword:String, id: String) {
        this.name = name
        this.email = email
        this.phoneNumber = phoneNumber
        this.password = password
        this.confirmPassword = confirmPassword
        this.id = id
    }
    constructor()
}