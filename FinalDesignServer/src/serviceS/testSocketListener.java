package serviceS;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class testSocketListener extends Thread{
	@SuppressWarnings("resource")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			DatagramSocket serverSocket = new DatagramSocket(13254);
			System.out.println("I built the daraGramsocket");
			byte[] data = new byte[50];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			while(true){
				serverSocket.receive(packet);
		        System.out.println("client:"+new String(data));  
		        serverSocket.send(packet);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
