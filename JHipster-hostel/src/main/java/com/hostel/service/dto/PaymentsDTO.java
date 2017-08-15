package com.hostel.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.hostel.domain.enumeration.PaymentStatus;
import com.hostel.domain.enumeration.PaymentAgainstType;

/**
 * A DTO for the Payments entity.
 */
public class PaymentsDTO implements Serializable {

    private String id;

    @NotNull
    private String room;

    private String roomName;

    @NotNull
    private String customer;

    private String customerName;

    //@NotNull
    private LocalDate dateOfJoin;

    @NotNull
    private Double amount;

    @NotNull
    private LocalDate paymentDate;

    @NotNull
    private LocalDate paymentFrom;

    @NotNull
    private LocalDate paymentTo;

    @NotNull
    private PaymentStatus paymentStatus;

    @NotNull
    private PaymentAgainstType payentAgainst;

    private String comments;

    @NotNull
    private String building;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public LocalDate getDateOfJoin() {
        return dateOfJoin;
    }

    public void setDateOfJoin(LocalDate dateOfJoin) {
        this.dateOfJoin = dateOfJoin;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public LocalDate getPaymentFrom() {
        return paymentFrom;
    }

    public void setPaymentFrom(LocalDate paymentFrom) {
        this.paymentFrom = paymentFrom;
    }

    public LocalDate getPaymentTo() {
        return paymentTo;
    }

    public void setPaymentTo(LocalDate paymentTo) {
        this.paymentTo = paymentTo;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PaymentAgainstType getPayentAgainst() {
        return payentAgainst;
    }

    public void setPayentAgainst(PaymentAgainstType payentAgainst) {
        this.payentAgainst = payentAgainst;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaymentsDTO paymentsDTO = (PaymentsDTO) o;
        if(paymentsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentsDTO{" +
            "id=" + getId() +
            ", room='" + getRoom() + "'" +
            ", customer='" + getCustomer() + "'" +
            ", dateOfJoin='" + getDateOfJoin() + "'" +
            ", amount='" + getAmount() + "'" +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", paymentFrom='" + getPaymentFrom() + "'" +
            ", paymentTo='" + getPaymentTo() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", payentAgainst='" + getPayentAgainst() + "'" +
            ", comments='" + getComments() + "'" +
            ", building='" + getBuilding() + "'" +
            "}";
    }
}
