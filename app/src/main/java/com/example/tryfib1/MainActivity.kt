package com.example.tryfib1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.tryfib1.databinding.ActivityMainBinding
import com.google.android.gms.tasks.Task
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var db : DatabaseReference
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var latitude : String = ""
    var longitude : String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()


        /**
         * Kotlin code to get Date DD/MM/YYYY
         */

        val calendarView : CalendarView  = binding.calendarView
        lateinit var lbdd : String

        calendarView!!.setOnDateChangeListener { calendarView, i, i1, i2 ->
            lbdd = i2.toString() + "/" + (i1 + 1) + "/" + i
            Toast.makeText(this,"date is $lbdd",Toast.LENGTH_SHORT).show()
        }

        binding.registerbutton.setOnClickListener {
            try {
                val name = binding.username.text.toString()
                val agee= binding.userage.text.toString()
                var age : Int= 0
                if(agee !=""){age = agee.toInt()}

                //Getting Gender from Radio Type
                val genderI = binding.usergender
                var gender : String? = null

                val bloodgroupI = binding.userbloodgroup
                var bloodgroup: String? = null


                val genderID = genderI.checkedRadioButtonId
                val genderrb: RadioButton = findViewById(genderID)
                gender = genderrb.text.toString()


                //Getting BloodGroup from Radio Type

                val bloodID = bloodgroupI.checkedRadioButtonId
                val bloodrb: RadioButton = findViewById(bloodID)
                bloodgroup = bloodrb.text.toString()

                val loc = binding.getlocation

                loc.setOnClickListener {
                    fetchLocation()
                }


                val final_latitude = latitude.toDouble()
                val final_longitude = longitude.toDouble()


                val phone = binding.userphonenumber.text.toString()
                //val location = binding.userlocation.text.toString()


                val donated_or_not = binding.donatedOrNot.isChecked
                if(donated_or_not == true){
                    lbdd = ""
                }


               // val lbdd = binding.userlastblooddonationdate.text.toString()
                val eligibility = binding.usereligibility.isChecked

                db = FirebaseDatabase.getInstance().getReference("Users")


                if(name != "" && age > 18 && age < 65  && gender != "" && bloodgroup!="" && phone !="" && latitude!=""){
                    val user = User(name, age, gender, bloodgroup, phone, final_latitude,final_longitude, lbdd, eligibility)
                    val userName = name+phone
                    db.child(userName).setValue(user).addOnSuccessListener {
                    binding.username.text.clear()
                    binding.userage.text.clear()
                    binding.usergender.clearCheck()
                    binding.userbloodgroup.clearCheck()
                    binding.userphonenumber.text.clear()
                    binding.usereligibility.setChecked(false)

                    Toast.makeText(this, "Successfully saved", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,Home::class.java))
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed ", Toast.LENGTH_SHORT).show()
                }
                }else{
                Toast.makeText(this,"Enter Valid Data",Toast.LENGTH_SHORT).show()
                }
            }

            catch (e :NullPointerException){
                Toast.makeText(this,"Enter Valid Data",Toast.LENGTH_SHORT).show()
            }
            catch (e:NumberFormatException){
                Toast.makeText(this,"Check Location Again",Toast.LENGTH_SHORT).show()
            }

        }
    }


    private fun fetchLocation() {
        val task : Task<Location> = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }
        task.addOnSuccessListener{
            if (it != null ){
                //Toast.makeText(applicationContext, "${it.latitude},${it.longitude}", Toast.LENGTH_SHORT).show()
                Toast.makeText(this,"Check Register Now",Toast.LENGTH_SHORT).show()
                latitude = it.latitude.toString()
                longitude = it.longitude.toString()
            }
        }
    }
}