package com.example.capstone_ii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    // UI Views
    private TextView authStatusTv;
    private Button authBtn;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init UI views
        authStatusTv = findViewById(R.id.authStatusTv);
        authBtn = findViewById(R.id.authBtn);

        // BIOMETRIC Initialization
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                // Error authenticating
                authStatusTv.setText("Authenticaion error: No FaceID enrolled!");
                // authStatusTv.setText("Authenticaion error: " + errString);
                Toast.makeText(MainActivity.this, "Authentication error: No FaceID enrolled!", Toast.LENGTH_SHORT).show();
                // Toast.makeText(MainActivity.this, "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                // Authentication succeed, continue tasks!
                authStatusTv.setText("Authentication succeed...!");
                Toast.makeText(MainActivity.this, "Authentication succeed...!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                // Failed authentication
                authStatusTv.setText("Authentication failed...!");
                Toast.makeText(MainActivity.this, "Authentication failed...!", Toast.LENGTH_SHORT).show();
            }
        });

        // Setup
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Login using face authentication")
                .setNegativeButtonText("User App Password")
                .build();

        // Handle authBtn click, start authentication
        authBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show authentication dialog
                biometricPrompt.authenticate(promptInfo);
            }
        });
    }
}