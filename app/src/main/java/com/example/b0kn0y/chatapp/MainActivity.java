package com.example.b0kn0y.chatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import helpers.FirebaseClient;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.emailEditId);
        password = (EditText) findViewById(R.id.passwordEditId);
        submit = (Button) findViewById(R.id.submit);

    }

    @Override
    protected void onStart() {
        super.onStart();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            final String emailField = email.getText().toString().trim();
            final String passwordField = password.getText().toString().trim();

            if(emailField.isEmpty() || passwordField.isEmpty()) {

                Toast.makeText(getApplicationContext(), "Fields must not be empty!", Toast.LENGTH_SHORT).show();
            }else {

                // Proceed to authentication
                FirebaseClient firebaseClient = new FirebaseClient();
                firebaseClient.loginUserWithEmailAndPassword(emailField, passwordField)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {

                            /**
                             * If authentication is successful,
                             * proceed to ChatView.
                             */
                            loadChatView();
                        } else {

                            /**
                             * If the user has not registered,
                             * then create his account since
                             * we don't have a view to register
                             * a user.
                             */
                            signUpUser(emailField, passwordField);
                        }
                        }
                    });
            }
            }
        });
    }

    /**
     * @description This method handles User Signup.
     * @param emailField
     * @param passwordField
     */
    private void signUpUser(String emailField, String passwordField) {
        FirebaseClient firebaseClient = new FirebaseClient();
        firebaseClient.registerUser(emailField, passwordField)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            loadChatView();
                        }else {
                            Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loadChatView() {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
