package com.tenchael.tcpbio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 描述：基于java实现TCP/IP方式的网络通讯示例，此为服务器端代码
 * 
 * @author Administrator
 *
 */
public class Server {

	public static final int port = 9527;

	public static void main(String[] args) throws Exception {

		// 监听本地端口
		ServerSocket ss = new ServerSocket(port);
		System.out.println("Server listen on port: " + port);

		// 接受客户端Socket连接
		Socket socket = ss.accept();

		// 从客户端读取数据的BufferedReader
		BufferedReader in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));

		// 向客户端写数据的PrintWriter
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		while (true) {
			// 从客户端读取字符流
			String line = in.readLine();
			if (line == null) {
				Thread.sleep(100);
				continue;
			}
			if ("quit".equalsIgnoreCase(line.trim())) {
				in.close();
				out.close();
				ss.close();
				System.out.println("Server has been shutdown!");
				System.exit(0);
			} else {
				System.out.println("Message from client: " + line);

				// 向客户端写数据
				out.println("Server response：" + line);
				Thread.sleep(100);
			}
		}
	}

}
