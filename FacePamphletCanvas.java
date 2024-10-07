/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import acm.graphics.*;
import acm.program.GraphicsProgram;

import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas implements FacePamphletConstants {
	// private GLabel label;
	private String message = "";

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public FacePamphletCanvas() {

	}

	/*
	 * public void run(){
	 * 
	 * FacePamphletProfile profile=new FacePamphletProfile("bob");
	 * profile.friends.add("Saba"); profile.friends.add("George");
	 * profile.setImage(new GImage("EricM.jpg")); profile.setStatus("coding");
	 * displayProfile(profile);
	 * 
	 * showMessage("hello my name is ..."); }
	 */

	/**
	 * This method displays a message string near the bottom of the canvas.
	 * Every time this method is called, the previously displayed message (if
	 * any) is replaced by the new message text passed in.
	 */
	public void showMessage(String msg) {
		message = msg;
		GLabel label = new GLabel(message);
		label.setFont(MESSAGE_FONT);
		add(label, getWidth() / 2 - label.getWidth() / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
	}

	/**
	 * This method displays the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages displayed near the
	 * bottom of the screen) and then the given profile is displayed. The
	 * profile display includes the name of the user from the profile, the
	 * corresponding image (or an indication that an image does not exist), the
	 * status of the user, and a list of the user's friends in the social
	 * network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		displayName(profile.getName());
		displayImage(profile.getImage());
		displayStatus(profile.getName(), profile.getStatus());
		displayFriends(profile.getFriends());
	}

	private void displayName(String name) {
		GLabel label = new GLabel(name);
		label.setFont(PROFILE_NAME_FONT);
		add(label, LEFT_MARGIN, TOP_MARGIN);
	}

	private void displayImage(GImage image) {
		if (image != null) {
			GImage profileImage = image;
			profileImage.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(profileImage, LEFT_MARGIN, IMAGE_MARGIN + TOP_MARGIN);
		} else {
			GRect rect = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(rect, LEFT_MARGIN, IMAGE_MARGIN + TOP_MARGIN);
			GLabel label = new GLabel("No Image");
			label.setFont(PROFILE_IMAGE_FONT);
			add(label, LEFT_MARGIN + 50, IMAGE_MARGIN + TOP_MARGIN + rect.getHeight() / 2);
		}
	}

	private void displayStatus(String name, String status) {
		GLabel statusLabel;
		if (status == null || status.equals("")) {
			statusLabel = new GLabel("No Current Status");
		} else {
			statusLabel = new GLabel(name + " is " + status);
		}
		statusLabel.setFont(PROFILE_STATUS_FONT);
		add(statusLabel, LEFT_MARGIN, STATUS_MARGIN + IMAGE_MARGIN + TOP_MARGIN + IMAGE_HEIGHT);
	}

	private void displayFriends(Iterator<String> friends) {
		GLabel label = new GLabel("Friends:");
		label.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(label, getWidth() / 2 - label.getWidth() / 2, IMAGE_MARGIN + TOP_MARGIN);
		double y = label.getY() + label.getHeight();
		double x = label.getX();

		while (friends.hasNext()) {
			GLabel friend = new GLabel(friends.next());
			friend.setFont(PROFILE_FRIEND_LABEL_FONT);
			add(friend, x, y);
			y += label.getHeight();
		}
	}
}
