package com.derich.tutorme;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    Button sign_in_btn,register_btn;
    private CheckBox checkBoxTutor;
    private CheckBox checkBoxStudent;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sign_in_btn=findViewById(R.id.signin_button);
        register_btn=findViewById(R.id.signup_button);
        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser!=null){
//            signOut();
            Intent intent= new Intent(MainActivity.this,MajorActivity.class);
            startActivity(intent);
        }
        else {
        }
        register_btn.setOnClickListener(view -> showSignUpDialog());
    }

    private void showSignUpDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Select Account type");
        LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.select_account_type,null);
        checkBoxTutor = add_menu_layout.findViewById(R.id.checkBoxTutor);
        checkBoxStudent = add_menu_layout.findViewById(R.id.checkBoxStudent);
        checkBoxTutor.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                checkBoxStudent.setChecked(false);
            }
            else {

            }
        });
        checkBoxStudent.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                checkBoxTutor.setChecked(false);
            }
            else {
            }
        });

        alertDialog.setView(add_menu_layout);
        alertDialog.setIcon(R.drawable.background);
        alertDialog.setPositiveButton("DONE", (dialog, i) -> {
            if (checkBoxStudent.isChecked() || checkBoxTutor.isChecked()){
                if (checkBoxStudent.isChecked()){
                    Intent intent=new Intent(this,SignUpActivity.class);
                    startActivity(intent);

                }
                else {
                    Intent intent=new Intent(this,SignUpTutor.class);
                    startActivity(intent);

                }
                }
                    else {
                        //location=false,type=false,rent=false;
                        Toast.makeText(MainActivity.this,"No section selected",Toast.LENGTH_SHORT).show();

                    }
        });

        alertDialog.setNegativeButton("Cancel", (dialog, i) -> dialog.dismiss());
        alertDialog.show();
    }
}