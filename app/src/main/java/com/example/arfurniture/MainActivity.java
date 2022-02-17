package com.example.arfurniture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.arfurniture.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth=FirebaseAuth.getInstance();

        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tilEmail.setError(null);
                binding.tilPassword.setError(null);
                String sEmail=binding.tieEmail.getText().toString().trim();
                String sPass=binding.tiePassword.getText().toString().trim();
                if (validate())
                {
                    firebaseAuth.signInWithEmailAndPassword(sEmail,sPass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            checkMailVerification(view);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(view,e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            private void checkMailVerification(View view) {
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                if (firebaseUser.isEmailVerified())
                {
                    Snackbar.make(view,"Logged in", Snackbar.LENGTH_LONG).show();
                    Intent toMainScreen=new Intent(getApplicationContext(),MainScreenActivity.class);
                    startActivity(toMainScreen);
                    finish();
                }else {
                    Snackbar.make(view,"Verify first", Snackbar.LENGTH_LONG).show();
                }
            }

            private boolean validate() {
                String vEmail=binding.tieEmail.getText().toString().trim();
                String vPass=binding.tiePassword.getText().toString().trim();
                if (vEmail.isEmpty() || vPass.isEmpty())
                {
                    if ((vEmail.isEmpty() || vEmail==null) && (vPass.isEmpty()|| vPass==null) )
                    {
                        binding.tilEmail.setError("Email must not be empty");
                        binding.tilPassword.setError("Password must not be empty");
                    }else if (vPass.isEmpty() || vPass==null){
                        binding.tilPassword.setError("Password must not be empty");
                    }
                    else if (vEmail.isEmpty()|| vEmail==null)
                    {
                        binding.tilEmail.setError("Email must not be empty");
                    }
                }
                else {
                    return true;
                }
                return false;
            }
        });
        binding.tvDontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSignUp=new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(toSignUp);
                finish();
            }
        });
        binding.btnGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        binding.tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toForgotPass = new Intent(MainActivity.this, ForgotPassActivity.class);
                startActivity(toForgotPass);
            }
        });
    }
}