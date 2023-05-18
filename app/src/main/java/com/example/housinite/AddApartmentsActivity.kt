package com.example.housinite

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.housinite.R
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap

class AddApartmentsActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    lateinit var imagePreview: ImageView
    lateinit var btn_choose_image: Button
    lateinit var btn_upload_image: Button
    lateinit var progress: ProgressDialog
    lateinit var edtLocation: EditText
    lateinit var edtBedrooms: EditText
    lateinit var edtFloor: EditText
    lateinit var edtRent: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_apartments)
        btn_choose_image = findViewById(R.id.btn_choose_image)
        btn_upload_image = findViewById(R.id.btn_upload_image)
        imagePreview = findViewById(R.id.image_preview)
        edtLocation = findViewById(R.id.mEdtLocation)
        edtBedrooms = findViewById(R.id.mEdtBedrooms)
        edtFloor = findViewById(R.id.mEdtFloor)
        edtRent = findViewById(R.id.mEdtRent)

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("Please wait...")

        btn_choose_image.setOnClickListener { launchGallery() }
        btn_upload_image.setOnClickListener { uploadImage() }
    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }

            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                imagePreview.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImage(){
        var location = edtLocation.text.toString().trim()
        var bedrooms = edtBedrooms.text.toString().trim()
        var floor = edtFloor.text.toString().trim()
        var rent = edtRent.text.toString().trim()
        var id = System.currentTimeMillis().toString()
        if (location.isEmpty()){
            edtLocation.setError("Please fill this input")
            edtLocation.requestFocus()
        }else if (bedrooms.isEmpty()){
            edtBedrooms.setError("Please fill this input")
            edtBedrooms.requestFocus()
        }else if (floor.isEmpty()){
            edtFloor.setError("Please fill this input")
            edtFloor.requestFocus()
        }else if (rent.isEmpty()){
            edtRent.setError("Please fill this input")
            edtRent.requestFocus()
        }else{
            if(filePath != null){

                val ref = storageReference?.child("apartments/" + UUID.randomUUID().toString())
                progress.show()
                val uploadTask = ref?.putFile(filePath!!)!!.addOnCompleteListener{
                    progress.dismiss()
                    if (it.isSuccessful){
                        ref.downloadUrl.addOnSuccessListener{
                        var apartmentsData = UploadApartments(location,bedrooms,floor,rent,it.toString(),id)
                            var ref = FirebaseDatabase.getInstance().getReference().child("Apartments/$id")
                            ref.setValue(apartmentsData)
                            Toast.makeText(this, "Apartment submitted successfully", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this, "Apartment submission failed", Toast.LENGTH_SHORT).show()
                    }
                }


            }else{
                Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
            }
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_add -> {
                startActivity(Intent(this,AddApartmentsActivity::class.java))
                true
            }

            R.id.action_view -> {
                startActivity(Intent(this,ApartmentsActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}