package berkea.vaportext.Activity

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.MenuItem
import android.view.View

import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.CustomEvent
import com.google.android.gms.ads.AdRequest
import java.util.Date
import berkea.vaportext.Model
import berkea.vaportext.R
import berkea.vaportext.TextUtils
import berkea.vaportext.ViewModel.DisplayMessageViewModel
import berkea.vaportext.databinding.ActivityDisplayMessageBinding

class DisplayMessageActivity : BaseActivity() {

    private var result: String? = null

    private var binding: ActivityDisplayMessageBinding? = null
    private var viewModel: DisplayMessageViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_message)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_display_message)
        viewModel = ViewModelProviders.of(this).get(DisplayMessageViewModel::class.java)

        init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        binding!!.adview.loadAd(AdRequest.Builder().build())
        handleActionBar(true)
        setResultText()
        setListeners()
    }

    private fun setResultText() {
        result = intent.getStringExtra(BUNDLE_RESULT)
        binding!!.textviewVapor.text = result
        binding!!.textviewVapor.movementMethod = ScrollingMovementMethod()
    }

    private fun setListeners() {
        binding!!.buttonCopy.setOnClickListener { v: View ->
            val model = Model(applicationContext, Date())
            TextUtils.copyToClipboard(result!!, model.getContext()!!)
            Answers.getInstance().logCustom(CustomEvent("Copied to Clipboard"))
        }

        binding!!.buttonShare.setOnClickListener { v: View ->
            shareText(result)
            Answers.getInstance().logCustom(CustomEvent("Shared Text"))
        }
    }

    fun shareText(text: String?) {
        val share = Intent(Intent.ACTION_SEND)
        share.type = "text/plain"
        share.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(share, getString(R.string.share_using)))
    }

    companion object {

        private val BUNDLE_RESULT = "RESULT"
    }
}
