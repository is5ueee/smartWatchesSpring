package com.ch.smartTank.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class webController {

	@RequestMapping("/Login")
	public String login() {
		return "Login";
	}
	
	@RequestMapping("/Register")
	public String register() {
		return "Register";
	}
	@RequestMapping("/WebSocket")
	public String websocket() {
		return "WebSocket";
	}
	@RequestMapping("/userinfo")
	public String userinfo() {
		return "userinfo";
	}
}
