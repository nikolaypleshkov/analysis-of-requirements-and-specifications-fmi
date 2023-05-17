package com.stu.fmi.appointment;

import com.stu.fmi.model.Customer;
import com.stu.fmi.controller.BookAppointmentController;
import com.stu.fmi.db.DB;
import com.stu.fmi.model.Appointment;
import com.stu.fmi.utils.MockCustomer;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AppointmentBookingSteps {
    private BookAppointmentController bookAppointmentController;
    private DB db;
    MockCustomer mockUser;

    private Customer customer;
    private Appointment appointment;
    private String message;
    private boolean appointmentSuccessful;

    @Before
    public void setup() {
        List<Appointment> appointments = new ArrayList<>();
        mockUser = new MockCustomer("nikolay", "pleshkov", "stu2001321014@uni-plovdiv.bg", "359887743456");
        ArrayList<Customer> userList = new ArrayList<>();
        userList.add(mockUser);

        db = new DB(appointments, userList);

        bookAppointmentController = new BookAppointmentController(db);
    }

    @Given("Отваряме екрана за запазване на часове")
    public void openAppointmentBookingScreen() {
        appointment = new Appointment();
        customer = new Customer();
    }

    private List<LocalDateTime> getAvailableDateTimes() {
        List<LocalDateTime> availableDateTimes = new ArrayList<>();

        // Add some example available date and time slots
        availableDateTimes.add(LocalDateTime.now().plusDays(1).withHour(9).withMinute(0));
        availableDateTimes.add(LocalDateTime.now().plusDays(1).withHour(10).withMinute(30));
        availableDateTimes.add(LocalDateTime.now().plusDays(2).withHour(14).withMinute(0));
        availableDateTimes.add(LocalDateTime.now().plusDays(2).withHour(15).withMinute(30));

        return availableDateTimes;
    }

        @When("Избираме налична дата и час")
    public void selectAvailableDateTime() {
        LocalDateTime selectedDateTime = null;

        // Implement the logic to select an available date and time
        List<LocalDateTime> availableDateTimes = getAvailableDateTimes();

        if (availableDateTimes.isEmpty()) {
            throw new RuntimeException("No available date and time slots found");
        }

        // Choose the first available date and time slot
        selectedDateTime = availableDateTimes.get(0);

        // Store the selected date and time in the appointment object
        appointment.setAppointmentDate(selectedDateTime);
    }

    @And("Въвеждаме име: {string}")
    public void enterFirstName(String firstName) {
        customer.setFirstName(firstName);
    }

    @And("Въвеждаме фамилия: {string}")
    public void enterLastName(String lastName) {
        customer.setLastName(lastName);
    }

    @And("Въвеждаме имейл: {string}")
    public void enterEmail(String email) {
        customer.setEmail(email);
    }

    @And("Въвеждаме телефонен номер: {string}")
    public void enterPhoneNumber(String phoneNumber) {
        customer.setPhone(phoneNumber);
    }

    @And("Имаме запазен час")
    public void hasValidAppoint(){
        bookAppointmentController.hasValidAppointment(customer);
    }

    @And("Имаме вече запазен час със съществуващ имейл")
    public void alreadyHaveAppointment(){
        bookAppointmentController.hasValidAppointment(customer);
    }

    @And("Виждаме лист с налични дати")
    public void getAvailableDates(){
        bookAppointmentController.getAvailableDates();
    }
    
    @And("Натистем бутона за запазване на час")
    public void clickBookAppointmentButton() {
        MockCustomer newMock = new MockCustomer("nikolay", "stu2001321014@uni-plovdiv.bg");
        message = bookAppointmentController.bookAppointment(appointment.getAppointmentDate(), customer);
    }

    @Then("Виждаме съобщение {string}")
    public void seeMessage(String expectedMessage) {
        assertEquals(expectedMessage, message);
    }

    @And("Получаваме имейл за потвърждение")
    public void receiveConfirmationEmail() {
        // Implement the logic to receive the confirmation email
    }

    @But("Часът не е запазен успешно")
    public void appointmentNotSuccessful() {
        assertFalse(appointmentSuccessful);
    }

    @And("Часът е запазен успешно")
    public void appointmentSuccessful() {
        assertTrue(appointmentSuccessful);
    }
}
