package com.superboard.onbrd.boardgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.global.util.MailUtil;

@RestController
public class TestController {
	
	@Autowired
	private MailUtil mail;
	
	@PostMapping("/mailtest")
	public void mailTest(@RequestParam(name="to") String to) throws Exception {
		mail.sendSimpleMessage(to);
	}

}
