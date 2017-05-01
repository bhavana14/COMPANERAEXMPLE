package trainedge.companera;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

public class Profile_Activity extends Activity {

    private ImageView imageView;
    private TextView name;
    private TextView email;
    private FirebaseAuth auth;
    private String username;
    private String email1;
    private boolean emailVerified;
    private String uid;
    private String providerId;
    private UserInfo profile;
    private Uri photoUrl;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);
        // create object
        imageView = (ImageView) findViewById(R.id.imageView);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null) {
            finish();


        }
        email.setText(user.getEmail());
        name.setText(user.getDisplayName());

        //Loading image from below url into imageView
        Picasso.with(this)
                .load(user.getPhotoUrl())
                .into(imageView);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    //user is signed in
                    // dialog.dismiss();
                    Intent detail = new Intent(Profile_Activity.this, Login_Activity.class);
                    startActivity(detail);
                    finish();
                    detail.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(detail);
                }

            }
        };
    }
}
