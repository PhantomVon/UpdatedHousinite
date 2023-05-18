package com.example.housinite

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AgentsActivity : AppCompatActivity() {
    lateinit var listAgents:ListView
    lateinit var agents:ArrayList<Agents>
    lateinit var adapter:AgentsAdapter
    lateinit var progressDialog: ProgressDialog
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agents)
        listAgents = findViewById(R.id.mListAgents)
        agents = ArrayList()
        adapter = AgentsAdapter(this,agents)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading")
        progressDialog.setMessage("Please wait...")
        // Create a reference to the database to load data
        var ref = FirebaseDatabase.getInstance().getReference().child("Agents")
        // Show the progress as you load data
        progressDialog.show()
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                agents.clear()
                for(data in snapshot.children){
                    var agent = data.getValue(Agents::class.java)
                    agents.add(agent!!)
                }
                adapter.notifyDataSetChanged()
                progressDialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AgentsActivity,"DB is inaccessible", Toast.LENGTH_LONG).show()
            }
        })
        listAgents.adapter = adapter
    }
}