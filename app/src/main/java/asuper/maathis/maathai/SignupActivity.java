package asuper.maathis.maathai;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";


   Button signup;
    EditText name, email, password;
    String register="http://192.168.43.184/maathai/register.php";

    Button btn;

    public boolean validator()
    {
        boolean istrue=true;
       // email1.isEmpty()||name1.isEmpty()||password1.isEmpty()
        if(email.getText().toString().isEmpty())
        {
            email.setError("Please enter your email");
            istrue=false;
        }
        else
        {
            email.setError(null);
        }
        if(name.getText().toString().isEmpty())
        {
            name.setError("Please enter your email");
            istrue=false;
        }
        else
        {
            name.setError(null);
        }
        if(password.getText().toString().isEmpty())
        {
            password.setError("Please enter your email");
            istrue=false;
        }
        else
        {
            password.setError(null);
        }

        return  istrue;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

//        Button signup;
//        EditText name, email, password;

        name=(EditText)findViewById(R.id.input_name);
        email=(EditText)findViewById(R.id.input_email);
        password=(EditText)findViewById(R.id.input_password);

        btn=(Button)findViewById(R.id.btn_signup);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    String email1=email.getText().toString();
                String password1=password.getText().toString();
                String name1=name.getText().toString();

                if(validator()==true)
                {
                    register(email1,password1, password1);
                }


            }
        });




    }

    public void register(final String email,final String password,final String name) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;
            SweetAlertDialog pDialog = new SweetAlertDialog(SignupActivity.this, SweetAlertDialog.PROGRESS_TYPE);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Registering...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("email", email);
                paramms.put("name", name);
                paramms.put("password", password);
                String s = rh.sendPostRequest(register, paramms);
                return s;

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();
                showthem(s);
                Toast.makeText(SignupActivity.this, s, Toast.LENGTH_SHORT).show();

            }


        }
        GetJSON jj = new GetJSON();
        jj.execute();


    }


    public void showthem(String s) {
        try {
            JSONObject json = new JSONObject(s);
            JSONArray array = json.getJSONArray("result");
            JSONObject jss = array.getJSONObject(0);
            String succes = jss.getString("status");
            if (succes.equals("1")) {
                new SweetAlertDialog(this,SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Huuuuray!!")
                        .setContentText("Registration succesifull")
                        .show();

            }
            else if (succes.equals("0"))
            {
                new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Ooops!!")
                        .setContentText("Registration Failed")
                        .show();
            }

        } catch (JSONException e) {
            new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(e.toString())
                    .show();
        }

    }



}
