package serviceS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.HashMap;

public class transferRecordSocket extends Thread{
	Socket socket;
	HashMap<String, String> hashMap = null;
	public transferRecordSocket(Socket s){
		this.socket = s;
	}

	/***********数据流写出************/
	public void out(String out) {
//		String tocheckString = out.substring(out.indexOf("?")+1);
//		String toSendString = out.substring(0,out.indexOf("?"));
//		
		if(nowCity.equals(receivedCity)){
			System.out.println("准备send "+out+"去客户端");
			sendMessage(out);
		}else {
			System.out.println("地点不对，这个socket不发送");
			System.out.println("this.now city is "+nowCity + 
					"and requestcity is"+this.receivedCity);
		}
//		try {
//			System.out.println(out+"in OUT function transferRecordSocket");
////			socket.getOutputStream().write(out.getBytes("UTF-8"));
////			socket.getOutputStream().flush();
////			socket.getOutputStream().close();
//			PrintWriter pWriter = new PrintWriter(socket.getOutputStream());
//			pWriter.println(out);
//			pWriter.flush();
////			pWriter = new PrintWriter(socket.getOutputStream());
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	private void sendMessage(final String msg) {
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				DataOutputStream outputStream = null;
				
				try {
					outputStream = new DataOutputStream(socket.getOutputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					outputStream.writeUTF(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}
	/*********设置参数***************/
	public void setParameter(HashMap<String, String> map){
		this.hashMap = map;
		this.receivedCity = map.get("city");
		System.out.println("received a request which city is "+receivedCity);
		checkMap = true;
	}
	boolean checkMap = false;
	
	/*************设置位置读取*****************/
	String nowLocation;
	private String nowCity = "南京市",receivedCity = "南京市";
	public void setLocation(String locaString) {
		this.nowLocation = locaString;
//		this.nowCity = locaString.substring(locaString.lastIndexOf(":")+1);
		this.nowCity = locaString;
		System.out.println("update: this phone's location is in "+locaString);
	}
	boolean chooseSocket = false;
	private void setCheckCorrect() {
		chooseSocket = true;
	}
	
	BufferedWriter writer = null;

	@Override
	public void run() {
		DataInputStream reader;
		try {
			reader = new DataInputStream(socket.getInputStream());
			
			while(true){
				
				if(checkMap && chooseSocket){
					
					//			System.out.println("koko");
					
					String line = hashMap.get("setTime") + "&"
							+ hashMap.get("fileNameReal");
					System.out.println("about to start a "
							+ "socket send message to phones ojbk" + line);
					if(line != null){
						socketManager.getSocketManager().publish(this, line);
					}
					checkMap = false;
					chooseSocket = false;
				}
				
				String msg = reader.readUTF();
				System.out.println("收到客户端上传位置信息: "+msg);
				if(msg.equals("chosen")){
					setCheckCorrect();
				}else {
					setLocation(msg);
				}
				
				
//				reader = new BufferedReader(
//						new InputStreamReader(socket.getInputStream()));
//				String lineString = null;
//				if((lineString = reader.readLine()) != null){
//					setLocation(lineString);
//				}
//				sleep(2000);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
