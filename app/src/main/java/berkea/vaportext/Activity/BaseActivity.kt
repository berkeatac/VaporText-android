package berkea.vaportext.Activity

import android.support.v7.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    protected fun handleActionBar(homeEnabled: Boolean) {
        if (supportActionBar != null) {
            supportActionBar!!.elevation = 0f
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(homeEnabled)
    }

}
