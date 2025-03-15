package ru.success.road_to_success
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.success.road_to_success.DTO.ProfileData.ProfileDataInterface
import ru.success.road_to_success.DTO.ProfileData.ProfileDataItems
import ru.success.road_to_success.Fragments.ChatFragment
import ru.success.road_to_success.Fragments.DocsFragment
import ru.success.road_to_success.Fragments.HomeFragment
import ru.success.road_to_success.Fragments.LoginFragment
import ru.success.road_to_success.Fragments.MovieFragment
import ru.success.road_to_success.Fragments.PhotoAlbumFragment
import ru.success.road_to_success.databinding.ActivityMainBinding
import ru.success.road_to_success.databinding.NavHeaderBinding

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding
    private lateinit var headerBinding: NavHeaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val headerView = binding.navView.getHeaderView(0)
        headerBinding = NavHeaderBinding.bind(headerView)




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

        fetchAlbums()
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

    private fun fetchAlbums() {
        var user_id = getUserIdFromPreferences()
        var access_token = getTokenFromPreferences()
        val retrofit = createRetrofit()
        val retrofiters = retrofit.create(ProfileDataInterface::class.java)
        retrofiters.getProfileData(access_token!!).enqueue(object : Callback<ProfileDataItems> {
            override fun onResponse(call: Call<ProfileDataItems>, response: Response<ProfileDataItems>) {
                if (response.isSuccessful) {
                    val profileUrl = response.body()!!.response[0].photo400orig
                    var profileFirstName = response.body()!!.response[0].firstname
                    var profileLastName = response.body()!!.response[0].lastname
                    headerBinding.profileName.text = profileFirstName + " " + profileLastName
                    headerBinding.profileId.text = "USER ID: " + user_id

                    Glide.with(this@MainActivity)
                        .load(profileUrl)
                        .transform(CircleCrop())
                        .into(headerBinding.profileImage)

                } else {
                    Log.e("API_ERROR", "Error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ProfileDataItems>, t: Throwable) {
                Log.e("API_ERROR", "Failure Call", t)
            }
        })
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.vk.com/method/")
            .build()
    }

    companion object {

    }
}