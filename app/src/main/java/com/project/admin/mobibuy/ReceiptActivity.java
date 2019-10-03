package com.project.admin.mobibuy;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReceiptActivity extends AppCompatActivity {
    TextView tvBalance;
    Button buttonHome,buttonPay;
    private TextView textViewName, textViewAddress;
    ListView myCart;
    TextView total, tvTitle;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        buttonHome = (Button) findViewById(R.id.buttonReceipt);
        buttonPay = (Button) findViewById(R.id.buttonPay);
        myCart = (ListView) findViewById(R.id.shoppingCart);
        total = (TextView) findViewById(R.id.tvTotal);
        tvTitle = (TextView) findViewById(R.id.textView2);
        tvBalance = (TextView)findViewById(R.id.tvBalance);
        buttonHome = (Button) findViewById(R.id.buttonReceipt);

        String[] bill = new String[] {
                "Samsung USB Type C Charger              500.00",
                "SparkleFresh Drinking Water 1 Litre       70.00",
                "Logitech Wireless Mouse                       600.00"
        };

        final List<String> items_list = new ArrayList<String>(Arrays.asList(bill));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, items_list);

        myCart.setAdapter(arrayAdapter);


        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<>(ReceiptActivity.this, android.R.layout.simple_list_item_1,arrayList);

        Typeface appleFontRegular= Typeface.createFromAsset(getAssets(),"fonts/SF-Regular.ttf");
        Typeface appleFontBold=Typeface.createFromAsset(getAssets(),"fonts/SF-Bold.ttf");
        buttonHome.setTypeface(appleFontRegular);
        tvBalance.setTypeface(appleFontBold);
        total.setTypeface(appleFontBold);
        total.setText("TOTAL: 1,170.00");

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReceiptActivity.this, HomeActivity2.class));
            }

        });
    }
}
