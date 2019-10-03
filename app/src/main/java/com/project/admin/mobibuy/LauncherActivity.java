package com.project.admin.mobibuy;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import java.io.IOException;
import java.util.HashMap;


public class LauncherActivity extends AppCompatActivity implements View.OnClickListener {

    private ParseContent parseContent;
    RelativeLayout rellay1, rellay2;
    EditText etphonenumber, etpassword;
    CheckBox remember;
    Button loginBtn, createBtn, forgotBtn;
    TextView tvPhonenumber, tvPassword, tvLogin;
    Handler handler = new Handler();
    private final int LoginTask = 1;
    private PreferenceHelper preferenceHelper;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        parseContent = new ParseContent(this);
        preferenceHelper = new PreferenceHelper(this);


        etphonenumber = findViewById(R.id.etPhonenumber);
        etpassword = findViewById(R.id.etPassword);


        rellay1 = findViewById(R.id.rellay1);
        rellay2 = findViewById(R.id.rellay2);
        tvLogin = findViewById(R.id.tvLogin);
        tvPhonenumber = findViewById(R.id.tvUsername);
        tvPassword = findViewById(R.id.tvPassword);
        loginBtn= findViewById(R.id.btnLogin);
        remember = findViewById(R.id.rememberMe);
        createBtn = findViewById(R.id.btnCreate);
        forgotBtn = findViewById(R.id.btnForgot);



        Typeface appleFont=Typeface.createFromAsset(getAssets(),"fonts/SF-Regular.ttf");
        tvLogin.setTypeface(appleFont);
        tvPhonenumber.setTypeface(appleFont);
        tvPassword.setTypeface(appleFont);
        forgotBtn.setTypeface(appleFont);
        remember.setTypeface(appleFont);
        createBtn.setTypeface(appleFont);
        loginBtn.setTypeface(appleFont);


        handler.postDelayed(runnable, 2000);
        loginBtn.setOnClickListener(this);
        createBtn.setOnClickListener(this);

    }


    public void onClick(View v) {
        //initiating the qr code scan

        switch (v.getId()) {

            case R.id.btnLogin:
               /* try {
                    login();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                startActivity(new Intent( LauncherActivity.this, HomeActivity.class));
                break;

            case R.id.btnCreate:
                startActivity(new Intent( LauncherActivity.this, RegisterActivity.class));
                break;

            default:
                break;
        }
    }
    private void login() throws IOException, JSONException {

        if (!MobiUtils.isNetworkAvailable(LauncherActivity.this)) {
            Toast.makeText(LauncherActivity.this, "Connect to the internet to continue", Toast.LENGTH_SHORT).show();
            return;
        }
        MobiUtils.showSimpleProgressDialog(LauncherActivity.this);
        final HashMap<String, String> map = new HashMap<>();
        map.put(MobiConstants.Params.PHONENUMBER, etphonenumber.getText().toString());
        map.put(MobiConstants.Params.PASSWORD, etpassword.getText().toString());
        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                try {
                    HttpRequest req = new HttpRequest(MobiConstants.ServiceType.LOGIN);
                    response = req.prepare(HttpRequest.Method.POST).withData(map).sendAndReadString();
                } catch (Exception e) {
                    response=e.getMessage();
                }
                return response;
            }
            protected void onPostExecute(String result) {
                //do something with response
                Log.d("newwwss", result);
                onTaskCompleted(result,LoginTask);
            }
        }.execute();
    }
    private void onTaskCompleted(String response,int task) {
        Log.d("responsejson", response.toString());
        MobiUtils.removeSimpleProgressDialog();  //will remove progress dialog
        switch (task) {
            case LoginTask:

                if (parseContent.isSuccess(response)) {

                    parseContent.saveInfo(response);
                    Toast.makeText(LauncherActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LauncherActivity.this,HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    this.finish();

                }else {
                    Toast.makeText(LauncherActivity.this, parseContent.getErrorMessage(response), Toast.LENGTH_SHORT).show();
                }
        }
    }


}
