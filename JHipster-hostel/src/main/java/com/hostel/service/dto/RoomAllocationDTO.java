package com.hostel.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.hostel.domain.enumeration.RoomStatus;

/**
 * A DTO for the RoomAllocation entity.
 */
public class RoomAllocationDTO implements Serializable {

    private String id;

    @NotNull
    private String roomId;

    private String userId;

    @NotNull
    private LocalDate fromDate;

    @NotNull
    private LocalDate toDate;

    @NotNull
    private RoomStatus currStatus;

    private String updatedBy;

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

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }
    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
    public RoomStatus getCurrStatus() {
        return currStatus;
    }

    public void setCurrStatus(RoomStatus currStatus) {
        this.currStatus = currStatus;
    }
    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    public LocalDate getUpdatedDateTime() {
        return updatedDateTime;
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

        RoomAllocationDTO roomAllocationDTO = (RoomAllocationDTO) o;

        if ( ! Objects.equals(id, roomAllocationDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RoomAllocationDTO{" +
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
