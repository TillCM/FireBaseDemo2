package com.tillster.firebasedemo2;

public class FirebaseDataModel
{
        String numberofStudentsInCourse;
        String assignedLecturer;

        public FirebaseDataModel(){};

    public FirebaseDataModel(String numberofStudentsInCourse, String assignedLecturer)
    {
        this.numberofStudentsInCourse = numberofStudentsInCourse;
        this.assignedLecturer = assignedLecturer;
    }

    public String getNumberofStudentsInCourse() {
        return numberofStudentsInCourse;
    }

    public void setNumberofStudentsInCourse(String numberofStudentsInCourse) {
        this.numberofStudentsInCourse = numberofStudentsInCourse;
    }

    public String getAssignedLecturer() {
        return assignedLecturer;
    }

    public void setAssignedLecturer(String assignedLecturer) {
        this.assignedLecturer = assignedLecturer;
    }

    public String toString()
    {
        return "Students "+ numberofStudentsInCourse + "lecturer  " + assignedLecturer;
    }
}
