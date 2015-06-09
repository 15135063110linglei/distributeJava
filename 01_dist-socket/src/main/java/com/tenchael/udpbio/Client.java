package com.tenchael.udpbio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 描述：基于java实现UDP/IP+BIO方式的网络通讯示例，此为客户端代码
 * 
 * @author Administrator
 *
 */
public class Client {

	// 目的地址
	public static final String DEST_ADDRESS = "192.168.1.187";

	// 源端口，本地监听的服务端口
	public static final int SRC_PORT = 9528;
	// 目的端口，远端服务端口
	public static final int DEST_PORT = 9527;

	public static void main(String[] args) throws Exception {

		// 由于UDP/IP是无连接的，如果需要双向通信，就必须启动一个监听端口，承担服务器的职能
		DatagramSocket serverSocket = new DatagramSocket(SRC_PORT);

		byte[] buffer = new byte[65507];
		DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);

		// 客户端socket
		DatagramSocket socket = new DatagramSocket();

		// 服务器地址
		InetAddress server = InetAddress.getByName(DEST_ADDRESS);

		// 从标准输入流读取数据的BufferedReader
		BufferedReader systemIn = new BufferedReader(new InputStreamReader(
				System.in));

		boolean flag = true;
		while (flag) {
			String command = systemIn.readLine();
			byte[] datas = command.getBytes("UTF-8");

			// 封装传送给服务器的数据报文,需指定数据报文的目的地址和目的端口
			DatagramPacket packet = new DatagramPacket(datas, datas.length,
					server, DEST_PORT);

			// 发送数据报文
			socket.send(packet);

			if (command == null || "quit".equalsIgnoreCase(command.trim())) {
				flag = false;
				System.out.println("Client quit!");
				socket.close();
				continue;
			}

			// 从服务器接受数据报文
			serverSocket.receive(receivePacket);
			String receiveResponse = new String(receivePacket.getData(), 0,
					receivePacket.getLength(), "UTF-8");

			System.out.println(receiveResponse);
		}
	}

}
