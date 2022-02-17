package com.example.arfurniture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.arfurniture.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth=FirebaseAuth.getInstance();

        binding.tvDontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMainAct=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(toMainAct);
                finish();
            }
        });
        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tilEmail.setError(null);
                binding.tilPassword.setError(null);
                String sEmail=binding.tieEmail.getText().toString().trim();
                String sPass=binding.tiePassword.getText().toString().trim();
                if (validate())
                {
                    firebaseAuth.createUserWithEmailAndPassword(sEmail,sPass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Snackbar.make(view,"Registration Completed", Snackbar.LENGTH_SHORT).show();
                            sendVerification(view);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(view,e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            private void sendVerification(View view) {
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Snackbar.make(view,"Verification sent. Verify it and then login", Snackbar.LENGTH_LONG).show();
                        firebaseAuth.signOut();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(view,e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
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

    }
}