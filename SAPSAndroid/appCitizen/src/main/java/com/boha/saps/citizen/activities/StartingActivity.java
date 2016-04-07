package com.boha.saps.citizen.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.boha.saps.citizen.R;
import com.boha.sapslibrary.util.SharedUtil;

public class StartingActivity extends Activity {

    Button btnRegister, btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(getApplicationContext(),LoginActivity.class);
                m.putExtra("type",LoginActivity.REGISTER);
                startActivity(m);
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(getApplicationContext(),LoginActivity.class);
                m.putExtra("type",LoginActivity.SIGN_IN);
                startActivity(m);
            }
        });
        checkVirgin();
    }

    private void checkVirgin() {
        if (SharedUtil.getCitizen(getApplicationContext()) != null) {
            Intent m = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(m);
        }

     }

}
