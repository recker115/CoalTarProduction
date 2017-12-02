package com.example.user.coaltarproduction;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.facebook.FacebookSdk;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Map;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    EditText uSername;
    EditText pAssword;
    Button login,signup;
    Animation animation;
    Animation animation2,animation3;
    LinearLayout linearLayout;
    ImageView imageView,facebooklogin;
    EditText username,getpAssword;
    TextView textView;
    TextInputLayout textInputLayout,textInputLayout2;
    TextView sign;
    ImageView googlelogin;
    GoogleApiClient googleapiclient;
    GoogleSignInOptions signinoptions;
    int req_code=1;
    String Password;
    Firebase root;
    Dialog dialog;
    CallbackManager callbackManager;
    public static int APP_REQUEST_CODE = 99;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uSername=(EditText) findViewById(R.id.username);
        pAssword=(EditText) findViewById(R.id.password);
        textInputLayout=(TextInputLayout) findViewById(R.id.inplayout);
        textInputLayout2=(TextInputLayout) findViewById(R.id.inplayout2);
        sign=(TextView) findViewById(R.id.signupby);
        signup=(Button) findViewById(R.id.signup1);
        login=(Button) findViewById(R.id.login1);
        dialog=new Dialog(this);
        dialog.setContentView(R.layout.progress);
        callbackManager=CallbackManager.Factory.create();
        root.setAndroidContext(this);
        facebooklogin=(ImageView) findViewById(R.id.facebookLogin);
        sharedPreferences=getSharedPreferences("LoginVer",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        googlelogin=(ImageView) findViewById(R.id.googleLogin);
        signinoptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleapiclient=new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signinoptions).build();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (uSername.getText().toString().length()!=10) {
                    uSername.requestFocus();
                    uSername.setError("Enter a valid mobileno");
                }
                else if (pAssword.getText().toString().equals("")) {
                    pAssword.requestFocus();
                    Toast.makeText(Login.this, "Enter your password", Toast.LENGTH_SHORT).show();
                }
                else {
                        dialog.show();
                    root=new Firebase("https://orderfoodfirebase.firebaseio.com/");
                    root.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.e("aaa","aaa");
                            if ((dataSnapshot.hasChild(uSername.getText().toString())) && ! (uSername.getText().toString().matches("")))
                            {
                                dialog.hide();
                                Log.e("aaa","has a child");
                                Log.e("text inputted username",username.getText().toString());
                                Firebase a=root.child(username.getText().toString());
                                a.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        Map<String, String> map = dataSnapshot.getValue(Map.class);
                                        Password = String.valueOf(map.get("Password"));
                                        Log.e("Database-Password",Password);
                                        Log.e("text inputted Password",pAssword.getText().toString());
                                        if (pAssword.getText().toString().matches(Password))
                                        {
                                            editor.putString("Mobile",uSername.getText().toString());
                                            editor.apply();
                                            Intent intent=new Intent(Login.this,HomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else
                                            pAssword.setError("Password does not match");


                                    }


                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {


                                    }
                                });
                            }
                            else {
                                dialog.hide();
                                uSername.setError("Enter valid username");
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });


                }


            }
        });
        facebooklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Click","Click");
                LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("public_profile","email"));

                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e("Success","Success");
                        GraphRequest graphRequest=GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),new GraphRequest.GraphJSONObjectCallback()
                        {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.e("response",response.toString());
                                Log.e("email","email="+object.optString("email"));
                                Log.e("name","name="+object.optString("name"));

                                //Log.e("name","name="+object.optString("data"));
                                try {
                                    String profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                    Log.e("name","name="+profilePicUrl);
                                    editor.putString("name",object.optString("name"));
                                    editor.putString("photo",profilePicUrl);
                                    editor.apply();
                                    startActivity(new Intent(Login.this,HomeActivity.class));
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,link,email,cover,picture.type(large)");
                        graphRequest.setParameters(parameters);
                        graphRequest.executeAsync();




                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(Login.this,"Facebook Login cancelled by you",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(Login.this,"Could not connect to facebook",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        animation= AnimationUtils.loadAnimation(this,R.anim.fadein);
        animation2=AnimationUtils.loadAnimation(this,R.anim.fadein2);
        animation3=AnimationUtils.loadAnimation(this,R.anim.fadein3);
        linearLayout=(LinearLayout)findViewById(R.id.linearlayout2);
        imageView=(ImageView) findViewById(R.id.camcorder1_img);
        username=(EditText)findViewById(R.id.username);
        getpAssword=(EditText)findViewById(R.id.password);
        textView=(TextView)findViewById(R.id.text);
        Animation myAnim = AnimationUtils.loadAnimation(this,R.anim.blink);
        sign.startAnimation(myAnim);
        textInputLayout.startAnimation(animation);
        textInputLayout2.startAnimation(animation);
        linearLayout.startAnimation(animation2);
        textView.startAnimation(animation3);

        imageView.startAnimation(animation);
        googlelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googlesignup1();

            }
        });

    }

    private void googlesignup1() {

        Intent intent=Auth.GoogleSignInApi.getSignInIntent(googleapiclient);
        startActivityForResult(intent,req_code);


    }

    public void signUp(View view) {
        Intent intent=new Intent(Login.this,SignUp.class);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==req_code)
        {
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handle(result);
        }


            try {
                callbackManager.onActivityResult(requestCode, resultCode, data);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    private void handle(GoogleSignInResult result) {
        GoogleSignInAccount acount=result.getSignInAccount();
        /*String name=acount.getDisplayName().toString();
        String imgage_url=acount.getPhotoUrl().toString();
        String email=acount.getEmail().toString();*/
        if (result.isSuccess())
        {
            Intent intent=new Intent(Login.this,HomeActivity.class);
            startActivity(intent);
            finish();

        }

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    void diall()
    {
        Thread this1=new Thread(){

            @Override
            public void run() {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        this1.start();
    }
}
