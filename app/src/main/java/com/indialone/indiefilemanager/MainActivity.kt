package com.indialone.indiefilemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.indialone.indiefilemanager.databinding.ActivityMainBinding
import com.indialone.indiefilemanager.fragments.AboutFragment
import com.indialone.indiefilemanager.fragments.HomeFragment
import com.indialone.indiefilemanager.fragments.InternalStorageFragment
import com.indialone.indiefilemanager.fragments.SdCardFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setSupportActionBar(mBinding.toolbar)

        mBinding.navView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,
            mBinding.drawerLayout,
            mBinding.toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        mBinding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment())
            .commit()
        mBinding.navView.setCheckedItem(R.id.nav_home)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                val homeFragment = HomeFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, homeFragment)
                    .addToBackStack(null)
                    .commit()
            }
            R.id.nav_internal -> {
                val internalStorageFragment = InternalStorageFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, internalStorageFragment)
                    .addToBackStack(null)
                    .commit()
            }
            R.id.nav_sdcard -> {
                val sdCardFragment = SdCardFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, sdCardFragment)
                    .addToBackStack(null)
                    .commit()
            }
            R.id.nav_about -> {
                val aboutFragment = AboutFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, aboutFragment)
                    .addToBackStack(null)
                    .commit()

            }
        }

        mBinding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        supportFragmentManager.popBackStackImmediate()
        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}