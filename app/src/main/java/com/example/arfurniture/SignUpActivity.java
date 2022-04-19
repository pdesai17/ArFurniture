package com.example.arfurniture;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.arfurniture.databinding.ActivitySignUpBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    String TAG = "SignUpActivity";
    ActivitySignUpBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;
    GoogleSignInClient mGoogleSignInClient;
    private final int RC_SIGN_IN = 104;

    String sName,sEmail,sPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();

        binding.tvDontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMainAct=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(toMainAct);
                finish();
            }
        });
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tilName.setError(null);
                binding.tilEmail.setError(null);
                binding.tilPassword.setError(null);
                sName = binding.tieName.getText().toString().trim();
                sEmail = binding.tieEmail.getText().toString().trim();
                sPass = binding.tiePassword.getText().toString().trim();
                if (validate()) {
                    firebaseAuth.createUserWithEmailAndPassword(sEmail, sPass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Snackbar.make(view, "Registration Completed", Snackbar.LENGTH_SHORT).show();
                            sendVerification(view);
                            addUserData();
                            //createWishlist();
                            //createCartList();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            private void createWishlist() {
                DocumentReference reference=firebaseFirestore
                        .collection("USERS")
                        .document(firebaseUser.getUid())
                        .collection("WISHLIST")
                        .document();
                /*Map<String,Integer> map1=new HashMap<>();
                map1.put("size",  0);
                reference.set(map1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: map1");
                    }
                });*/
            }

            private void createCartList() {
                DocumentReference reference1=firebaseFirestore
                        .collection("USERS")
                        .document(firebaseUser.getUid())
                        .collection("MY CART")
                        .document();
                /*Map<String,Integer> map1=new HashMap<>();
                map1.put("size",  0);
                reference1.set(map1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: map1");
                    }
                });*/
            }

            private void addUserData() {
                Log.d(TAG, "addUserData: ");
                DocumentReference documentReference=firebaseFirestore
                        .collection("USERS")
                        .document(firebaseUser.getUid());
                Map<String,String> map=new HashMap<>();
                map.put("name",sName);
                map.put("email",sEmail);
                documentReference.set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: ");
                    }
                });
                /*CollectionReference reference=firebaseFirestore
                        .collection("USERS")
                        .document(firebaseUser.getUid())
                        .collection("WISHLIST");*/
                /*Map<String,Integer> map1=new HashMap<>();
                map1.put("size",  0);
                reference.set(map1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: map1");
                    }
                });*/
                /*DocumentReference reference=firebaseFirestore
                        .collection("USERS")
                        .document(firebaseUser.getUid())
                        .collection("WISHLIST")
                        .document("prod_id");
                Map<String,Integer> map1=new HashMap<>();
                map1.put("size",  0);
                reference.set(map1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: map1");
                    }
                });*/
                Log.d(TAG, "addUserData: data added");

            }

            private void sendVerification(View view) {
                firebaseUser=firebaseAuth.getCurrentUser();
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
                String vName=binding.tieName.getText().toString().trim();
                String vEmail=binding.tieEmail.getText().toString().trim();
                String vPass=binding.tiePassword.getText().toString().trim();
                Log.d(TAG, "validate: vName= "+vName);
                Log.d(TAG, "validate: vEmail= "+vEmail);
                Log.d(TAG, "validate: vPass= "+vPass);
                if (vEmail.isEmpty() || vPass.isEmpty() || vName.isEmpty()) {
                    if ((vName.isEmpty() || vName==null) &&(vEmail.isEmpty() || vEmail == null) && (vPass.isEmpty() || vPass == null)) {
                        binding.tilName.setError("Name must not be empty");
                        binding.tilEmail.setError("Email must not be empty");
                        binding.tilPassword.setError("Password must not be empty");
                    }
                    if(vName.isEmpty() || vName.isEmpty()){
                        binding.tilName.setError("Name must not be empty");
                    }
                    if (vPass.isEmpty() || vPass == null) {
                        binding.tilPassword.setError("Password must not be empty");
                    }
                    if (vEmail.isEmpty() || vEmail == null) {
                        binding.tilEmail.setError("Email must not be empty");
                    }
                } else {
                    return true;
                }
                return false;
            }
        });
// Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        /*binding.btnGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.progressBar.setVisibility(View.VISIBLE);
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });*/
    }
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleSignInClient.signOut();
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
                binding.progressBar.setVisibility(View.GONE);
                if (acct != null) {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Google sign in successful",Snackbar.LENGTH_SHORT).show();
                    Intent toMainScreen=new Intent(this,MainScreenActivity.class);
                    startActivity(toMainScreen);
                    finish();
                }
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
}