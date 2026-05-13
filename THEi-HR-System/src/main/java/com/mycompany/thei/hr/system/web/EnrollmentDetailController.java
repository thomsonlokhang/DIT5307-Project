package com.mycompany.thei.hr.system.web;

import com.mycompany.thei.hr.system.ejb.CrudServiceBean;
import com.mycompany.thei.hr.system.entity.FeePaymentRecord;
import com.mycompany.thei.hr.system.entity.TrainingEnrollment;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("enrollmentDetailController")
@ViewScoped
public class EnrollmentDetailController implements Serializable {

    @EJB
    private CrudServiceBean crudService;

    private Long enrollmentId;
    private TrainingEnrollment enrollment;
    private double newPaymentAmount;

    /**
     * Finds and loads the target TrainingEnrollment based on the context ID.
     */
    public void loadEnrollment() {
        if (enrollmentId != null) {
            this.enrollment = crudService.findEnrollmentById(enrollmentId);
        }
    }

    /**
     * Iterates through the list of partial payments tracked in FeePaymentRecord and sums them up.
     * @return Total amount paid towards this specific course.
     */
    public double getTotalPaid() {
        if (enrollment == null || enrollment.getFeePaymentRecords() == null) return 0.0;
        double total = 0.0;
        for (FeePaymentRecord record : enrollment.getFeePaymentRecords()) {
            total += record.getAmountPaid();
        }
        return total;
    }

    /**
     * Derives exactly how much money remains unpaid.
     * @return double representation of the remaining financial balance.
     */
    public double getRemainingBalance() {
        if (enrollment == null) return 0.0;
        return Math.max(0.0, enrollment.getTotalTrainingFee() - getTotalPaid());
    }

    /**
     * Flags the database enrollment record as fully paid unconditionally, 
     * dismisses overdue status, and erases lingering EJB payment warnings.
     * @return JSF refresh redirect parameter set.
     */
    public String markAsFullyPaid() {
        if (enrollment != null) {
            enrollment.setFullyPaid(true);
            enrollment.setPaymentOverdue(false); // Also reset the overdue flag
            crudService.updateTrainingEnrollment(enrollment);
            crudService.deactivateWarningForEnrollment(enrollment.getId());
        }
        return "enrollmentDetail?id=" + enrollmentId + "&faces-redirect=true";
    }

    /**
     * Applies a discrete partial dollar amount to the billing record.
     * Detects if the sub-payment fully clears the debt.
     * @return JSF refresh redirect parameter set.
     */
    public String addPayment() {
        if (enrollment != null && newPaymentAmount > 0 && !enrollment.isFullyPaid()) {
            // Validate that we don't overpay significantly past balance or simply that it's not already paid
            FeePaymentRecord newRecord = new FeePaymentRecord();
            newRecord.setAmountPaid(newPaymentAmount);
            
            // The addPayment helper method in the entity handles the relationship
            enrollment.addPayment(newRecord);
            
            // Calculate total paid so far
            double totalPaid = getTotalPaid();

            // Auto-update to fully paid if total payments equal or exceed the total fee
            if (totalPaid >= enrollment.getTotalTrainingFee()) {
                enrollment.setFullyPaid(true);
                enrollment.setPaymentOverdue(false);
                crudService.deactivateWarningForEnrollment(enrollment.getId());
            }

            crudService.updateTrainingEnrollment(enrollment);
            newPaymentAmount = 0; // Reset
        }
        return "enrollmentDetail?id=" + enrollmentId + "&faces-redirect=true";
    }

    // --- Getters and Setters ---

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public TrainingEnrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(TrainingEnrollment enrollment) {
        this.enrollment = enrollment;
    }

    public double getNewPaymentAmount() {
        return newPaymentAmount;
    }

    public void setNewPaymentAmount(double newPaymentAmount) {
        this.newPaymentAmount = newPaymentAmount;
    }
}
