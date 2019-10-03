package com.project.admin.mobibuy;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    TextView welcomeText;
    TextView txtBalance;
    Button shopButton;
    Button searchButton;
    Button historyButton;
    Button settingsButton;
    Button helpButton;
    Button exitButton;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        preferenceHelper = new PreferenceHelper(this);



        txtBalance = (TextView) findViewById(R.id.txtbalance);
        welcomeText = (TextView) findViewById(R.id.welcometxt);
        welcomeText.setText("Welcome back, Louis!");
        Typeface appleFont=Typeface.createFromAsset(getAssets(),"fonts/SF-Regular.ttf");
        Typeface appleFontBold=Typeface.createFromAsset(getAssets(),"fonts/SF-Bold.ttf");
        welcomeText.setTypeface(appleFontBold);
        txtBalance.setTypeface(appleFontBold);

        shopButton = (Button) findViewById(R.id.btnShop);
        shopButton.setTypeface(appleFont);

        searchButton = (Button) findViewById(R.id.btnSearch);
        searchButton.setTypeface(appleFont);

        historyButton = (Button) findViewById(R.id.btnHistory);
        historyButton.setTypeface(appleFont);

        settingsButton = (Button) findViewById(R.id.btnSettings);
        settingsButton.setTypeface(appleFont);

        helpButton = (Button) findViewById(R.id.btnHelp);
        helpButton.setTypeface(appleFont);

        exitButton = (Button) findViewById(R.id.btnExit);
        exitButton.setTypeface(appleFont);

        shopButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //initiating the qr code scan

        switch (v.getId()) {

            case R.id.btnExit:
                preferenceHelper.putIsLogin(false);
                Intent intent = new Intent(HomeActivity.this,LauncherActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                HomeActivity.this.finish();

                break;

            case R.id.btnShop:
                startActivity(new Intent(HomeActivity.this, ShopActivity.class));
                break;

            default:
                break;
        }
    }
}
