package com.tenchael.cxf;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class Client {

	public static final String SERVICE_ADDRESS = "http://192.168.1.187:9527/business";

	public static void main(String[] args) throws Exception {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(Business.class);
		factory.setAddress(SERVICE_ADDRESS);
		Business business = (Business) factory.create();
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
