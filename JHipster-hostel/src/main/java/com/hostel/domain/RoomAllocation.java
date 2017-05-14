package com.hostel.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.hostel.domain.enumeration.RoomStatus;

/**
 * A RoomAllocation.
 */

@Document(collection = "room_allocation")
public class RoomAllocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("room_id")
    private String roomId;

    @Field("user_id")
    private String userId;

    @NotNull
    @Field("from_date")
    private LocalDate fromDate;

    @NotNull
    @Field("to_date")
    private LocalDate toDate;

    @NotNull
    @Field("curr_status")
    private RoomStatus currStatus;

    @Field("updated_by")
    private String updatedBy;

    @Field("updated_date_time")
    private LocalDate updatedDateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomId() {
        return roomId;
    }

    public RoomAllocation roomId(String roomId) {
        this.roomId = roomId;
        return this;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public RoomAllocation userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public RoomAllocation fromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public RoomAllocation toDate(LocalDate toDate) {
        this.toDate = toDate;
        return this;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public RoomStatus getCurrStatus() {
        return currStatus;
    }

    public RoomAllocation currStatus(RoomStatus currStatus) {
        this.currStatus = currStatus;
        return this;
    }

    public void setCurrStatus(RoomStatus currStatus) {
        this.currStatus = currStatus;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public RoomAllocation updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedDateTime() {
        return updatedDateTime;
    }

    public RoomAllocation updatedDateTime(LocalDate updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
        return this;
    }

    public void setUpdatedDateTime(LocalDate updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoomAllocation roomAllocation = (RoomAllocation) o;
        if (roomAllocation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, roomAllocation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RoomAllocation{" +
            "id=" + id +
            ", roomId='" + roomId + "'" +
            ", userId='" + userId + "'" +
            ", fromDate='" + fromDate + "'" +
            ", toDate='" + toDate + "'" +
            ", currStatus='" + currStatus + "'" +
            ", updatedBy='" + updatedBy + "'" +
            ", updatedDateTime='" + updatedDateTime + "'" +
            '}';
    }
}
