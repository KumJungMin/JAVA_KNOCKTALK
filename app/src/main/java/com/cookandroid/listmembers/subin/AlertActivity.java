package com.cookandroid.listmembers.subin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.listmembers.R;

public class AlertActivity extends AppCompatActivity {

    Button remove, correct;
    Intent preIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        remove=(Button)findViewById(R.id.remove);
        correct=(Button)findViewById(R.id.correct);
        preIntent = getIntent();

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent thisIntent = new Intent();
                thisIntent.putExtra("result", "delete");
                setResult(2, thisIntent);
                finish();
            }
        });

        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent thisIntent = new Intent(getApplicationContext(), PopupActivity.class);
                thisIntent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                thisIntent.putExtra("place",preIntent.getStringExtra("place"));
                thisIntent.putExtra("memo",preIntent.getStringExtra("memo"));
                thisIntent.putExtra("color",preIntent.getStringExtra("color"));
                thisIntent.putExtra("end",preIntent.getIntExtra("end",24));
                thisIntent.putExtra("keyword",preIntent.getStringExtra("keyword"));
                thisIntent.putExtra("start",preIntent.getIntExtra("start",0));
                thisIntent.putExtra("SELECTOR",preIntent.getStringExtra("SELECTOR"));
                startActivity(thisIntent);
                finish();

            }
        });

    }
}
