package com.ch.smartTank.servlet;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.ch.smartTank.WebSocket.WebSocketServer;
import com.ch.smartTank.mqtt.MqttSign;
import com.ch.smartTank.service.ServiceImpl;

@WebServlet(name = "mathLet",urlPatterns="/mathLet",description="servlet")
public class mathLet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String productKey = "a1W4dzPF0g6";
    String deviceName = "wifi";
    String deviceSecret = "b348beb1493051ccb812dcd10c6b6f80";
    MqttSign sign = new MqttSign();
    MqttClient sampleClient = null;
    ServiceImpl serv=new ServiceImpl();

	   
    /**
     * 褰撳墠鍦ㄧ嚎杩炴帴鏁�
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    /**
     * 鐢ㄦ潵瀛樻斁姣忎釜瀹㈡埛绔搴旂殑 WebSocketServer 瀵硅薄
     */
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    /**
     * 涓庢煇涓鎴风鐨勮繛鎺ヤ細璇濓紝闇�瑕侀�氳繃瀹冩潵缁欏鎴风鍙戦�佹暟鎹�
     */
    private Session session;

    /**
     * 鎺ユ敹 userId
     */
    private String userId = "33";

    /**
     * 杩炴帴寤虹珛鎴愬姛璋冪敤鐨勬柟娉�
     */

	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
	        String localbroker = "tcp://127.0.0.1:61613";
	        MemoryPersistence persistence = new MemoryPersistence();
	        try{
	            //Paho Mqtt 瀹㈡埛绔�
	            //sampleClient = new MqttClient(broker, sign.getClientid(), persistence);
	        	sampleClient = new MqttClient(localbroker, "springserver"+userId, persistence);

	            //Paho Mqtt 杩炴帴鍙傛暟
	            MqttConnectOptions connOpts = new MqttConnectOptions();
	            connOpts.setCleanSession(true);
	            connOpts.setKeepAliveInterval(180);
	            //connOpts.setUserName(sign.getUsername());
	            //connOpts.setPassword(sign.getPassword().toCharArray());
	            connOpts.setUserName("admin");
	            connOpts.setPassword("password".toCharArray());
	            sampleClient.connect(connOpts);
	            System.out.println("broker: " + localbroker + " Connected");
	        }catch (MqttException e) {
	            System.out.println("reason " + e.getReasonCode());
	            System.out.println("msg " + e.getMessage());
	            System.out.println("loc " + e.getLocalizedMessage());
	            System.out.println("cause " + e.getCause());
	            e.printStackTrace();
	            System.out.println("excep " + e); 
	        } 
	        String topic ="data";
	        Double temp = Math.random()*20+20;
	        Double turbidity = Math.random()*50+2900;
	        Double f_status = Math.random()*255;
	        Double s_status = Math.random()*255;
	        Double a_status = Math.random()*255;

	        String content = "{\"temp\":" + temp.toString() +",\"turbidity\":" +turbidity.toString()+",\"f_status\":" +f_status.toString()
	        		+",\"s_status\":" +s_status+",\"a_status\":" +a_status+"}";
	        MqttMessage message1 = new MqttMessage(content.getBytes());
	            	    message1.setQos(0);
	            	    try {
	            			sampleClient.publish(topic, message1);
	            		} catch (MqttPersistenceException e) {
	            			// TODO Auto-generated catch block
	            			e.printStackTrace();
	            		} catch (MqttException e) {
	            			// TODO Auto-generated catch block
	            			e.printStackTrace();
	            		}
	            	    System.out.println("publish: " + content);
	         }
	                
		
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		 String localbroker = "tcp://127.0.0.1:61613";
	        MemoryPersistence persistence = new MemoryPersistence();
	        try{
	            //Paho Mqtt 瀹㈡埛绔�
	            //sampleClient = new MqttClient(broker, sign.getClientid(), persistence);
	        	sampleClient = new MqttClient(localbroker, "springserver"+userId, persistence);

	            //Paho Mqtt 杩炴帴鍙傛暟
	            MqttConnectOptions connOpts = new MqttConnectOptions();
	            connOpts.setCleanSession(true);
	            connOpts.setKeepAliveInterval(180);
	            //connOpts.setUserName(sign.getUsername());
	            //connOpts.setPassword(sign.getPassword().toCharArray());
	            connOpts.setUserName("admin");
	            connOpts.setPassword("password".toCharArray());
	            sampleClient.connect(connOpts);
	            System.out.println("broker: " + localbroker + " Connected");
	        }catch (MqttException e) {
	            System.out.println("reason " + e.getReasonCode());
	            System.out.println("msg " + e.getMessage());
	            System.out.println("loc " + e.getLocalizedMessage());
	            System.out.println("cause " + e.getCause());
	            e.printStackTrace();
	            System.out.println("excep " + e); 
	        }
	        String topic ="data";
	        Double temp = Math.random()*20+20;
	        Double turbidity = Math.random()*50+2900;
	        Double f_status = Math.random()*255;
	        Double s_status = Math.random()*255;
	        Double a_status = Math.random()*255;
	        String content = "{\"temp\":" + temp.toString() +",\"turbidity\":" +turbidity.toString()+",\"f_status\":" +f_status.toString()
    		+",\"s_status\":" +s_status+",\"a_status\":" +a_status+"}";
	        MqttMessage message1 = new MqttMessage(content.getBytes());
	            	    message1.setQos(0);
	            	    try {
	            			sampleClient.publish(topic, message1);
	            		} catch (MqttPersistenceException e) {
	            			// TODO Auto-generated catch block
	            			e.printStackTrace();
	            		} catch (MqttException e) {
	            			// TODO Auto-generated catch block
	            			e.printStackTrace();
	            		}
	            	    System.out.println("publish: " + content);
	         }
		
}
