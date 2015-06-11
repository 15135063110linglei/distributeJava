package com.tenchael.webservice;

import javax.xml.ws.Endpoint;

import com.tenchael.webservice.impl.BusinessImpl;

/**
 * 描述：基于Java Webservice实现的服务器端
 * 
 * @author Administrator
 *
 */
public class Server {

	public static void main(String[] args) {
		Endpoint.publish("http://192.168.1.187:9527/BusinessService",
				new BusinessImpl());
		System.out.println("Server has beed started");
	}

}
