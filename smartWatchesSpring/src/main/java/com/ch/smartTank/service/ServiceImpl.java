package com.ch.smartTank.service;

import com.ch.smartTank.Mapper.MyMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceImpl implements Service{

    @Autowired
    MyMapper mapper;

    @Override
    public ResultSet login(String username, String password) {

        mapper.login()
        try {
            ResultSet rs = sql.executeQuery(logSql);
            if (rs.next()) {
                return rs;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql.closeDB();
        return null;
    }

    public Boolean register(String username, String password,String phone,String email ) {
    
        // ��ȡSql��ѯ���?
        String regSql = "insert into user(muser,mpsd,memail,mphone) values('"+ username+ "','"+ password+"','"+ phone + "','" + email + "') ";
// ��ȡDB����
        DBManager sql = DBManager.createInstance();
        sql.connectDB();

        int ret = sql.executeUpdate(regSql);
        if (ret != 0) {
            sql.closeDB();
            return true;
        }
        sql.closeDB();
        
        return false;
    }
    
    @Override
    public Boolean changemessage(String changemessagename, String changemessageid,String changemessage) {
        
    
    	
        String chgSql = "update user set "+ changemessagename + " =' "+ changemessage + "' WHERE mid = ' "+changemessageid  + "'";
        DBManager sql = DBManager.createInstance();
        sql.connectDB();

        int ret = sql.executeUpdate(chgSql);
        if (ret != 0) {
            sql.closeDB();
            return true;
        }
        sql.closeDB();
        
        return false;
    }
    
@Override
public Boolean changepsd(String changepsdid, String newpassword) {
        
    
    	
        String chgSql = "update user set mpsd ='"+ newpassword + "' WHERE mid = ' "+changepsdid  + "'";
        DBManager sql = DBManager.createInstance();
        sql.connectDB();

        int ret = sql.executeUpdate(chgSql);
        if (ret != 0) {
            sql.closeDB();
            return true;
        }
        sql.closeDB();
        
        return false;
    }
@Override
public ResultSet alldata() {

    // ��ȡSql��ѯ���?
    String selSql = "select * from user ";

    // ��ȡDB����
    DBManager sql = DBManager.createInstance();
    sql.connectDB();

    // ����DB����
    try {
        ResultSet rs = sql.executeQuery(selSql);
        if (rs.next()) {
            return rs;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    sql.closeDB();
    return null;
}
@Override
public Boolean delete(String id) {
    
    
	
    String chgSql = "delete from user where mid ='"+id+"'";
    DBManager sql = DBManager.createInstance();
    sql.connectDB();

    int ret = sql.executeUpdate(chgSql);
    if (ret != 0) {
        sql.closeDB();
        return true;
    }
    sql.closeDB();
    
    return false;
}

@Override
public Boolean adddata(String username, String password,String phone,String email ) {
	LocalTime now = LocalTime.now();
    // ��ȡSql��ѯ���?
    String addSql = "insert into data(Time,mpsd,memail,mphone) values('"+ now.toString()+ "','"+ password+"','"+ phone + "','" + email + "') ";
//��ȡDB����
    DBManager sql = DBManager.createInstance();
    sql.connectDB();

    int ret = sql.executeUpdate(addSql);
    if (ret != 0) {
        sql.closeDB();
        return true;
    }
    sql.closeDB();
    
    return false;
}
@Override
public Boolean insert(String payload) {
	LocalTime now = LocalTime.now();
	JSONObject jsonObj = JSON.parseObject(payload);
	String chgSql;
		chgSql = "INSERT INTO data (Time,Temp,Turbidity,F_status,S_status,A_status"
	    		+ ") VALUES ('"+now.toString()+"','"+jsonObj.get("temp")+"','"+jsonObj.get("turbidity")+"','"+jsonObj.get("f_status")+"','"+jsonObj.get("s_status")+"','"+jsonObj.get("a_status")+"')";
    DBManager sql = DBManager.createInstance();
    sql.connectDB();

    int ret = sql.executeUpdate(chgSql);
    if (ret != 0) {
        sql.closeDB();
        return true;
    }
    sql.closeDB();
    
    return false;
}
@Override
public ResultSet alldata1() {

    // ��ȡSql��ѯ���?
    String selSql = "select * from data ";

    // ��ȡDB����
    DBManager sql = DBManager.createInstance();
    sql.connectDB();

    // ����DB����
    try {
        ResultSet rs = sql.executeQuery(selSql);
        if (rs.next()) {
            return rs;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    sql.closeDB();
    return null;
}
}