package com.tenchael.rmi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * 描述：基于RMI实现的客户端
 * 
 * @author Tenchael
 *
 */
public class Client {

	public static final String host = "localhost";
	public static final String name = "BusinessDemo";

	public static void main(String[] args) throws Exception {
		Registry registry = LocateRegistry.getRegistry(host);

		Business business = (Business) registry.lookup(name);
		BufferedReader systemIn = new BufferedReader(new InputStreamReader(
				System.in));
		while (true) {
			String command = systemIn.readLine();
			if (command == null || "quit".equalsIgnoreCase(command.trim())) {
				System.out.println("Client quit!");
				try {
					business.echo(command);
				} catch (Exception e) {
					// IGNORE
				}
				System.exit(0);
			}
			System.out.println(business.echo(command));
		}
	}

}
