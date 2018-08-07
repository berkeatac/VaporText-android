package berkea.vaportext.Activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.CustomEvent
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import berkea.vaportext.Constants
import berkea.vaportext.R
import berkea.vaportext.ViewModel.MainViewModel
import berkea.vaportext.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var viewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        init()
    }

    fun displayIntent(result: String): Intent {
        val intent = Intent(applicationContext, DisplayMessageActivity::class.java)
        intent.putExtra(BUNDLE_RESULT, result)
        return intent
    }

    private fun init() {
        Answers.getInstance().logCustom(CustomEvent("Opened App"))
        hideActionBar()
        setListeners()
        logUser()
        initializeLoadAd()
    }

    private fun logUser() {
        val android_id = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        Crashlytics.setUserIdentifier(android_id)
    }

    private fun sendClickEvent() {
        // Crashlytics
        Answers.getInstance().logCustom(CustomEvent("Pressed Vaporize Button"))

        // Firebase
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Opened App")
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle)
    }

    private fun hideActionBar() {
        if (supportActionBar != null) {
            supportActionBar!!.elevation = 0f
        }
    }

    private fun setListeners() {
        binding!!.buttonVaporize.setOnClickListener { view: View ->
            viewModel!!.createResultString(binding!!.edittext.text.toString(), binding!!.checkboxCaps.isChecked)
            sendClickEvent()
        }

        viewModel!!.resultString.observe(this, Observer { s ->
            if (s != null) {
                val intent = displayIntent(s)
                startActivity(intent)
            } else {
                val toast = Toast.makeText(applicationContext,
                        "Enter Some Text", Toast.LENGTH_SHORT)
                toast.show()
            }
        })
    }

    private fun initializeLoadAd() {
        MobileAds.initialize(this, Constants.AD_KEY)
        binding!!.adview.loadAd(AdRequest.Builder().build())
    }

    companion object {

        private val BUNDLE_RESULT = "RESULT"
    }

}
