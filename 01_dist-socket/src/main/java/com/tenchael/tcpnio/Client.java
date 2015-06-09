package com.tenchael.tcpnio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * 描述：基于java NIO实现的tcp client
 * 
 * @author Administrator
 *
 */
public class Client {
	public static final String host = "192.168.1.187";
	public static final int port = 9527;

	public static void main(String[] args) throws Exception {
		SocketChannel channel = SocketChannel.open();

		// 设置为非阻塞模式
		channel.configureBlocking(false);
		SocketAddress target = new InetSocketAddress(host, port);

		// 对于非阻塞方式，立即返回false
		channel.connect(target);
		Selector selector = Selector.open();

		// 向channel注册selector以及感兴趣的连接事件
		channel.register(selector, SelectionKey.OP_CONNECT);

		// 从标准输入流读取数据的BufferedReader
		BufferedReader systemIn = new BufferedReader(new InputStreamReader(
				System.in));
		while (true) {
			if (channel.isConnected()) {
				String command = systemIn.readLine();
				channel.write(Charset.forName("UTF-8").encode(command));
				if (command == null || "quit".equalsIgnoreCase(command.trim())) {
					systemIn.close();
					channel.close();
					selector.close();
					System.out.println("Client quit!");
					System.exit(0);
				}
			}

			// 设置超时时间
			int nKeys = selector.select(1000);
			if (nKeys > 0) {
				for (SelectionKey key : selector.selectedKeys()) {
					// 对于发生连接的事件
					if (key.isConnectable()) {
						SocketChannel sc = (SocketChannel) key.channel();
						sc.configureBlocking(false);
						sc.register(selector, SelectionKey.OP_READ);
						sc.finishConnect();
					}
					// 对于有数据可读
					else if (key.isReadable()) {
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						SocketChannel sc = (SocketChannel) key.channel();
						int readBytes = 0;
						try {
							int ret = 0;
							try {
								while ((ret = sc.read(buffer)) > 0) {
									readBytes += ret;
								}
							} finally {
								buffer.flip();
							}
							if (readBytes > 0) {
								System.out.println(Charset.forName("UTF-8")
										.decode(buffer).toString());
								buffer = null;
							}
						} finally {
							if (buffer != null) {
								buffer.clear();
							}
						}
					}
				}
				selector.selectedKeys().clear();
			}
		}
	}

}
