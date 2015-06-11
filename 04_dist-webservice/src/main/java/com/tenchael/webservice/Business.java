package com.tenchael.webservice;

/**
 * 描述：服务端对外暴露的接口
 * 
 * @author Administrator
 *
 */
public interface Business {

	/**
	 * 显示客户端提供的信息，并返回
	 */
	public String echo(String message);

}
