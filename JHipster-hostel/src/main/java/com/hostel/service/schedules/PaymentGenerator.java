package com.hostel.service.schedules;

import com.hostel.config.Constants;
import com.hostel.domain.Payments;
import com.hostel.domain.RoomAllocation;
import com.hostel.domain.User;
import com.hostel.domain.enumeration.PaymentAgainstType;
import com.hostel.domain.enumeration.PaymentStatus;
import com.hostel.domain.enumeration.RoomStatus;
import com.hostel.repository.BuildingRepository;
import com.hostel.service.PaymentsService;
import com.hostel.service.RoomAllocationService;
import com.hostel.service.RoomService;
import com.hostel.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Binu on 09-08-2017.
 */
@Component
public class PaymentGenerator {

    private final PaymentsService  paymentsService;

    private final RoomAllocationService roomAllocationService;

    private final UserService userService;

    private final RoomService roomService;

    public PaymentGenerator(PaymentsService  paymentsService, RoomAllocationService roomAllocationService, UserService userService, RoomService roomService) {
        this.paymentsService =   paymentsService;
        this.roomAllocationService = roomAllocationService;
        this.userService = userService;
        this.roomService = roomService;
    }
/*
    https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/scheduling/support/CronSequenceGenerator.html
    The pattern is a list of six single space-separated fields: representing second, minute, hour, day, month, weekday. Month and weekday names can be given as the first three letters of the English names.
    Example patterns:
        "0 0 * * * *" = the top of every hour of every day.
        "*//*10 * * * * *" = every ten seconds.
    "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
        "0 0 6,19 * * *" = 6:00 AM and 7:00 PM every day.
    "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30, 10:00 and 10:30 every day.
        "0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
    "0 0 0 25 12 ?" = every Christmas Day at midnight
    */

    @Scheduled(cron = "0 */2 * * * *")
    public void generateRentPayments(){
        System.out.println(new Date());

        // get all allocations
        List<RoomAllocation> allocations = roomAllocationService.findRoomAllocationsByStatus(RoomStatus.ACTIVE);

        if(!CollectionUtils.isEmpty(allocations)){

            for(RoomAllocation allocation : allocations){
                // case 1 : Advance amount
                // find payments by user + room where status in PAID, PAID_PARTIAL, NOT_PAID, and type ADVANCE
                // if no payments, make advance payment entry


                //case 2 : Rent Amount
                // find payments COUNT() by user + room where status in PAID, PAID_PARTIAL, NOT_PAID, and type RENT
                List<PaymentStatus> paymentStatus = new ArrayList<PaymentStatus>();
                paymentStatus.add(PaymentStatus.NOT_PAID);
                paymentStatus.add(PaymentStatus.PAID);
                paymentStatus.add(PaymentStatus.PAID_PARTIAL);

                LocalDate newPaymentFromDate = null;
                LocalDate newPaymentToDate = null;
                do {
                    // do-while to handle old user data inputs
                    //

                    Payments lastRentPayment = paymentsService.findUserRoomLastPaymentWithTypeAndStatus(allocation.getUserId(), allocation.getRoomId(), PaymentAgainstType.RENT, paymentStatus);

                    Payments rentPayment = null;
                    newPaymentFromDate = null;
                    newPaymentToDate = null;
                    if (lastRentPayment == null) {
                        // if no rentPayment , make first rent payment
                        // newPaymentFromDate = Allocation start date
                        newPaymentFromDate = allocation.getFromDate();

                        // newPaymentToDate = 'lastPaidDate' + 30 days
                        newPaymentToDate = computeBillCycleToDate(allocation.getFromDate());
                    } else {
                        // if payments available(1 or more),
                        // object having highest 'paymentTo' date as 'lastPaidDate'

                        // newPaymentFromDate = 'lastPaidDate' + 1 day
                        newPaymentFromDate = addDays(lastRentPayment.getPaymentTo(), 1);

                        // newPaymentToDate = 'lastPaidDate' + 30 days
                        newPaymentToDate = computeBillCycleToDate(newPaymentFromDate);
                    }

                    if (newPaymentToDate.isBefore(LocalDate.now().plusDays(30))) {
                        rentPayment = new Payments(null, allocation.getRoomId(), allocation.getUserId(), null, 0d, null,
                            newPaymentFromDate, newPaymentToDate, PaymentStatus.NOT_PAID,
                            PaymentAgainstType.RENT, Constants.PAYMENT_ENTRY_COMMENT, allocation.getBuildingId());

                        // save payment entry
                        paymentsService.savePayment(rentPayment);
                    }
                }while(newPaymentToDate.isBefore(LocalDate.now().plusDays(30)));

            }
        }
    }

    private LocalDate computeBillCycleToDate(LocalDate paymentFromDate){
        int daysInMonth = getNumberOfDaysInMonth(paymentFromDate);
        return addDays(paymentFromDate, daysInMonth-1);
    }

    private LocalDate addDays(LocalDate paymentFromDate, int daysInMonth) {
        return paymentFromDate.plusDays(daysInMonth);
    }

    private int getNumberOfDaysInMonth(LocalDate month) {
        YearMonth yearMonthObject = YearMonth.of(month.getYear(), month.getMonthValue());
        return yearMonthObject.lengthOfMonth();
    }

}
