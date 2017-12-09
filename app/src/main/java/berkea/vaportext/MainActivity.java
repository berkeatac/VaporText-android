package berkea.vaportext;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    Button button;
    private AdView mAdView;
    CheckBox checkBox;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);
        MobileAds.initialize(getApplicationContext(),"ca-app-pub-7995520615225219~4866392143");


        checkBox = (CheckBox) findViewById(R.id.checkBox);
        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.text);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString() != ""){

                    Intent intent = displayIntent();
                    startActivity(intent);
                }
            }
        });

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    public String toVaporText(String message, Boolean caps){
        int len = message.length();
        if(caps){
            message = message.toUpperCase();
        }

        String result = "";
        for(int i=0; i < len; i++){
            char ch = message.charAt(i);
            result = result + ch + " ";
        }
        return result;

    }

    public Intent displayIntent(){
        Intent intent = new Intent(getApplicationContext(), DisplayMessageActivity.class);
        String message = editText.getText().toString();
        Boolean caps = checkBox.isChecked();
        String result = toVaporText(message, caps);

        intent.putExtra("result", result);
        return intent;
    }

    @Override
    protected void onPause() {
        mAdView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mAdView!=null){  // Check if Adview is not null in case of fist time load.
            mAdView.resume();}
    }
}
