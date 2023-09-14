package org.example.examResults;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ExamTest {

    @Test
    void shouldReturnTwoPositionsForMode() {
        List<Student> students = new ArrayList<>();
        Exam exam = new Exam(10, students);
        students.add(new Student(1, 3, exam));
        students.add(new Student(2, 8, exam));
        students.add(new Student(3, 10, exam));
        students.add(new Student(4, 3, exam));
        students.add(new Student(5, 8, exam));
        students.add(new Student(6, 0, exam));

        Map<Integer, Integer> modeMap = exam.calculateMode();

        assertThat(modeMap).hasSize(2);
        assertThat(modeMap).containsEntry(3,2);
        assertThat(modeMap).containsEntry(8,2);
        assertThat(modeMap).doesNotContainEntry(0,1);
        assertThat(modeMap).doesNotContainEntry(10,1);
    }

    @Nested
    class ExamTestNestedBlock {
        Exam exam;

        @BeforeEach
        void initializeEntryData() {
            List<Student> students = new ArrayList<>();
            exam = new Exam(10, students);
            students.add(new Student(1, 3, exam));
            students.add(new Student(2, 0, exam));
            students.add(new Student(3, 10, exam));
            students.add(new Student(4, 6, exam));
            students.add(new Student(5, 8, exam));
        }

        @Test
        void shouldReturnAveragePointsFromExam() {
            double avgPoints = exam.calculateAvgPoints();

            assertThat(avgPoints).isEqualTo(5.4);
        }

        @Test
        void shouldReturnAveragePercentageFromExam() {
            int avgPercentage = exam.calculateAvgPercentage();

            assertThat(avgPercentage).isEqualTo(54);
        }

        @Test
        void shouldReturnMedianFromExam() {
            double median = exam.calculateMedian();

            assertThat(median).isEqualTo(6.0);
        }

        @Test
        void shouldReturnEmptyMapForMode() {
            Map<Integer, Integer> modeMap = exam.calculateMode();

            assertThat(modeMap).isEmpty();
        }
    }
}