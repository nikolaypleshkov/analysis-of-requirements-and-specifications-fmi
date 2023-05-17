package com.stu.fmi.controller;

import com.stu.fmi.db.DB;
import com.stu.fmi.model.Appointment;
import com.stu.fmi.model.Customer;

import java.time.LocalDateTime;
import java.util.*;

public class BookAppointmentController {
    private DB db;
    private String message;

    // Rest of the class implementation

    public String getMessage() {
        return message;
    }

    public BookAppointmentController(DB db) {
        this.db = db;
    }

    public List<Appointment> getAllAppointments() {
        return db.getAppointments();
    }

    public List<LocalDateTime> getAvailableDates() {
        List<Appointment> appointments = db.getAppointments();

        List<LocalDateTime> availableDates = new ArrayList<>();
        LocalDateTime currentDateTime = LocalDateTime.now();

        while (availableDates.size() < 10) {
            if (!isAppointmentBooked(appointments, currentDateTime)) {
                availableDates.add(currentDateTime);
            }
            currentDateTime = currentDateTime.plusHours(1);
        }

        return availableDates;
    }

    public boolean isAppointmentBooked(List<Appointment> appointments, LocalDateTime dateTime) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentDate().equals(dateTime)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasValidAppointment(Customer customer) {
        List<Appointment> appointments = db.getAppointments();
        LocalDateTime currentDateTime = LocalDateTime.now();

        for (Appointment appointment : appointments) {
            if (appointment.getCustomer().equals(customer) && appointment.getAppointmentDate().isAfter(currentDateTime)) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidCustomer(Customer customer) {
        // Validate if all necessary information is provided
        return customer.getEmail() != null && !customer.getEmail().isEmpty() &&
                customer.getFirstName() != null && !customer.getFirstName().isEmpty() &&
                customer.getLastName() != null && !customer.getLastName().isEmpty() &&
                customer.getPhone() != null && !customer.getPhone().isEmpty();
    }

    public String verifyAppointment(Appointment appointment, String verificationCode) {
        if(verificationCode == null){
            return "Въведете валиден код";
        }
        if (appointment.isVerified()) {
            return "Вече имате потвърдена среща";
        }

        if (verificationCode.equals(appointment.getVerificationCode())) {
            appointment.setVerified(true);
            return "Успешно потвърден час";
        } else {
            return "Грешен код за потвърждение";
        }
    }

    public String bookAppointment(LocalDateTime dateTime, Customer customer) {
        if (isAppointmentBooked(db.getAppointments(), dateTime)) {
            message =  "Няма налични часове за избраната дата";
            return "Няма налични часове за избраната дата";
        }

        if (hasValidAppointment(customer)) {
            message = "Вече имате запазен час";
            return "Вече имате запазен час";
        }

        if (!isValidCustomer(customer)) {
            message = "Трябва да въведете валидна информация за вас";
            return "Трябва да въведете валидна информация за вас";
        }

        Appointment appointment = new Appointment(dateTime, customer, false, "");
        db.addAppointment(appointment);
        return appointment.getCustomer().sendVerificationCode();
    }
}
