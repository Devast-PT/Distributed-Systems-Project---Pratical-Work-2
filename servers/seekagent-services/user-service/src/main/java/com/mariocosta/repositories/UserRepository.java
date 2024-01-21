package com.mariocosta.repositories;

import com.mariocosta.entities.Userr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Userr, Long> {

    Userr findByEmail(String email);

    Userr findByUsername(String username);

}
