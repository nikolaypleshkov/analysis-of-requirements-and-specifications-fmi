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

    private List<LocalDateTime> getAvailableDateTimes() {
        List<LocalDateTime> availableDateTimes = new ArrayList<>();

        availableDateTimes.add(LocalDateTime.now().plusDays(1).withHour(9).withMinute(0));
        availableDateTimes.add(LocalDateTime.now().plusDays(1).withHour(10).withMinute(30));
        availableDateTimes.add(LocalDateTime.now().plusDays(2).withHour(14).withMinute(0));
        availableDateTimes.add(LocalDateTime.now().plusDays(2).withHour(15).withMinute(30));

        return availableDateTimes;
    }

    @Before
    public void setup() {
        customer = new Customer();

        List<Appointment> appointments = new ArrayList<>();
        List<LocalDateTime> availableDateTimes = getAvailableDateTimes();

        mockUser = new MockCustomer("nikolay", "pleshkov", "stu2001321014@uni-plovdiv.bg", "359887743456");
        appointment = new Appointment(availableDateTimes.get(0), mockUser, false, "");

        ArrayList<Customer> userList = new ArrayList<>();
        userList.add(mockUser);


        db = new DB(appointments, userList);

        bookAppointmentController = new BookAppointmentController(db);
    }

    @Given("Отваряме екрана за запазване на часове")
    public void openAppointmentBookingScreen() {
        setup();
    }

        @When("Избираме налична дата и час")
    public void selectAvailableDateTime() {
        LocalDateTime selectedDateTime = null;

        List<LocalDateTime> availableDateTimes = getAvailableDateTimes();

        if (availableDateTimes.isEmpty()) {
            throw new RuntimeException("No available date and time slots found");
        }

        selectedDateTime = availableDateTimes.get(0);

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
        bookAppointmentController.hasValidAppointment(mockUser);
    }

    @And("Имаме вече запазен час със съществуващ имейл")
    public void alreadyHaveAppointment(){
        bookAppointmentController.hasValidAppointment(mockUser);
    }

    @And("Виждаме лист с налични дати")
    public void getAvailableDates(){
        bookAppointmentController.getAvailableDates();
    }
    
    @And("Натистем бутона за запазване на час")
    public void clickBookAppointmentButton() {
        message = bookAppointmentController.bookAppointment(appointment.getAppointmentDate(), mockUser);
    }

    @Then("Виждаме съобщение {string}")
    public void seeMessage(String expectedMessage) {
        assertEquals("Кода е изпратен: " + mockUser.getVerificationCode(), message);
    }

    @And("Получаваме имейл за потвърждение")
    public void receiveConfirmationEmail() {
        assertEquals("Кода е изпратен: " + mockUser.getVerificationCode(), message);
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
