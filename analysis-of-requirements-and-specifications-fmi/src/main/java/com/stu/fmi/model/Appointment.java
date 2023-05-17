package com.stu.fmi.model;
import java.time.LocalDateTime;

public class Appointment {
    private LocalDateTime appointmentDate;
    private Customer customer;
    private boolean verified;
    private String verificationCode;

    public Appointment() {
    }

    public Appointment(LocalDateTime appointmentDate, Customer customer, boolean verified, String verificationCode) {
        this.appointmentDate = appointmentDate;
        this.customer = customer;
        this.verified = verified;
        this.verificationCode = verificationCode;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentData=" + appointmentDate +
                ", customer=" + customer +
                '}';
    }
}
