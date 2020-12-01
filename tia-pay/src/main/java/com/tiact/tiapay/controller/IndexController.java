package com.tiact.tiapay.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Tia_ct
 */
@Controller
@RequestMapping("/")
public class IndexController {

	@RequestMapping("/")
	public String index() {
		return "pay";
	}


}
