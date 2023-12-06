package com.example.authorization.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MemberTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>default or parameterless constructor of {@link Member}
     *   <li>{@link Member#setAddress(String)}
     *   <li>{@link Member#setAge(int)}
     *   <li>{@link Member#setContactNumber(long)}
     *   <li>{@link Member#setEmail(String)}
     *   <li>{@link Member#setFullName(String)}
     *   <li>{@link Member#setGender(String)}
     *   <li>{@link Member#setMemberId(Long)}
     *   <li>{@link Member#setPassword(String)}
     *   <li>{@link Member#toString()}
     *   <li>{@link Member#getAddress()}
     *   <li>{@link Member#getAge()}
     *   <li>{@link Member#getContactNumber()}
     *   <li>{@link Member#getEmail()}
     *   <li>{@link Member#getFullName()}
     *   <li>{@link Member#getGender()}
     *   <li>{@link Member#getMemberId()}
     *   <li>{@link Member#getPassword()}
     * </ul>
     */
    @Test
    void testConstructor() {
        Member actualMember = new Member();
        actualMember.setAddress("42 Main St");
        actualMember.setAge(1);
        actualMember.setContactNumber(1L);
        actualMember.setEmail("jane.doe@example.org");
        actualMember.setFullName("Dr Jane Doe");
        actualMember.setGender("Gender");
        actualMember.setMemberId(123L);
        actualMember.setPassword("iloveyou");
        String actualToStringResult = actualMember.toString();
        assertEquals("42 Main St", actualMember.getAddress());
        assertEquals(1, actualMember.getAge());
        assertEquals(1L, actualMember.getContactNumber());
        assertEquals("jane.doe@example.org", actualMember.getEmail());
        assertEquals("Dr Jane Doe", actualMember.getFullName());
        assertEquals("Gender", actualMember.getGender());
        assertEquals(123L, actualMember.getMemberId().longValue());
        assertEquals("iloveyou", actualMember.getPassword());
        assertEquals("Member(memberId=123, email=jane.doe@example.org, fullName=Dr Jane Doe, gender=Gender, age=1,"
                + " contactNumber=1, password=iloveyou, address=42 Main St)", actualToStringResult);
    }
}

