package com.hostel.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the customer_rooms database table.
 * 
 */
@Entity
@Table(name = "customer_rooms")
@NamedQuery(name = "CustomerRoom.findAll", query = "SELECT c FROM CustomerRoom c")
public class CustomerRoom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cust_room_id", unique = true, nullable = false)
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name = "active_date")
	private Date activeDate;

	@Column(name = "customer_id")
	private Long customerId;

	@Temporal(TemporalType.DATE)
	@Column(name = "inactive_date")
	private Date inactiveDate;

	@Column(name = "room_id")
	private Long roomId;

	@Column(name = "status")
	private Integer status;

	public CustomerRoom() {
	}

	public Long getCustRoomId() {
		return this.id;
	}

	public void setCustRoomId(Long custRoomId) {
		this.id = custRoomId;
	}

	public Date getActiveDate() {
		return this.activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	public Long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Date getInactiveDate() {
		return this.inactiveDate;
	}

	public void setInactiveDate(Date inactiveDate) {
		this.inactiveDate = inactiveDate;
	}

	public Long getRoomId() {
		return this.roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}