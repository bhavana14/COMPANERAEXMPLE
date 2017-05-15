package trainedge.companera;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import static trainedge.companera.MapsActivity.KEY_ADDRESS;
import static trainedge.companera.MapsActivity.KEY_LAT;
import static trainedge.companera.MapsActivity.KEY_LNG;

public class CreationActivity extends AppCompatActivity {

    private String selectedAddress;
    private double lat;
    private double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        if (extras==null){
            Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show();
        }
        selectedAddress = extras.getString(KEY_ADDRESS);
        lat = extras.getDouble(KEY_LAT);
        lng = extras.getDouble(KEY_LNG);

        //update UI
    }

}
