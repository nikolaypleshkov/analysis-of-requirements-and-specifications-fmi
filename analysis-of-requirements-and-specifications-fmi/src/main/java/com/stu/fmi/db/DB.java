package com.stu.fmi.db;

import com.stu.fmi.model.Appointment;
import com.stu.fmi.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class DB {
    private List<Appointment> appointments;
    private List<Customer> customers;

    public DB(List<Appointment> appointments, List<Customer> customers) {
        this.appointments = appointments != null ? appointments : new ArrayList<>();
        this.customers = customers != null ? customers : new ArrayList<>();
    }
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

}
