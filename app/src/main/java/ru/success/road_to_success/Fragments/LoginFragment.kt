package ru.success.road_to_success.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import ru.success.road_to_success.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var web_login: WebView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


        web_login = view.findViewById(R.id.web_login)
        web_login.settings.javaScriptEnabled = true
        web_login.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        web_login.loadUrl("https://oauth.vk.com/authorize?client_id=6121396&scope=photos,video,messages,docs,audio,offline&redirect_uri=https://oauth.vk.com/blank.html&response_type=token&display=page")
        web_login.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

                //Checker for Link in String
                url?.let {
                    if (it.startsWith("https://oauth.vk.com/blank.html#")) {
                        //Method call for saving token and user_id
                        saveTokenAndUserId(requireContext(), it)


                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, HomeFragment())
                            .commit()


                    }
                }
            }
        }

        clearWebViewData(web_login)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? { // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    private fun saveTokenAndUserId(context: Context, url: String) {
        val regex = Regex("access_token=([^&]+).*user_id=(\\d+)")
        val match = regex.find(url)

        if (match != null) {
            val (accessToken, userId) = match.destructured

            val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                putString("access_token", accessToken)
                putString("user_id", userId)
                apply()
            }
        }
    }

    private fun clearWebViewData(webView: WebView) {
        // Очищаем кэш WebView
        webView.clearCache(true)

        // Очищаем cookies
        CookieManager.getInstance().removeAllCookies(null)
        CookieManager.getInstance().flush()

        // Очищаем историю WebView
        webView.clearHistory()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */ // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = LoginFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}