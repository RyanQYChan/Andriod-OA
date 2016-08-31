package com.ryan.oa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ryan.oa.utils.CommonUtils;

@Controller

public class CommController {

	@RequestMapping(value = "/getInsertId", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public static int getLatestInsertId(){
		return CommonUtils.getLatestInsertId();
	}
}
