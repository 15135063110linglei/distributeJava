package com.tenchael.udpbio;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 描述：基于java实现UDP/IP+BIO方式的网络通讯示例，此为服务器端代码
 * 
 * @author Administrator
 *
 */
public class Server {

	// 目的地址
	public static final String DEST_ADDRESS = "192.168.1.189";

	// 源端口，本地监听的服务端口
	public static final int SRC_PORT = 9527;
	// 目的端口，远端服务端口
	public static final int DEST_PORT = 9528;

	public static void main(String[] args) throws Exception {
		// 由于UDP/IP是无连接的，如果需要双向通信，就必须启动一个监听端口，承担服务器的职能
		DatagramSocket server = new DatagramSocket(SRC_PORT);
		DatagramSocket client = new DatagramSocket();

		InetAddress serverAddress = InetAddress.getByName(DEST_ADDRESS);
		byte[] buffer = new byte[65507];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

		while (true) {
			// 接受数据报文
			server.receive(packet);
			String line = new String(packet.getData(), 0, packet.getLength(),
					"UTF-8");
			if ("quit".equalsIgnoreCase(line.trim())) {
				server.close();
				System.exit(0);
			} else {
				System.out.println("Message from client: " + line);
				packet.setLength(buffer.length);
				String response = "Server response：" + line;
				byte[] datas = response.getBytes("UTF-8");

				// 响应报文，封装了传输给客户端的数据报文，需指定目的地址和目的端口
				DatagramPacket responsePacket = new DatagramPacket(datas,
						datas.length, serverAddress, DEST_PORT);

				// 发送给客户端
				client.send(responsePacket);
				Thread.sleep(100);
			}
		}
	}

}
