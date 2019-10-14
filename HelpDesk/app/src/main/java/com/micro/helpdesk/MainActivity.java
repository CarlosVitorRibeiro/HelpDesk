package com.micro.helpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.gson.Gson;
import com.micro.helpdesk.api.LoginTask;
import com.micro.helpdesk.api.OnEventListener;
import com.micro.helpdesk.model.Login;


public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.BtLogar);
        final EditText viewlogin = (EditText) findViewById(R.id.txtNome);
        final EditText viewsenha = (EditText) findViewById(R.id.txtSenha);


        button.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {


                final String txtlogin = viewlogin.getText().toString();
                final String txtsenha = viewsenha.getText().toString();

                final LoginTask loginTask = new LoginTask(getApplicationContext(), new OnEventListener<String>() {

                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_LONG).show();
                        Gson gson = new Gson();
                        Login[] logins = gson.fromJson(result, Login[].class);

                        for (Login login : logins) {
                            if (login.getLogin().toLowerCase().equals(txtlogin) && login.getPassword().toLowerCase().equals(txtsenha)) {

                                Intent intent = new Intent(MainActivity.this, TabsActivity.class);
                                startActivity(intent);
                            }
                        }

                    }

                    public void onFailure(Exception e) {
                        Toast.makeText(getApplicationContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                loginTask.execute();
            }
        });
    }
}