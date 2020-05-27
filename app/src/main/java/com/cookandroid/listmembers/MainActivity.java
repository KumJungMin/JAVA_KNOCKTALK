package com.cookandroid.listmembers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.cookandroid.listmembers.fragments.ScheduleFragment;
import com.cookandroid.listmembers.subin.ListAdapter;
import com.cookandroid.listmembers.fragments.MemberFragment;
import com.cookandroid.listmembers.fragments.MessageFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.mainActivity_bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_member:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_frame, new MemberFragment()).commit();
                        return true;
                    case R.id.action_schedule:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_frame, new ScheduleFragment()).commit();
                        return true;
                    case R.id.action_message:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_frame, new MessageFragment()).commit();
                        return true;
                }

                return false;
            }
        });

        passPushTokenToServer();
    }

//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
//                    @Override
//                    public void onSuccess(InstanceIdResult instanceIdResult) {
//                        String newToken = instanceIdResult.getToken();
//                        Log.d(TAG, "-------새토큰------: " + newToken);
//                    }
//                });
//
//        Button btn_1 = findViewById(R.id.main_btn_1);
//        btn_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String savedToken = FirebaseInstanceId.getInstance().getId();
//                Log.d(TAG, "onClick: 등록되어있는 토큰 아이디: " + savedToken);
//            }
//        });
//    }

    void passPushTokenToServer(){

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String token = instanceIdResult.getToken();
                Map<String, Object> map = new HashMap<>();
                map.put("pushToken", token);
                FirebaseDatabase.getInstance().getReference().child("users").child(uid).updateChildren(map);
            }
        });

    }




}
