package com.newsfeed.main;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Item implements Comparable<Item> {
	private static int itemCount = 0;
	private int id;
	private User owner;
	private String comment;
	private int upvotes;
	private int downvotes;
	private Map<Integer, Item> replies;
	private Date createdTime;

	public Item(User owner, String comment) {
		itemCount++;
		this.id = itemCount;
		this.owner = owner;
		this.comment = comment;
		this.replies = new HashMap<Integer, Item>();
		this.createdTime = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void incrementUpvotes() {
		upvotes += 1;
	}

	public int getUpvotes() {
		return upvotes;
	}

	public void incrementDownvotes() {
		downvotes += 1;
	}

	public int getDownvotes() {
		return downvotes;
	}

	public void setDownvotes(int downvotes) {
		this.downvotes = downvotes;
	}

	public Map<Integer, Item> getReplies() {
		return replies;
	}

	public void setReplies(Map<Integer, Item> replies) {
		this.replies = replies;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public int getScore() {
		int score = upvotes - downvotes;
		score += replies.size();
		score += createdTime.getTime();
		return score;
	}

	@Override
	public int compareTo(Item target) {
		return target.getScore() - this.getScore();
	}

}
