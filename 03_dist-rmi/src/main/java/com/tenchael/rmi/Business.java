package com.tenchael.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 描述：服务器端的业务功能类
 * 
 * @author Tenchael
 *
 */
public interface Business extends Remote {

	/**
	 * 显示客户端提供的信息，并返回
	 */
	public String echo(String message) throws RemoteException;

}
