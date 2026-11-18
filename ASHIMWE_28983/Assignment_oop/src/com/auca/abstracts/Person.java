package com.auca.abstracts;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;

public abstract class Person implements Identifiable, Registrable {
    private String personId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;

    public Person(String personId, String firstName, String lastName,
                  String email, String phoneNumber, LocalDate dateOfBirth) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public abstract String getRoleDescription();

    @Override
    public String getId() {
        return personId;
    }

    @Override
    public String getDisplayName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void validate() throws ValidationException {
        if (personId == null || personId.trim().isEmpty())
            throw new ValidationException("Person ID is required.");
        if (firstName == null || firstName.trim().length() < 2)
            throw new ValidationException("First name must have at least 2 characters.");
        if (lastName == null || lastName.trim().length() < 2)
            throw new ValidationException("Last name must have at least 2 characters.");
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
            throw new ValidationException("Invalid email address.");
        if (phoneNumber == null || !phoneNumber.matches("^\\+?\\d{10,15}$"))
            throw new ValidationException("Invalid phone number.");
        if (dateOfBirth == null || !dateOfBirth.isBefore(LocalDate.now()))
            throw new ValidationException("Date of birth must be in the past.");
    }

    @Override
    public void printDetails() {
        System.out.println(this);
    }

    @Override
    public String getRegistrationSummary() {
        return getRoleDescription() + ": " + getDisplayName() + " [" + personId + "]";
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Person ID: " + personId +
                "\nName: " + getDisplayName() +
                "\nEmail: " + email +
                "\nPhone: " + phoneNumber +
                "\nDate of Birth: " + dateOfBirth;
    }
}