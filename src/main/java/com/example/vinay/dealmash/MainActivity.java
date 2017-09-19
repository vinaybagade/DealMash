package com.example.vinay.dealmash;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    LoginDataBaseAdapter loginDataBaseAdapter;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signinbutton =(Button)findViewById(R.id.signin);
        TextView signupredirect = (TextView)findViewById(R.id.redirecttosignup);
        loginDataBaseAdapter= new LoginDataBaseAdapter(this);
        loginDataBaseAdapter =loginDataBaseAdapter.open();
        session = new Session(this);
        if(session.isloggedin()){
            Intent intent = new Intent(getApplicationContext(),UserDashboard.class);
            startActivity(intent);
        }
        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText email = (EditText)findViewById(R.id.useremail);
                final EditText password= (EditText)findViewById(R.id.password);
                String storedpassword= loginDataBaseAdapter.getPassword(email.getText().toString());
                if(storedpassword.equals(password.getText().toString())){
                    String username = loginDataBaseAdapter.getUsername(email.getText().toString());
                    session.setloggedin(username);
                    Intent intent = new Intent(getApplicationContext(),UserDashboard.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Username or Password invalid", Toast.LENGTH_LONG).show();
                }

            }
        });
        signupredirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
