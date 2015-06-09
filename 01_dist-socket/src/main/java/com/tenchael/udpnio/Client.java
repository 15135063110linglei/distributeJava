package com.tenchael.udpnio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;

/**
 * 描述：基于java NIO实现的udp client
 * 
 * @author Administrator
 *
 */
public class Client {

	public static final String REMOTE_ADDRESS = "192.168.1.187";

	public static final int SRC_PORT = 9528;
	public static final int DEST_PORT = 9527;

	public static void main(String[] args) throws Exception {
		
		DatagramChannel receiveChannel = DatagramChannel.open();

		// 设置为非阻塞方法
		receiveChannel.configureBlocking(false);
		DatagramSocket socket = receiveChannel.socket();
		// 绑定本地端口用作服务
		socket.bind(new InetSocketAddress(SRC_PORT));

		Selector selector = Selector.open();
		receiveChannel.register(selector, SelectionKey.OP_READ);

		DatagramChannel sendChannel = DatagramChannel.open();
		sendChannel.configureBlocking(false);
		SocketAddress target = new InetSocketAddress(REMOTE_ADDRESS, DEST_PORT);
		sendChannel.connect(target);

		BufferedReader systemIn = new BufferedReader(new InputStreamReader(
				System.in));

		while (true) {
			String command = systemIn.readLine();
			sendChannel.write(Charset.forName("UTF-8").encode(command));
			if (command == null || "quit".equalsIgnoreCase(command.trim())) {
				systemIn.close();
				sendChannel.close();
				selector.close();
				System.out.println("Client quit!");
				System.exit(0);
			}
			int nKeys = selector.select(1000);
			if (nKeys > 0) {
				for (SelectionKey key : selector.selectedKeys()) {
					if (key.isReadable()) {
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						DatagramChannel dc = (DatagramChannel) key.channel();
						dc.receive(buffer);
						buffer.flip();
						System.out.println(Charset.forName("UTF-8")
								.decode(buffer).toString());
						buffer = null;
					}
				}
				selector.selectedKeys().clear();
			}
		}
	}

}
