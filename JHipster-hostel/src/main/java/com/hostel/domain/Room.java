package com.hostel.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Room.
 */

@Document(collection = "room")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("room_name")
    private String roomName;

    @Field("floor")
    private String floor;

    @Field("building")
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

    public Room roomName(String roomName) {
        this.roomName = roomName;
        return this;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getFloor() {
        return floor;
    }

    public Room floor(String floor) {
        this.floor = floor;
        return this;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getBuilding() {
        return building;
    }

    public Room building(String building) {
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
        Room room = (Room) o;
        if (room.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Room{" +
            "id=" + id +
            ", roomName='" + roomName + "'" +
            ", floor='" + floor + "'" +
            ", building='" + building + "'" +
            '}';
    }
}
