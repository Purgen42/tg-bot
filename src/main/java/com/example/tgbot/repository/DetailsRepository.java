package com.example.tgbot.repository;

import com.example.tgbot.entity.user.User;
import com.example.tgbot.entity.user.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DetailsRepository extends JpaRepository<UserDetails, UUID> {
}
