package com.hostel.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the room database table.
 * 
 */
@Entity
@Table(name="room")
@NamedQuery(name="Room.findAll", query="SELECT r FROM Room r")
public class Room implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name="bath_attached", length=1)
	private Integer bathAttached;

	@Column(length=45)
	private String description;

	@Column(name="room_num", length=5)
	private String roomNum;

	@Column(name="room_title", length=45)
	private String roomTitle;
	
	@Column(name="room_status", length=1)
	private Integer status;

	public Room() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getBathAttached() {
		return this.bathAttached;
	}

	public void setBathAttached(Integer bathAttached) {
		this.bathAttached = bathAttached;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRoomNum() {
		return this.roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public String getRoomTitle() {
		return this.roomTitle;
	}

	public void setRoomTitle(String roomTitle) {
		this.roomTitle = roomTitle;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}