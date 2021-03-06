package com.example.tristagram.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tristagram.R;
import com.example.tristagram.pojo.Users;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.example.tristagram.connections.HttpUrlConnections.checkJSONSignUp;
import static com.example.tristagram.connections.HttpUrlConnections.insertUserHttpUrlSignUp;
import static com.example.tristagram.connections.HttpUrlConnections.sendDataGETmailSignUp;
import static com.example.tristagram.connections.HttpUrlConnections.sendDataGETuserSignUp;

public class Signup extends AppCompatActivity{
    private Button btnTerms;
    private Button btnSend;
    private EditText txtUser;
    private EditText txtEmail;
    private EditText txtEmail2;
    private EditText txtPassword;
    private EditText txtPassword2;
    private CheckBox cbTnC;
    private TextView txtAlert;
    private Context context;
    private static final Pattern COMPROBAR_EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        txtUser = (EditText) findViewById(R.id.txtUserSU);
        txtEmail = (EditText) findViewById(R.id.txtEmailSU);
        txtEmail2 = (EditText) findViewById(R.id.txtEmailSU2);
        txtPassword = (EditText) findViewById(R.id.txtPassSU);
        txtPassword2 = (EditText) findViewById(R.id.txtPassSU2);
        cbTnC = (CheckBox) findViewById(R.id.cbTerms);
        txtAlert = (TextView) findViewById(R.id.txtWarningSU);
        btnTerms = (Button) findViewById(R.id.btnTnC);
        btnSend = (Button) findViewById(R.id.btnSignUpEnviar);

        txtAlert.setVisibility(View.INVISIBLE);
        btnSend.setEnabled(false);

        btnTerms.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                AlertDialog.Builder ventana = new AlertDialog.Builder(Signup.this);
                ventana.setTitle("T??rminos de uso y servicio");
                ventana.setMessage("Vamos a coger tus datos y los vamos" + "\n" +
                                    "a vender, estate tranquilo.");
                ventana.setCancelable(true);
                ventana.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog tNc = ventana.create();
                tNc.show();
            }
        });

        cbTnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btnSend.isEnabled()!=true){
                    btnSend.setEnabled(true);
                }
                else{
                    btnSend.setEnabled(false);
                }

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    txtAlert.setVisibility(View.INVISIBLE);
                    txtUser.setTextColor(Color.BLACK);
                    txtPassword.setTextColor(Color.BLACK);
                    txtPassword2.setTextColor(Color.BLACK);
                    txtEmail.setTextColor(Color.BLACK);
                    txtEmail2.setTextColor(Color.BLACK);
                    cbTnC.setChecked(false);
                    btnSend.setEnabled(false);

                    String user, email1, email2, password1, password2;

                    user = txtUser.getText().toString();
                    email1 = txtEmail.getText().toString();
                    email2 = txtEmail2.getText().toString();


                    if (checkUserOK(user) != true) {
                        if (email1.equals(email2)) {
                            if (checkMailFormat(email1) != false) {
                                password1 = txtPassword.getText().toString();
                                password2 = txtPassword2.getText().toString();
                                if (password1.equals(password2)) {
                                    Thread tr1 = new Thread() {
                                        @Override
                                        public void run() {
                                            String user = txtUser.getText().toString();
                                            final String answer = sendDataGETuserSignUp(user);
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    final boolean exists = checkJSONSignUp(answer);
                                                    if (exists != true) {

                                                        Thread tr2 = new Thread() {
                                                            @Override
                                                            public void run() {
                                                                String user,mail, pass;
                                                                user = txtUser.getText().toString();
                                                                mail = txtEmail.getText().toString();
                                                                pass = txtPassword.getText().toString();
                                                                Users userPass = new Users(mail,pass); // <---- creo usuario usando el constructor de contrasena y el mail
                                                                String passHash = String.valueOf(userPass.hashCode()); // <--- cojo el hash del usuario y lo casteo a string para pasarselo a la BD
                                                                final String answer = sendDataGETmailSignUp(mail);
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        final boolean exists = checkJSONSignUp(answer);
                                                                        if (exists != true) {
                                                                            context = getApplicationContext();  // modifique entrada de parametros para contexto igual no va.
                                                                            insertUserHttpUrlSignUp(context, user,passHash,mail); // <--------- inserta usuario, la pass es el hashcode, solo el user sabria la pass
                                                                        } else {
                                                                            Toast.makeText(getApplicationContext(),"Este email ya est?? registrado.",Toast.LENGTH_LONG).show();
                                                                            txtEmail.setTextColor(Color.RED);
                                                                            txtEmail2.setTextColor(Color.RED);
                                                                            txtAlert.setVisibility(View.VISIBLE);
                                                                        }
                                                                    }
                                                                });
                                                            }
                                                        };
                                                        tr2.start();

                                                    } else {
                                                        Toast.makeText(getApplicationContext(),"El usuario no est?? disponible.",Toast.LENGTH_LONG).show();
                                                        txtUser.setTextColor(Color.RED);
                                                        txtAlert.setVisibility(View.VISIBLE);
                                                    }
                                                }
                                            });
                                        }
                                    };
                                    tr1.start();

                                } else {
                                    Toast.makeText(getApplicationContext(),"Las contrase??as no coinciden.",Toast.LENGTH_LONG).show();
                                    txtPassword.setTextColor(Color.RED);
                                    txtPassword2.setTextColor(Color.RED);
                                    txtAlert.setVisibility(View.VISIBLE);
                                }

                            } else {
                                Toast.makeText(getApplicationContext(),"El formato del email no es v??lido.",Toast.LENGTH_LONG).show();
                                txtEmail.setTextColor(Color.RED);
                                txtEmail2.setTextColor(Color.RED);
                                txtAlert.setVisibility(View.VISIBLE);
                            }

                        } else {
                            Toast.makeText(getApplicationContext(),"Los mail no coinciden.",Toast.LENGTH_LONG).show();
                            txtEmail.setTextColor(Color.RED);
                            txtEmail2.setTextColor(Color.RED);
                            txtAlert.setVisibility(View.VISIBLE);
                        }
                    }else{
                        txtAlert.setVisibility(View.VISIBLE);
                        txtUser.setTextColor(Color.RED);
                    }
                }catch(Exception e){}
            }
        });

    }



    public static boolean checkMailFormat(String mail) {
        Matcher matcher = COMPROBAR_EMAIL_REGEX.matcher(mail);
        return matcher.find();
    }

    /**
     * comprueba que el user no tenga palabras prohibidas y llama a metodo para comprobar caracteres
     * especiales
     * @param user
     * @return
     */
    public boolean checkUserOK(String user){
        String rango = "([a-zA-Z0-9??-????-????-??])*";
        try {
            DataInputStream listaTxt = new DataInputStream(getAssets().open(String.format("prohibidas.txt")));
            Scanner lector = new Scanner(listaTxt);
            while (lector.hasNextLine()) {
                String line = lector.nextLine();
                if(user.matches(rango+line+rango)) {
                    Toast.makeText(getApplicationContext(), "El nombre de usuario contiene una palabra prohibida.", Toast.LENGTH_LONG).show();
                    return true;
                }else {
                    if(Character.isDigit(user.charAt(0))) {
                        Toast.makeText(getApplicationContext(), "El nombre de usuario no puede empezar con un n??mero.", Toast.LENGTH_LONG).show();
                        return true;
                    }else {
                        if(checkSpecials(user)!=false) {
                            Toast.makeText(getApplicationContext(), "El nombre de usuario no puede contener caracteres especiales.", Toast.LENGTH_LONG).show();
                            return true;
                        }
                    }
                }
            }
        }catch(IOException e) {}
        return false;
    }

    /**
     * comprueba si un string contiene caracteres raros, a??adir al string los que se quiera matchear
     * @param user
     * @return
     */
    public static boolean checkSpecials(String user) {
        String rangoSpecials = "([@#~|!??$%&//())=???])";
        if(user.length()!=0) {
            for(int n =0 ;n<user.length();n++) {
                String caracter = String.valueOf(user.charAt(n));
                if(caracter.matches(rangoSpecials)) {
                    return true;
                }
            }
        }
        return false;
    }

}