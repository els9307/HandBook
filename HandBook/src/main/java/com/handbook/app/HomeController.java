package com.handbook.app;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.IIOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.handbook.service.S_Service;
import com.handbook.util.UploadFileUtils;
import com.handbook.vo.S_BOARD;
import com.handbook.vo.S_FRIENDLIST;
import com.handbook.vo.S_USERINFO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private S_Service s_service;

	@Autowired
	private BCryptPasswordEncoder pwdEncoder;

	@Resource(name = "uploadPath")
	private String uploadPath;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);
		return "S_login.s";
	}

	@PostMapping("ListResult")
	public String ListResult(S_BOARD board1, Model model) {
		List<S_BOARD> list = s_service.ListResult(board1);
		model.addAttribute("list", list);
		return "callBack/ListResult";
	}

	@PostMapping("index")
	public String index(S_USERINFO userinfo, Model model) {
		
		S_USERINFO info = s_service.userlogin(userinfo);
		model.addAttribute("info", info);
		return "S_index.c";
	}
	// �쑀���젙蹂�
	@PostMapping("S_myPage")
	public String S_myPage(S_USERINFO userinfo, Model model) {
		S_USERINFO info = s_service.userlogin(userinfo);
		model.addAttribute("info", info);
		return "S_myPage.c";
	}
	// 濡쒓렇�씤
	@PostMapping("user_login")
	public String user_login(S_USERINFO userinfo, HttpSession session, Model model) {
		S_USERINFO login = s_service.userlogin(userinfo);
		boolean pwdMatch = pwdEncoder.matches(userinfo.getUser_pwd(), login.getUser_pwd());
		if (login != null && pwdMatch == true) {
			session.setAttribute("session_id", userinfo.getUser_id());
//			S_USERINFO info = s_service.userlogin(userinfo);
//			model.addAttribute("info",info);
			return "S_index.c";
		}
		return "S_login.s";
	}
	@GetMapping("S_index")
	public String index() {
		return "S_index.c";
	}

	@GetMapping("S_join")
	public String join() {
		return "S_join.s";
	}

	@GetMapping("S_login")
	public String login() {
		return "S_login";
	}

	@GetMapping("logOut")
	public String logOut(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@PostMapping("LoginMenu")
	public String loginMenu(S_USERINFO userinfo, Model model) {
		return "S_index.c";
	}



	// �븘�씠�뵒 以묐났�솗�씤
	@PostMapping("idChk")
	@ResponseBody
	public int idChk(S_USERINFO userinfo) {
		int result = s_service.idChk(userinfo);
		return result;
	}

	// �쉶�썝媛��엯
	@PostMapping("User_Join")
	public String user_join(S_USERINFO userinfo) {
		int result = s_service.idChk(userinfo);
		if (result == 1) {
			return "S_login.s";
		} else if (result == 0) {
			String inputPass = userinfo.getUser_pwd();
			String pwd = pwdEncoder.encode(inputPass);
			userinfo.setUser_pwd(pwd);
			s_service.userJoin(userinfo);
		}
		return "S_login.s";
	}


	@PostMapping("user_Update")
	public String userUpdate(S_USERINFO userinfo, HttpSession session, MultipartFile file, HttpServletRequest req,String realName)
			throws IIOException, Exception {
		String inputPass = userinfo.getUser_pwd();
		String pwd = pwdEncoder.encode(inputPass);
		userinfo.setUser_pwd(pwd);
		System.out.println("file    ==========" + file);
		if (file.getOriginalFilename() != null && file.getOriginalFilename() != "") {
			new File(uploadPath + req.getParameter("user_img")).delete();

			String imgUploadPath = uploadPath + File.separator ;
			String imgPath = imgUploadPath;
			String fileName = "";
			fileName = UploadFileUtils.fileUpload(imgPath,  file.getBytes(),realName);
			userinfo.setUser_img(realName);
		} else {
			userinfo.setUser_img(req.getParameter("user_img"));
		}
		s_service.userUpdate(userinfo);
		session.invalidate();
		return "redirect:/";
	}

	@PostMapping("BoardInsert")
	public String BoardInsert(S_BOARD board, String check, MultipartFile file, HttpServletRequest req, Model model,String realName)
			throws IOException, Exception {
			logger.info("POST  -  湲��벐湲�");
		if (file.getOriginalFilename() != null && file.getOriginalFilename() != "") {
			
			//로컬주소
	
			String fileName = "";
			fileName = UploadFileUtils.fileUpload(realName);
			board.setB_img(realName);
		}else {//�뙆�씪泥⑤�瑜� �븯吏� �븡�쑝硫�
			board.setB_num("");
		}
		s_service.BoardInsert(board);
		return "redirect:S_index.c";
	}

	@PostMapping("RealName")
	@ResponseBody
	public String RealName(MultipartFile file) throws Exception {
		String imgUploadPath = uploadPath + File.separator + "imgUpload";
		String ymdPath = UploadFileUtils.calcPath(imgUploadPath);
		//경로만 들고옴
		System.out.println("ymdPath"+ymdPath);
		String fileName = UploadFileUtils.fileUpload(file.getOriginalFilename(),ymdPath);
		System.out.println("파일네임"+fileName);
		
		String imgUploadPath1 = uploadPath + File.separator ;
		String imgPath = imgUploadPath1;
		//로컬주소
		fileName = UploadFileUtils.fileUpload(imgPath,  file.getBytes(),fileName);
		return fileName;
	}
	@PostMapping("detailViewDelete")
	public String detailViewDelete(String b_num) {
		s_service.detailViewDelete(b_num);
		return "S_index.c";
	}
	
	@PostMapping("friendList")
	public  String friendList(Model model,HttpSession session,S_USERINFO userinfo,S_FRIENDLIST friendlist) {
		List<S_USERINFO> arr = s_service.friendList(userinfo);
		model.addAttribute("arr",arr);
		return "callBack/FriendList";
	}
	@GetMapping("S_userPage")
	public String myPage() {
		return "S_userPage.h";
	}
	@PostMapping("S_userPage")
	public String S_userPage(String fName) {
		System.out.println("fName="+fName);
		return "S_userPage.h";
	}
}
