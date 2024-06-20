package com.SafetyNet.Alerts.service.dto;

import java.util.List;

public class ListHouseholdStationDTO {
    private List<HouseholdDTO> households;

    public ListHouseholdStationDTO() {}

    public ListHouseholdStationDTO(List<HouseholdDTO> households) {
        this.households = households;
    }

    public List<HouseholdDTO> getHouseholds() {
        return households;
    }

    public void setHouseholds(List<HouseholdDTO> households) {
        this.households = households;
    }

    @Override
    public String toString() {
        return "ListHouseholdStationDTO{" +
                "households=" + households +
                '}';
    }
}
