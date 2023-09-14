package org.example.examResults;

public class Student {
    private int studentId;
    private int resultPoints;
    private int resultPercentage;

    public Student(int studentId, int resultPoints, Exam exam) {
        this.studentId = studentId;
        this.resultPoints = resultPoints;
        this.resultPercentage = (int) (resultPoints / (double) exam.getMaxResult() * 100);
    }

    public int getStudentId() {
        return studentId;
    }

    public int getResultPoints() {
        return resultPoints;
    }

    public void setResultPoints(int resultPoints) {
        this.resultPoints = resultPoints;
    }

    public int getResultPercentage() {
        return resultPercentage;
    }
}
