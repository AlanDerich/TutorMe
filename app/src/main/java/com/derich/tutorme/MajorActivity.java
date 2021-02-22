package com.derich.tutorme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MajorActivity extends AppCompatActivity {
    RecyclerView rvTutors;
    private FirebaseUser mUser;
    private TutorsAdapter mTutorsAdapter;
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    List<TutorDetails> mTutorInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major);
        rvTutors=findViewById(R.id.recyclerViewAllTutors);
        rvTutors.setHasFixedSize(true);
        rvTutors.setLayoutManager(new LinearLayoutManager(MajorActivity.this));
        populateRecyclerView();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        MenuItem mItem= menu.findItem(R.id.logout);
        mItem.setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        switch (item.getItemId()){
            case R.id.logout:
                if (mUser!=null){
                    signOut();
                }
                else {
                    Intent intent= new Intent(MajorActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                        startActivity(new Intent(MajorActivity.this,MainActivity.class));
                    }
                });
    }
    private void populateRecyclerView(){
        mTutorInfo=new ArrayList<>();
        String collectionId="all tutors";
        mFirestore.collectionGroup(collectionId).orderBy("tutorName").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (DocumentSnapshot snapshot : queryDocumentSnapshots)
                                mTutorInfo.add(snapshot.toObject(TutorDetails.class));
                        } else {
                            Toast.makeText(MajorActivity.this,"No data found.",Toast.LENGTH_LONG).show();


                        }
                        populate();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MajorActivity.this,"Something went terribly wrong." + e,Toast.LENGTH_LONG).show();
                    }
                });

    }
    private void populate() {
        mTutorsAdapter = new TutorsAdapter(mTutorInfo);
        mTutorsAdapter.setHasStableIds(true);
        mTutorsAdapter.notifyDataSetChanged();
        rvTutors.setAdapter(mTutorsAdapter);
    }
}