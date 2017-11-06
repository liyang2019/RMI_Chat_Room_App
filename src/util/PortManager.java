package util;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Port manager, give available port number.
 *
 */
public class PortManager {

	private static Queue<Integer> availPorts;
	
	/**
	 * Singleton pattern
	 */
	public static PortManager Singleton = new PortManager();
	
	private PortManager() {
		availPorts = new LinkedList<>();
		// set the available ports for receiver number from 2101 to 2199, 
		for (int i = 3101; i < 3200; i++) {
			availPorts.offer(i);
		}
	}	
	
	/**
	 * get one available port.
	 * @return a port number.
	 */
	public int getAvailPort() {
		if (availPorts.isEmpty()) {
			System.out.println("No available port.");
			return -1;
		}
		return availPorts.poll();
	}
	
}
