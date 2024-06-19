package com.SafetyNet.Alerts.controller;

import com.SafetyNet.Alerts.service.GlobalService;
import com.SafetyNet.Alerts.service.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GlobalControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GlobalService globalService;

    @InjectMocks
    private GlobalController globalController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(globalController).build();
    }

    private ListPersonMedicalRecordDTO getSampleListPersonMedicalRecordDTO() {
        // Create sample ListPersonMedicalRecordDTO for testing
        return new ListPersonMedicalRecordDTO(List.of(new PersonMedicalRecordDTO("John", "Doe", "123 Street", "123-456-7890")), 1, 0);
    }

    private ListChildAddressDTO getSampleListChildAddressDTO() {
        // Create sample ListChildAddressDTO for testing
        return new ListChildAddressDTO(List.of(new ChildAddressDTO("John", "Doe", 10)), List.of(new HouseholdMemberDTO("Jane", "Doe", 50)));
    }

    private ListPhoneNumberStationDTO getSampleListPhoneNumberStationDTO() {
        // Create sample ListPhoneNumberStationDTO for testing
        return new ListPhoneNumberStationDTO(List.of(new PhoneNumberStationDTO("123-456-7890"), new PhoneNumberStationDTO("987-654-3210")));
    }

    private ListPersonAddressDTO getSampleListPersonAddressDTO() {
        // Create sample ListPersonAddressDTO for testing
        return new ListPersonAddressDTO(List.of(new PersonAddressDTO("John", "Doe", "123-456-7890", 40, null, null, "1")));
    }

    private ListHouseholdStationDTO getSampleListHouseholdStationDTO() {
        // Create sample PersonHouseholdDTO
        PersonHouseholdDTO personHouseholdDTO = new PersonHouseholdDTO("John", "Doe", "123 Street", "123-456-7890", 45, null, null);
        List<PersonHouseholdDTO> householdMemberDTO = List.of(personHouseholdDTO);
        // Create sample HouseholdDTO
        HouseholdDTO householdDTO = new HouseholdDTO("123 Street", householdMemberDTO);
        // Create sample ListHouseholdStationDTO containing the HouseholdDTO
        return new ListHouseholdStationDTO(List.of(householdDTO));
    }


    private ListPersonDTO getSampleListPersonDTO() {
        // Create sample ListPersonDTO for testing
        return new ListPersonDTO(List.of(new PersonDTO("John", "Doe", 60, null, "John.Doe@example.com", null)));
    }

    private ListEmailCityDTO getSampleListEmailCityDTO() {
        // Create sample ListEmailCityDTO for testing
        return new ListEmailCityDTO(List.of(new EmailCityDTO("john@example.com"), new EmailCityDTO("jane@example.com")));
    }

    @Test
    public void testGetPersonsByStationNumber() throws Exception {
        ListPersonMedicalRecordDTO listPersonMedicalRecordDTO = getSampleListPersonMedicalRecordDTO();

        when(globalService.getPersonsByStationNumber("1")).thenReturn(listPersonMedicalRecordDTO);

        mockMvc.perform(get("/firestation").param("stationNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personMedicalRecordDTOs[0].firstName").value("John"))
                .andExpect(jsonPath("$.personMedicalRecordDTOs[0].lastName").value("Doe"))
                .andExpect(jsonPath("$.personMedicalRecordDTOs[0].address").value("123 Street"))
                .andExpect(jsonPath("$.personMedicalRecordDTOs[0].phoneNumber").value("123-456-7890"));

        verify(globalService).getPersonsByStationNumber("1");
    }

    @Test
    public void testGetChildByAddress() throws Exception {
        ListChildAddressDTO listChildAddressDTO = getSampleListChildAddressDTO();

        when(globalService.getChildByAddress("123 Street")).thenReturn(listChildAddressDTO);

        mockMvc.perform(get("/childAlert").param("address", "123 Street"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.childAddressDTOs[0].firstName").value("John"))
                .andExpect(jsonPath("$.childAddressDTOs[0].lastName").value("Doe"))
                .andExpect(jsonPath("$.childAddressDTOs[0].age").value(10))
                .andExpect(jsonPath("$.household[0].firstName").value("Jane"))
                .andExpect(jsonPath("$.household[0].lastName").value("Doe"))
                .andExpect(jsonPath("$.household[0].age").value(50));

        verify(globalService).getChildByAddress("123 Street");
    }

    @Test
    public void testGetPhoneNumberByStationNumber() throws Exception {
        ListPhoneNumberStationDTO listPhoneNumberStationDTO = getSampleListPhoneNumberStationDTO();

        when(globalService.getPhoneNumberByStationNumber("1")).thenReturn(listPhoneNumberStationDTO);

        mockMvc.perform(get("/phoneAlert").param("firestation", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumberStationDTOs[0].phoneNumber").value("123-456-7890"))
                .andExpect(jsonPath("$.phoneNumberStationDTOs[1].phoneNumber").value("987-654-3210"));

        verify(globalService).getPhoneNumberByStationNumber("1");
    }

    @Test
    public void testGetPersonByAddress() throws Exception {
        ListPersonAddressDTO listPersonAddressDTO = getSampleListPersonAddressDTO();

        when(globalService.getPersonByAddress("123 Street")).thenReturn(listPersonAddressDTO);

        mockMvc.perform(get("/fire").param("address", "123 Street"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personAddressDTO[0].firstName").value("John"))
                .andExpect(jsonPath("$.personAddressDTO[0].lastName").value("Doe"))
                .andExpect(jsonPath("$.personAddressDTO[0].phoneNumber").value("123-456-7890"))
                .andExpect(jsonPath("$.personAddressDTO[0].age").value(40));

        verify(globalService).getPersonByAddress("123 Street");
    }

//    @Test
//    public void testGetHouseholdByStationNumber() throws Exception {
//        ListHouseholdStationDTO listHouseholdStationDTO = getSampleListHouseholdStationDTO();
//        List<String> stationNumber = List.of("1");
//
//        when(globalService.getHouseholdByStationNumber(stationNumber)).thenReturn(listHouseholdStationDTO);
//
//        mockMvc.perform(get("/flood").param("stationNumbers", "1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.households[0].address").value("123 Street"))
//                .andExpect(jsonPath("$.households[0].persons[0].firstname").value("John"))
//                .andExpect(jsonPath("$.households[0].persons[0].lastname").value("Doe"));
//
//        verify(globalService).getHouseholdByStationNumber(stationNumber);
//    }

    @Test
    public void testGetPersonInfo() throws Exception {
        ListPersonDTO listPersonDTO = getSampleListPersonDTO();

        when(globalService.getPersonInfo("John", "Doe")).thenReturn(listPersonDTO);

        mockMvc.perform(get("/personInfo").param("firstName", "John").param("lastName", "Doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personDTO[0].firstName").value("John"))
                .andExpect(jsonPath("$.personDTO[0].lastName").value("Doe"))
                .andExpect(jsonPath("$.personDTO[0].age").value(60))
                .andExpect(jsonPath("$.personDTO[0].email").value("John.Doe@example.com"));

        verify(globalService).getPersonInfo("John", "Doe");
    }

    @Test
    public void testGetEmailByCity() throws Exception {
        ListEmailCityDTO listEmailCityDTO = getSampleListEmailCityDTO();

        when(globalService.getEmailByCity("SomeCity")).thenReturn(listEmailCityDTO);

        mockMvc.perform(get("/communityEmail").param("city", "SomeCity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emailCityDTO[0].email").value("john@example.com"))
                .andExpect(jsonPath("$.emailCityDTO[1].email").value("jane@example.com"));

        verify(globalService).getEmailByCity("SomeCity");
    }

    private static String asJsonString(final Object obj) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}