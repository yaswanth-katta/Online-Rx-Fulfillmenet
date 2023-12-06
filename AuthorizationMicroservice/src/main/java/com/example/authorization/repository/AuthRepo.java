package com.example.authorization.repository;

import com.example.authorization.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepo extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);

}
