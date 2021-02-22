package com.derich.tutorme;

public class TutorDetails {
    private String tutorName;
    private String tutorSubjects;
    private String tutorClasses;
    private String tutorEmail;
    private String tutorPhone;

    public TutorDetails() {
    }

    public TutorDetails(String tutorName, String tutorSubjects, String tutorClasses, String tutorEmail,String tutorPhone) {
        this.tutorName = tutorName;
        this.tutorPhone=tutorPhone;
        this.tutorSubjects = tutorSubjects;
        this.tutorClasses = tutorClasses;
        this.tutorEmail = tutorEmail;
    }

    public String getTutorPhone() {
        return tutorPhone;
    }

    public void setTutorPhone(String tutorPhone) {
        this.tutorPhone = tutorPhone;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getTutorSubjects() {
        return tutorSubjects;
    }

    public void setTutorSubjects(String tutorSubjects) {
        this.tutorSubjects = tutorSubjects;
    }

    public String getTutorClasses() {
        return tutorClasses;
    }

    public void setTutorClasses(String tutorClasses) {
        this.tutorClasses = tutorClasses;
    }

    public String getTutorEmail() {
        return tutorEmail;
    }

    public void setTutorEmail(String tutorEmail) {
        this.tutorEmail = tutorEmail;
    }
}
