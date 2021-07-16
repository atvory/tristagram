package com.example.tristagram.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.tristagram.R;
import com.example.tristagram.pojo.Users;
import static com.example.tristagram.connections.HttpUrlConnections.checkJSONlogin;
import static com.example.tristagram.connections.HttpUrlConnections.sendDataGETlogin;

public class Login extends AppCompatActivity {
    Button btnEntrar;
    Button btnSignIn;
    EditText txtEmail;
    EditText txtPassword;
    ImageView imageView;
    private static final String IP_SERVIDOR = "192.168.100.30";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnEntrar = (Button) findViewById(R.id.btnLogin);
        btnSignIn = (Button) findViewById(R.id.btnSignUp);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPass);

        FrameLayout frameLayout = findViewById(R.id.frameGradiente);
        AnimationDrawable animationDrawable = (AnimationDrawable) frameLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        //abre actividad PerfilPpal
        btnEntrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Thread tr = new Thread(){
                    @Override
                    public void run() {
                        String mail,pass;
                        mail = txtEmail.getText().toString();
                        pass = txtPassword.getText().toString();
                        Users userPass = new Users(mail,pass);
                        String passHash = String.valueOf(userPass.hashCode());
                        final String answer = sendDataGETlogin(mail,passHash);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final Users user = checkJSONlogin(answer);
                                if(user!=null){
                                    Intent intentLogIn = new Intent(Login.this, Principal.class);
                                    String idString = (user.getId()).toString();
                                    intentLogIn.putExtra("id",idString);
                                    intentLogIn.putExtra("user",user.getUser());
                                    intentLogIn.putExtra("profile",user.getProfileImage());
                                    startActivity(intentLogIn);
                                }else{
                                    Toast toast = Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrectas.",Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            }
                        });
                    }
                };
                tr.start();
            }
        });

        //abre actividad SignUp
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSU = new Intent(Login.this, Signup.class);
                startActivity(intentSU);
            }
        });
    }


}