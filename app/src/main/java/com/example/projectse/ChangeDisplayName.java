package com.example.projectse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeDisplayName extends Activity implements View.OnClickListener {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText et_dname;
    Button bt_change, bt_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_display_name);
        initView();
        bt_change.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
    }

    private void initView() {
        et_dname = findViewById(R.id.et_dname);
        bt_change = findViewById(R.id.bt_change);
        bt_cancel = findViewById(R.id.bt_cancel);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        et_dname.setHint(sharedPreferences.getString("dname", "Display name"));
    }

    @Override
    public void onClick(View view) {
        if (view == bt_change) { //press change
            if (TextUtils.isEmpty(et_dname.getText().toString())) { //invalid name
                Toast.makeText(ChangeDisplayName.this, "Invalid name", Toast.LENGTH_SHORT).show();
            } else {//valid name
                //save data
                //send data back to AfterSigninActivity
                Intent intent = new Intent();
                intent.putExtra("displayName", et_dname.getText().toString());
                setResult(RESULT_OK, intent);

                Toast.makeText(ChangeDisplayName.this, "Your display name is changed", Toast.LENGTH_SHORT).show();
                editor.putString("dname", et_dname.getText().toString());
                editor.commit();
                //finish activity
                finish();
            }
        }
        if (view == bt_cancel) { //press cancel
            //finish activity
            finish();
        }
    }

//    private boolean checkName(Editable text) {
//        if (text.equals("")) return false;
//        else return true;
//    }
}