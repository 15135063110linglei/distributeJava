package com.tenchael.tcpbio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 描述：基于java实现TCP/IP+BIO(Blocking IO)方式的网络通讯示例，此为客户端代码
 * 
 * @author Administrator
 *
 */
public class Client {

	public static final String host = "192.168.1.187";
	public static final int port = 9527;

	public static void main(String[] args) throws Exception {

		// 创建Socket连接
		Socket socket = new Socket(host, port);

		// 从服务器读取数据的BufferedReader
		BufferedReader in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));

		// 向服务器写数据的PrintWriter
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

		// 从标准输入流读取数据的BufferedReader
		BufferedReader systemIn = new BufferedReader(new InputStreamReader(
				System.in));
		boolean flag = true;
		while (flag) {
			String command = systemIn.readLine();
			if (command == null || "quit".equalsIgnoreCase(command.trim())) {
				flag = false;
				System.out.println("Client quit!");
				out.println("quit");
				out.close();
				in.close();
				socket.close();
				continue;
			}

			// 向服务器发送数据
			out.println(command);

			// 从服务器读取数据
			String response = in.readLine();
			System.out.println(response);
		}
	}

}
