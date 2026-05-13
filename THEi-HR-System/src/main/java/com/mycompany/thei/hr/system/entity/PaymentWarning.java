package com.mycompany.thei.hr.system.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class PaymentWarning implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ENROLLMENT_ID")
    private TrainingEnrollment associatedEnrollment;

    private String warningMessage;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date warningDate;

    // For simplicity, we could also just store boolean isResolved.
    private boolean active = true;

    public PaymentWarning() {
        this.warningDate = new Date();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TrainingEnrollment getAssociatedEnrollment() { return associatedEnrollment; }
    public void setAssociatedEnrollment(TrainingEnrollment associatedEnrollment) { this.associatedEnrollment = associatedEnrollment; }

    public String getWarningMessage() { return warningMessage; }
    public void setWarningMessage(String warningMessage) { this.warningMessage = warningMessage; }

    public Date getWarningDate() { return warningDate; }
    public void setWarningDate(Date warningDate) { this.warningDate = warningDate; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
