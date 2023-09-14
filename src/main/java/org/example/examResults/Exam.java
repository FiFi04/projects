package org.example.examResults;

import java.util.*;
import java.util.stream.Collectors;

public class Exam {
    private int maxResult;
    private List<Student> studentsAttended;
    private double averagePoints;
    private int averagePercentage;
    private double median;
    private Map<Integer, Integer> mode;

    public Exam(int maxResult, List<Student> studentsAttended) {
        this.maxResult = maxResult;
        this.studentsAttended = studentsAttended;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public void setStudentsAttended(List<Student> studentsAttended) {
        this.studentsAttended = studentsAttended;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public List<Student> getStudentsAttended() {
        return studentsAttended;
    }

    public double getAveragePoints() {
        return averagePoints;
    }

    public double getAveragePercentage() {
        return averagePercentage;
    }

    public double getMedian() {
        return median;
    }

    public Map<Integer, Integer> getMode() {
        return mode;
    }

    public double calculateAvgPoints() {
        int studentsPoints = 0;
        for (Student student : studentsAttended) {
            studentsPoints += student.getResultPoints();
        }
        return averagePoints = studentsPoints / (double) studentsAttended.size();
    }

    public int calculateAvgPercentage() {
        return averagePercentage = (int) ((calculateAvgPoints() / maxResult) * 100);
    }

    public double calculateMedian() {
        List<Integer> studentsSortedPoints = studentsAttended.stream()
                .map(Student::getResultPoints)
                .sorted()
                .toList();
        int size = studentsAttended.size();
        if (size == 1) {
            return median = studentsSortedPoints.get(0);
        } else if (size == 2) {
            return median = ((double) studentsSortedPoints.get(0) + studentsSortedPoints.get(1)) / 2;
        } else if (size % 2 != 0) {
            return median = studentsSortedPoints.get(size / 2);
        } else {
            return median = ((double) studentsSortedPoints.get(size / 2 - 1) + studentsSortedPoints.get(size / 2)) / 2;
        }
    }

    public Map<Integer, Integer> calculateMode() {
        Map<Integer, Integer> numberOfOccurrences = getNumberOfOccurrences();
        numberOfOccurrences = sortByNumberOfOccurrences(numberOfOccurrences);

        Map<Integer, Integer> mostFrequentValues = new TreeMap<>();
        Iterator<Map.Entry<Integer, Integer>> entries = numberOfOccurrences.entrySet().iterator();
        Map.Entry<Integer, Integer> firstIndex = entries.next();
        mostFrequentValues.put(firstIndex.getKey(), firstIndex.getValue());
        boolean firstIteration = true;
        Map.Entry<Integer, Integer> current;
        Map.Entry<Integer, Integer> next = null;

        while (entries.hasNext()) {
            if (firstIteration) {
                current = firstIndex;
                firstIteration = false;
            } else {
                current = next;
            }
            next = entries.next();
            if (current.getValue().equals(next.getValue()) && current.getValue() > 1) {
                mostFrequentValues.put(next.getKey(), next.getValue());
            } else {
                break;
            }
        }
        return mode = mostFrequentValues;
    }

    private Map<Integer, Integer> getNumberOfOccurrences() {
        Map<Integer, Integer> numberOfOccurrences = new TreeMap<>();
        for (Student student : studentsAttended) {
            int resultPoints = student.getResultPoints();
            numberOfOccurrences.put(resultPoints, numberOfOccurrences.getOrDefault(resultPoints, 0) + 1);
        }
        return numberOfOccurrences;
    }

    private Map<Integer, Integer> sortByNumberOfOccurrences(Map<Integer, Integer> numberOfOccurrences) {
        numberOfOccurrences = numberOfOccurrences.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return numberOfOccurrences;
    }
}
