package serviceS;

import java.util.HashMap;
import java.util.Vector;


//����һ��Manager,�����̵߳Ĺ���
//����������
public class socketManager {
	//������
	private socketManager(){}
	private static final socketManager SOCKET_MANAGER = new socketManager();
	public static socketManager getSocketManager(){
		return SOCKET_MANAGER;
	}
	
	Vector<transferRecordSocket> vector = new Vector<transferRecordSocket>();
	
	public void add(transferRecordSocket trSocket){
		vector.add(trSocket);
	}
	
	public void publish(transferRecordSocket isSocket,String message){
		//interactSocket���ú���������Ϣ
		for (int i = 0; i < vector.size(); i++) {
			transferRecordSocket iS = vector.get(i);
//			//�����Լ�socket ��������Ϣ
//			if(!isSocket.equals(iS)){
//				iS.out(message);
//			}
			
			System.out.println(message+"in socketManger");
			iS.out(message);
		}
	}
	
	public void addParameter(HashMap<String, String> map){
		for(int i=0; i<vector.size(); i++){
			transferRecordSocket tRecordSocket = vector.get(i);
			tRecordSocket.setParameter(map);
			System.out.println("sended a map to socket");
		}
	}
}
