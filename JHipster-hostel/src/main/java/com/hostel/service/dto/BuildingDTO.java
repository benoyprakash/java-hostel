package com.hostel.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Building entity.
 */
public class BuildingDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private String location;

    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BuildingDTO buildingDTO = (BuildingDTO) o;

        if ( ! Objects.equals(id, buildingDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BuildingDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", location='" + location + "'" +
            ", address='" + address + "'" +
            '}';
    }
}
