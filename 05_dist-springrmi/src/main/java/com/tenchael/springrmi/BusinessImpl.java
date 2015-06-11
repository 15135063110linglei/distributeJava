package com.tenchael.springrmi;

/**
 * 描述：对外暴露的服务
 * 
 * @author Administrator
 *
 */
public class BusinessImpl implements Business {

	public String echo(String message) {
		if ("quit".equalsIgnoreCase(message.toString())) {
			System.out.println("Server will be shutdown!");
			System.exit(0);
		}
		System.out.println("Message from client: " + message);
		return "Server response：" + message;
	}

}
