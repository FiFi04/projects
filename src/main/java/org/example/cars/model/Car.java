package org.example.cars.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Car implements Comparable<Car>{
    public final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private String brand;
    private String model;
    private int manufactureYear;
    private LocalDate serviceDate;
    private LocalDate insuranceDate;
    private String licenseNumber;


    public Car(String brand, String model, int manufactureYear, LocalDate serviceDate, LocalDate insuranceDate,
               String licenseNumber) {
        this.brand = brand;
        this.model = model;
        this.manufactureYear = manufactureYear;
        this.serviceDate = serviceDate;
        this.insuranceDate = insuranceDate;
        this.licenseNumber = licenseNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Marka: ");
        sb.append(brand);
        sb.append(", model: ");
        sb.append(model);
        sb.append(", rok produkcji: ");
        sb.append(manufactureYear);
        sb.append(", ważność przeglądu: ");
        sb.append(serviceDate);
        sb.append(", ważność ubezpieczenia OC: ");
        sb.append(insuranceDate);
        sb.append(", nr rejestracyjny: ");
        sb.append(licenseNumber);
        return sb.toString();
    }

    public String getFileDescriptionFormat() {
        final String separator = ";";
        StringBuilder sb = new StringBuilder();
        sb.append(brand);
        sb.append(separator);
        sb.append(model);
        sb.append(separator);
        sb.append(manufactureYear);
        sb.append(separator);
        sb.append(serviceDate.format(DATE_FORMAT));
        sb.append(separator);
        sb.append(insuranceDate.format(DATE_FORMAT));
        sb.append(separator);
        sb.append(licenseNumber);
        return sb.toString();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }

    public LocalDate getInsuranceDate() {
        return insuranceDate;
    }

    public void setInsuranceDate(LocalDate insuranceDate) {
        this.insuranceDate = insuranceDate;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    @Override
    public int compareTo(Car otherCar) {
        if (this.brand == null) {
            return -1;
        } else if (otherCar.brand == null) {
            return 1;
        } else {
            return this.brand.compareTo(otherCar.brand);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return Objects.equals(licenseNumber, car.licenseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licenseNumber);
    }
}
