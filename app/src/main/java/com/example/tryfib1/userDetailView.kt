package com.example.tryfib1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class userDetailView : AppCompatActivity() {

    private lateinit var  db : DatabaseReference
    var bloodgroup: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail_view)


        val bloodgroupI : RadioGroup= findViewById(R.id.userbloodgroup)


        val getdonarbtn : Button = findViewById(R.id.getdonorbtn)

        getdonarbtn.setOnClickListener {
            val bloodID = bloodgroupI.checkedRadioButtonId
            val bloodrb: RadioButton = findViewById(bloodID)
            bloodgroup = bloodrb.text.toString()
            getData()
        }

    }

    fun getData(){
        db = FirebaseDatabase.getInstance().getReference("Users")
        Toast.makeText(this,"$bloodgroup",Toast.LENGTH_SHORT).show()
        db.child("Ravindra9959273686").get().addOnSuccessListener {
            if (it.exists()){
                val name = it.child("name").value
                val age = it.child("age").value
                val gender = it.child("gender").value
                val blood = it.child("bloodgroup").value
                Toast.makeText(this,"$name  $age \n $gender  $blood",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"No Bloogroup match",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
        }

    }
}