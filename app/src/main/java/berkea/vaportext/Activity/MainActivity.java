package berkea.vaportext.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import berkea.vaportext.Constants;
import berkea.vaportext.R;
import berkea.vaportext.ViewModel.MainViewModel;
import berkea.vaportext.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity{

    private static final String BUNDLE_RESULT = "RESULT";

    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        init();
    }

    public Intent displayIntent(@NonNull String result) {
        Intent intent = new Intent(getApplicationContext(), DisplayMessageActivity.class);
        intent.putExtra(BUNDLE_RESULT, result);
        return intent;
    }

    private void init() {
        Answers.getInstance().logCustom(new CustomEvent("Opened App"));
        hideActionBar();
        setListeners();
        logUser();
        initializeLoadAd();
    }

    private void logUser() {
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        Crashlytics.setUserIdentifier(android_id);
    }

    private void sendClickEvent() {
        // Crashlytics
        Answers.getInstance().logCustom(new CustomEvent("Pressed Vaporize Button"));

        // Firebase
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Opened App");
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle);
    }

    private void hideActionBar() {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }
    }

    private void setListeners() {
        binding.buttonVaporize.setOnClickListener(view -> {
            viewModel.createResultString(binding.edittext.getText().toString(), binding.checkboxCaps.isChecked());
            sendClickEvent();
        });

        viewModel.getResultString().observe(this, result -> {
            if(result != null) {
                Intent intent = displayIntent(result);
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Enter Some Text", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void initializeLoadAd() {
        MobileAds.initialize(this, Constants.AD_KEY);
        binding.adview.loadAd(new AdRequest.Builder().build());
    }

}
