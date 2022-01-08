package ru.glushko.dnkstockapp.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.glushko.dnkstockapp.R
import ru.glushko.dnkstockapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var _mainActivityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        _mainActivityBinding.bottomNavView.setupWithNavController(navController)
        setContentView(_mainActivityBinding.root)
    }


}