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
				System.out.println("������һ��socket");
				//��������
				JOptionPane.showMessageDialog(null, "�пͻ������ӵ���12345�˿�");
				//��socket���ݸ��µ��߳�
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
