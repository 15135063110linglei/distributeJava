package com.tenchael.dubbo.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tenchael.dubbo.provider.DemoService;

public class Consumer {
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "consumer-context.xml" });
		context.start();
		DemoService demoService = (DemoService) context.getBean("demoService");
		String response = demoService.echo("helooooooo");
		System.out.println(response);

		System.in.read(); // 按任意键退出
	}
}
