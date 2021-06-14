package it.rdev.blog.api.controller.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserDTO {
	
	private Integer id;
	private String username;
	private String password;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public UserDTO setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public UserDTO setPassword(String password) {
		this.password = password;
		return this;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", username=" + username + "]";
	}
	
	
}