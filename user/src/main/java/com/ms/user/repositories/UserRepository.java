package com.ms.user.repositories;

import com.ms.user.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {

    @Modifying
    @Query("UPDATE UserModel SET deletedAt = CURRENT_TIMESTAMP WHERE email = :email and name = :name")
    void disableUser(@Param("email") String email, @Param("name") String name);

    Optional<UserModel> findByNameAndEmail(String name, String email);
}
