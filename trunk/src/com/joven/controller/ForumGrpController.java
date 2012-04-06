package com.joven.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joven.model.ForumGroup;
import com.joven.service.ForumGroupService;

@Controller
@RequestMapping("/bbs/forumgroup.do")
public class ForumGrpController{
	
	@Resource(name="forumGroupServiceImp")
	private ForumGroupService forumGroupService;

	//取得所有版块组和版块
	@RequestMapping(params = "method=listAll")
	public String listAll(ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<ForumGroup> forumGrps =forumGroupService.getForumGroups(true);
		model.addAttribute("forumGrps",forumGrps);
		return "bbs/manager/forumManager";
	}
	
	//取得所有版块组和版块及版主
	@RequestMapping(params = "method=listForumGroups")
	public String listForumGroupData(ModelMap model,
			HttpServletRequest request, HttpServletResponse response){
		int fgid=-1;
		if (request.getParameter("fgid")!=null){
			fgid=Integer.parseInt(request.getParameter("fgid"));
		}
		List<ForumGroup> forumGrps=forumGroupService.getFGDatas(fgid);
		model.addAttribute("forumGrps",forumGrps);
		model.put("fgid", fgid);
		return "bbs/forumlist";
	}
	
	//新增版块组
	@RequestMapping(params = "method=save")
	public String add(ForumGroup forumGroup,ModelMap model,HttpServletRequest request, HttpServletResponse response) throws Exception{
		int rt=forumGroupService.addGroup(forumGroup); 
		if (rt==0){
			return "redirect:forumgroup.do?method=listAll";
		}
		else{
			List<String> errors=new ArrayList<String>();
			errors.add("版块组名已经存在.");
			model.put("errors",errors);
			return "bbs/manager/errors";
		}
	}
	
	//修改版块组
	@RequestMapping(params = "method=update")
	public String update(ForumGroup forumGroup,ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		int rt=forumGroupService.updateGroup(forumGroup);
		if (rt==0){
			return "redirect:forumgroup.do?method=listAll";
		}
		else{
			List<String> errors=new ArrayList<String>();
			errors.add("版块组名已经存在.");
			model.put("errors",errors);
			return "bbs/manager/errors";
		}
	}
	
	//删除版块组
	@RequestMapping(params = "method=delete")
	public String del(ForumGroup forumGroup,ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		int rt=forumGroupService.delGroup(forumGroup.getId());
		
		if (rt==0){
			return "redirect:forumgroup.do?method=listAll";
		}
		else{
			List<String> errors=new ArrayList<String>();
			errors.add("版块组下还有版块,不能删除.");
			model.put("errors",errors);
			return "bbs/manager/errors";
		}
	}
	
}
