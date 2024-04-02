package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long>
{
	Role findByName(String name);
}
