package com.ch.smartTank.controller;


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ch.smartTank.service.ServiceImpl;


@RestController
public class Controller {
	
	@RequestMapping("/changemsg")
	public String changemessage(@RequestBody String map) {
		JSONObject obj=JSON.parseObject(map);
		String changemessage=(String)obj.get("changemessage");
		String changemessagename=(String)obj.get("changemessagename");
		String changemessageid=(String)obj.get("changemessageid");	
		System.out.print("//"+changemessage+"//"+changemessagename+"//"+changemessageid);
		
		ServiceImpl serv = new ServiceImpl();

		String text="";
        boolean chged = serv.changemessage(changemessagename, changemessageid,changemessage);
        if (chged) {
            System.out.print("Succss");
            text="success";
        } else {
            System.out.print("Failed");
            text="failed";
        }
		return text;
	}
	@RequestMapping("/changepsd")
	public String changepassword(@RequestBody String map) {
		JSONObject obj=JSON.parseObject(map);
		String newpassword=(String)obj.get("newpassword");
		String changepsdid=(String)obj.get("changepsdid");
		System.out.print("//"+changepsdid+"//"+newpassword+"//");
		
		ServiceImpl serv = new ServiceImpl();

		String text="";
        boolean chged = serv.changepsd(changepsdid,newpassword);
        if (chged) {
            System.out.print("Succss");
            text="success";
        } else {
            System.out.print("Failed");
            text="failed";
        }
		return text;
	}  
	
	@RequestMapping("/userinfoimp")
	public String userinofimp() {
		ServiceImpl ser=new ServiceImpl();
		ResultSet rs=ser.alldata();
		JSONArray jsonarray=new JSONArray();
	       // ???????????? 
		try {
			ResultSetMetaData metaData = rs.getMetaData();
	       // ??????ResultSet?????????????????? 
	       while (rs.next()) { 
	    	   int columnCount = metaData.getColumnCount(); 
	            // ???????????????? 
	        	JSONObject jsonObj = new JSONObject();  
	            for (int i = 1; i <= columnCount; i++) { 
	            	   
	                String columnName =metaData.getColumnLabel(i); 
	 
	                String value = rs.getString(columnName); 
	 
	                jsonObj.put(columnName, value); 
	 
	            }   
	            jsonarray.add(jsonObj);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	 
	       return jsonarray.toJSONString(); 
		
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String id) {
		ServiceImpl ser=new ServiceImpl();
		if(ser.delete(id)) {
			return "????????????" ;
		}else {

			return "????????????" ;
		}
	}
	@RequestMapping("/users")
	public ModelAndView users() {
		return new ModelAndView("users");
	}
	@RequestMapping("/data")
	public String data() {
		ServiceImpl ser=new ServiceImpl();
		ResultSet rs=ser.alldata1();
		JSONArray jsonarray=new JSONArray();
	       // ???????????? 
		try {
			ResultSetMetaData metaData = rs.getMetaData();
	       // ??????ResultSet?????????????????? 
	       while (rs.next()) { 
	    	   int columnCount = metaData.getColumnCount(); 
	            // ???????????????? 
	        	JSONObject jsonObj = new JSONObject();  
	            for (int i = 1; i <= columnCount; i++) { 
	            	   
	                String columnName =metaData.getColumnLabel(i); 
	 
	                String value = rs.getString(columnName); 
	 
	                jsonObj.put(columnName, value); 
	 
	            }   
	            jsonarray.add(jsonObj);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	 
	       return jsonarray.toJSONString(); 
		
	}
}
