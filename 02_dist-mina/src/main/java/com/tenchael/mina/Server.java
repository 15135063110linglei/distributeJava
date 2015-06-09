package com.tenchael.mina;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.mina.common.IoAcceptor;
import org.apache.mina.common.IoHandler;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.SocketAcceptor;

/**
 * 描述：基于Mina实现的服务器端
 * 
 * @author Tenchael
 *
 */
public class Server {

	public static final int port = 9527;

	public static void main(String[] args) throws Exception {

		// 设置一个线程池大小为CPU核数+1的IoAcceptor对象
		final IoAcceptor acceptor = new SocketAcceptor(Runtime.getRuntime()
				.availableProcessors() + 1, Executors.newCachedThreadPool());

		// 增加一个将发送对象序列化以及将接受字节流反序列化的filterChain
		acceptor.getFilterChain().addLast("stringserialize",
				new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));

		// IoHandler的实现，以便当mina建立连接、接收到消息后通知应用
		IoHandler handler = new IoHandlerAdapter() {

			public void messageReceived(IoSession session, Object message)
					throws Exception {
				if ("quit".equalsIgnoreCase(message.toString())) {
					acceptor.unbindAll();
					System.out.println("Server has been shutdown!");
					System.exit(0);
				}
				System.out.println("Message from client: " + message);

				// 发送消息响应客户端
				session.write("Server response：" + message);
			}

		};
		acceptor.bind(new InetSocketAddress(port), handler);
		System.out.println("Server listen on port: " + port);
	}

}
