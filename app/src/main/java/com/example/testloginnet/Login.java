package com.example.testloginnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText userId, password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userId = findViewById(R.id.userId);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userIdtext = userId.getText().toString();
                String passwordText = password.getText().toString();
                if (userIdtext.isEmpty() || passwordText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill all Fields!", Toast.LENGTH_SHORT).show();
                } else {
                    //databsae Initialization
                    //performe qery
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserEntity userEntity = userDao.login(userIdtext, passwordText);
                            if (userEntity == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                String name = userEntity.name;
                                startActivity(new Intent(Login.this, HomeScreen.class).putExtra("name", name));

                            }
                        }
                    }).start();
                }
            }
        });
    }
}