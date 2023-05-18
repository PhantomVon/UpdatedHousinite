package com.example.housinite

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class AgentsSignUp : AppCompatActivity() {
    lateinit var edtName: EditText
    lateinit var edtEmail: EditText
    lateinit var edtPhoneNumber: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtConfirmPassword: EditText
    lateinit var btnSave: Button
    lateinit var btnView: Button
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agents_sign_up)
        edtName = findViewById(R.id.agentsName)
        edtEmail = findViewById(R.id.agentsEmail)
        edtPhoneNumber = findViewById(R.id.agentsPhone)
        edtPassword = findViewById(R.id.agentsPassword)
        edtConfirmPassword = findViewById(R.id.agentsConfirmPassword)
        btnSave = findViewById(R.id.mBtnSave)
        btnView = findViewById(R.id.mBtnView)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Saving")
        progressDialog.setMessage("Please wait...")
        btnSave.setOnClickListener {
            var name = edtName.text.toString().trim()
            var email = edtEmail.text.toString().trim()
            var phoneNumber = edtPhoneNumber.text.toString().trim()
            var password = edtPassword.text.toString().trim()
            var confirmPassword = edtConfirmPassword.text.toString().trim()
            var id = System.currentTimeMillis().toString()
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
                var agent = Agents(name, email, phoneNumber, password, confirmPassword, id)
                // Create a reference to the Firebase
                var ref = FirebaseDatabase.getInstance().getReference().child("Agents/"+id)
                progressDialog.show()
                ref.setValue(agent).addOnCompleteListener {
                    progressDialog.dismiss()
                    if (it.isSuccessful){
                        Toast.makeText(this,"Agent saved successfully!", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this,"Agent saving failed!", Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
        btnView.setOnClickListener {
            var intent = Intent(this,AgentsActivity::class.java)
            startActivity(intent)
        }

    }
}