package com.example.poc

import android.app.Notification
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

import com.example.poc.GoogleMaps.MapFragment
import com.example.poc.NetworkList.NetworkListFragment
import com.example.poc.OcrCamera.OcrTextRecognitionFragment
import com.example.poc.Util.NotificationHelper
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    internal var fragment: Fragment? = null
    internal var bottomNavigationView: BottomNavigationView? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_search -> {
                fragment = NetworkListFragment()
                switchFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_ocr -> {
                fragment = OcrTextRecognitionFragment()
                switchFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_maps -> {
                fragment = MapFragment()
                switchFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.fragment_holder, fragment!!).commit()
        true
    }


    fun turnOffBottomnaviIcon() {
        bottomNavigationView = findViewById(R.id.navigation)
        if (bottomNavigationView != null) {
            bottomNavigationView!!.menu.removeItem(R.id.navigation_maps)
        }
        fragment = NetworkListFragment()
        switchFragment(fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.navigation)
        bottomNavigationView!!.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        showNotification()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_holder,
                    NetworkListFragment()).commit()
        }
    }

    private fun switchFragment(fragment: Fragment?) {
        val fragmentTransaction: FragmentTransaction
        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_holder, fragment!!)
        fragmentTransaction.commit()
    }

    fun showNotification() {
        val showNotification = NotificationHelper(this)
        val builder = showNotification.getChannel(getString(R.string.welcome), getString(R.string.enjoy))
        showNotification.notificationManager.notify(1, builder.build())

    }

}
