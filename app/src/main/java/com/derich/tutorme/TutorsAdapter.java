package com.derich.tutorme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TutorsAdapter extends RecyclerView.Adapter<TutorsAdapter.ViewHolder> {
    private Context mContext;
    List<TutorDetails> mTutorInfoList;
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private final FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

    public TutorsAdapter(List<TutorDetails> mTutorInfoList){
        this.mTutorInfoList = mTutorInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_all_available_tutors,parent,false);
        mContext = parent.getContext();
        return new TutorsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Locale locale = new Locale("en","KE");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        TutorDetails tutorDetails = mTutorInfoList.get(position);
        holder.tutorName.setText(tutorDetails.getTutorName());
        holder.tutorClasses.setText(tutorDetails.getTutorClasses());
        holder.tutorSubjects.setText(tutorDetails.getTutorSubjects());
        holder.tutorContact.setText(tutorDetails.getTutorPhone());
    }

    @Override
    public int getItemCount() {
        return mTutorInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView tutorName;
        private final TextView tutorSubjects;
        private final TextView tutorClasses;
        private final TextView tutorContact;
        ViewHolder(View itemView) {
            super(itemView);
            tutorName=itemView.findViewById(R.id.list_all_tutors_tutor_name);
            tutorSubjects=itemView.findViewById(R.id.tv_list_all_tutors_subjects);
            tutorClasses= itemView.findViewById(R.id.tv_list_all_tutors_classes);
            tutorContact= itemView.findViewById(R.id.tv_list_all_tutors_contact);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
//    public interface OnStageClickListener{
//        void onStageClick(MarkerInfo markerInfo);
//    }
}