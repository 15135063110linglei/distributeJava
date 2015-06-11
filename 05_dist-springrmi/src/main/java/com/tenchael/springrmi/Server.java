package com.tenchael.springrmi;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Server {

	public static void main(String[] args) throws Exception {
		new ClassPathXmlApplicationContext("server.xml");
		System.out.println("Server has been started");
	}

}
