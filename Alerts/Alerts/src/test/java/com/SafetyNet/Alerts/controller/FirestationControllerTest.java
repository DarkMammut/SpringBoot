package com.SafetyNet.Alerts.controller;

import com.SafetyNet.Alerts.model.Firestation;
import com.SafetyNet.Alerts.service.FirestationService;
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

public class FirestationControllerTest {
    private MockMvc mockMvc;

    @Mock
    private FirestationService firestationService;

    @InjectMocks
    private FirestationController firestationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(firestationController).build();
    }

    private List<Firestation> getFirestations() {
        Firestation firestation1 = new Firestation("1509 Culver St", "1");
        Firestation firestation2 = new Firestation("123 Test St", "2");
        return Arrays.asList(firestation1, firestation2);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Firestation> firestations = getFirestations();

        when(firestationService.getFirestations()).thenReturn(firestations);

        mockMvc.perform(get("/firestations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address").value("1509 Culver St"))
                .andExpect(jsonPath("$[0].station").value("1"))
                .andExpect(jsonPath("$[1].address").value("123 Test St"))
                .andExpect(jsonPath("$[1].station").value("2"));

        verify(firestationService).getFirestations();
    }

    @Test
    public void testCreate() throws Exception {
        Firestation firestation = getFirestations().get(0);
        String json = """
            {
                "station": "1",
                "address": "1509 Culver St"
            }
        """;

        when(firestationService.save(any(Firestation.class))).thenReturn(firestation);

        mockMvc.perform(post("/firestations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address").value("1509 Culver St"))
                .andExpect(jsonPath("$.station").value("1"));
    }

    @Test
    public void testGet() throws Exception {
        Firestation firestation = getFirestations().get(0);

        when(firestationService.getFirestationByStation("1")).thenReturn(firestation);

        mockMvc.perform(get("/firestations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("1509 Culver St"))
                .andExpect(jsonPath("$.station").value("1"));

        verify(firestationService).getFirestationByStation("1");
    }

    @Test
    public void testUpdate() throws Exception {
        Firestation firestation = getFirestations().get(0);
        String json = """
            {
                "station": "1",
                "address": "1509 Culver St"
            }
        """;

        when(firestationService.updateFirestation(any(Firestation.class))).thenReturn(firestation);

        mockMvc.perform(put("/firestations/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("1509 Culver St"))
                .andExpect(jsonPath("$.station").value("1"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/firestations/1"))
                .andExpect(status().isOk());

        verify(firestationService).deleteFirestation("1");
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
