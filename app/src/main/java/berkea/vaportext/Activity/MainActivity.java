package berkea.vaportext.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Date;

import berkea.vaportext.Constants;
import berkea.vaportext.Model;
import berkea.vaportext.R;
import berkea.vaportext.TextUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.button) Button mVapeButton;
    @BindView(R.id.adView) AdView mAdView;
    @BindView(R.id.checkBox) CheckBox mCheckBox;
    @BindView(R.id.text) EditText mEditText;

    private String message;
    private FirebaseAnalytics mFirebaseAnalytics;
    public static Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }

        mVapeButton.setOnClickListener(this);

        init();
    }

    private void init() {
        model = new Model(getApplicationContext(),new Date());

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        logUser();
        Answers.getInstance().logCustom(new CustomEvent("Opened App"));
        MobileAds.initialize(this, Constants.AD_KEY);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void logUser() {
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        Crashlytics.setUserIdentifier(android_id);
    }

    public Intent displayIntent(String result) {
        Intent intent = new Intent(getApplicationContext(), DisplayMessageActivity.class);
        intent.putExtra("result", result);
        return intent;
    }

    public String getResultStr() {
        message = mEditText.getText().toString();
        Boolean caps = mCheckBox.isChecked();
        return TextUtils.toVaporText(message, caps);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.vapor_menu, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                if(!mEditText.getText().toString().equals("")) {
                    Intent intent = displayIntent(getResultStr());
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Blank Text", Toast.LENGTH_SHORT);
                    toast.show();
                }

                // CRASHLYTICS
                Answers.getInstance().logCustom(new CustomEvent("Pressed Vaporize Button"));
                //FIREBASE
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Opened App");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle);
                break;
        }
    }

    @Override
    protected void onPause() {
        mAdView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mAdView != null) {
            mAdView.resume();
        } else {
            mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
    }
}
