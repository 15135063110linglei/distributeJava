package com.tenchael.mina;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.mina.common.ConnectFuture;
import org.apache.mina.common.IoHandler;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.SocketConnector;

/**
 * 描述：基于Mina实现的tcp client
 * 
 * @author Tenchael
 *
 */
public class Client {

	public static final String host = "127.0.0.1";
	public static final int port = 9527;

	public static void main(String[] args) throws Exception {

		// 设置一个线程池大小为CPU核数+1的SocketConnector对象
		SocketConnector ioConnector = new SocketConnector(Runtime.getRuntime()
				.availableProcessors() + 1, Executors.newCachedThreadPool());

		// 设置TCP的NoDelay为true
		ioConnector.getDefaultConfig().getSessionConfig().setTcpNoDelay(true);

		// 增加一个将发送对象序列化以及将接受字节流反序列化的filterChain
		ioConnector.getFilterChain().addLast("stringserialize",
				new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		InetSocketAddress socketAddress = new InetSocketAddress(host, port);

		// IoHandler的实现，以便当mina建立连接、接收到消息后通知应用
		IoHandler handler = new IoHandlerAdapter() {

			public void messageReceived(IoSession session, Object message)
					throws Exception {
				System.out.println(message);
			}

		};

		// 异步建立连接
		ConnectFuture connectFuture = ioConnector.connect(socketAddress,
				handler);

		// 阻塞等待连接建立完毕
		connectFuture.join();

		IoSession session = connectFuture.getSession();
		BufferedReader systemIn = new BufferedReader(new InputStreamReader(
				System.in));
		while (true) {
			String command = systemIn.readLine();
			if (command == null || "quit".equalsIgnoreCase(command.trim())) {
				System.out.println("Client quit!");
				session.write("quit");
				session.close();
				System.exit(0);
			}

			// 发送消息
			session.write(command);
		}
	}

}
