
/*
 * File: FacePamphletDatabase.java
 * -------------------------------
 * This class keeps track of the profiles of all users in the
 * FacePamphlet application.  Note that profile names are case
 * sensitive, so that "ALICE" and "alice" are NOT the same name.
 */

import java.util.*;

public class FacePamphletDatabase implements FacePamphletConstants {

	public ArrayList<FacePamphletProfile> database;

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * database.
	 */
	public FacePamphletDatabase() {
		database = new ArrayList<FacePamphletProfile>();
	}

	/**
	 * This method adds the given profile to the database. If the name
	 * associated with the profile is the same as an existing name in the
	 * database, the existing profile is replaced by the new profile passed in.
	 */
	public void addProfile(FacePamphletProfile profile) {

		removeProfile(profile);
		database.add(profile);
	}

	// This function removes profile from database if it exists
	private void removeProfile(FacePamphletProfile profile) {

		for (int i = 0; i < database.size(); i++) {
			if (database.get(i).getName().equals(profile.getName())) {
				database.remove(i);
			}
		}
	}

	// This function also removes profile from database, but parameter is String
	// name this time
	private void removeProfile(String name) {

		for (int i = 0; i < database.size(); i++) {
			if (database.get(i).getName().equals(name)) {
				database.remove(i);
			}
		}
	}

	/**
	 * This method returns the profile associated with the given name in the
	 * database. If there is no profile in the database with the given name, the
	 * method returns null.
	 */
	public FacePamphletProfile getProfile(String name) {

		for (FacePamphletProfile person : database) {
			if (person.getName().equals(name)) {
				return person;
			}
		}

		return null;
	}

	/**
	 * This method removes the profile associated with the given name from the
	 * database. It also updates the list of friends of all other profiles in
	 * the database to make sure that this name is removed from the list of
	 * friends of any other profile.
	 * 
	 * If there is no profile in the database with the given name, then the
	 * database is unchanged after calling this method.
	 */
	public void deleteProfile(String name) {
		ArrayList<String> friendsNames = getProfile(name).friends;
		removeProfile(name);

		for (int i = 0; i < friendsNames.size(); i++) {
			getProfile(friendsNames.get(i)).friends.remove(name);
		}
	}

	/**
	 * This method returns true if there is a profile in the database that has
	 * the given name. It returns false otherwise.
	 */
	public boolean containsProfile(String name) {

		for (FacePamphletProfile person : database) {
			if (person.getName().equals(name)) {
				return true;
			}
		}

		return false;
	}

}
