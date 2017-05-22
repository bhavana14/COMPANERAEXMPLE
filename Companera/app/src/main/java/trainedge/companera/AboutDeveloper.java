package trainedge.companera;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class AboutDeveloper extends AppCompatActivity implements View.OnClickListener {


    private TextView tvmailid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_developer);
       /* imgAbout = (ImageView) findViewById(R.id.imgAbout);
        appIcon = (ImageView) findViewById(R.id.img1);
        bhavana = (ImageView) findViewById(R.id.imgd1);
        RelativeLayout relativeLayout2=findViewById(R.id.relativeLayout2);

        Glide.with(this).load(R.drawable.myloc).into(imgAbout);
        Glide.with(this).load(R.drawable.logo).into(appIcon);
        Glide.with(this).load(R.drawable.my_pic).into(bhavana);

        tvDevelopers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (relativeLayout2.getVisibility() == View.GONE) {
                    relativeLayout2.setVisibility(View.VISIBLE);
                } else {
                    relativeLayout2.setVisibility(View.GONE);
                }

            }
        });*/
        tvmailid = (TextView) findViewById(R.id.tvmailid);
        tvmailid.setOnClickListener(this);
        tvmailid.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"companera14@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                i.putExtra(Intent.EXTRA_TEXT, "body of email");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                   // Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(AboutDeveloper.this, "Press Long to send Mail ...", Toast.LENGTH_SHORT).show();
    }
}
