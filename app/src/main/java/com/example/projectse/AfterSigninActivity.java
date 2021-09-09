package com.example.projectse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.ComponentActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;



public class AfterSigninActivity extends AppCompatActivity {

    Button singleModeSwitchScr, multiModeSwitchScr, quitBtn;
    ImageButton avatarBtn, displayNameBtn;
    String signedInPLayerUsername = "",signedUpPLayerUsername = "", path = "", avtShow;
    ImageView userAvt;
    TextView userDisplayName;
    SharedPreferences sharedPreferences;
    FirebaseFirestore usersDB;
    DocumentReference ref;
    private static final String TAG = "AfterSigninActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_after_signin);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        singleModeSwitchScr = findViewById(R.id.singlemode_AfterSigninActivity);
        multiModeSwitchScr = findViewById(R.id.multimode_AfterSigninActivity);
        quitBtn = findViewById(R.id.quit_AfterSigninActivity);

        avatarBtn = findViewById(R.id.avatarBtn_AfterSigninActivity);
        displayNameBtn = findViewById(R.id.displayNameBtn_AfterSigninActivity);
        userAvt = findViewById(R.id.avatar_AfterSigninActivity);
        userDisplayName = findViewById(R.id.displayName_AfterSigninActivity);

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
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        singleModeSwitchScr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfterSigninActivity.this, SingleMode.class);
                startActivity(intent);
                finish();
            }
        });

        multiModeSwitchScr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfterSigninActivity.this, WaitingRoom.class);
                startActivity(intent);
            }
        });

        quitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });

        avatarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfterSigninActivity.this, ChangeAvatar.class);
                startActivityForResult(intent, 1);
            }
        });
        displayNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfterSigninActivity.this, ChangeDisplayName.class);
                startActivityForResult(intent, 2);
            }
        });

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
                    display = "Hello, " + value;
                    userDisplayName.setText(display);
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

