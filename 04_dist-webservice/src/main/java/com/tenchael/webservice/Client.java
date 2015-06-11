package com.tenchael.webservice;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.tenchael.webservice.client.BusinessService;

/**
 * 描述：基于Webservice实现的客户端
 * 
 * @author Administrator
 *
 */
public class Client {

	public static void main(String[] args) throws Exception {
		BusinessService businessService = new BusinessService();
		com.tenchael.webservice.client.Business business = businessService
				.getBusinessPort();
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
