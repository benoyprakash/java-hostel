package com.hostel.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.hostel.domain.enumeration.PaymentStatus;

import com.hostel.domain.enumeration.PaymentAgainstType;

/**
 * A Payments.
 */
@Document(collection = "payments")
public class Payments implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @NotNull
    @Field("room")
    private String room;

    @NotNull
    @Field("customer")
    private String customer;

//    @NotNull
    @Field("date_of_join")
    private LocalDate dateOfJoin;

    @NotNull
    @Field("amount")
    private Double amount;

    //@NotNull
    @Field("payment_date")
    private LocalDate paymentDate;

    @NotNull
    @Field("payment_from")
    private LocalDate paymentFrom;

    @NotNull
    @Field("payment_to")
    private LocalDate paymentTo;

    @NotNull
    @Field("payment_status")
    private PaymentStatus paymentStatus;

    @NotNull
    @Field("payent_against")
    private PaymentAgainstType payentAgainst;

    @Field("comments")
    private String comments;

    @NotNull
    @Field("building")
    private String building;

    public Payments() {
    }

    public Payments(String id, String room, String customer, LocalDate dateOfJoin, Double amount, LocalDate paymentDate, LocalDate paymentFrom, LocalDate paymentTo, PaymentStatus paymentStatus, PaymentAgainstType payentAgainst, String comments, String building) {
        this.id = id;
        this.room = room;
        this.customer = customer;
        this.dateOfJoin = dateOfJoin;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentFrom = paymentFrom;
        this.paymentTo = paymentTo;
        this.paymentStatus = paymentStatus;
        this.payentAgainst = payentAgainst;
        this.comments = comments;
        this.building = building;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public Payments room(String room) {
        this.room = room;
        return this;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getCustomer() {
        return customer;
    }

    public Payments customer(String customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public LocalDate getDateOfJoin() {
        return dateOfJoin;
    }

    public Payments dateOfJoin(LocalDate dateOfJoin) {
        this.dateOfJoin = dateOfJoin;
        return this;
    }

    public void setDateOfJoin(LocalDate dateOfJoin) {
        this.dateOfJoin = dateOfJoin;
    }

    public Double getAmount() {
        return amount;
    }

    public Payments amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public Payments paymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public LocalDate getPaymentFrom() {
        return paymentFrom;
    }

    public Payments paymentFrom(LocalDate paymentFrom) {
        this.paymentFrom = paymentFrom;
        return this;
    }

    public void setPaymentFrom(LocalDate paymentFrom) {
        this.paymentFrom = paymentFrom;
    }

    public LocalDate getPaymentTo() {
        return paymentTo;
    }

    public Payments paymentTo(LocalDate paymentTo) {
        this.paymentTo = paymentTo;
        return this;
    }

    public void setPaymentTo(LocalDate paymentTo) {
        this.paymentTo = paymentTo;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public Payments paymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PaymentAgainstType getPayentAgainst() {
        return payentAgainst;
    }

    public Payments payentAgainst(PaymentAgainstType payentAgainst) {
        this.payentAgainst = payentAgainst;
        return this;
    }

    public void setPayentAgainst(PaymentAgainstType payentAgainst) {
        this.payentAgainst = payentAgainst;
    }

    public String getComments() {
        return comments;
    }

    public Payments comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getBuilding() {
        return building;
    }

    public Payments building(String building) {
        this.building = building;
        return this;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Payments payments = (Payments) o;
        if (payments.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), payments.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Payments{" +
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
