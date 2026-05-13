package com.mycompany.thei.hr.system.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class FeePaymentRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amountPaid;

    @Temporal(TemporalType.DATE)
    private Date paymentDate;

    @ManyToOne
    @JoinColumn(name = "ENROLLMENT_ID")
    private TrainingEnrollment trainingEnrollment;

    public FeePaymentRecord() {
        this.paymentDate = new Date();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(double amountPaid) { this.amountPaid = amountPaid; }

    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }

    public TrainingEnrollment getTrainingEnrollment() { return trainingEnrollment; }
    public void setTrainingEnrollment(TrainingEnrollment trainingEnrollment) { this.trainingEnrollment = trainingEnrollment; }
}
