package com.c2psi.businessmanagement.repositories.client.command;

import com.c2psi.businessmanagement.models.Command;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<Command, Long> {
}
