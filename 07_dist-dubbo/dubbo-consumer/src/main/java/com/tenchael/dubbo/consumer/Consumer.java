package com.tenchael.dubbo.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tenchael.dubbo.provider.DemoService;

public class Consumer {

	// load context config file from local project
	public static final String contextLocation = "consumer-context.xml";

	// load context file from network
	// public static final String contextLocation = "http://192.168.1.60/cfg-files/consumer-context.xml";

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { contextLocation });
		context.start();
		DemoService demoService = (DemoService) context.getBean("demoService");
		String response = demoService.echo("helooooooo");
		System.out.println(response);

		System.in.read(); // 按任意键退出
	}
}
