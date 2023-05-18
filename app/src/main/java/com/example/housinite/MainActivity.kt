package com.example.housinite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    lateinit var cardAgents: CardView
    lateinit var cardOwners: CardView
    lateinit var cardApartments: CardView
    lateinit var cardHouses: CardView
    lateinit var cardNotifications: CardView
    lateinit var cardExit: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardAgents = findViewById(R.id.cardAgents)
        cardAgents.setOnClickListener {
            var intent = Intent(this,AgentsSignUp::class.java)
            startActivity(intent)
        }

        cardOwners = findViewById(R.id.cardOwners)
        cardOwners.setOnClickListener {
            var intent = Intent(this,OwnersSignUp::class.java)
            startActivity(intent)
        }

        cardApartments = findViewById(R.id.cardApartments)
        cardApartments.setOnClickListener {
            var intent = Intent(this,ApartmentsActivity::class.java)
            startActivity(intent)
        }

        cardHouses = findViewById(R.id.cardHouses)
        cardHouses.setOnClickListener {
            var intent = Intent(this,HousesActivity::class.java)
            startActivity(intent)
        }

        cardNotifications = findViewById(R.id.cardNotifications)
        cardNotifications.setOnClickListener {
            var intent = Intent(this,NotificationsActivity::class.java)
            startActivity(intent)
        }

        cardExit = findViewById(R.id.cardExit)
        cardExit.setOnClickListener{
            moveTaskToBack(true)
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(1)
        }

    }
}
