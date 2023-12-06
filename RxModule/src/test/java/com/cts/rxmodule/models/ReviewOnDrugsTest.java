package com.cts.rxmodule.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ReviewOnDrugsTest {

    @Test
    void testConstructor() {
        ReviewOnDrugs actualReviewOnDrugs = new ReviewOnDrugs();
        actualReviewOnDrugs.setComments("Comments");
        actualReviewOnDrugs.setEmail("jane.doe@example.org");
        actualReviewOnDrugs.setRating(1);
        actualReviewOnDrugs.setReviewId(123);
        actualReviewOnDrugs.setRxName("Rx Name");
        String actualToStringResult = actualReviewOnDrugs.toString();
        assertEquals("Comments", actualReviewOnDrugs.getComments());
        assertEquals("jane.doe@example.org", actualReviewOnDrugs.getEmail());
        assertEquals(1, actualReviewOnDrugs.getRating());
        assertEquals(123, actualReviewOnDrugs.getReviewId());
        assertEquals("Rx Name", actualReviewOnDrugs.getRxName());
        assertEquals("ReviewOnDrugs(reviewId=123, email=jane.doe@example.org, rxName=Rx Name, comments=Comments, rating=1)",
                actualToStringResult);
    }

    @Test
    void testConstructor2() {
        ReviewOnDrugs actualReviewOnDrugs = new ReviewOnDrugs(123, "jane.doe@example.org", "Rx Name", "Comments", 1);
        actualReviewOnDrugs.setComments("Comments");
        actualReviewOnDrugs.setEmail("jane.doe@example.org");
        actualReviewOnDrugs.setRating(1);
        actualReviewOnDrugs.setReviewId(123);
        actualReviewOnDrugs.setRxName("Rx Name");
        String actualToStringResult = actualReviewOnDrugs.toString();
        assertEquals("Comments", actualReviewOnDrugs.getComments());
        assertEquals("jane.doe@example.org", actualReviewOnDrugs.getEmail());
        assertEquals(1, actualReviewOnDrugs.getRating());
        assertEquals(123, actualReviewOnDrugs.getReviewId());
        assertEquals("Rx Name", actualReviewOnDrugs.getRxName());
        assertEquals(
                "ReviewOnDrugs(reviewId=123, email=jane.doe@example.org, rxName=Rx Name, comments=Comments, rating=1)",
                actualToStringResult);
    }

}

