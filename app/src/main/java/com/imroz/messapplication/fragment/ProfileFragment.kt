package com.imroz.messapplication.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.imroz.messapplication.R
import com.imroz.messapplication.activity.User
import com.imroz.messapplication.activity.ui.login.LoggedInUserView
import com.imroz.messapplication.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    lateinit var firebaseUser: FirebaseUser

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    lateinit var user: User
    lateinit var txtNameUser: TextView
    lateinit var txtEmailUser: TextView
    lateinit var txtPhoneUser: TextView
    lateinit var uid: String
    lateinit var btnProfileLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_profile, container, false)

//        binding= FragmentProfileBinding.bind(view)
        firebaseAuth= FirebaseAuth.getInstance()
        firebaseUser= firebaseAuth.currentUser!!

        txtNameUser= view.findViewById(R.id.txtNameUser)
        txtEmailUser= view.findViewById(R.id.txtEmailUser)
        txtPhoneUser= view.findViewById(R.id.txtPhoneUser)

//        uid= firebaseAuth.currentUser?.uid.toString()

        databaseReference= FirebaseDatabase.getInstance().getReference("Users")
        val query = databaseReference.orderByChild("email").equalTo(firebaseUser.email)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataSnapshot1 in dataSnapshot.children) {
                    // Retrieving Data from firebase
                    val name = "" + dataSnapshot1.child("name").value
                    val emaill = "" + dataSnapshot1.child("email").value
                    val phone = "" + dataSnapshot1.child("phoneNumber").value
                    // setting data to our text view
                    txtNameUser.setText(name)
                    txtEmailUser.setText(emaill)
                    txtPhoneUser.setText(phone)

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
//        if (uid.isNotEmpty()){
////            getUserData()
//            databaseReference.child(uid).addValueEventListener(object :ValueEventListener{
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    user= snapshot.getValue(User::class.java)!!
//                    binding.txtNameUser.setText(user.name)
//                    binding.txtEmailUser.setText(user.email)
//                    binding.txtPhoneUser.setText(user.phoneNumber)
//
//
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Toast.makeText(activity, "Unable to add your data", Toast.LENGTH_SHORT).show()
//                }
//
//            })
//        }

        btnProfileLogout= view.findViewById(R.id.profileLogout)
        btnProfileLogout.setOnClickListener{
            val alertBuilder= AlertDialog.Builder(activity)
            alertBuilder.setTitle("Logout!!")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes"){_,_ ->
                    Firebase.auth.signOut()
                    Toast.makeText(activity, "Successfully logout!!", Toast.LENGTH_SHORT).show()
                    activity?.finish()

                }
                .setNegativeButton("No"){_,_ ->
                    DashboardFragment()
                }.create().show()
        }

        return view
    }

//    private fun getUserData() {
//        databaseReference.child(uid).addValueEventListener(object :ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                user= snapshot.getValue(User::class.java)!!
//                binding.txtNameUser.setText(user.name)
//                binding.txtEmailUser.setText(user.email)
//                binding.txtPhoneUser.setText(user.phoneNumber)
//
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(activity, "Unable to add your data", Toast.LENGTH_SHORT).show()
//            }
//
//        })
//    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        Toast.makeText(
            activity,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }


}