package org.example.examResults;

import org.example.examResults.exceptions.ExamMaxPointsException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExamResultControllerTest {

    @Test
    void shouldThrowExceptionWhenStudentPointsAreHigherThanExamPoints() {
        ExamResultController examResultController = new ExamResultController();
        List<Student> students = new ArrayList<>();
        Exam exam = new Exam(10, students);
        students.add(new Student(1, 11, exam));

        assertThrows(ExamMaxPointsException.class, () -> examResultController.readEachStudentResult(students, 1, exam));
    }

}