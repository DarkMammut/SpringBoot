package com.SafetyNet.Alerts.controller;

import com.SafetyNet.Alerts.model.MedicalRecord;
import com.SafetyNet.Alerts.service.MedicalRecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MedicalRecordControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MedicalRecordService medicalRecordService;

    @InjectMocks
    private MedicalRecordController medicalRecordController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(medicalRecordController).build();
    }

    private List<MedicalRecord> getMedicalRecords() {
        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("John");
        medicalRecord1.setLastName("Doe");
        medicalRecord1.setBirthdate("01/01/1990");
        medicalRecord1.setMedications(List.of("med1:100mg", "med2:200mg"));
        medicalRecord1.setAllergies(List.of("allergy1", "allergy2"));

        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setFirstName("Jane");
        medicalRecord2.setLastName("Doe");
        medicalRecord2.setBirthdate("02/02/1992");
        medicalRecord2.setMedications(List.of("med3:300mg"));
        medicalRecord2.setAllergies(List.of("allergy3"));

        return Arrays.asList(medicalRecord1, medicalRecord2);
    }

    @Test
    public void testGetAll() throws Exception {
        List<MedicalRecord> medicalRecords = getMedicalRecords();

        when(medicalRecordService.getMedicalRecords()).thenReturn(medicalRecords);

        mockMvc.perform(get("/medicalrecords"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].birthdate").value("01/01/1990"))
                .andExpect(jsonPath("$[0].medications[0]").value("med1:100mg"))
                .andExpect(jsonPath("$[0].medications[1]").value("med2:200mg"))
                .andExpect(jsonPath("$[0].allergies[0]").value("allergy1"))
                .andExpect(jsonPath("$[0].allergies[1]").value("allergy2"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].lastName").value("Doe"))
                .andExpect(jsonPath("$[1].birthdate").value("02/02/1992"))
                .andExpect(jsonPath("$[1].medications[0]").value("med3:300mg"))
                .andExpect(jsonPath("$[1].allergies[0]").value("allergy3"));

        verify(medicalRecordService).getMedicalRecords();
    }

    @Test
    public void testCreate() throws Exception {
        MedicalRecord medicalRecord = getMedicalRecords().get(0);
        String json = """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "birthdate": "01/01/1990",
                    "medications": [
                        "med1:100mg",
                        "med2:200mg"
                    ],
                    "allergies": [
                        "allergy1",
                        "allergy2"
                    ]
                }
            """;

        when(medicalRecordService.save(any(MedicalRecord.class))).thenReturn(medicalRecord);

        mockMvc.perform(post("/medicalrecords")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.birthdate").value("01/01/1990"))
                .andExpect(jsonPath("$.medications[0]").value("med1:100mg"))
                .andExpect(jsonPath("$.medications[1]").value("med2:200mg"))
                .andExpect(jsonPath("$.allergies[0]").value("allergy1"))
                .andExpect(jsonPath("$.allergies[1]").value("allergy2"));

//        verify(medicalRecordService).save(medicalRecord);
    }

    @Test
    public void testGet() throws Exception {
        MedicalRecord medicalRecord = getMedicalRecords().get(0);

        when(medicalRecordService.getMedicalRecordByKey("John", "Doe")).thenReturn(medicalRecord);

        mockMvc.perform(get("/medicalrecords/John/Doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.birthdate").value("01/01/1990"))
                .andExpect(jsonPath("$.medications[0]").value("med1:100mg"))
                .andExpect(jsonPath("$.medications[1]").value("med2:200mg"))
                .andExpect(jsonPath("$.allergies[0]").value("allergy1"))
                .andExpect(jsonPath("$.allergies[1]").value("allergy2"));

        verify(medicalRecordService).getMedicalRecordByKey("John", "Doe");
    }

    @Test
    public void testUpdate() throws Exception {
        MedicalRecord medicalRecord = getMedicalRecords().get(0);
        String json = """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "birthdate": "01/01/1990",
                    "medications": [
                        "med1:100mg",
                        "med2:200mg"
                    ],
                    "allergies": [
                        "allergy1",
                        "allergy2"
                    ]
                }
            """;

        when(medicalRecordService.updateMedicalrecord(any(MedicalRecord.class))).thenReturn(medicalRecord);

        mockMvc.perform(put("/medicalrecords/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))

                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.birthdate").value("01/01/1990"))
                .andExpect(jsonPath("$.medications[0]").value("med1:100mg"))
                .andExpect(jsonPath("$.medications[1]").value("med2:200mg"))
                .andExpect(jsonPath("$.allergies[0]").value("allergy1"))
                .andExpect(jsonPath("$.allergies[1]").value("allergy2"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/medicalrecords/John/Doe"))
                .andExpect(status().isOk());

        verify(medicalRecordService).deleteMedicalRecord("John", "Doe");
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
