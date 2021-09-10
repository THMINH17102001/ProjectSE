package com.example.projectse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SignedIn extends Activity implements View.OnClickListener{
    ImageButton ib_signout, ib_changename, ib_changeavt, ib_help, ib_rank, ib_color, ib_history, ib_search;
    Button bt_singlemode, bt_multimode, bt_quit;
    TextView tv_displayname;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String uname;

    ImageView userAvt;
    FirebaseFirestore usersDB;
    DocumentReference ref;
    String path = "", avtShow;
    private static final String TAG = "AfterSigninActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in);

        userAvt = findViewById(R.id.avt_SignedIn);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        path= sharedPreferences.getString("uname", "");
        usersDB = FirebaseFirestore.getInstance();
        ref = usersDB.collection("users").document(path);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        avtShow = "avt" + document.getString("avt");
                        int resId = getResources().getIdentifier(avtShow, "drawable", getPackageName());
                        userAvt.setImageResource(resId);

                        String displayNameDB = document.getString("displayName");
                        String finalDisplayName;
                        if(!TextUtils.isEmpty(displayNameDB))
                        {
                            finalDisplayName = "Hi! " + displayNameDB;
                            tv_displayname.setText(finalDisplayName);
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        initView();
        setAllListen();


        }

    private void setAllListen() {
        ib_signout.setOnClickListener(this);
        ib_changename.setOnClickListener(this);
        ib_changeavt.setOnClickListener(this);
        bt_singlemode.setOnClickListener(this);
        bt_multimode.setOnClickListener(this);
        bt_quit.setOnClickListener(this);
    }

    private void initView() {
        ib_signout=(ImageButton) findViewById(R.id.ib_signout);
        ib_changeavt=(ImageButton) findViewById(R.id.ib_changeavt);
        ib_changename=(ImageButton) findViewById(R.id.ib_changename);
        bt_singlemode=(Button) findViewById(R.id.bt_singlemode);
        bt_multimode=(Button) findViewById(R.id.bt_multimode);
        bt_quit=(Button) findViewById(R.id.bt_quit);
        tv_displayname=(TextView) findViewById(R.id.tv_displayname);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        editor=sharedPreferences.edit();
        uname=sharedPreferences.getString("uname", "error!");
    }

    private String getDisplayname(String uname) {
        return "displayname";
    }

    @Override
    public void onClick(View view) {
        if(view==ib_signout) {
            editor.putBoolean("Signed", false);
            editor.commit();
            Intent intent = new Intent(SignedIn.this, MainActivity.class);
            startActivity(intent);
        }
        if(view==bt_singlemode) {
            Intent intent = new Intent(SignedIn.this, SingleMode.class);
            startActivity(intent);
        }
        if(view==bt_multimode) {
            Intent intent = new Intent(SignedIn.this, WaitingRoom.class);
            startActivity(intent);
        }

        if(view==ib_changeavt) {
            Intent intent = new Intent(SignedIn.this, ChangeAvatar.class);
            startActivityForResult(intent, 1);
        }

        if(view==ib_changename) {
            Intent intent = new Intent(SignedIn.this, ChangeDisplayName.class);
            startActivityForResult(intent, 2);
        }
            if(view==bt_quit) {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                int value = data.getIntExtra("avatarChoice", 0);
                String avt = "avt" + Integer.toString(value);
                int resId = getResources().getIdentifier(avt, "drawable", getPackageName());
                userAvt.setImageResource(resId);
                ref = usersDB.collection("users").document(path);
                ref
                        .update("avt", Integer.toString(value))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Avatar is updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating document", e);
                            }
                        });

            }
        }
        if(requestCode == 2)
        {
            if(resultCode == RESULT_OK)
            {
                String value = data.getStringExtra("displayName");
                String display = "";
                if(!TextUtils.isEmpty(value))
                {
                    display = "Hi! " + value;
                    tv_displayname.setText(display);
                }
                ref = usersDB.collection("users").document(path);
                ref
                        .update("displayName", value)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Display name is updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating Display name", e);
                            }
                        });

            }

        }
    }

}
