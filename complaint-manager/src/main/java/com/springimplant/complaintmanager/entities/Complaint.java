package com.springimplant.complaintmanager.entities;


import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="complaints")
public class Complaint {
	
	@Id
	@Column(name="idcomplaints",updatable = false, nullable = false)
	@GenericGenerator(name="inc",strategy="increment")
	@GeneratedValue(generator="inc")
	private Integer id;
	
	@Column(name="message")
	private String message;
	
	@Column(name="sender_name")
	private String senderName;
	
	@Column(name="sender_email")
	private String senderEmail;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Complaint() {
		super();
	}

	@Override
	public String toString() {
		return "Complaint [id=" + id + ", message=" + message + ", senderName=" + senderName + ", senderEmail="
				+ senderEmail + "]";
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public Complaint(String message, String senderName, String senderEmail) {
		super();
		this.message = message;
		this.senderName = senderName;
		this.senderEmail = senderEmail;
	}

}
