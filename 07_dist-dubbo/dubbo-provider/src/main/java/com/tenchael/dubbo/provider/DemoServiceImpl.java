package com.tenchael.dubbo.provider;

public class DemoServiceImpl implements DemoService {

	public String echo(String msg) {
		return "provider response: " + msg;
	}

}
