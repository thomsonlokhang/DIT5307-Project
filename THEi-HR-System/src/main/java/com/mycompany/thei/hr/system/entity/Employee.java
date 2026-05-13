package com.mycompany.thei.hr.system.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String lastName;

    @NotNull
    private String firstName;
    
    private String title;
    @Column(unique = true, nullable = false)
    private String employeeIdNumber;
    private String department;
    @Pattern(regexp = "^[0-9]{8}$", message="Mobile number must be 8 digits")
    private String mobilePhoneNumber;
    @Email(message="Invalid email format")
    private String emailAddress;
    @Column(unique = true, nullable = false)
    private String hkidNumber;

    public Employee() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getEmployeeIdNumber() { return employeeIdNumber; }
    public void setEmployeeIdNumber(String employeeIdNumber) { this.employeeIdNumber = employeeIdNumber; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getMobilePhoneNumber() { return mobilePhoneNumber; }
    public void setMobilePhoneNumber(String mobilePhoneNumber) { this.mobilePhoneNumber = mobilePhoneNumber; }

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public String getHkidNumber() { return hkidNumber; }
    public void setHkidNumber(String hkidNumber) { this.hkidNumber = hkidNumber; }
}

