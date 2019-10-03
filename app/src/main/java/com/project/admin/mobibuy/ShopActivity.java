package com.project.admin.mobibuy;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//implementing onclicklistener
public class ShopActivity extends AppCompatActivity implements View.OnClickListener {

    //View Objects
    private Button buttonScan;
    Button buttonPay;
    private TextView textViewName, textViewAddress;
    ListView myCart;
    TextView total, tvTitle, tvBalance;

    int totalAmount;
    int counter;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    //qr code scanner object
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);


        //View objects
        buttonScan = (Button) findViewById(R.id.buttonScan);
        buttonPay = (Button) findViewById(R.id.buttonPay);
        myCart = (ListView) findViewById(R.id.shoppingCart);
        total = (TextView) findViewById(R.id.tvTotal);
        tvTitle = (TextView) findViewById(R.id.textView2);
        tvBalance = (TextView)findViewById(R.id.tvBalance);

        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<>(ShopActivity.this, android.R.layout.simple_list_item_1,arrayList);
        myCart.setAdapter(adapter);

        Typeface appleFontRegular=Typeface.createFromAsset(getAssets(),"fonts/SF-Regular.ttf");
        Typeface appleFontBold=Typeface.createFromAsset(getAssets(),"fonts/SF-Bold.ttf");
        buttonScan.setTypeface(appleFontRegular);
        buttonPay.setTypeface(appleFontRegular);
        tvBalance.setTypeface(appleFontBold);

        total.setTypeface(appleFontBold);
        //intializing scan object
        qrScan = new IntentIntegrator(this);


        buttonScan.setOnClickListener(this);
        buttonPay.setOnClickListener(this);

    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data


                arrayList.add(result.getContents().toString());


                adapter.notifyDataSetChanged();

                if (counter == 0)
                total.setText("TOTAL: 500.00");

                if (counter > 0)
                    total.setText("TOTAL: 570.00");

                if (counter > 1)
                    total.setText("TOTAL: 1,170.00");

                counter++;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onClick(View v) {
        //initiating the qr code scan

            switch (v.getId()) {

                case R.id.buttonScan:
                    qrScan.initiateScan();
                    break;

                case R.id.buttonPay:
                    startActivity(new Intent(ShopActivity.this, PaymentActivity.class));
                    break;

                default:
                    break;
            }
    }

}