package com.tenchael.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 描述：基于java实现UDP/IP+BIO方式的终端聊天软件
 * 
 * @author Administrator
 *
 */
public class App {

	public static final int PORT = 9527;

	public static void main(String[] args) throws Exception {

		// 由于UDP/IP是无连接的，如果需要双向通信，就必须启动一个监听端口，承担服务器的职能
		DatagramSocket receiveSocket = new DatagramSocket(PORT);

		BufferedReader systemIn = new BufferedReader(new InputStreamReader(
				System.in));
		System.out.println("input remote address:");
		String remoteAddr = systemIn.readLine();

		System.out.println("===========================");

		// 开启一个消息发送线程
		Sender sender = new Sender(remoteAddr, PORT);
		new Thread(sender).start();

		byte[] buffer = new byte[65507];
		DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);

		boolean flag = true;
		while (flag) {
			// 接受数据报文
			receiveSocket.receive(receivePacket);
			String line = new String(receivePacket.getData(), 0,
					receivePacket.getLength(), "UTF-8");
			if ("quit".equalsIgnoreCase(line.trim())) {
				System.out.println("bye!");
				receiveSocket.close();
				System.exit(0);
			} else {
				System.out.println(remoteAddr + "-> " + line);
				Thread.sleep(100);
			}
		}
	}

}

class Sender implements Runnable {

	// 客户端socket
	DatagramSocket socket;
	// 从标准输入流读取数据的BufferedReader
	BufferedReader systemIn;
	// 远端服务器地址
	InetAddress remoteAddress;
	// 远端服务器端口
	int port;

	public Sender(String remoteAddr, int port) {
		try {
			this.remoteAddress = InetAddress.getByName(remoteAddr);
			this.port = port;
			socket = new DatagramSocket();
			// 从标准输入流读取数据的BufferedReader
			systemIn = new BufferedReader(new InputStreamReader(System.in));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			try {
				String command = systemIn.readLine();
				byte[] datas = command.getBytes("UTF-8");

				// 封装传送给服务器的数据报文,需指定数据报文的目的地址和目的端口
				DatagramPacket packet = new DatagramPacket(datas, datas.length,
						remoteAddress, port);

				// 发送数据报文
				socket.send(packet);

				if (command == null || "quit".equalsIgnoreCase(command.trim())) {
					socket.close();
					throw new RuntimeException();
				}
			} catch (Exception e) {
				System.exit(0);
			}

		}
	}

}
