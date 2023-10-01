package org.example.cars.repository;

import org.example.cars.model.Car;
import org.example.cars.view.MainCarFx;

import java.io.*;
import java.util.List;

public class FileWriteThread extends Thread {
    private final String TABLE_HEADERS = "MARKA;MODEL;ROK;BADANIE;OC;NR_REJ";
    private File carsFile;
    private List<Car> cars;
    private List<Car> lastSaveCars;

    public FileWriteThread(File file, List<Car> cars) {
        this.carsFile = file;
        this.cars = cars;
    }

    @Override
    public void run() {
        while (MainCarFx.isProgramRunning) {
            if (!CarRepository.getCars().equals(lastSaveCars)) {
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    if (!CarRepository.getCars().equals((lastSaveCars))) {
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(carsFile))) {
                            writer.write(TABLE_HEADERS);
                            writer.newLine();
                            for (Car car : cars) {
                                writer.write(car.getFileDescriptionFormat());
                                writer.newLine();
                            }
                            System.out.println("Zapisano plik przed zakończemiem programu");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(carsFile))) {
                    writer.write(TABLE_HEADERS);
                    writer.newLine();
                    for (Car car : cars) {
                        writer.write(car.getFileDescriptionFormat());
                        writer.newLine();
                    }
                    System.out.println("Pomyślnie zaktualizowano listę aut w pliku. Wątek: " + this.getId());
                } catch (FileNotFoundException e) {
                    System.err.println("Nie znaleziono pliku o nazwie " + carsFile.getName());
                    e.printStackTrace();
                } catch (IOException e) {
                    System.err.println("Błąd zapisu");
                    e.printStackTrace();
                }
                lastSaveCars = List.copyOf(CarRepository.getCars());
            }
        }
    }
}
