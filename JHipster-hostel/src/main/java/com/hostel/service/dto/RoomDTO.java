package com.hostel.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Room entity.
 */
public class RoomDTO implements Serializable {

    private String id;

    private String roomName;

    private String floor;

    private String building;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }
    public String getBuilding() {
        return building;
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

        RoomDTO roomDTO = (RoomDTO) o;

        if ( ! Objects.equals(id, roomDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RoomDTO{" +
            "id=" + id +
            ", roomName='" + roomName + "'" +
            ", floor='" + floor + "'" +
            ", building='" + building + "'" +
            '}';
    }
}
