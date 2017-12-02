package com.example.user.coaltarproduction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.CountryCodeSpinner;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.accountkit.ui.SkinManager;
import com.facebook.accountkit.ui.UIManager;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.collection.LLRBNode;
import com.hbb20.CountryCodePicker;

public class SignUp extends AppCompatActivity {
    LinearLayout linearLayout;
    Firebase root;
    Button button;
    EditText name,password,mailid,mobileno;
    final  static int APP_REQUEST_CODE=21;
    CountryCodePicker codePicker;
    private SkinManager.Skin skin=SkinManager.Skin.TRANSLUCENT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        root.setAndroidContext(this);
        Animation myanim= AnimationUtils.loadAnimation(this,R.anim.fadein);
          linearLayout=(LinearLayout) findViewById(R.id.linear);
        linearLayout.startAnimation(myanim);
        name=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        mailid=(EditText) findViewById(R.id.email);
        mobileno=(EditText) findViewById(R.id.Mobile_No);
        codePicker=(CountryCodePicker) findViewById(R.id.countryspinner);
        //countrycodes=(CountryCodeSpinner) findViewById(R.id.countryspinner);

        root=new Firebase("https://orderfoodfirebase.firebaseio.com/");


        button=(Button) findViewById(R.id.signup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("")) {
                    name.requestFocus();
                    name.setError("Enter a valid name");
                }
                else if (password.getText().toString().equals("")) {
                    password.requestFocus();
                    password.setError("Enter a valid password");
                }else if (mobileno.getText().toString().equals("")) {
                    mobileno.requestFocus();
                    mobileno.setError("Enter a valid mobileno");
                }
                else if (!(android.util.Patterns.EMAIL_ADDRESS.matcher(mailid.getText().toString()).matches())) {
                    mailid.requestFocus();
                    mailid.setError("Enter a valid email");
                }
                else {


                    phoneLogin();




                }
            }
        });

    }

    private void phoneLogin() {
        UIManager uiManager;
        uiManager = new SkinManager(
                SkinManager.Skin.TRANSLUCENT,
                Color.TRANSPARENT,
                R.drawable.login1_img,
                SkinManager.Tint.BLACK,
                0);


        final Intent intent = new Intent(getApplicationContext(), AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.CODE); // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        configurationBuilder.setInitialPhoneNumber(new PhoneNumber(codePicker.getSelectedCountryCode(),mobileno.getText().toString()));
        configurationBuilder.setUIManager(uiManager);
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        Log.e("start","start");
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) {
            Log.e("Sucess","success");// confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();

            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
            } else {
                Log.e("success","success");
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                } else {
                    toastMessage = String.format(
                            "Success:%s...",
                            loginResult.getAuthorizationCode().substring(0,10));
                }
                root.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!(dataSnapshot.hasChild(mobileno.getText().toString()))) {

                            Firebase primarykey=root.child(mobileno.getText().toString());
                            Firebase password1=   primarykey.child("Password");
                            password1.setValue(password.getText().toString());
                            Intent intent=new Intent(SignUp.this,HomeActivity.class);
                            startActivity(intent);
                            finish();


                        }
                        else {
                            Toast.makeText(SignUp.this, "Mobileno already used up,Please login ", Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(SignUp.this,Login.class);
                            startActivity(intent);
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                // If you have an authorization code, retrieve it from
                // loginResult.getAuthorizationCode()
                // and pass it to your server and exchange it for an access token.

                // Success! Start your next activity...

            }

            // Surface the result to your user in an appropriate way.
           /* Toast.makeText(
                    this,
                    toastMessage,
                    Toast.LENGTH_LONG)
                    .show();*/
        }

    }
}
