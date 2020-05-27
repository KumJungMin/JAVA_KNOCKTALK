package com.cookandroid.listmembers;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.listmembers.models.Member;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class RegisterActivity extends AppCompatActivity {
    Button btn_register, btn_login;
    EditText et_address, et_studid;
    RadioGroup status;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_register = (Button) findViewById(R.id.btn_register);
        mAuth = FirebaseAuth.getInstance();
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               createAccount();
//               member();
                }
        });
        btn_login = (Button) findViewById(R.id.btn_done);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStartActivity(LoginActivity.class);
            }
        });
       RadioButton rbtn_prof = findViewById(R.id.rbtn_prof);
       RadioButton rbtn_stud = findViewById(R.id.rbtn_stud);
       et_address = findViewById(R.id.et_address);
       et_studid = findViewById(R.id.et_studid);
        rbtn_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_address.setVisibility(View.VISIBLE);
                if (et_studid.getVisibility() == View.VISIBLE) {
                    et_studid.setVisibility(View.GONE);
                }
            }
        });

        rbtn_stud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_address.getVisibility() == View.VISIBLE) {
                    et_address.setVisibility(View.GONE);
                }
                et_studid.setVisibility(View.VISIBLE);
            }
        });
    }

    private void member(){
        String office = null;
        String studId = null;
        Drawable profile;
        final String name = ((EditText) findViewById(R.id.et_name)).getText().toString();
        final String dp = ((EditText) findViewById(R.id.et_dp)).getText().toString();
        status = findViewById(R.id.radioGroup);
        RadioButton checkedBtn = findViewById(status.getCheckedRadioButtonId());
        final String btnStatus = checkedBtn.getText().toString();
        if (btnStatus.equals("교수")) {
            office = ((EditText) findViewById(R.id.et_address)).getText().toString();
        } else {
            studId = ((EditText) findViewById(R.id.et_studid)).getText().toString();
        }
        user = mAuth.getCurrentUser();
        String uid = user.getUid();
        Member member = new Member();
        member.studId = studId;
        member.office = office;
        member.name = name;
        member.dp = dp;
        member.btnStatus = btnStatus;
        member.uid = uid;

        FirebaseDatabase.getInstance().getReference()
                .child("users").child(uid).setValue(member)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        RegisterActivity.this.finish();
                        myStartActivity(LoginActivity.class);
                    }
                });
    }

    private void createAccount() {
        String email = ((EditText) findViewById(R.id.et_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.et_pwd)).getText().toString();
        String password_confirm = ((EditText) findViewById(R.id.et_pwd_confirm)).getText().toString();
        if (email.length() > 0 && password.length() > 0 && password_confirm.length() > 0){
            if(password.equals(password_confirm)){
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    user = FirebaseAuth.getInstance().getCurrentUser();
                                    member();
                                    startToast("회원가입을 축하드립니다.");
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    startToast("회원가입에 실패하였습니다.");
                                }
                            }
                        });
            }

        } else {
            startToast("비밀번호가 일치하지 않습니다.");
        }
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void myStartActivity(Class c){
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

