package com.dj.mall.admin.web.index;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Administrator
 * @date 2019��11��26��
 */
@Controller
@RequestMapping("/index/")
public class IndexPageController {
	
	@RequestMapping("toIndex")
	public String toIndex() {
		return "/index/index";
	}
	
	@RequestMapping("toTop")
	public String toTop() {
		return "/index/top";
	}
	
	@RequestMapping("toLeft")
	public String toLeft() {
		return "/index/left";
	}
	
	@RequestMapping("toRight")
	public String toRight() {
		return "/index/right";
	}
	

}
