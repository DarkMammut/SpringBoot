package com.SafetyNet.Alerts.service.dto;

import java.util.List;

public class ListChildAddressDTO {

    private List<ChildAddressDTO> childAddressDTOs;
    private List<HouseholdMemberDTO> household;

    public ListChildAddressDTO() {
    }

    public ListChildAddressDTO(List<ChildAddressDTO> childAddressDTOs, List<HouseholdMemberDTO> household) {
        this.childAddressDTOs = childAddressDTOs;
        this.household = household;
    }

    public List<ChildAddressDTO> getChildAddressDTOs() {
        return childAddressDTOs;
    }

    public void setChildAddressDTOs(List<ChildAddressDTO> childAddressDTOs) {
        this.childAddressDTOs = childAddressDTOs;
    }

    public List<HouseholdMemberDTO> getHousehold() {
        return household;
    }

    public void setHousehold(List<HouseholdMemberDTO> household) {
        this.household = household;
    }

    @Override
    public String toString() {
        return "ListChildAddressDTO{" +
                "childAddressDTOs=" + childAddressDTOs +
                ", household=" + household +
                '}';
    }
}
