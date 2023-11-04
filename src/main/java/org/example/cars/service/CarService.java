package org.example.cars.service;

import org.example.cars.exceptions.CarAlreadyExistsException;
import org.example.cars.exceptions.DateExceededException;
import org.example.cars.exceptions.IncorrectLicenseNumberException;
import org.example.cars.model.Car;
import org.example.cars.repository.CarRepository;

import java.time.LocalDate;
import java.util.*;

public class CarService implements Service {
    private CarRepository carRepository = new CarRepository();

    @Override
    public void add(Car car) {
        LocalDate maximumValidityPeriod = LocalDate.now().plusYears(1);
        loadPlaceLicenseNumbers();
        try {
            if (maximumValidityPeriod.isBefore(car.getInsuranceDate())) {
                final String dateExceptionMessage = "Ważność daty ubezpieczenia nie może być większa niż rok od dzisiejszej daty";
                System.err.println(dateExceptionMessage);
                throw new DateExceededException(dateExceptionMessage);
            }
        } catch (NullPointerException e) {
            throw new RuntimeException("Pole z datą ubezpieczenia nie może być puste");
        }

        try {
            if (maximumValidityPeriod.isBefore(car.getServiceDate())) {
                final String dateExceptionMessage = "Ważność daty przeglądu nie może być większa niż rok od dzisiejszej daty";
                System.err.println(dateExceptionMessage);
                throw new DateExceededException(dateExceptionMessage);
            }
        } catch (NullPointerException e) {
            throw new RuntimeException("Pole z datą przeglądu nie może być puste");
        }

        if (isCarAlreadyExists(car)) {
            final String carExistsExceptionMessage = "Auto o podanych danych już istnieje w bazie danych";
            System.err.println(carExistsExceptionMessage);
            throw new CarAlreadyExistsException(carExistsExceptionMessage);
        }
        if (!isLicenseNumberCorrect(car)) {
            final String incorrectLicenseExceptionMessage = "Nieprawidłowy numer tablicy rejestracyjnej";
            System.err.println(incorrectLicenseExceptionMessage);
            throw new IncorrectLicenseNumberException(incorrectLicenseExceptionMessage);
        }
        if (!areAllFieldsCompleted(car)) {
            final String requiredInfoMessage = "Wszystkie pola są wymagane. Wprowadź poprawnie dane";
            System.err.println(requiredInfoMessage);
            throw new RuntimeException(requiredInfoMessage);
        }
        if (!isManufactureYearCorrect(car)) {
            String message = "Rok produkcji nie może być późniejszy niz aktualny rok";
            System.err.println(message);
            throw new DateExceededException(message);
        }
        carRepository.addCar(car);
    }

    private boolean isManufactureYearCorrect(Car car) {
        return LocalDate.now().getYear() >= car.getManufactureYear();

    }

    private boolean areAllFieldsCompleted(Car car) {
        return !(car.getBrand() == null || car.getBrand().matches("^\\s*$") || car.getModel() == null || car.getModel().matches("^\\s*$") ||
                car.getManufactureYear() == 0 || car.getServiceDate() == null || car.getInsuranceDate() == null || car.getLicenseNumber() == null);
    }

    @Override
    public void delete(Car car) {
        carRepository.delete(car);
    }

    public List<Car> getCarsWithServiceDateExpiring() {
        List<Car> carsWithServiceDateExpiring = CarRepository.getCars().stream()
                .filter(car -> LocalDate.now().plusMonths(1).isAfter(car.getServiceDate()))
                .toList();
        return carsWithServiceDateExpiring;
    }

    public List<Car> getCarsWithInsuranceDateExpiring() {
        List<Car> carsWithInsuranceDateExpiring = CarRepository.getCars().stream()
                .filter(car -> LocalDate.now().plusMonths(1).isAfter(car.getInsuranceDate()))
                .toList();
        return carsWithInsuranceDateExpiring;
    }

    public List<Car> getCarsWithServiceDateExpiringByMonth(int month) {
        List<Car> carsWithServiceDateExpiringByMonth = CarRepository.getCars().stream()
                .filter(car -> car.getServiceDate().getMonthValue() == month)
                .toList();
        return carsWithServiceDateExpiringByMonth;
    }

    public List<Car> getCarsWithInsuranceDateExpiringByMonth(int month) {
        List<Car> carsWithInsuranceDateExpiringByMonth = CarRepository.getCars().stream()
                .filter(car -> car.getInsuranceDate().getMonthValue() == month)
                .toList();
        return carsWithInsuranceDateExpiringByMonth;
    }

    public List<Car> getCarsByBrand(String brand) {
        List<Car> carsByBrand = CarRepository.getCars().stream()
                .filter(car -> car.getBrand().toLowerCase().contains(brand))
                .toList();
        return carsByBrand;
    }

    public List<Car> getCarsByModel(String model) {
        List<Car> carsByModel = CarRepository.getCars().stream()
                .filter(car -> car.getModel().toLowerCase().contains(model))
                .toList();
        return carsByModel;
    }

    public List<Car> getCarsSortedByBrandName() {
        List<Car> carsToSort = new ArrayList<>(CarRepository.getCars());
        Collections.sort(carsToSort);
        return carsToSort;
    }

    public void interruptWriteThread() {
        carRepository.getFileWriteThread().interrupt();
    }

    private boolean isLicenseNumberCorrect(Car car) {
        String licenseNumber = car.getLicenseNumber();
        boolean isPrefixCorrect = validateLicensePrefix(licenseNumber);
        return licenseNumber.length() > 6 && licenseNumber.length() < 9 && isPrefixCorrect;
    }

    private boolean validateLicensePrefix(String licenseNumber) {
        for (String licensePrefix : CarRepository.getPlateLicensePl()) {
            if (licenseNumber.startsWith(licensePrefix)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCarAlreadyExists(Car car) {
        for (Car existingCar : CarRepository.getCars()) {
            if (existingCar.equals(car)) {
                return true;
            }
        }
        return false;
    }

    private void loadPlaceLicenseNumbers() {
        if (CarRepository.getPlateLicensePl() == null) {
            CarRepository.readLicensePrefixFromFile();
        }
    }
}
