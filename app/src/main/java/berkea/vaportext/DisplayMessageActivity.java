package berkea.vaportext;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class DisplayMessageActivity extends AppCompatActivity implements View.OnClickListener {

    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        getSupportActionBar().setElevation(0);
        final String result = getIntent().getStringExtra("result");

        TextView mTextView = (TextView) findViewById(R.id.textNo);
        mTextView.setText(result);
        mTextView.setMovementMethod(new ScrollingMovementMethod());

        Button mCopyButton = (Button) findViewById(R.id.copyButton);
        Button mShareButton = (Button) findViewById(R.id.shareButton);
        Button mBackButton = (Button) findViewById(R.id.backButton);

        mCopyButton.setOnClickListener(this);
        mShareButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);

        AdView mAdView = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void copyToClipboard(String copyText) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager)
                    getSystemService(Context.CLIPBOARD_SERVICE);
            if(clipboard != null) {
                clipboard.setText(copyText);
            }
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                    getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData
                    .newPlainText("Your text", copyText);
            if(clipboard != null) {
                clipboard.setPrimaryClip(clip);
            }
        }

        Toast toast = Toast.makeText(getApplicationContext(),
                "Text is copied", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 50, 50);
        toast.show();
    }

    public void shareText(String text) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(share, "Share using"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.copyButton:
                result = getIntent().getStringExtra("result");
                copyToClipboard(result);
                break;

            case R.id.shareButton:
                shareText(result);
                break;

            case R.id.backButton:
                Intent a = new Intent(getApplicationContext(), MainActivity.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(a);
                break;
        }
    }
}
