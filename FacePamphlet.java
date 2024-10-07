
/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {

	private final JLabel label = new JLabel("Name");
	private JTextField nameTextField;
	private JButton addButton;
	private JButton deleteButton;
	private JButton lookUpButton;

	private JTextField changeStatusField;
	private JButton changeStatusButton;
	private JTextField changePictureField;
	private JButton changePictureButton;
	private JTextField addFriendField;
	private JButton addFriendButton;

	private FacePamphletDatabase database;
	private FacePamphletProfile currentProfile;
	private FacePamphletCanvas canvas;

	/**
	 * This method has the responsibility for initializing the interactors in
	 * the application, and taking care of any other initialization that needs
	 * to be performed.
	 */
	public void init() {

		add(label, NORTH);

		nameTextField = new JTextField(20);
		add(nameTextField, NORTH);
		nameTextField.addActionListener(this);

		addButton = new JButton("add");
		add(addButton, NORTH);
		deleteButton = new JButton("delete");
		add(deleteButton, NORTH);
		lookUpButton = new JButton("lookUp");
		add(lookUpButton, NORTH);

		changeStatusField = new JTextField(15);
		add(changeStatusField, WEST);
		changeStatusField.addActionListener(this);
		changeStatusButton = new JButton("change Status");
		add(changeStatusButton, WEST);

		add(new JLabel(""), WEST);
		add(new JLabel(""), WEST);

		changePictureField = new JTextField(15);
		add(changePictureField, WEST);
		changePictureField.addActionListener(this);
		changePictureButton = new JButton("change Picture");
		add(changePictureButton, WEST);

		add(new JLabel(""), WEST);
		add(new JLabel(""), WEST);

		addFriendField = new JTextField(15);
		add(addFriendField, WEST);
		addFriendField.addActionListener(this);
		addFriendButton = new JButton("add Friend");
		add(addFriendButton, WEST);

		database = new FacePamphletDatabase();
		canvas = new FacePamphletCanvas();
		add(canvas);

		addActionListeners();
	}

	public void run() {

	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addButton) {
			add();
		} else if (e.getSource() == deleteButton) {
			delete();
		} else if (e.getSource() == lookUpButton) {
			lookUp();
		} else if (e.getSource() == changeStatusField || e.getSource() == changeStatusButton) {
			changeStatus();
		} else if (e.getSource() == changePictureField || e.getSource() == changePictureButton) {
			changePicture();
		} else if (e.getSource() == addFriendField || e.getSource() == addFriendButton) {
			addFriend();
		}
	}

	// This func adds person to database and draws it on canvas
	private void add() {
		canvas.removeAll();
		String name = nameTextField.getText();
		if (name != null && !name.equals("")) {
			FacePamphletProfile profile = new FacePamphletProfile(name);
			if (!database.containsProfile(name)) {
				database.addProfile(profile);
				canvas.showMessage("new profile created");
				currentProfile = profile;
			} else {
				canvas.showMessage("profile with the name " + name + " already exists");
			}
			canvas.displayProfile(currentProfile);
		}
	}

	// THis function deltes profile from canvas and from database also
	private void delete() {
		canvas.removeAll();
		String name = nameTextField.getText();
		if (database.containsProfile(name)) {
			database.deleteProfile(name);
			currentProfile = null;
			updateCurrentProfile(currentProfile);
			canvas.showMessage("Profile of " + name + " was deleted");

		} else {
			currentProfile = null;
			updateCurrentProfile(currentProfile);
			canvas.showMessage("There is no profile named " + name);
		}

	}

	// This functions looks up if there is profile with that name
	private void lookUp() {
		canvas.removeAll();
		String name = nameTextField.getText();
		if (database.containsProfile(name)) {
			currentProfile = database.getProfile(name);
			updateCurrentProfile(currentProfile);
			canvas.showMessage("Displaying " + name);
		} else {
			currentProfile = null;
			updateCurrentProfile(currentProfile);
			canvas.showMessage("There is no profile named " + name);
		}
	}

	// This function changes status of profile
	private void changeStatus() {
		canvas.removeAll();
		String text = changeStatusField.getText();
		if (currentProfile != null) {

			currentProfile.setStatus(text);
			updateCurrentProfile(currentProfile);
		} else {
			canvas.showMessage("Please select a profile to change status");
		}
	}

	// This function changesPicture of profile
	private void changePicture() {
		canvas.removeAll();
		String text = changePictureField.getText();
		GImage image = null;
		try {
			image = new GImage(text + ".jpg");
		} catch (ErrorException ex) {

		}
		if (currentProfile == null) {
			canvas.showMessage("Please select a profile to change picture");
		} else {
			if (image != null) {
				currentProfile.setImage(image);
				updateCurrentProfile(currentProfile);
				canvas.showMessage("Picture Updated");
			} else {
				canvas.removeAll();
				updateCurrentProfile(currentProfile);
				canvas.showMessage("Unable to open image file " + text);
			}
		}
	}

	// This function adds friend in database and also updates on canvas
	private void addFriend() {
		canvas.removeAll();
		String name = addFriendField.getText();
		if (currentProfile == null) {
			canvas.showMessage("Please select a profile to add friend");
		} else {
			if (database.containsProfile(name) && isNotTheSamePerson(name)) {
				if (!currentProfile.friends.contains(name)) {
					currentProfile.friends.add(name);
					database.getProfile(name).friends.add(currentProfile.getName());
					updateCurrentProfile(currentProfile);
					canvas.showMessage(name + " was added as a friend");
				} else {
					updateCurrentProfile(currentProfile);
					canvas.showMessage("They are Already Friends");
				}
			} else {
				updateCurrentProfile(currentProfile);
				canvas.showMessage("The friend you want to add does not exist");
			}
		}

	}

	// This function checks if currentProfile has the same name as parameter
	// String name
	private boolean isNotTheSamePerson(String name) {
		if (name.equals(currentProfile.getName())) {
			return false;
		}
		return true;
	}

	// This function draws currentProgile
	public void updateCurrentProfile(FacePamphletProfile profile) {
		if (profile == null) {
			canvas.removeAll();
		} else {
			currentProfile = profile;
			canvas.displayProfile(profile);
		}
	}

}