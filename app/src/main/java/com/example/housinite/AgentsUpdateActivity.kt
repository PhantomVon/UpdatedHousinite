package com.example.housinite

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.housinite.Agents
import com.example.housinite.R
import com.google.firebase.database.FirebaseDatabase

class AgentsUpdateActivity : AppCompatActivity() {
    lateinit var edtName: EditText
    lateinit var edtEmail: EditText
    lateinit var edtPhoneNumber: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtConfirmPassword: EditText
    lateinit var progressDialog: ProgressDialog
    lateinit var btnUpdate:Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agents_update)
        edtName = findViewById(R.id.agentsName)
        edtEmail = findViewById(R.id.agentsEmail)
        edtPhoneNumber = findViewById(R.id.agentsPhone)
        edtPassword = findViewById(R.id.agentsPassword)
        edtConfirmPassword = findViewById(R.id.agentsConfirmPassword)
        btnUpdate = findViewById(R.id.mBtnAgentsUpdate)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Updating")
        progressDialog.setMessage("Please wait...")
        // Receive data sent via the intent
        var receivedName = intent.getStringExtra("name")
        var receivedEmail = intent.getStringExtra("email")
        var receivedPhoneNumber = intent.getStringExtra("phoneNumber")
        var receivedPassword = intent.getStringExtra("password")
        var receivedConfirmPassword = intent.getStringExtra("confirm password")
        var receivedId = intent.getStringExtra("id")
        // Display the received data on the EditTexts
        edtName.setText(receivedName)
        edtEmail.setText(receivedEmail)
        edtPhoneNumber.setText(receivedPhoneNumber)
        edtPassword.setText(receivedPassword)
        edtConfirmPassword.setText(receivedConfirmPassword)
        // Set an onClick listener to button update
        btnUpdate.setOnClickListener {
            var name = edtName.text.toString().trim()
            var email = edtEmail.text.toString().trim()
            var phoneNumber = edtPhoneNumber.text.toString().trim()
            var password = edtPassword.text.toString().trim()
            var confirmpasword = edtConfirmPassword.text.toString().trim()
            var id = receivedId!!
            // Check if user is submitting empty fields
            if (name.isEmpty()) {
                edtName.setError("Please fill this input")
                edtName.requestFocus()
            }else if (email.isEmpty()){
                edtEmail.setError("Please fill this input")
                edtEmail.requestFocus()
            }else if (phoneNumber.isEmpty()){
                edtPhoneNumber.setError("Please fill this input")
                edtPhoneNumber.requestFocus()
            }else if (phoneNumber.isEmpty()){
                edtPassword.setError("Please fill this input")
                edtPassword.requestFocus()
            }else if (phoneNumber.isEmpty()){
                edtConfirmPassword.setError("Please fill this input")
                edtConfirmPassword.requestFocus()
            }else{
                // Proceed to save
                var agents = Agents(name, email, phoneNumber, password, confirmpasword, id)
                // Create a reference to the Firebase
                var ref = FirebaseDatabase.getInstance().getReference().child("Users/"+id)
                progressDialog.show()
                ref.setValue(agents).addOnCompleteListener {
                    progressDialog.dismiss()
                    if (it.isSuccessful){
                        Toast.makeText(this,"User updated successfully!", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@AgentsUpdateActivity,AgentsUpdateActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this,"User updating failed!", Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }
}