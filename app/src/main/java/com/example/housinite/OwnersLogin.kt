package com.example.housinite


import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class OwnersLogin : AppCompatActivity() {
    lateinit var edtUserEmail: EditText
    lateinit var edtUserPassword: EditText
    lateinit var btnSignIn: Button
    lateinit var tvSignIn: TextView
    lateinit var progressDialog: ProgressDialog
    lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owners_login)


        edtUserEmail = findViewById(R.id.ownersName)
        edtUserPassword = findViewById(R.id.ownersPassword)
        btnSignIn = findViewById(R.id.mBtnSignIn)
        tvSignIn = findViewById(R.id.mTvSignUp)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading")
        progressDialog.setMessage("Please wait...")
        mAuth = FirebaseAuth.getInstance()
        btnSignIn.setOnClickListener {
            var userEmail = edtUserEmail.text.toString().trim()
            var userPassword = edtUserPassword.text.toString().trim()
            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all inputs", Toast.LENGTH_LONG).show()
            }else{
                progressDialog.show()
                mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener {
                    progressDialog.dismiss()
                    if (it.isSuccessful){
                        Toast.makeText(this,"User logged in successfully", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@OwnersLogin,MainActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this,"Login failed", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        tvSignIn.setOnClickListener {
            startActivity(Intent(this@OwnersLogin,OwnersSignUp::class.java))

        }
    }
}