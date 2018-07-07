package berkea.vaportext.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.google.android.gms.ads.AdRequest;
import java.util.Date;
import berkea.vaportext.Model;
import berkea.vaportext.R;
import berkea.vaportext.TextUtils;
import berkea.vaportext.ViewModel.DisplayMessageViewModel;
import berkea.vaportext.databinding.ActivityDisplayMessageBinding;

public class DisplayMessageActivity extends BaseActivity{

    private static final String BUNDLE_RESULT = "RESULT";

    @Nullable
    private String result;

    private ActivityDisplayMessageBinding binding;
    private DisplayMessageViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_display_message);
        viewModel = ViewModelProviders.of(this).get(DisplayMessageViewModel.class);

        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        binding.adview.loadAd(new AdRequest.Builder().build());
        handleActionBar(true);
        setResultText();
        setListeners();
    }

    private void setResultText() {
        result = getIntent().getStringExtra(BUNDLE_RESULT);
        binding.textviewVapor.setText(result);
        binding.textviewVapor.setMovementMethod(new ScrollingMovementMethod());
    }

    private void setListeners() {
        binding.buttonCopy.setOnClickListener(v -> {
            Model model = new Model(getApplicationContext(),new Date());
            TextUtils.copyToClipboard(result, model.getContext());
            Answers.getInstance().logCustom(new CustomEvent("Copied to Clipboard"));
        });

        binding.buttonShare.setOnClickListener(v -> {
            shareText(result);
            Answers.getInstance().logCustom(new CustomEvent("Shared Text"));
        });
    }

    public void shareText(String text) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(share, getString(R.string.share_using)));
    }
}
