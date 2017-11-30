
/**
 * CSE143 HW3
 * Written by: Su Wang, enrolled in quiz session AC, Student ID: 1627344 
 * AssassinManager administers the game of "Assassin" by keeping track of players who are in the game and who are out.
 */
import java.util.*;

public class AssassinManager {
	private AssassinNode frontKill;
	private AssassinNode frontGrave;

	/**
	 * Initialize an AssassinManager, represents the information of who is
	 * stalking whom in the game.
	 * 
	 * @param names
	 *            should be a list of string, the list should not be empty or
	 *            null, will throw IllegalArgumentException if violated. Assumes
	 *            names are non-empty, non-null strings without duplicates. The
	 *            names' order represents player's order.
	 */
	public AssassinManager(List<String> names) {
		if (names == null || names.size() == 0) {
			throw new IllegalArgumentException();
		}
		frontKill = null;
		frontGrave = null;
		for (int i = names.size() - 1; i >= 0; i--) {
			frontKill = new AssassinNode(names.get(i), frontKill);
		}
	}

	/**
	 * Prints the names of people who are in the game. In the format of:" X is
	 * stalking Y". One per line. Will print "X is stalking X" if the game is
	 * over and X is the winner.
	 */
	public void printKillRing() {
		if (frontKill != null) {
			AssassinNode current = frontKill;
			while (current.next != null) {
				System.out.println("    " + current.name + " is stalking " 
			+ current.next.name);
				current = current.next;	
			}
			System.out.println("    " + current.name + " is stalking " 
			+ frontKill.name);
		}
	}

	/**
	 * Print the names of people who were "killed" during the game. In the
	 * format of:" X was killed by Y". One per line. Players who were "most
	 * recently killed" will be printed first. Print nothing if everyone is in
	 * the game.
	 */
	public void printGraveyard() {
		if (frontGrave != null) {
			AssassinNode current = frontGrave;
			while (current != null) {
				System.out.println("    " + current.name + " was killed by " 
			+ current.killer);
				current = current.next;
			}
		}
	}

	/**
	 * Check if a given person is currently in the game.
	 * 
	 * @param name
	 *            should be string, case insensitive.
	 * @returns true if the person is in the game, false if not.
	 */
	public boolean killRingContains(String name) {
		return indexOf(name, frontKill) >= 0;
	}

	/**
	 * Check if a given person has already been "killed" and thus out of game.
	 * 
	 * @param name
	 *            should be string, case insensitive.
	 * @returns true if the person is out of game, false if not.
	 */
	public boolean graveyardContains(String name) {
		return indexOf(name, frontGrave) >= 0;
	}

	/**
	 * Check if the game is over, i.e. only one person is still in the game.
	 * 
	 * @returns true if game is over, false if not.
	 */
	public boolean isGameOver() {
		return frontKill.next == null;
	}

	/**
	 * Gets the name of the game's winner.
	 * 
	 * @returns a string, represents the winner of the game. returns null if the
	 *          game is still on.
	 */
	public String winner() {
		if (isGameOver()) {
			return frontKill.name;
		} else {
			return null;
		}
	}

	/**
	 * Move someone who had just been "killed" out of the game. Mark this player
	 * as someone out of game and keep track of his killer.
	 * 
	 * @param name
	 *            should be a string, case insensitive, will throw
	 *            IllegalStateException if the game is over. the given name
	 *            should be a person in the game, throws
	 *            IllegalArgumentException if not found.If the passing-in name
	 *            can not be found and the game is over, throw
	 *            IllegalStateException.
	 */
	public void kill(String name) {
		if (isGameOver()) {
			throw new IllegalStateException();
		} else if (!killRingContains(name)) {
			throw new IllegalArgumentException();
		}
		AssassinNode killed = goTo(indexOf(name, frontKill));
		// if the killed person is the first one in the kill ring.
		if (indexOf(name, frontKill) == 0) {
			frontKill = frontKill.next;
			// go the the last node in the kill ring and get its name
			AssassinNode last = frontKill;
			while (last.next != null) {
				last = last.next;
			}
			killed.killer = last.name;
			// if the killed person is not the first person in the kill ring.
		} else {
			killed.killer = goTo(indexOf(name, frontKill) - 1).name;
			goTo(indexOf(name, frontKill) - 1).next = goTo(indexOf(name, frontKill)).next;
		}
		// if the given name is the first person being "killed".
		if (frontGrave == null) {
			killed.next = null;
			// if the given name is not the first person being "killed", put his
			// name at the front.
		} else {
			killed.next = frontGrave;
		}
		frontGrave = killed;
	}

	/**
	 * Gets the order of a person in a list. 
	 * 
	 * @param name
	 *            should be a string, case insensitive.
	 * @param front
	 *            should be an the first AssassinNode that you are
	 *            searching for an index in.
	 * @returns an integer, indicates the index of the name found. Starting from
	 *          zero. Returns -1 if the name is not found.
	 */
	private int indexOf(String name, AssassinNode front) {
		int index = 0;
		AssassinNode current = front;
		while (current != null) {
			if (current.name.toLowerCase().equals(name.toLowerCase())) {
				return index;
			}
			index++;
			current = current.next;
		}
		return -1;
	}

	/**
	 * Go to a node given the index of that node.
	 * 
	 * @param index
	 *            should be an integer, starting from zero.
	 * @returns an AssassinNode.
	 */
	private AssassinNode goTo(int index) {
		AssassinNode current = frontKill;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current;
	}

}
