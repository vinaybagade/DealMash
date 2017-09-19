package com.example.vinay.dealmash;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Vinay on 31-01-2017.
 */
public class SignUpActivity extends Activity {
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();
        final EditText useremail= (EditText)findViewById(R.id.signupuseremail);
        final EditText username =(EditText)findViewById(R.id.signupusername);
        final EditText password =(EditText)findViewById(R.id.signuppassword);
        Button createaccount =(Button)findViewById(R.id.signupbutton);
        createaccount.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String mail= useremail.getText().toString();
                String name =username.getText().toString();
                String pass= password.getText().toString();
                if(mail.equals("")|| name.equals("")|| pass.equals("")){
                    Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
                    return;
                }else{
                    String username =loginDataBaseAdapter.getUsername(mail);
                    if(!username.equals("")){
                        Toast.makeText(getApplicationContext(), "User Already Registered", Toast.LENGTH_LONG).show();
                    }else {
                        loginDataBaseAdapter.insertEntry(mail, name, pass);
                        Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
        TextView gotologin =(TextView)findViewById(R.id.redirecttosignin);
        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
