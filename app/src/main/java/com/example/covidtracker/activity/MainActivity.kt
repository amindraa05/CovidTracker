package com.example.covidtracker.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.covidtracker.R
import com.example.covidtracker.fragment.BahasaFragment
import com.example.covidtracker.fragment.HomeFragment
import com.example.covidtracker.fragment.NotificationFragment
import com.example.covidtracker.utils.AlarmReceiver
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        alarmReceiver = AlarmReceiver()

        setContentView(R.layout.activity_main)
//        showIndonesia()
//        showProvince()

        bottomNavigationView.setOnNavigationItemSelectedListener(this)

//        btn_switch.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                val repeatTime = "02:11"
//                val repeatMessage = "Yuk buka Github User App"
//                alarmReceiver.setRepeatingAlarm(
//                    this, AlarmReceiver.TYPE_REPEATING,
//                    repeatTime, repeatMessage
//                )
//            } else {
//                alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING)
//            }
//        }

        loadFragment(HomeFragment())

    }




    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        when (item.getItemId()) {
            R.id.ic_home -> fragment = HomeFragment()
            R.id.ic_notification -> fragment = NotificationFragment()
            R.id.ic_maps -> fragment = BahasaFragment()
        }
        return loadFragment(fragment)
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_container, fragment)
                .addToBackStack(null)
                .commit()
            return true
        }
        return false
    }
}