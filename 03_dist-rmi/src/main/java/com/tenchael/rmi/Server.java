package com.tenchael.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.tenchael.rmi.impl.BusinessImpl;

/**
 * 描述：基于RMI实现的服务器端
 * 
 * @author Tenchael
 *
 */
public class Server {

	public static final int port = 9527;
	public static final String name = "BusinessDemo";

	public static void main(String[] args) throws Exception {

		Business business = new BusinessImpl();
		UnicastRemoteObject.exportObject(business, port);
		Registry registry = LocateRegistry.createRegistry(1099);
		registry.rebind(name, business);
	}

}
