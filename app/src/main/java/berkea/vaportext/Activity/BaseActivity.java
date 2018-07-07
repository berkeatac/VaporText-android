package berkea.vaportext.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import berkea.vaportext.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void handleActionBar(boolean homeEnabled) {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeEnabled);
    }

}
