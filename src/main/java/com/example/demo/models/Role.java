package com.example.demo.models;

import java.util.*;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role
{    
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String name;
	
	@OneToMany(mappedBy = "role", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Privilege> userRoles = new ArrayList<>();

	public Role()
	{
		super();
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return "Role [id=" + id + ", name=" + name + ", userRoles=" + userRoles + "]";
	}
}