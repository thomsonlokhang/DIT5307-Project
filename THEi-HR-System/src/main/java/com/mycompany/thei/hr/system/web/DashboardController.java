package com.mycompany.thei.hr.system.web;

import com.mycompany.thei.hr.system.ejb.CrudServiceBean;
import com.mycompany.thei.hr.system.entity.PaymentWarning;
import com.mycompany.thei.hr.system.entity.TrainingEnrollment;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named("dashboardController")
@ViewScoped
public class DashboardController implements Serializable {

    @EJB
    private CrudServiceBean crudService;

    private List<PaymentWarning> activeWarnings;
    private List<TrainingEnrollment> allEnrollments;

    @PostConstruct
    public void init() {
        refreshDashboard();
    }

    public void refreshDashboard() {
        activeWarnings = crudService.getActiveWarnings();
        // Sort warnings by days remaining until cancellation (ascending)
        activeWarnings.sort((w1, w2) -> {
            long days1 = calculateDaysToCancellation(w1.getAssociatedEnrollment());
            long days2 = calculateDaysToCancellation(w2.getAssociatedEnrollment());
            return Long.compare(days1, days2);
        });
        
        allEnrollments = crudService.getAllEnrollments();
    }

    private long calculateDaysToCancellation(TrainingEnrollment enrollment) {
        if (enrollment == null || enrollment.getTrainingStartDate() == null) {
            return 0;
        }
        long millisInDay = 1000 * 60 * 60 * 24;
        long cancellationBuffer = 21 * millisInDay; // Cancellation is 21 days before start date
        long cancellationTimestamp = enrollment.getTrainingStartDate().getTime() - cancellationBuffer;
        long timeDiff = cancellationTimestamp - new Date().getTime();
        return timeDiff / millisInDay;
    }

    public String getDaysRemainingMessage(TrainingEnrollment enrollment) {
        long days = calculateDaysToCancellation(enrollment);
        if (enrollment.isPaymentOverdue() && days < 0) {
            return "OVERDUE – Cancellation Pending";
        } else if (enrollment.isPaymentOverdue()) {
            return "OVERDUE – Payment Required";
        } else if (days < 0) {
            return "WARNING – Start Date approaching";
        }
        return days + " days until cancellation";
    }

    public List<PaymentWarning> getActiveWarnings() {
        return activeWarnings;
    }

    public List<TrainingEnrollment> getAllEnrollments() {
        return allEnrollments;
    }
}
