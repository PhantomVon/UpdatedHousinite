package com.example.housinite

import android.widget.EditText

class UploadHouses {
    var location: String = ""
    var bedrooms: String = ""
    var description: String = ""
    var rent: String = ""
    var image: String = ""
    var id: String = ""

    constructor(
        location: String,
        bedrooms: String,
        description: String,
        rent: String,
        image:String,
        id: String
    ) {
        this.location = location
        this.bedrooms = bedrooms
        this.description = description
        this.rent = rent
        this.image = image

        this.id = id
    }
    constructor()
}