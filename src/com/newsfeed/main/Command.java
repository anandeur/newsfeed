package com.newsfeed.main;

public enum Command {
	signup("signup"), login("login"), post("post"), follow("follow"), reply("reply"), upvote("upvote"), downvote(
			"downvote"), shownewsfeed("shownewsfeed");
	private String value;

	private Command(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
