package com.example.note.login02;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.app.PendingIntent.getActivity;

public class Main2Activity extends AppCompatActivity {

    private static final String REGISTER_URL = "http://sigequip.esy.es/Insert2.php";
    private String urlJsonArry = "http://sigequip.esy.es/getLogin2.php";
    public static final String KEY_USERNAME = "usuUsuario";
    public static final String KEY_PASSWORD = "usuPass";
    EditText editTextUsername;
    EditText editTextPassword;
    TextView txt;
    private static String TAG = MainActivity.class.getSimpleName();
    //Nomb
    private Button btAlta;
    private Button Consultar;
    private String jsonResponse2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btAlta = (Button) findViewById(R.id.btAlta);
        editTextUsername=(EditText)findViewById(R.id.etNombre);
        editTextPassword=(EditText)findViewById(R.id.etPass);
        txt=(TextView)findViewById(R.id.txt);
        Consultar=(Button)findViewById(R.id.btConsulta);

        Consultar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // making json object request
                ConsultaRegistros();
            }
        });

        btAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AltaRegistros();
            }
        });

        //buttonRegister.
    }

    private void ConsultaRegistros() {

        JsonArrayRequest req = new JsonArrayRequest(urlJsonArry, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                try {
                    jsonResponse2 = "";
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject person = (JSONObject) response.get(i);

                        String usuNombre=person.getString("usuNombre");
                        String usuPass=person.getString("usuPass");

                        jsonResponse2 += "usuNombre: " + usuNombre + "\n\n";
                        jsonResponse2 += "usuPass: " + usuPass + "\n\n";
                    }

                    txt.setText(jsonResponse2);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    /*
    ////////////////////////////////////////////////////////////////////////////////////////////////
     */
    private void AltaRegistros() {

            /*
             Obtener valores actuales de los controles

            final String titulo = titulo_input.getText().toString();
            final String descripcion = descripcion_input.getText().toString();
            final String fecha = fecha_text.getText().toString();
            final String categoria = categoria_spinner.getSelectedItem().toString();
            final String prioridad = prioridad_spinner.getSelectedItem().toString();
            REGISTER_URL
        */
            final String usuNombre=editTextUsername.getText().toString();
            final String usuPass= editTextPassword.getText().toString();


            HashMap<String, String> map = new HashMap<>();// Mapeo previo

            map.put("usuNombre", usuNombre);
            map.put("usuPass", usuPass);


            // Crear nuevo objeto Json basado en el mapa
            JSONObject jobject = new JSONObject(map);

            // Depurando objeto Json...
            Log.d(TAG, jobject.toString());

            // Actualizar datos en el servidor
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest

                (Request.Method.POST,REGISTER_URL,jobject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                   // procesarRespuestaActualizar(response);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d(TAG, "Error Volley: " + error.getMessage());
                                }
                            }

                    ) {
                        @Override
                        public Map<String, String> getHeaders() {
                            Map<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=utf-8");
                            headers.put("Accept", "application/json");
                            return headers;
                        }

                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8" + getParamsEncoding();
                        }
                    }
            );

            }



}

/*
    private void registerUser(){
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Main2Activity.this,response,Toast.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Main2Activity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME,username);
                params.put(KEY_PASSWORD,password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }*/


/* private void registerUser(){
     AppController.getInstance().addToRequestQueue(putRequest);

 /*   @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            //registerUser();
            //De acá para abajo es nuevo el código
            final RequestQueue cola=Volley.newRequestQueue(this);
            String REGISTER_URL = "http://sigequip.esy.es/Insert2.php";

            StringRequest stringRequest=new StringRequest(Request.Method.POST, REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            editTextUsername = (EditText) findViewById(R.id.etNombre);
                            editTextPassword = (EditText) findViewById(R.id.etPass);

                            usuN=editTextUsername.getText().toString().trim();
                            usuP=editTextUsername.getText().toString().trim();

                            Toast.makeText(getApplicationContext(),"Ok",Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                }
            }
            ){
                @Override
                public Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("KEY_USERNAME", "usuN");
                    params.put("KEY_PASSWORD", "usuP");
                    return getParams();
                }
            };
            cola.add(stringRequest);
        }
    }*/


/*
Ejemplo de método POST
url = "http://httpbin.org/post";
StringRequest postRequest = new StringRequest(Request.Method.POST, url,
    new Response.Listener<String>()
    {
        @Override
        public void onResponse(String response) {
            // response
            Log.d("Response", response);
        }
    },
    new Response.ErrorListener()
    {
         @Override
         public void onErrorResponse(VolleyError error) {
             // error
             Log.d("Error.Response", response);
       }
    }
) {
    @Override
    protected Map<String, String> getParams()
    {
            Map<String, String>  params = new HashMap<String, String>();
            params.put("name", "Alif");
            params.put("domain", "http://itsalif.info");

            return params;
    }
};
queue.add(postRequest);


    }*/

