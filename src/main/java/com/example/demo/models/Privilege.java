package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name="privilege")
public class Privilege {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="role_id")
	private Role role;
	
	public Privilege() {
		
	}

	public Privilege(User user, Role role) {
		this.user = user;
		this.role = role;
	}

	public Long getUserRoleId() {
		return id;
	}

	public void setUserRoleId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString()
	{
		return "UserRole [id=" + id + ", role=" + role.getName() + "]";
	}
}
