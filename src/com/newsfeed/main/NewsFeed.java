package com.newsfeed.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class NewsFeed {

	private static final Map<String, User> signedUpUsers = new HashMap<String, User>();
	private static User loggedInUser = null;

	public static void main(String[] args) {
		System.out.println("Welcome to News Feed");
		while (true) {
			Scanner scan = new Scanner(System.in);
			try {
				String input = scan.nextLine();
				String[] params = input.split("~");
				Command command = Command.valueOf(params[0]);
				switch (command) {
				case signup:
					if (params.length == 2) {
						User user = new User(params[1]);
						signedUpUsers.put(params[1].toLowerCase(), user);
					} else {
						invalidInput(input);
					}
					break;
				case login:
					if (params.length == 2) {
						String username = params[1].toLowerCase();
						if (signedUpUsers.containsKey(username)) {
							User user = signedUpUsers.get(username);
							loggedInUser = user;
							printNewsFeed();
						} else {
							userUnavailable(username);
						}
					} else {
						invalidInput(input);
					}
					break;
				case post:
					if (params.length == 2) {
						if (loggedInUser != null) {
							Item post = new Item(loggedInUser, params[1]);
							loggedInUser.getPosts().put(post.getId(), post);
							// add post to all followers
							List<User> followers = loggedInUser.getFollowers();
							for (User follower : followers) {
								follower.getPosts().put(post.getId(), post);
							}
						} else {
							noLogin();
						}
					} else {
						invalidInput(input);
					}
					break;
				case follow:
					if (params.length == 2) {
						if (loggedInUser != null) {
							String username = params[1].toLowerCase();
							if (signedUpUsers.containsKey(username)) {
								User user = signedUpUsers.get(username);
								loggedInUser.getFollowing().add(user);
								user.getFollowers().add(loggedInUser);
								// Add following users posts
								loggedInUser.getPosts().putAll(user.getPosts());
							} else {
								userUnavailable(username);
							}
						} else {
							noLogin();
						}
					} else {
						invalidInput(input);
					}
					break;
				case reply:
					if (params.length == 3) {
						if (loggedInUser != null) {
							Integer postId = Integer.valueOf(params[1]);
							Item post = loggedInUser.getPosts().get(postId);
							if (post != null) {
								Item reply = new Item(loggedInUser, params[2]);
								post.getReplies().put(reply.getId(), reply);
							} else {
								noPost(postId);
							}
						} else {
							noLogin();
						}
					} else {
						invalidInput(input);
					}
					break;
				case upvote:
					if (params.length == 2) {
						if (loggedInUser != null) {
							Integer postId = Integer.valueOf(params[1]);
							Map<Integer, Item> postAndReplies = new HashMap<Integer, Item>();
							for (Item post : loggedInUser.getPosts().values()) {
								postAndReplies.put(post.getId(), post);
								for (Item reply : post.getReplies().values()) {
									postAndReplies.put(reply.getId(), reply);
								}
							}
							Item post = postAndReplies.get(postId);
							if (post != null) {
								post.incrementUpvotes();
							} else {
								noPost(postId);
							}
						} else {
							noLogin();
						}
					} else {
						invalidInput(input);
					}
					break;
				case downvote:
					if (params.length == 2) {
						if (loggedInUser != null) {
							Integer postId = Integer.valueOf(params[1]);
							Map<Integer, Item> postAndReplies = new HashMap<Integer, Item>();
							for (Item post : loggedInUser.getPosts().values()) {
								postAndReplies.put(post.getId(), post);
								for (Item reply : post.getReplies().values()) {
									postAndReplies.put(reply.getId(), reply);
								}
							}
							Item post = postAndReplies.get(postId);
							if (post != null) {
								post.incrementDownvotes();
							} else {
								noPost(postId);
							}
						} else {
							noLogin();
						}
					} else {
						invalidInput(input);
					}
					break;
				case shownewsfeed:
					if (params.length == 1) {
						if (loggedInUser != null) {
							printNewsFeed();
						} else {
							noLogin();
						}
					} else {
						invalidInput(input);
					}
					break;
				}
			} catch (Exception e) {
				System.out.println("Invalid input");
			}
		}
	}

	public static void invalidInput(String input) {
		System.out.println("Invalid input: " + input);
	}

	public static void userUnavailable(String username) {
		System.out.println("User not available: " + username);
	}

	public static void noLogin() {
		System.out.println("No user logged in");
	}

	public static void noPost(Integer id) {
		System.out.println("No post with id:" + id);
	}

	public static void printNewsFeed() {
		List<Item> posts = new ArrayList<Item>(loggedInUser.getPosts().values());
		Collections.sort(posts);
		for (Item post : posts) {
			System.out.println("id: " + post.getId());
			System.out.println("(" + post.getUpvotes() + " upvote, " + post.getDownvotes() + " downvotes)");
			System.out.println(post.getOwner());
			System.out.println(post.getComment());
			System.out.println(post.getCreatedTime());
			// replies
			Collection<Item> replies = post.getReplies().values();
			for (Item reply : replies) {
				System.out.println("\t id: " + reply.getId());
				System.out.println("\t (" + reply.getUpvotes() + " upvote, " + reply.getDownvotes() + " downvotes)");
				System.out.println("\t " + reply.getOwner());
				System.out.println("\t " + reply.getComment());
				System.out.println("\t " + reply.getCreatedTime());
			}
		}
	}

}
