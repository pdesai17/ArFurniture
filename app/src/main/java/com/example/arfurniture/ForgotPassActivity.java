package com.example.arfurniture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.arfurniture.databinding.ActivityForgotPassBinding;

public class ForgotPassActivity extends AppCompatActivity {
    ActivityForgotPassBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityForgotPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent(ForgotPassActivity.this, MainActivity.class);
                startActivity(toMain);
                finish();
            }
        });
    }
}