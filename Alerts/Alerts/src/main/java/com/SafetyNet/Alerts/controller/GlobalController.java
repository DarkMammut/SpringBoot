package com.SafetyNet.Alerts.controller;

import com.SafetyNet.Alerts.service.GlobalService;
import com.SafetyNet.Alerts.service.dto.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class GlobalController {

    private final GlobalService globalService;

    public GlobalController(GlobalService globalService) {
        this.globalService = globalService;
    }

    @GetMapping("firestation")
    public ListPersonMedicalRecordDTO getPersonsByStationNumber(@RequestParam String stationNumber) {
        return globalService.getPersonsByStationNumber(stationNumber);
    }

    @GetMapping("childAlert")
    public ListChildAddressDTO getChildByAddress(@RequestParam String address) {
        System.out.println("Address" + address);
        return globalService.getChildByAddress(address);
    }

    @GetMapping("phoneAlert")
    public ListPhoneNumberStationDTO getPhoneNumberByStationNumber(@RequestParam String firestation) {
        return globalService.getPhoneNumberByStationNumber(firestation);
    }

    @GetMapping("fire")
    public ListPersonAddressDTO getPersonByAddress(@RequestParam String address) {
        return globalService.getPersonByAddress(address);
    }

    @GetMapping("flood")
    public ListHouseholdStationDTO getHouseholdByStationNumber(@RequestParam List<String> stationNumbers) {
        return globalService.getHouseholdByStationNumber(stationNumbers);
    }

    @GetMapping("personInfo")
    public ListPersonDTO getPersonInfo(@RequestParam String firstName, String lastName) {
        return globalService.getPersonInfo(firstName, lastName);
    }

    @GetMapping("communityEmail")
    public ListEmailCityDTO getEmailByCity(@RequestParam String city) {
        return globalService.getEmailByCity(city);
    }
}