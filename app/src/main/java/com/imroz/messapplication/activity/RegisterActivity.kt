package com.imroz.messapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.imroz.messapplication.R
import com.imroz.messapplication.activity.ui.login.LoginActivity
import com.imroz.messapplication.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    lateinit var txtLogin: TextView
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        firebaseAuth= FirebaseAuth.getInstance()

        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener{

            binding.btnLogin.visibility= View.INVISIBLE
            val name= binding.txtNameUser.text.toString()
            val email= binding.txtEmailUser.text.toString()
            val phoneNo= binding.txtContactNo.toString()
            val password= binding.password.text.toString()
            val confirmPassword= binding.confirmPassword.text.toString()

            if(name.isNotEmpty() &&
                email.isNotEmpty() &&
                phoneNo.isNotEmpty() &&
                password.isNotEmpty() &&
                confirmPassword.isNotEmpty()){
                if (password==confirmPassword){

                    database= FirebaseDatabase.getInstance().getReference("Users")

                    val user= User(name,email,phoneNo)
                    database.child(name).setValue(user).addOnSuccessListener {
                        binding.txtNameUser.text.clear()
                        binding.txtEmailUser.text.clear()
                        binding.txtContactNo.text.clear()
                        binding.password.text.clear()
                        binding.confirmPassword.text.clear()
                        Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT)
                            .show()
                    }.addOnFailureListener {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT)
                            .show()
                    }

                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful){
                            val intent= Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                            this.finish()
                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }else{
                       Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT)
                           .show()
                }
            }else{
                Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        txtLogin= findViewById(R.id.txtLogin)
        binding.txtLogin.setOnClickListener{
            val intent= Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}