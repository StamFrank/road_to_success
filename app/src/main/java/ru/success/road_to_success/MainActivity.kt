package ru.success.road_to_success
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import ru.success.road_to_success.Fragments.ChatFragment
import ru.success.road_to_success.Fragments.DocsFragment
import ru.success.road_to_success.Fragments.HomeFragment
import ru.success.road_to_success.Fragments.LoginFragment
import ru.success.road_to_success.Fragments.MovieFragment
import ru.success.road_to_success.Fragments.PhotoAlbumFragment
import ru.success.road_to_success.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val navigationView = binding.navView
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }

        val tokenAccess = getTokenFromPreferences()
        val userId = getUserIdFromPreferences()


        if (savedInstanceState == null) {
            if (tokenAccess.isNullOrEmpty() || userId.isNullOrEmpty()) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, LoginFragment())
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment())
                    .commit()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment())
                    .commit()
            }
            R.id.nav_chat -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ChatFragment())
                    .commit()
            }
            R.id.nav_docs -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, DocsFragment())
                    .commit()
            }
            R.id.nav_movie -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MovieFragment())
                    .commit()
            }
            R.id.nav_photo_album -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, PhotoAlbumFragment())
                    .commit()
            }
            R.id.nav_logout -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, LoginFragment())
                    .commit()
                clearCredentialsFromPreferences()
                Toast.makeText(this, "Logout Completed", Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun getTokenFromPreferences(): String? {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        return sharedPreferences.getString("access_token", null)
    }

    private fun getUserIdFromPreferences(): String? {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        return sharedPreferences.getString("user_id", null)
    }

    private fun clearCredentialsFromPreferences() {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("access_token")
        editor.remove("user_id")
        editor.apply()
    }

    companion object {

    }
}