package com.project.admin.mobibuy;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import java.io.IOException;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText etfirstname, etlastname, etphonenumber, etpassword;
    private Button btnregister, btnlogin;
    private TextView tvTitle,tvfirstname, tvlastname, tvphonenumber, tvpassword;
    private ParseContent parseContent;
    private PreferenceHelper preferenceHelper;
    private final int RegTask = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        preferenceHelper = new PreferenceHelper(this);
        parseContent = new ParseContent(this);

        Typeface appleFont=Typeface.createFromAsset(getAssets(),"fonts/SF-Regular.ttf");
        Typeface appleFontBold=Typeface.createFromAsset(getAssets(),"fonts/SF-Bold.ttf");


        if(preferenceHelper.getIsLogin()){
            Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            this.finish();
        }

        etfirstname = (EditText) findViewById(R.id.etFirstname);
        etfirstname.setTypeface(appleFontBold);
        etlastname = (EditText) findViewById(R.id.etLastname);
        etlastname.setTypeface(appleFontBold);
        etphonenumber = (EditText) findViewById(R.id.etPhonenumber);
        etphonenumber.setTypeface(appleFontBold);
        etpassword = (EditText) findViewById(R.id.etPassword);
        etpassword.setTypeface(appleFontBold);
        tvTitle = (TextView) findViewById(R.id.welcometxt);
        tvTitle.setTypeface(appleFontBold);
        btnregister = (Button) findViewById(R.id.btnRegister);
        btnregister.setTypeface(appleFontBold);
        btnlogin =  (Button) findViewById(R.id.btnLogin);
        btnlogin.setTypeface(appleFontBold);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LauncherActivity.class);
                startActivity(intent);
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    register();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void register() throws IOException, JSONException {
        if (!MobiUtils.isNetworkAvailable(RegisterActivity.this)) {
            Toast.makeText(RegisterActivity.this, "Internet is required!", Toast.LENGTH_SHORT).show();
            return;
        }
        MobiUtils.showSimpleProgressDialog(RegisterActivity.this);
        final HashMap<String, String> map = new HashMap<>();
        map.put(MobiConstants.Params.FIRSTNAME, etfirstname.getText().toString());
        map.put(MobiConstants.Params.LASTNAME, etlastname.getText().toString());
        map.put(MobiConstants.Params.PHONENUMBER, etphonenumber.getText().toString());
        map.put(MobiConstants.Params.PASSWORD, etpassword.getText().toString());
        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                try {
                    HttpRequest req = new HttpRequest(MobiConstants.ServiceType.REGISTER);
                    response = req.prepare(HttpRequest.Method.POST).withData(map).sendAndReadString();
                } catch (Exception e) {
                    response=e.getMessage();
                }
                return response;
            }
            protected void onPostExecute(String result) {
                //do something with response
                Log.d("newwwss", result);
                onTaskCompleted(result, RegTask);
            }
        }.execute();
    }
    private void onTaskCompleted(String response,int task) {
        Log.d("responsejson", response.toString());
        MobiUtils.removeSimpleProgressDialog();  //will remove progress dialog
        switch (task) {
            case RegTask:

                if (parseContent.isSuccess(response)) {
                    parseContent.saveInfo(response);
                    Toast.makeText(RegisterActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    this.finish();
                }else {
                    Toast.makeText(RegisterActivity.this, parseContent.getErrorMessage(response), Toast.LENGTH_SHORT).show();
                }
        }
    }}