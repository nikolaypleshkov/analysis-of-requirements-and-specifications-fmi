package com.stu.fmi.verification;

import com.stu.fmi.controller.BookAppointmentController;
import com.stu.fmi.db.DB;
import com.stu.fmi.model.Appointment;
import com.stu.fmi.model.Customer;
import com.stu.fmi.utils.MockCustomer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class VerificationSteps {
    private BookAppointmentController bookAppointmentController;

    MockCustomer mockUser;

    Appointment appointment;
    LocalDateTime appointmentDate = LocalDateTime.now().plusDays(1); // Example appointment date

    private String verificationCode;
    private String message;

    private String confirmationCode;

    @Given("Получили сме код за потвърждение")
    public void getVerficationCode() {
        mockUser = new MockCustomer("nikolay", "pleshkov", "stu2001321014@uni-plovdiv.bg", "359887743456");
        ArrayList<Customer> userList = new ArrayList<>();
        userList.add(mockUser);

        DB db = new DB(null,userList);
        bookAppointmentController = new BookAppointmentController(db);
        verificationCode = generateVerificationCode();
    }

    @When("Въведем правилния код за потвърждение")
    public void enteringCorrectCode() {
        Appointment appointment = new Appointment();
        appointment.setVerificationCode(verificationCode);
        bookAppointmentController.verifyAppointment(appointment, verificationCode);
        message = bookAppointmentController.getMessage();
    }

    @Then("Виждаме съобщение за потвърден час {string}")
    public void successfulAppointment(String message) {
        Assert.assertEquals("Успешно потвърден час", message);
    }

    @Then("Маркираме срещата като потвърдена")
    public void appointmentVerified() {
        Appointment appointment = new Appointment();
        Assert.assertTrue(appointment.isVerified());
    }

    @When("Въведем неправилния код за потвърждение")
    public void enteringWrongCode() {
        String invalidCode = "123456";
        Appointment appointment = new Appointment();
        appointment.setVerificationCode(verificationCode);
        bookAppointmentController.verifyAppointment(appointment, invalidCode);
        message = bookAppointmentController.getMessage();
    }

    @Then("Виждаме съобщение за грешен код {string}")
    public void wrongCodeErrorMessage(String message) {
        Assert.assertEquals("Грешен код за потвърждение", message);
    }

    @Given("Маркиме срещата като потвърдена")
    public void alreadyHaveAppointment() {
        Appointment appointment = new Appointment();
        appointment.setVerified(true);
        bookAppointmentController.bookAppointment(appointment.getAppointmentDate(), mockUser);
    }

    @Then("Виждаме за вече потвърдена среща {string}")
    public void alreadyHaveAppointmentMessage(String message) {
        Assert.assertEquals("Вече имате потвърдена среща", message);
    }

    @Given("Имаме потвърдена среща")
    public void givenHaveConfirmedAppointment() {
        // Assuming appointment is already confirmed
        appointment = new Appointment();
        appointment.setVerified(true);
    }

    @When("Въведем код за потвърждение")
    public void whenEnterConfirmationCode() {
        // Assuming the code entered is the same as the confirmation code
        appointment.setVerificationCode(confirmationCode);
    }

@When("Изпращаме формуляра без да въвеждаме код")
    public void sendingEmptyForm() {
        Appointment appointment = new Appointment();
        bookAppointmentController.verifyAppointment(appointment, mockUser.getVerificationCode());
        message = bookAppointmentController.getMessage();
    }

    @Then("Виждаме съобщение за невалиден код {string}")
    public void emptyFormCode(String message) {
        Assert.assertEquals("Въведете валиден код", message);
    }

    @Given("Не сме получили код за потвърждение")
    public void codeIsNotSend() {
        verificationCode = null;
    }

    @When("Избираме повторно изпращане на кода за потвърждение")
    public void sendCodeAgain() {
        verificationCode = generateVerificationCode();
    }

    @Then("Получаваме код за потвърждение на имейла")
    public void verificationCodeSend() {
        Assert.assertNotNull(verificationCode);
    }

    private String generateVerificationCode() {
        // Implement your logic to generate the verification code
        return "123456";
    }
}