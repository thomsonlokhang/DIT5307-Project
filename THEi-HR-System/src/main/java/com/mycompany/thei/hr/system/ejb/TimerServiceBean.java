package com.mycompany.thei.hr.system.ejb;

import com.mycompany.thei.hr.system.entity.PaymentWarning;
import com.mycompany.thei.hr.system.entity.TrainingEnrollment;

import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Date;
import java.util.List;

@Singleton
@Startup
public class TimerServiceBean {

    @Resource
    private TimerService timerService;

    @EJB
    private CrudServiceBean crudService;

    /**
     * Creates a timer to repeatedly check or warn about upcoming training payments.
     * Currently configured for a 30-second demo duration before triggering the timeout.
     * 
     * @param enrollment The training enrollment entity to attach to the timer
     */
    public void createPaymentWarningTimer(TrainingEnrollment enrollment) {
        // Production logic: 30 days before the training start date
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(enrollment.getTrainingStartDate());
        cal.add(java.util.Calendar.DAY_OF_YEAR, -30);
        Date timeoutDate = cal.getTime();
        
        // --- For Demo Video --- 
        // Uncomment the lines below to trigger the timer in 30 seconds for easy demonstration
        long demoDuration = 30 * 1000; 
        timeoutDate = new Date(System.currentTimeMillis() + demoDuration);
        
        // Pass the ID, not the whole object. It's safer and more efficient.
        timerService.createSingleActionTimer(timeoutDate, new TimerConfig(enrollment.getId(), true));
        System.out.println("Timer created for Enrollment ID: " + enrollment.getId() + " to expire on " + timeoutDate);
    }

    /**
     * Callback method executed when the created timer expires.
     * Checks if the enrollment is fully paid; if not, marks it as overdue and creates a warning.
     * 
     * @param timer The EJB Timer object containing the associated enrollment ID as its info
     */
    @Timeout
    public void timeout(Timer timer) {
        Long enrollmentId = (Long) timer.getInfo();
        System.out.println("Timer expired: Checking payment status for Enrollment ID: " + enrollmentId);

        // Fetch the enrollment directly by ID - much more efficient
        TrainingEnrollment enrollment = crudService.findEnrollmentById(enrollmentId);

        if (enrollment != null && !enrollment.isFullyPaid()) {
            // 1. Set the overdue flag as required
            enrollment.setPaymentOverdue(true);
            crudService.updateTrainingEnrollment(enrollment);
            
            // 2. Create the warning entity
            PaymentWarning warning = new PaymentWarning();
            warning.setAssociatedEnrollment(enrollment);
            warning.setWarningMessage("Payment for " + enrollment.getProgramName() + " is overdue.");
            warning.setActive(true);
            
            crudService.createWarning(warning);
            System.out.println("Payment warning created in DB for enrollment ID: " + enrollmentId);
        } else {
            System.out.println("Enrollment ID " + enrollmentId + " is fully paid or does not exist. No warning created.");
        }
    }
}

