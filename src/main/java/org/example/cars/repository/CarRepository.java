package org.example.cars.repository;

import org.example.cars.model.Car;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CarRepository {
    private static final String CARS_FILE_PATH = "src/main/resources/carsApp/auta.txt";
    private static final String LICENSE_PLATES_FILE_PATH = "src/main/resources/carsApp/tablice.txt";
    private static File carsFile = new File(CARS_FILE_PATH).getAbsoluteFile();
    private static Set<String> PLATE_LICENSE_PL;
    private static List<Car> cars;

    static {
            cars = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(carsFile))) {
                reader.readLine(); // headers line
                while (reader.ready()) {
                    String[] carDetails = reader.readLine().split(";");
                    String brand = carDetails[0];
                    String model = carDetails[1];
                    int manufactureYear = Integer.parseInt(carDetails[2]);
                    LocalDate serviceDate = LocalDate.parse(carDetails[3], Car.DATE_FORMAT);
                    LocalDate insuranceDate = LocalDate.parse(carDetails[4], Car.DATE_FORMAT);
                    String registrationNumber = carDetails[5];
                    cars.add(new Car(brand, model, manufactureYear, serviceDate, insuranceDate, registrationNumber));
                }
            } catch (FileNotFoundException e) {
                System.err.println("Nie znaleziono pliku o nazwie " + carsFile.getName());
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("Błąd odczytu pliku");
                e.printStackTrace();
            }
        }

    public void addCar(Car car) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(carsFile, true))) {
            writer.newLine();
            writer.write(car.getFileDescriptionFormat());
            cars.add(car);
            System.out.println("Dane pojazdu dodano do bazy aut");
        } catch (IOException e) {
            System.err.println("Błąd zapisu danych");
            e.printStackTrace();
        }
    }

    public void delete(Car car) {
        final String CARS_TEMP_FILE_PATH = "src/main/resources/carsApp/autaTemp.txt";
        File carsTempFile = new File(CARS_TEMP_FILE_PATH).getAbsoluteFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(carsFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(carsTempFile))) {
            while (reader.ready()) {
                String line = reader.readLine();
                if (line.equals(car.getFileDescriptionFormat())) {
                    continue;
                }
                writer.write(line);
                if (reader.ready()) {
                    writer.newLine();
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Nie znaleziono pliku o nazwie " + carsFile.getName());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Błąd zapisu");
            e.printStackTrace();
        }
        cars.remove(car);
        boolean isDeleted = carsFile.delete();
        boolean isRenamed = carsTempFile.renameTo(new File("src/main/resources/carsApp/" + carsFile.getName()).getAbsoluteFile());
        if (isDeleted && isRenamed) {
            System.out.println("Pomyślnie usunięto dane auta: " + car);
        }
    }

    public static void readLicensePrefixFromFile() {
        PLATE_LICENSE_PL = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(LICENSE_PLATES_FILE_PATH))) {
            PLATE_LICENSE_PL.addAll((Arrays.stream(reader.readLine()
                    .split(";"))
                    .collect(Collectors.toSet())));
        } catch (IOException e) {
            System.err.println("Błąd odczytu pliku");
            e.printStackTrace();
        }
    }

    public static Set<String> getPlateLicensePl() {
        return PLATE_LICENSE_PL;
    }

    public static List<Car> getCars() {
        return cars;
    }
}
