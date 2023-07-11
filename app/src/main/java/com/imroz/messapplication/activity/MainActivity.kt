package com.imroz.messapplication.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.common.data.DataBufferRef
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.imroz.messapplication.R
import com.imroz.messapplication.activity.ui.login.LoginActivity
import com.imroz.messapplication.databinding.ActivityMainBinding
import com.imroz.messapplication.fragment.AboutFragment
import com.imroz.messapplication.fragment.DashboardFragment
import com.imroz.messapplication.fragment.ProfileFragment
import com.imroz.messapplication.fragment.SlotBookingFragment

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbar: Toolbar
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var frameLayout: FrameLayout
    private lateinit var binding: ActivityMainBinding
    
    var previousMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        toolbar = findViewById(R.id.toolbar)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        frameLayout = findViewById(R.id.frame)

        setUpToolbar()

        val actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity, drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        openDashboard()

        navigationView.setNavigationItemSelectedListener {

            if (previousMenuItem!=null){
                previousMenuItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it

            when(it.itemId){
                R.id.dashboard ->{
                    openDashboard()
                    drawerLayout.closeDrawers() }
                R.id.slotBooking ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frame, SlotBookingFragment()).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title = "Slot Booking"
                }
                R.id.profile ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frame, ProfileFragment()).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title = "User Profile"
                }
                R.id.aboutApp ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frame, AboutFragment()).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title = "About App"
                }
                R.id.logout ->{
                    val alertBuilder= AlertDialog.Builder(this)
                    alertBuilder.setTitle("Logout!!")
                        .setMessage("Are you sure you want to exit?")
                        .setPositiveButton("Yes"){_,_ ->
                            Firebase.auth.signOut()
                            Toast.makeText(this, "Successfully logout!!", Toast.LENGTH_SHORT).show()
                            this.finish()
                        }
                        .setNegativeButton("No"){_,_ ->
                            DashboardFragment()
                        }.create().show()
                }
            }

            return@setNavigationItemSelectedListener true
        }

    }

    fun setUpToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title="Dashboard"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id= item.itemId
        if (id== android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun openDashboard(){
        supportFragmentManager.beginTransaction().replace(R.id.frame, DashboardFragment()).commit()
        navigationView.setCheckedItem(R.id.dashboard)
        supportActionBar?.title="Dashboard"
    }

    override fun onBackPressed() {
        val fragment= supportFragmentManager.findFragmentById(R.id.frame)
        when(fragment){
            !is DashboardFragment-> openDashboard()
            else -> finishAffinity()
        }
    }

}