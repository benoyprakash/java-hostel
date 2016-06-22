package com.hostel.info;

/**
 * The persistent class for the room database table.
 * 
 */
public class RoomInfo {

	private String id;

	private byte bathAttached;

	private String description;

	private String roomNum;

	private String roomTitle;

	public RoomInfo() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte getBathAttached() {
		return this.bathAttached;
	}

	public void setBathAttached(byte bathAttached) {
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

}