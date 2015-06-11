package com.tenchael.cxf;

import javax.jws.WebService;

/**
 * 描述：以webservice方式对外暴露的服务
 * 
 * @author Administrator
 *
 */
@WebService(serviceName = "BusinessService", endpointInterface = "com.tenchael.cxf.Business")
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
