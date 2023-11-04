package org.example.cars.repository;

import org.example.cars.model.Car;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileWriteThread extends Thread {
    private final String TABLE_HEADERS = "MARKA;MODEL;ROK;BADANIE;OC;NR_REJ";
    private boolean changedCarList = false;
    private List<Car> cars;
    private List<Car> addedCars = new ArrayList<>();
    private List<Car> deletedCars = new ArrayList<>();

    public FileWriteThread() {
        this.cars = CarRepository.getCars();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                System.out.println("Wątek został przerwany: ID wątku " + this.getId());
                break;
            } finally {
                if (changedCarList) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(CarRepository.getCarsFile()))) {
                        writer.write(TABLE_HEADERS);
                        writer.newLine();
                        for (Car car : cars) {
                            writer.write(car.getFileDescriptionFormat());
                            writer.newLine();
                        }
                        System.out.println("Pomyślnie zaktualizowano listę aut w pliku. Wątek: " + this.getId());
                        System.out.println("Dodane auta:" + addedCars);
                        System.out.println("Usunięte auta:" + deletedCars);
                        addedCars.clear();
                        deletedCars.clear();
                        changedCarList = false;
                    } catch (FileNotFoundException e) {
                        System.err.println("Nie znaleziono pliku o nazwie " + CarRepository.getCarsFile().getName());
                        e.printStackTrace();
                    } catch (IOException e) {
                        System.err.println("Błąd zapisu");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    void addNewCar(Car car) {
        addedCars.add(car);
    }

    void addDeletedCar(Car car) {
        deletedCars.add(car);
    }

    public boolean isChangedCarList() {
        return changedCarList;
    }

    public void setChangedCarList(boolean changedCarList) {
        this.changedCarList = changedCarList;
    }
}
