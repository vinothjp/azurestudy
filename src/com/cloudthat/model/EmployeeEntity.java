package com.cloudthat.model;

import com.microsoft.azure.storage.table.TableServiceEntity;

public class EmployeeEntity extends TableServiceEntity {

	public EmployeeEntity(String lastName, String firstName) {
		this.partitionKey = lastName;
		this.rowKey = firstName;
	}

	public EmployeeEntity() {
	}

	private String email;
	private String profileURL;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfileURL() {
		return profileURL;
	}

	public void setProfileURL(String profileURL) {
		this.profileURL = profileURL;
	}

	@Override
	public String toString() {
		return "User [ firstName=" + this.rowKey + ", lastName=" + this.partitionKey + ", email=" + email + "]";
	}

}
