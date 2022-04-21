package com.ch.smartTank.service;

import java.sql.ResultSet;

/**
 * @author Liu Zhe
 * @Description
 * @date 2022/4/21 11:11
 */
public interface Service {
	ResultSet login(String username, String password) ;

	Boolean changemessage(String changemessagename, String changemessageid,String changemessage);

	Boolean changepsd(String changepsdid, String newpassword);

	ResultSet alldata();

	Boolean delete(String id);

	Boolean adddata(String username, String password,String phone,String email );

	Boolean insert(String payload);

	ResultSet alldata1();
}
