package com.derich.tutorme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {
    EditText editTextName,editTextClass,editTextEmail,editTextPassword,editTextConfirmPassword;
    Button btnSave;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseFirestore mFirestore= FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editTextName=findViewById(R.id.student_sign_up_name);
        editTextClass=findViewById(R.id.student_sign_up_class);
        editTextEmail=findViewById(R.id.student_sign_up_email);
        editTextPassword=findViewById(R.id.student_sign_up_password);
        editTextConfirmPassword=findViewById(R.id.student_sign_up_confirm_password);
        btnSave=findViewById(R.id.student_sign_up_button);
        btnSave.setOnClickListener(view -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPass = editTextConfirmPassword.getText().toString().trim();
            String name=editTextName.getText().toString().trim();
            String studentsClass=editTextClass.getText().toString().trim();
            if (email.isEmpty() || password.isEmpty() || confirmPass.isEmpty() || name.isEmpty()||studentsClass.isEmpty()){
                Toast.makeText(SignUpActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
            }
            else {
                if (password.equals(confirmPass)){
                    createNewUser(email,password,name,studentsClass);
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void createNewUser(String email,String password,String name,String studentsClass){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                final FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                String section = "student";
                StudentDetails newUser = new StudentDetails(name,studentsClass,email);
                mFirestore.collection("users").document("students").collection("all students").document(email)
                        .set(newUser)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(SignUpActivity.this,"Account created successfully",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignUpActivity.this,MajorActivity.class));

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUpActivity.this,"Not saved. Try again later.",Toast.LENGTH_LONG).show();
                            }
                        });
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
}