package com.boha.saps.citizen.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.boha.saps.citizen.R;
import com.boha.sapslibrary.util.DataUtil;
import com.boha.sapslibrary.util.GCMUtil;
import com.boha.sapslibrary.util.OKUtil;
import com.boha.sapslibrary.util.SharedUtil;
import com.boha.vodacom.dto.CitizenDTO;
import com.boha.vodacom.dto.GcmDeviceDTO;
import com.boha.vodacom.dto.ResponseDTO;

public class LoginActivity extends Activity {

    EditText eName, eMail, ePassword, eCellphone;
    RadioButton radioFemale, radioMale;
    Button btnSignIn, btnRegister;

    int type;
    public static final int REGISTER = 1, SIGN_IN = 2;
    public static final String LOG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        type = getIntent().getIntExtra("type", REGISTER);
        setFields();
        checkVirgin();
    }

    private void checkVirgin() {
        CitizenDTO m = SharedUtil.getCitizen(getApplicationContext());
        if (m != null) {
            startMain();
            return;
        }
        registerDevice();

    }
    private void registerDevice() {
        GCMUtil.startGCMRegistration(getApplicationContext(), new GCMUtil.GCMUtilListener() {
            @Override
            public void onDeviceRegistered(String id) {
                Log.w(LOG,"GCM Device ready to go with user data!!!");
            }

            @Override
            public void onGCMError() {

            }
        });
    }
    private void setFields() {

        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        eName = (EditText) findViewById(R.id.SI_name);
        eMail = (EditText) findViewById(R.id.SI_editEmail);
        ePassword = (EditText) findViewById(R.id.SI_password);
        eCellphone = (EditText) findViewById(R.id.SI_cellphone);
        radioFemale = (RadioButton) findViewById(R.id.radioFemale);
        radioMale = (RadioButton) findViewById(R.id.radioMale);
        if (type == SIGN_IN) {
            btnRegister.setVisibility(View.GONE);
            btnSignIn.setVisibility(View.VISIBLE);
            eName.setVisibility(View.GONE);
            eCellphone.setVisibility(View.GONE);
        } else {
            btnRegister.setVisibility(View.VISIBLE);
            btnSignIn.setVisibility(View.GONE);
        }
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendRegistration();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendSignIn();
            }
        });
    }

    private void startMain() {
        Intent m = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(m);
    }
    private void sendRegistration() {
        if( eMail.getText().toString().length() == 0 ) {
            eMail.setError("Email address is required!");
            return;
        }
        if( ePassword.getText().toString().length() < 5 ) {
            ePassword.setError("Password should be at least 5 characters!");
            return;
        }
        CitizenDTO m = new CitizenDTO();
        m.setEmail(eMail.getText().toString());
        m.setPassword(ePassword.getText().toString());
        if (eName.getText().toString().isEmpty()) {
            m.setName("Anonymous");
        } else {
            m.setName(eName.getText().toString());
        }
        if (!eCellphone.getText().toString().isEmpty()) {
            m.setCellphone(eCellphone.getText().toString());
        }
        if (radioFemale.isChecked()) {
            m.setGender(2);
        }
        if (radioMale.isChecked()) {
            m.setGender(1);
        }

        GcmDeviceDTO g = SharedUtil.getGCMDevice(getApplicationContext());
        if (g != null) {
            m.getDeviceList().add(g);
        }
        DataUtil.registerCitizen(getApplicationContext(), m, new OKUtil.OKListener() {
            @Override
            public void onResponse(ResponseDTO response) {
                SharedUtil.saveCitizen(getApplicationContext(),response.getCitizens().get(0));
                SharedUtil.savePanicTypes(getApplicationContext(),response.getPanicTypes());
                startMain();
            }

            @Override
            public void onError(String message) {

            }
        });
    }
    private void sendSignIn() {
        if( eMail.getText().toString().length() == 0 ) {
            eMail.setError("Email address is required!");
            return;
        }
        if( ePassword.getText().toString().length() < 4 ) {
            ePassword.setError("Password should be at least 4 characters!");
            return;
        }


        GcmDeviceDTO g = SharedUtil.getGCMDevice(getApplicationContext());

        DataUtil.signinCitizen(getApplicationContext(), eMail.getText().toString(), ePassword.getText().toString(), g,new OKUtil.OKListener() {
            @Override
            public void onResponse(ResponseDTO response) {
                SharedUtil.saveCitizen(getApplicationContext(),response.getCitizens().get(0));
                SharedUtil.savePanicTypes(getApplicationContext(),response.getPanicTypes());
                startMain();
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
