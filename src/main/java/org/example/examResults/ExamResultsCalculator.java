package org.example.examResults;

import java.util.ArrayList;
import java.util.List;

public class ExamResultsCalculator {
    public static void main(String[] args) {
        ExamResultController examResultController = new ExamResultController();
        List<Student> students = new ArrayList<>();

        int numberOfStudents = examResultController.readNumberOfStudents();
        int examMaxPoints = examResultController.readExamMaxPoints();

        Exam exam = new Exam(examMaxPoints, students);

        examResultController.readEachStudentResult(students, numberOfStudents, exam);

        examResultController.printStudentInfo(students);
        examResultController.printExamInfo(exam);
    }

}
