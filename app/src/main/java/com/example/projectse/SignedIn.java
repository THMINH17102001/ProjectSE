package com.example.projectse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class SignedIn extends Activity implements View.OnClickListener{
    ImageButton ib_signout, ib_changename, ib_changeavt, ib_help, ib_rank, ib_color, ib_history, ib_search;
    Button bt_singlemode, bt_multimode, bt_quit;
    TextView tv_displayname;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in);
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
        tv_displayname.setText(getDisplayname(uname));
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
        if(view==bt_quit) {
            finish();
        }
    }
}
