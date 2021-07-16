package com.example.tristagram.connections;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.tristagram.pojo.Users;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class HttpUrlConnections {
    private static final String IP_SERVIDOR = "192.168.100.31";


    // hace falta editar este metodo para tener encapsulado en la clase.
    /*public void cargaFotosBD(){
        Thread tr =new Thread(){
            @Override
            public void run() {
                final String answer = sendDataGET(getIntent().getStringExtra("user"));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(answer);
                        ArrayList<String> a =checkJSON(answer);
                        //lista con las las URL a las fotos del perfil
                        for (String x : a){
                            System.out.println(x);
                        }
                    }
                });

            }
        };
        tr.start();
    }*/

    public ArrayList<String> checkJSON(String answer) {

        ArrayList<String> listaImages=new ArrayList<>();
        String pathImage="";
        try {
            JSONArray json = new JSONArray(answer);
            for (int i = 0; i < json.length(); i++) {
                pathImage = json.getJSONObject(i).getString("rutaImage");
                listaImages.add(pathImage);
            }

            return listaImages;
        } catch (Exception e) {
            return null;
        }
    }

    public String sendDataGET(String user){

        String linea = "";
        int answer = 0;
        StringBuilder result = null;
        try {
            URL url = new URL("http://"+IP_SERVIDOR+"/tristagramwebservice/source/prepareds/fotosPerfil.php?user="+user);  // <------- he puesto la ip de la constante.
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            answer = con.getResponseCode();
            result = new StringBuilder();
            System.out.println("Respuesta getResponseCode()"+answer);

            if(answer==200){
                InputStream is = new BufferedInputStream(con.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while((linea = br.readLine())!=null){
                    result.append(linea);
                }
                is.close();
                br.close();
            }
            con.disconnect();
        }catch(Exception e){}


        return result.toString();
    }


    public static String sendDataGETprofilephoto(String id){
        URL url = null;
        String linea = "";
        int answer = 0;
        StringBuilder result = null;
        try {
            String user= "javi";
            url = new URL("http://"+IP_SERVIDOR+"/tristagramwebservice/source/prepareds/returnpp.php?id="+id);
            //fotosPerfil.php?user="+user
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            answer = con.getResponseCode();

            result = new StringBuilder();

            if(answer==HttpURLConnection.HTTP_OK){
                InputStream is = new BufferedInputStream(con.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while((linea = br.readLine())!=null){
                    result.append(linea);
                }
                is.close();
                br.close();
            }
            con.disconnect();

            String resultString = result.toString();
            try{
                JSONArray json = new JSONArray(resultString);
                if(json.length()>0){
                    String profile = json.getJSONObject(0).getString("profile_image_path");

                    return profile;
                }
            }catch(Exception e){}
            return null;

        }catch(Exception e){}
        return null;
    }

    /**
     * reemplaza la foto perfil del usuario segun su id
     */
    public static void sendDataPUTprofile(String id,String imagePath){
        URL url = null;
        HttpURLConnection con = null;
        int answer = 0;
        try{
            url = new URL("http://"+IP_SERVIDOR+"/tristagramwebservice/source/prepareds/putprofile.php?id="+id+"&image="+imagePath);

        }catch(Exception e){}

    }

    public static void sendImage(String image){
        URL url = null;
        HttpURLConnection con = null;
        int answer = 0;
        try{
            String date = rLocalDateTime();
            url = new URL("http://"+IP_SERVIDOR+"/tristagramwebservice/source/prepareds/images/"+image);
            con = (HttpURLConnection) url.openConnection();
            answer = con.getResponseCode();
            if(answer==HttpURLConnection.HTTP_OK){

            }
            con.disconnect();
        }catch (Exception e){}
    }

    /**
     * devuelve fecha para nombrar las fotos
     * @return
     */
    public static String rLocalDateTime() {

        String date = LocalDateTime.now().toString();
        date = "d"+date;
        date = date.replace("-", "");
        date = date.replace(":", "");
        date = date.replace("T", "_h");
        date = date.substring(0,15);

        return date;
    }








    // SendData y checkJson del login
    public static String sendDataGETlogin(String email, String password){
        URL url = null;
        String linea = "";
        int answer = 0;
        StringBuilder result = null;
        try {
            url = new URL("http://"+IP_SERVIDOR+"/tristagramwebservice/source/prepareds/loginuser.php?email="+email+"&password="+password);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            answer = con.getResponseCode();

            result = new StringBuilder();

            if(answer==HttpURLConnection.HTTP_OK){
                InputStream is = new BufferedInputStream(con.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while((linea = br.readLine())!=null){
                    result.append(linea);
                }
                is.close();
                br.close();
            }
            con.disconnect();
        }catch(Exception e){}
        return result.toString();
    }

    public static String checkJSONtransaction(String data){
        String profilePath="";
        try {
            JSONArray json = new JSONArray(data);
            if(json.length()>0){
                profilePath = json.getJSONObject(0).getString("profile_image_path");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return profilePath;
    }


    public static Users checkJSONlogin(String answer){
        try{
            JSONArray json = new JSONArray(answer);
            if(json.length()>0){
                Integer id = json.getJSONObject(0).getInt("id"); // <---------------
                String user = json.getJSONObject(0).getString("user");
                String profile = json.getJSONObject(0).getString("profile_image_path");
                Users users = new Users(id,user,profile);
                return users;
            }
        }catch(Exception e){}
        return null;
    }




    // METODOS DEL SIGNUP

    public static String sendDataGETuserSignUp(String user){
        URL url = null; // http://localhost/tristagramwebservice/source/prepared/checknewuser.php;
        String linea = "";
        int answer = 0;
        StringBuilder result = null;
        try {
            url = new URL("http://"+IP_SERVIDOR+"/tristagramwebservice/source/prepareds/checknewuser.php?user="+user);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            answer = con.getResponseCode();

            result = new StringBuilder();

            if(answer==HttpURLConnection.HTTP_OK){
                InputStream is = new BufferedInputStream(con.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while((linea = br.readLine())!=null){
                    result.append(linea);
                }
            }
        }catch(Exception e){}
        return result.toString();
    }

    public static String sendDataGETmailSignUp(String email){
        URL url = null; // http://localhost/tristagramwebservice/source/prepared/checknewemail.php;
        String linea = "";
        int answer = 0;
        StringBuilder result = null;
        try {
            url = new URL("http://"+IP_SERVIDOR+"/tristagramwebservice/source/prepareds/checknewmail.php?email="+email);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            answer = con.getResponseCode();

            result = new StringBuilder();

            if(answer==HttpURLConnection.HTTP_OK){
                InputStream is = new BufferedInputStream(con.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while((linea = br.readLine())!=null){
                    result.append(linea);
                }
            }
        }catch(Exception e){}
        return result.toString();
    }

    public static String insertUserHttpUrlSignUp(Context context, String user, String password, String email){
        URL url = null; // http://localhost/tristagramwebservice/source/prepared/insertuser.php;
        String linea = "";
        int answer = 0;
        StringBuilder result = null;
        try {
            url = new URL("http://"+IP_SERVIDOR+"/tristagramwebservice/source/prepareds/insertuser.php?user="+user+
                    "&password="+password+"&email="+email);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            answer = con.getResponseCode();

            result = new StringBuilder();
            if(answer==200) { // la respuesta de la conexion es HttpUrlConnection.OK o bien el codigo 200

                Toast.makeText(context, "Insertado", Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){}
        return result.toString();
    }

    public static boolean checkJSONSignUp(String answer){
        try{
            JSONArray json = new JSONArray(answer);
            if(json.length()>0){
                return true;
            }
        }catch(Exception e){}
        return false;
    }
    public static String sendDataMuro(String user){

        String linea = "";
        int answer = 0;
        StringBuilder result = null;
        try {
            URL url = new URL("http://192.168.100.31/tristagramwebservice/source/prepareds/fotosMuro.php?user="+user);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            answer = con.getResponseCode();
            result = new StringBuilder();

            if(answer==200){
                InputStream is = new BufferedInputStream(con.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while((linea = br.readLine())!=null){
                    result.append(linea);
                }
                is.close();
                br.close();
            }
            con.disconnect();
        }catch(Exception e){}
        return result.toString();
    }


    //metodo dejar de seguir
    public static void dejarDeSeguir(int idUser, int idSeguido) {
        Thread tr = new Thread(() -> {
           URL url =null;
            try {
                url = new URL("http://192.168.100.31/tristagramwebservice/source/prepareds/dejarDeSeguir.php?id_user="+idUser+"&id_seguido="+idSeguido);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                System.out.println("Respuesta="+con.getResponseMessage());
                con.disconnect();
            } catch (IOException malformedURLException) {
                malformedURLException.printStackTrace();
            }
        });
        tr.start();
    }
    //metodo para empezar a seguir
    public static void empezarASeguir(int idUser, int idSeguido) {
        Thread tr = new Thread(() -> {
            URL url =null;
            try {
                url = new URL("http://192.168.100.31/tristagramwebservice/source/prepareds/empezarASeguir.php?id_user="+idUser+"&id_seguido="+idSeguido);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                System.out.println("Respuesta="+con.getResponseMessage());
                con.disconnect();
            } catch (IOException malformedURLException) {
                malformedURLException.printStackTrace();
            }
        });
        tr.start();
    }




}
