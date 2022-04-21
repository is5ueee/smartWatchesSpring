package com.ch.smartTank.WebSocket;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ch.smartTank.mqtt.MqttSign;
import com.ch.smartTank.service.ServiceImpl;

@ServerEndpoint("/websocket/{userId}")
@Component
public class WebSocketServer {
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
    private String userId = "";

    /**
     * 杩炴帴寤虹珛鎴愬姛璋冪敤鐨勬柟娉�
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            webSocketMap.put(userId, this);
        } else {
            webSocketMap.put(userId, this);
            addOnlineCount();
        }
        System.out.print("鐢ㄦ埛杩炴帴:" + userId + ",褰撳墠鍦ㄧ嚎浜烘暟涓�:" + getOnlineCount());
        
        //sign.calculate(productKey, deviceName, deviceSecret);

        //System.out.println("username: " + sign.getUsername());
       //System.out.println("password: " + sign.getPassword());
        //System.out.println("clientid: " + sign.getClientid());

        //浣跨敤Paho杩炴帴闃块噷浜戠墿鑱旂綉骞冲彴
        //String port = "443";
        //String broker = "ssl://" + productKey + ".iot-as-mqtt.cn-shanghai.aliyuncs.com" + ":" + port;
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
           try {
            sendMessage("杩炴帴鎴愬姛锛�");
        } catch (IOException e) {
        	System.out.print("鐢ㄦ埛:" + userId + ",缃戠粶寮傚父!!!!!!");
        }
    }

    /**
     * 杩炴帴鍏抽棴璋冪敤鐨勬柟娉�
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            subOnlineCount();
        }
        try {
   		 sampleClient.disconnect();
   	} catch (MqttException e) {
   		// TODO Auto-generated catch block
   		e.printStackTrace();}
        System.out.print("鐢ㄦ埛閫�鍑�:" + userId + ",褰撳墠鍦ㄧ嚎浜烘暟涓�:" + getOnlineCount());
    }

    /**
     * 鏀跺埌瀹㈡埛绔秷鎭悗璋冪敤鐨勬柟娉�
     *
     * @param message 瀹㈡埛绔彂閫佽繃鏉ョ殑娑堟伅
     */
    @OnMessage
    public void onMessage(String message, Session session) {
    	System.out.print("鐢ㄦ埛娑堟伅:" + userId + ",鎶ユ枃:" + message);
    	JSONObject jsonObject = JSON.parseObject(message);
        if (!StringUtils.isEmpty(message)) {
            try {
            	
            	if(jsonObject.getString("controll").equals("subscribe")) {
            		//String topicReply = "/sys/" + productKey + "/" + deviceName + "/thing/event/property/post";
            		String topicReply = "data";
                    try {
            			sampleClient.subscribe(topicReply, new Mqtt3PostPropertyMessageListener());
            		} catch (MqttException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}
                    System.out.println("subscribe: " + topicReply);
            	}else {
            		//Paho Mqtt 娑堟伅鍙戝竷
            	    //String topic = "/sys/" + productKey + "/" + deviceName + "/thing/event/property/post";
            		String topic ="message";
            	    String content = "{\"message\":"+jsonObject.getString("controll") +
            	    		"}";
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
                //jsonObject.put("fromUserId", this.userId);
                //String toUserId = jsonObject.getString("toUserId");
               // if (!StringUtils.isEmpty(toUserId) && webSocketMap.containsKey(toUserId)) {
                    //webSocketMap.get(toUserId).sendMessage(jsonObject.toJSONString());
                //} else {
                	//System.out.print("璇锋眰鐨� userId:" + toUserId + "涓嶅湪璇ユ湇鍔″櫒涓�");
                //}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 鍙戠敓閿欒鏃惰皟鐢�
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
    	System.out.print("鐢ㄦ埛閿欒:" + this.userId + ",鍘熷洜:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 瀹炵幇鏈嶅姟鍣ㄤ富鍔ㄦ帹閫�
     */
    public void sendMessage(String message) throws IOException {
    		this.session.getBasicRemote().sendText(message);
    }
    
    class Mqtt3PostPropertyMessageListener implements IMqttMessageListener {
        @Override
        public void messageArrived(String var1, MqttMessage var2) throws Exception {
            System.out.println("reply topic  : " + var1);
            System.out.println("reply payload: " + var2.toString());
            sendMessage(var2.toString());
            serv.insert(var2.toString());
        }
    }


    public static synchronized AtomicInteger getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount.getAndIncrement();
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount.getAndDecrement();
    }
}