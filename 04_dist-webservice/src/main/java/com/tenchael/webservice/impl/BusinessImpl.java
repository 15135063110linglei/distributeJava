package com.tenchael.webservice.impl;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.tenchael.webservice.Business;

/**
 * 描述：以webservice方式对外暴露的服务
 * 
 * @author Administrator
 *
 */
@WebService(name = "Business", serviceName = "BusinessService", targetNamespace = "http://webservice.tenchael.com/client")
@SOAPBinding(style = SOAPBinding.Style.RPC)
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
