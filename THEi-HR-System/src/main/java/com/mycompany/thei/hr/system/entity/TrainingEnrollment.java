package com.mycompany.thei.hr.system.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class TrainingEnrollment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String programName;
    private String programDescription;

    @Temporal(TemporalType.DATE)
    private Date enrollmentDate;

    @Temporal(TemporalType.DATE)
    private Date trainingStartDate;

    private double totalTrainingFee;

    private boolean paymentOverdue;
    private boolean fullyPaid;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ENROLLMENT_EMPLOYEE",
            joinColumns = @JoinColumn(name = "ENROLLMENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
    private List<Employee> enrolledEmployees;

    @OneToMany(mappedBy = "trainingEnrollment", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<FeePaymentRecord> feePaymentRecords;

    public TrainingEnrollment() {
        this.enrolledEmployees = new ArrayList<>();
        this.feePaymentRecords = new ArrayList<>();
        this.fullyPaid = false;
    }

    public void addEmployee(Employee employee) {
        if (this.enrolledEmployees == null) {
            this.enrolledEmployees = new ArrayList<>();
        }
        this.enrolledEmployees.add(employee);
    }

    public void addPayment(FeePaymentRecord record) {
        if (this.feePaymentRecords == null) {
            this.feePaymentRecords = new ArrayList<>();
        }
        record.setTrainingEnrollment(this);
        this.feePaymentRecords.add(record);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProgramName() { return programName; }
    public void setProgramName(String programName) { this.programName = programName; }

    public String getProgramDescription() { return programDescription; }
    public void setProgramDescription(String programDescription) { this.programDescription = programDescription; }

    public Date getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(Date enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    public Date getTrainingStartDate() { return trainingStartDate; }
    public void setTrainingStartDate(Date trainingStartDate) { this.trainingStartDate = trainingStartDate; }

    public double getTotalTrainingFee() { return totalTrainingFee; }
    public void setTotalTrainingFee(double totalTrainingFee) { this.totalTrainingFee = totalTrainingFee; }

    public boolean isPaymentOverdue() { return paymentOverdue; }
    public void setPaymentOverdue(boolean paymentOverdue) { this.paymentOverdue = paymentOverdue; }

    public boolean isFullyPaid() { return fullyPaid; }
    public void setFullyPaid(boolean fullyPaid) { this.fullyPaid = fullyPaid; }

    public List<Employee> getEnrolledEmployees() { return enrolledEmployees; }
    public void setEnrolledEmployees(List<Employee> enrolledEmployees) { this.enrolledEmployees = enrolledEmployees; }

    public List<FeePaymentRecord> getFeePaymentRecords() { return feePaymentRecords; }
    public void setFeePaymentRecords(List<FeePaymentRecord> feePaymentRecords) { this.feePaymentRecords = feePaymentRecords; }
}
