package com.tenchael.cxf;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

public class Server {

	public static final String PUBLISH_ADDRESS = "http://192.168.1.187:9527/business";

	public static void main(String[] args) throws Exception {
		Business service = new BusinessImpl();
		JaxWsServerFactoryBean svrFactory = new JaxWsServerFactoryBean();
		svrFactory.setServiceClass(Business.class);
		svrFactory.setAddress(PUBLISH_ADDRESS);
		svrFactory.setServiceBean(service);
		svrFactory.create();
	}

}
