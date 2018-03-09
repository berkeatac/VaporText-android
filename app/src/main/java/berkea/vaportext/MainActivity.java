package berkea.vaportext;

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
import com.crashlytics.android.answers.ContentViewEvent;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String AD_KEY = "ca-app-pub-7995520615225219~4866392143";
    private Button mVapeButton;
    private AdView mAdView;
    private CheckBox mCheckBox;
    private EditText mEditText;

    private String message;

    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }
        MobileAds.initialize(this, AD_KEY);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Log User with its unique device id on start up
        logUser();

        mCheckBox = (CheckBox) findViewById(R.id.checkBox);
        mVapeButton = (Button) findViewById(R.id.button);
        mEditText = (EditText) findViewById(R.id.text);

        mVapeButton.setOnClickListener(this);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        Crashlytics.setUserIdentifier(android_id);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.vapor_menu, menu);
        return true;
    }

    public String toVaporText(String message, Boolean isUpperCase) {
        int len = message.length();
        if(isUpperCase) {
            message = message.toUpperCase();
        }

        StringBuilder result = new StringBuilder();
        for(int i = 0; i < len; i++) {
            char ch = message.charAt(i);
            result.append(ch).append(" ");
        }
        return result.toString();
    }

    public Intent displayIntent() {
        Intent intent = new Intent(getApplicationContext(), DisplayMessageActivity.class);
        message = mEditText.getText().toString();
        Boolean caps = mCheckBox.isChecked();
        String result = toVaporText(message, caps);

        intent.putExtra("result", result);
        return intent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:

                // Get button click tracked
                trackAction();

                if(!mEditText.getText().toString().equals("")) {
                    Intent intent = displayIntent();
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Blank Text", Toast.LENGTH_SHORT);
                    toast.show();
                }

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

    private void trackAction() {
        // TODO: Use your own attributes to track content views in your app
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("Pressed Button")
                .putContentType("Action"));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mAdView != null) {  // Check if Adview is not null in case of fist time load.
            mAdView.resume();
        } else {
            mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
    }
}
