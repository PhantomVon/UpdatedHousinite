package com.example.housinite

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.housinite.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.security.acl.Owner

class OwnersSignUp : AppCompatActivity() {
    lateinit var ownerEdtName: EditText
    lateinit var ownerEdtEmail: EditText
    lateinit var ownerEdtPhoneNumber: EditText
    private lateinit var ownerEdtPassword: EditText
    private lateinit var ownerEdtConfirmPassword: EditText
    lateinit var btnSignUp: Button
    lateinit var tvSignIn: TextView
    lateinit var progressDialog: ProgressDialog
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owners_sign_up)
        ownerEdtName = findViewById(R.id.ownerName)
        ownerEdtEmail = findViewById(R.id.ownerEmail)
        ownerEdtPhoneNumber = findViewById(R.id.ownerPhone)
        ownerEdtPassword = findViewById(R.id.ownerPassword)
        ownerEdtConfirmPassword = findViewById(R.id.ownerConfirmPassword)
        btnSignUp = findViewById(R.id.mBtnSignUp)
        tvSignIn = findViewById(R.id.mTvSignIn)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Saving")
        progressDialog.setMessage("Please wait...")
        mAuth = FirebaseAuth.getInstance()
        btnSignUp.setOnClickListener {
            var ownerName = ownerEdtName.text.toString().trim()
            var ownerEmail = ownerEdtEmail.text.toString().trim()
            var ownerPhoneNumber = ownerEdtPhoneNumber.text.toString().trim()
            var ownerPassword = ownerEdtPassword.text.toString().trim()
            var ownerConfirmPassword = ownerEdtConfirmPassword.text.toString().trim()
            var id = System.currentTimeMillis().toString()
            if (ownerName.isEmpty() || ownerEmail.isEmpty() || ownerPhoneNumber.isEmpty() || ownerPassword.isEmpty() || ownerConfirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all inputs", Toast.LENGTH_LONG).show()
            } else if (!ownerPassword.equals(ownerConfirmPassword)) {
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_LONG).show()
            } else if (ownerPassword.length < 6) {
                Toast.makeText(this, "Password too short", Toast.LENGTH_LONG).show()
            } else {
                progressDialog.show()
                mAuth.createUserWithEmailAndPassword(ownerEmail, ownerPassword)
                    .addOnCompleteListener {
                        progressDialog.dismiss()
                        if (it.isSuccessful) {
                            var ref =
                                FirebaseDatabase.getInstance().getReference()
                                    .child("Owners/" + id)
                            var ownerData = Owners(
                                ownerName,
                                ownerEmail,
                                ownerPhoneNumber,
                                ownerPassword,
                                mAuth.currentUser!!.uid,
                                id
                            )
                            ref.setValue(ownerData)
                            Toast.makeText(
                                this,
                                "Owner registered successfully",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            mAuth.signOut()
                            startActivity(
                                Intent(
                                    this@OwnersSignUp,
                                    OwnersLogin::class.java
                                )
                            )
                            finish()
                        } else {
                            Toast.makeText(this, "Registration failed", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
            }
        }
        tvSignIn.setOnClickListener {
            startActivity(Intent(this@OwnersSignUp, OwnersLogin::class.java))

        }
    }
}