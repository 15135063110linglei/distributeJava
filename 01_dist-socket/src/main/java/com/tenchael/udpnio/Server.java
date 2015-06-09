package com.tenchael.udpnio;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;

/**
 * 描述：基于Java NIO实现的udp服务器端
 * 
 * @author Administrator
 *
 */
public class Server {
	
	public static final String REMOTE_ADDRESS = "192.168.1.189";

	public static final int SRC_PORT = 9527;
	public static final int DEST_PORT = 9528;

	public static void main(String[] args) throws Exception {

		DatagramChannel sendChannel = DatagramChannel.open();
		sendChannel.configureBlocking(false);
		SocketAddress target = new InetSocketAddress(REMOTE_ADDRESS, DEST_PORT);
		sendChannel.connect(target);

		DatagramChannel receiveChannel = DatagramChannel.open();
		DatagramSocket serverSocket = receiveChannel.socket();
		serverSocket.bind(new InetSocketAddress(SRC_PORT));
		System.out.println("Data receive listen on port: " + SRC_PORT);
		receiveChannel.configureBlocking(false);
		Selector selector = Selector.open();
		receiveChannel.register(selector, SelectionKey.OP_READ);
		while (true) {
			int nKeys = selector.select(1000);
			if (nKeys > 0) {
				for (SelectionKey key : selector.selectedKeys()) {
					if (key.isReadable()) {
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						DatagramChannel dc = (DatagramChannel) key.channel();
						dc.receive(buffer);
						buffer.flip();
						String message = Charset.forName("UTF-8")
								.decode(buffer).toString();
						System.out.println("Message from client: " + message);
						if ("quit".equalsIgnoreCase(message.trim())) {
							dc.close();
							selector.close();
							sendChannel.close();
							System.out.println("Server has been shutdown!");
							System.exit(0);
						}
						String outMessage = "Server response：" + message;
						sendChannel.write(Charset.forName("UTF-8").encode(
								outMessage));
					}
				}
				selector.selectedKeys().clear();
			}
		}
	}

}
