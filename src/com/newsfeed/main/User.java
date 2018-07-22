package com.newsfeed.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

	private String name;
	private List<User> following;
	private List<User> followers;
	private boolean loggedIn = false;
	private Map<Integer, Item> posts;

	public User(String name) {
		this.name = name;
		this.following = new ArrayList<User>();
		this.followers = new ArrayList<User>();
		this.posts = new HashMap<Integer, Item>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getFollowing() {
		return following;
	}

	public void setFollowing(List<User> following) {
		this.following = following;
	}

	public List<User> getFollowers() {
		return followers;
	}

	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public Map<Integer, Item> getPosts() {
		return posts;
	}

	public void setPosts(Map<Integer, Item> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
