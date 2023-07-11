package com.imroz.messapplication.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.imroz.messapplication.R
import com.imroz.messapplication.activity.ui.login.LoginActivity

lateinit var btnDashBooking: Button
lateinit var btnDashLogout: Button


class DashboardFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        btnDashBooking= view.findViewById(R.id.dashboardBooking)
        btnDashLogout= view.findViewById(R.id.dashboardLogout)

        btnDashBooking.setOnClickListener{
            (context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.frame, SlotBookingFragment()).commit()
            Toast.makeText(activity,"Book your slots here..", Toast.LENGTH_SHORT).show()
        }

        btnDashLogout.setOnClickListener{
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

}

//<ScrollView
//android:layout_width="match_parent"
//android:layout_height="match_parent"
//android:orientation="vertical"
//android:scrollbars="vertical">
//
//<TextView
//android:id="@+id/txtDashboard"
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:padding="10dp"
//android:text="Day wise Menu:"
//android:textSize="20sp" />
//
//<LinearLayout
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:layout_marginTop="50dp"
//android:orientation="vertical">
//
//<TextView
//android:id="@+id/txtMon"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginTop="20dp"
//android:padding="10dp"
//android:text="Monday:"
//android:textSize="20sp" />
//
//<ImageView
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_gravity="center"
//android:src="@drawable/mess_app_icon"/>
//
//<TextView
//android:id="@+id/txtTue"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginTop="20dp"
//android:padding="10dp"
//android:text="Tuesday"
//android:textSize="20sp" />
//<TextView
//android:id="@+id/txtWed"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginTop="20dp"
//android:padding="10dp"
//android:text="Wednesday"
//android:textSize="20sp" />
//<TextView
//android:id="@+id/txtThur"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginTop="20dp"
//android:padding="10dp"
//android:text="Thursday"
//android:textSize="20sp" />
//<TextView
//android:id="@+id/txtFri"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginTop="20dp"
//android:padding="10dp"
//android:text="Friday"
//android:textSize="20sp" />
//<TextView
//android:id="@+id/txtSat"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginTop="20dp"
//android:padding="10dp"
//android:text="Saturday"
//android:textSize="20sp" />
//
//</LinearLayout>
//
//<LinearLayout
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:layout_marginTop="450dp"
//android:orientation="vertical">
//
//<Button
//android:id="@+id/dashboardBooking"
//android:layout_width="257dp"
//android:layout_height="61dp"
//android:layout_marginLeft="50dp"
//android:layout_marginTop="20dp"
//android:layout_marginRight="50dp"
//android:backgroundTint="@color/teal_700"
//android:padding="8dp"
//android:text="Slot Booking"
//android:textAllCaps="false"
//android:textSize="18sp"
//android:textStyle="bold" />
//
//<Button
//android:id="@+id/dashboardLogout"
//android:layout_width="257dp"
//android:layout_height="61dp"
//android:layout_marginLeft="50dp"
//android:layout_marginTop="20dp"
//android:layout_marginRight="50dp"
//android:backgroundTint="@color/teal_700"
//android:padding="8dp"
//android:text="Logout"
//android:textAllCaps="false"
//android:textSize="18sp"
//android:textStyle="bold" />
//
//</LinearLayout>
//</ScrollView>