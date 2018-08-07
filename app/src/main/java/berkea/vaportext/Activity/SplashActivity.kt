package berkea.vaportext.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.ndk.CrashlyticsNdk
import io.fabric.sdk.android.Fabric

/**
 * Created by berkeatac on 27/12/2017.
 */

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics(), CrashlyticsNdk())

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}