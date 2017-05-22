package trainedge.companera;

import android.content.Context;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;

import android.widget.Toast;

import com.firebase.geofire.GeoLocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import trainedge.companera.ProfileGeofenceNotification;


public class SoundProfileManager {

    private int count;
    Context context;
    private ProfileModel profileModel;

    public SoundProfileManager(Context context) {

        count = 0;
        this.context = context;
    }

    public void changeSoundProfile(final Context context, final String key, GeoLocation location) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference profilesRef = FirebaseDatabase.getInstance().getReference("profiles").child(uid).child(key);
        Toast.makeText(this.context, "key= " + key, Toast.LENGTH_SHORT).show();
        profilesRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                   /* String savedKey = context.getSharedPreferences("notifier_pref", Context.MODE_PRIVATE).getString("name", "");
                    boolean state = dataSnapshot.child("state").getValue(Boolean.class);
                    String date = dataSnapshot.child("date").getValue(String.class);

                    if (!date.equals("none")) {
                        try {
                           // Date dateVal = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                            Date dateVal=new SimpleDateFormat("dd-MM-yyyy").parse(date);
                            if (new Date().equals(dateVal)) {
                                if (!savedKey.equals(key)) {
                                    ProfileGeofenceNotification.notify(context, "sound profile updated", 0);
                                    dataSnapshot.getRef().child("state").setValue(true);
                                    profileModel = new ProfileModel(dataSnapshot);
                                    updateSoundProfile(profileModel);
                                    context.getSharedPreferences("notifier_pref", Context.MODE_PRIVATE).edit()
                                            .putString("name", key).apply();
                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    } else {
                        if (!savedKey.equals(key)) {
                            ProfileGeofenceNotification.notify(context, "sound profile updated", 0);
                            dataSnapshot.getRef().child("state").setValue(true);
                            profileModel = new ProfileModel(dataSnapshot);
                            updateSoundProfile(profileModel);
                            context.getSharedPreferences("notifier_pref", Context.MODE_PRIVATE).edit()
                                    .putString("name", key).apply();
                        }
                    }*/
                    boolean state = dataSnapshot.child("state").getValue(Boolean.class);
                    if (!state) {
                        ProfileGeofenceNotification.notify(context, "sound profile updated", 0);
                        dataSnapshot.getRef().child("state").setValue(true);
                        profileModel = new ProfileModel(dataSnapshot);
                        updateSoundProfile(profileModel);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SoundProfileManager.this.context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateSoundProfile(ProfileModel profileModel) {
        boolean state = profileModel.getState();
        boolean isSilent = profileModel.isSilent();
        boolean isVibrate = profileModel.isVibrate();
        String name = profileModel.getKey();
        final AudioManager profileMode = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if (isSilent) {
            profileMode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        } else if (isVibrate) {
            profileMode.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        } else {
            int volume = profileModel.getVolume();
            String msgtone = profileModel.getMsgtone();
            String ringtone = profileModel.getRingtone();
            profileMode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            try {
                RingtoneManager.setActualDefaultRingtoneUri(
                        context,
                        RingtoneManager.TYPE_RINGTONE,
                        Uri.parse(ringtone)
                );
                RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION, Uri.parse(msgtone));
            } catch (Exception e) {
                Toast.makeText(context, "permission to change ringtone not given, please upgrade ur phone", Toast.LENGTH_SHORT).show();
            }
            profileMode.setStreamVolume(AudioManager.STREAM_RING, volume, 0);
        }
        ProfileGeofenceNotification.notify(context, "Sound Profile loaded ->" + name, 0);


    }

    public void setToDefualt(String key) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference profilesRef = FirebaseDatabase.getInstance().getReference("profiles").child(uid).child(key);
     //   Toast.makeText(this.context, "key= " + key, Toast.LENGTH_SHORT).show();
        profilesRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {

                    dataSnapshot.getRef().child("state").setValue(false);
                    //updateSoundProfile(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SoundProfileManager.this.context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
