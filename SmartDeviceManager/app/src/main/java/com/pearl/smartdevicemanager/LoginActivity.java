package com.pearl.smartdevicemanager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pearl.smartdevicemanager.beans.IoTUsers;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;


public class LoginActivity extends Activity {

    @BindView(R.id.input_email) EditText email_et;
    @BindView(R.id.input_password) EditText password_et;
    @BindView(R.id.btn_login) Button login_btn;
    @BindView(R.id.link_signup) TextView signup_tv;

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Bmob.initialize(this, "1f1f8a4eac5575b2b1bf9bde5c2ad719");

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getApplicationContext(),"butter knife", Toast.LENGTH_SHORT).show();
                mLogin();
            }
        });

        signup_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        }
    }
    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }


    public void mLogin(){
   //    检测用户输入
        if(!validate()) {
            onLoginFailed();
            return;
        }

        login_btn.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        String email = email_et.getText().toString();
        String password = password_et.getText().toString();

        BmobUser user = new BmobUser();
        user.setEmail(email);
        user.setPassword(password);

        user.login(this,new SaveListener(){

            @Override
            public void onSuccess() {
                Log.e("TAG", "SUCCESS");
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);

            }

            @Override
            public void onFailure(int i, String s) {

                Log.e(i+"", s);
                Log.e("TAG", "failure");
                return;
            }
        });


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);


    }


    //登录失败
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        login_btn.setEnabled(true);
    }

    public void onLoginSuccess() {
        login_btn.setEnabled(true);
        finish();
    }



    public boolean validate() {
        boolean valid = true;

        String email = email_et.getText().toString();
        String password = password_et.getText().toString();

//        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            email_et.setError("enter a valid email address");
//            valid = false;
//        } else {
//            email_et.setError(null);
//        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            password_et.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password_et.setError(null);
        }

        return valid;
    }
}
