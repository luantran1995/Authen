package com.ltran.study.controller;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ltran.study.model.JwtRequest;

@RestController
public class AuthenController {
    @ResponseBody
    @RequestMapping(value ="/token" , method = RequestMethod.GET)
	public String firstPage( @RequestHeader(value ="BBBBBBBBBBBB") String token , JwtRequest jsst ) {
		return "Hello World";
	}
}
