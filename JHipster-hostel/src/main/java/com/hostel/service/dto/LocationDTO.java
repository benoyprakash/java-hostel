package com.hostel.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Location entity.
 */
public class LocationDTO implements Serializable {

    private String id;

    @NotNull
    private String locationName;

    @NotNull
    private String country;

    @NotNull
    private String state;

    @NotNull
    private String district;

    @NotNull
    private String locality;

    private String client;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }
    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocationDTO locationDTO = (LocationDTO) o;

        if ( ! Objects.equals(id, locationDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LocationDTO{" +
            "id=" + id +
            ", locationName='" + locationName + "'" +
            ", country='" + country + "'" +
            ", state='" + state + "'" +
            ", district='" + district + "'" +
            ", locality='" + locality + "'" +
            ", client='" + client + "'" +
            '}';
    }
}
