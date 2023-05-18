package com.example.housinite

import android.widget.EditText

class UploadApartments {
    var location: String = ""
    var bedrooms: String = ""
    var floor: String = ""
    var rent: String = ""
    var image: String = ""
    var id: String = ""

    constructor(
        location: String,
        bedrooms: String,
        floor: String,
        rent: String,
        image:String,
        id: String
    ) {
        this.location = location
        this.bedrooms = bedrooms
        this.floor = floor
        this.rent = rent
        this.image = image

        this.id = id
    }
    constructor()
}