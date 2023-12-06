package com.cts.ordermodule.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PrescriptionDetailsTest {


    @Test
    void testConstructor() {
        PrescriptionDetails actualPrescriptionDetails = new PrescriptionDetails();
        actualPrescriptionDetails.setDoctorDetails("Doctor Details");
        actualPrescriptionDetails.setDosageDefinitionForaDay("Dosage Definition Fora Day");
        actualPrescriptionDetails.setInsuranceProvider("Insurance Provider");
        actualPrescriptionDetails.setNumberOfRefills(10);
        actualPrescriptionDetails.setPrescriptionId(123L);
        actualPrescriptionDetails.setPrescriptionStartDate("2020-03-01");
        actualPrescriptionDetails.setRxId("42");
        assertEquals("Doctor Details", actualPrescriptionDetails.getDoctorDetails());
        assertEquals("Dosage Definition Fora Day", actualPrescriptionDetails.getDosageDefinitionForaDay());
        assertEquals("Insurance Provider", actualPrescriptionDetails.getInsuranceProvider());
        assertEquals(10, actualPrescriptionDetails.getNumberOfRefills());
        assertEquals(123L, actualPrescriptionDetails.getPrescriptionId());
        assertEquals("2020-03-01", actualPrescriptionDetails.getPrescriptionStartDate());
        assertEquals("42", actualPrescriptionDetails.getRxId());
    }


    @Test
    void testConstructor2() {
        PrescriptionDetails actualPrescriptionDetails = new PrescriptionDetails(123L, "42", "Insurance Provider",
                "2020-03-01", "Dosage Definition Fora Day", "Doctor Details", 10);

        assertEquals("Doctor Details", actualPrescriptionDetails.getDoctorDetails());
        assertEquals("42", actualPrescriptionDetails.getRxId());
        assertEquals("2020-03-01", actualPrescriptionDetails.getPrescriptionStartDate());
        assertEquals(123L, actualPrescriptionDetails.getPrescriptionId());
        assertEquals(10, actualPrescriptionDetails.getNumberOfRefills());
        assertEquals("Insurance Provider", actualPrescriptionDetails.getInsuranceProvider());
        assertEquals("Dosage Definition Fora Day", actualPrescriptionDetails.getDosageDefinitionForaDay());
    }
}

