package com.derich.tutorme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class SignUpTutor extends AppCompatActivity {
    EditText editTextName,editTextContact,editTextSubjects,editTextClasses,editTextEmail,editTextPassword,editTextConfirmPassword;
    Button btnSave;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseFirestore mFirestore= FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_tutor);
        editTextName=findViewById(R.id.tutor_sign_up_name);
        editTextSubjects=findViewById(R.id.tutor_sign_up_subjects);
        editTextContact=findViewById(R.id.tutor_sign_up_contact);
        editTextClasses=findViewById(R.id.tutor_sign_up_classes);
        editTextEmail=findViewById(R.id.tutor_sign_up_email);
        editTextPassword=findViewById(R.id.tutor_sign_up_password);
        editTextConfirmPassword=findViewById(R.id.tutor_sign_up_confirm_password);
        btnSave=findViewById(R.id.tutor_sign_up_button);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String confirmPass = editTextConfirmPassword.getText().toString().trim();
                String name=editTextName.getText().toString().trim();
                String subjects=editTextSubjects.getText().toString().trim();
                String contact = editTextContact.getText().toString().trim();
                String studentsClasses=editTextClasses.getText().toString().trim();
                //check if any of the fields is empty
                if (email.isEmpty()|| contact.isEmpty() ||subjects.isEmpty()||studentsClasses.isEmpty() || password.isEmpty() || confirmPass.isEmpty() || name.isEmpty()||studentsClasses.isEmpty()){
                    Toast.makeText(SignUpTutor.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (password.equals(confirmPass)){
                        createNewUser(email,password,name,studentsClasses,subjects,contact);
                    }
                    else {
                        Toast.makeText(SignUpTutor.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void createNewUser(String email,String password,String name,String studentsClasses,String subjects,String contact){
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
                            TutorDetails newUser = new TutorDetails(name,subjects,studentsClasses,email,contact);
                            mFirestore.collection("users").document("tutors").collection("all tutors").document(email)
                                    .set(newUser)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(SignUpTutor.this,"Account created successfully",Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(SignUpTutor.this,MajorActivity.class));

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SignUpTutor.this,"Not saved. Try again later.",Toast.LENGTH_LONG).show();
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpTutor.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
}