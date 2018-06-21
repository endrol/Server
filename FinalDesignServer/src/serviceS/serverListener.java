package serviceS;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class serverListener extends Thread{
	@SuppressWarnings("resource")
	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(12345);
			while(true){
				Socket socket = serverSocket.accept();
				System.out.println("链接上一个socket");
				//建立连接
				JOptionPane.showMessageDialog(null, "有客户端链接到了12345端口");
				//将socket传递给新的线程
				transferRecordSocket tRecordSocket = new transferRecordSocket(socket);
				tRecordSocket.start();
				socketManager.getSocketManager().add(tRecordSocket);
			}
		} catch (HeadlessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
