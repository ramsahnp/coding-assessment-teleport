package com.teleport.must.have.skills.one_three.repository;

import com.teleport.must.have.skills.one_three.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}

