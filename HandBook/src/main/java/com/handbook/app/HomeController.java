package com.handbook.app;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.imageio.IIOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.handbook.service.S_Service;
import com.handbook.util.UploadFileUtils;
import com.handbook.vo.S_BOARD;
import com.handbook.vo.S_FRIENDLIST;
import com.handbook.vo.S_ImgPathName;
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
		System.out.println(list.size());

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
	public String userUpdate(S_USERINFO userinfo, HttpSession session, MultipartFile file, HttpServletRequest req,
			String realName) throws IIOException, Exception {
		String inputPass = userinfo.getUser_pwd();
		String pwd = pwdEncoder.encode(inputPass);
		userinfo.setUser_pwd(pwd);
		System.out.println("file    ==========" + file);
		if (file.getOriginalFilename() != null && file.getOriginalFilename() != "") {
			new File(uploadPath + req.getParameter("user_img")).delete();

			String imgUploadPath = uploadPath + File.separator;
			String imgPath = imgUploadPath;
			String fileName = "";
			// fileName = UploadFileUtils.fileUpload(imgPath, file.getBytes(),realName);
			userinfo.setUser_img(realName);
		} else {
			userinfo.setUser_img(req.getParameter("user_img"));
		}
		s_service.userUpdate(userinfo);
		session.invalidate();
		return "redirect:/";
	}

	
	//게시글 insert
	@PostMapping("BoardInsert")
	public String BoardInsert(S_BOARD board,S_ImgPathName img, MultipartFile file, HttpServletRequest req, Model model)
			throws IOException, Exception {
		if (file.getOriginalFilename() != null && file.getOriginalFilename() != "") {
			//img 컬럼에 데이터값 저장을 위한 setter
			board.setB_img(img.getRealAddress()+img.getRealName());
			File subName = new File(img.getImgPath() + img.getSubAddress() + img.getSubName());
			UploadFileUtils.fileDelete(subName);
		}else {//파일이 선택되지 않았다면 
			board.setB_num("");
		}
		s_service.BoardInsert(board);
		return "redirect:S_index.c";
	}

	@PostMapping("subName")
	@ResponseBody
	public S_ImgPathName subName(MultipartFile file) throws Exception {
		String imgUploadPath = uploadPath + File.separator + "imgUpload";
		//저장하는 날자로 폴더 생성 
		String ymdPath = UploadFileUtils.calcPath(imgUploadPath);
		
		//저장하는 파일의 이름을 생성
		S_ImgPathName img = UploadFileUtils.fileUpload(file.getOriginalFilename(), ymdPath);

		//파일이 저장되는 로컬주소 
		String imgUploadPath1 = uploadPath + File.separator ;
		img.setImgPath(imgUploadPath1);
		//파일이 저장되는 로컬주소 및 폴더주소
		img = UploadFileUtils.subFileUpload(img.getImgPath(),  file.getBytes(),img);
		return img;
	}
	
	@PostMapping("deleteImg")
	@ResponseBody
	public S_ImgPathName deleteImg(String DeleteNum ,S_ImgPathName img, MultipartFile File) {
		File subName = new File(img.getImgPath()+img.getSubAddress()+img.getSubName());
		File realName = new File(img.getImgPath() + img.getRealAddress() + img.getRealName());
		UploadFileUtils.fileDelete(subName);
		UploadFileUtils.fileDelete(realName);
		return img;
	}

	@PostMapping("detailViewDelete")
	public String detailViewDelete(String b_num) {
		s_service.detailViewDelete(b_num);
		return "S_index.c";
	}

	@PostMapping("friendList")
	public String friendList(Model model, HttpSession session, S_USERINFO userinfo, S_FRIENDLIST friendlist) {
		List<S_USERINFO> arr = s_service.friendList(userinfo);
		System.out.println(arr.size());
		model.addAttribute("arr", arr);
		return "callBack/FriendList";
	}

	@GetMapping("S_userPage")
	public String UserPage() {
		return "S_userPage.h";
	}

	@PostMapping("S_userPage")
	public String UserPage(S_USERINFO userinfo,S_FRIENDLIST fList,Model model) {
		S_FRIENDLIST friendList = s_service.GetState(fList);
		S_USERINFO userInfo = s_service.getUserPage(fList.getF_id());
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("friendList", friendList);
		return "S_userPage.h";
	}

	// 친구신청
	/*
	 * m_state : 자신의 상태 f_state : 친구의 상태 flag 값 1: 친구신청 발신상태 flag 값 2: 친구신청 수신상태
	 * flag 값 3: 이미 친구상태 친구 신청을 누를경우 DB S_friend 안에 값을 넣는다.
	 */
	@PostMapping("ApplyFriend")
	public void ApplyFriend(S_FRIENDLIST friendList) {
		s_service.ApplyFriend(friendList);
	}

	/* 유저 검색 */
	@PostMapping("UserSearch")
	public String UserSearch(String userName, Model model) {
		List<S_USERINFO> userInfo = s_service.UserSearch(userName);
		if (userInfo.size() == 0 || userName == "") {
			return "callBack/NotUserList";
		} else {
			model.addAttribute("userInfo", userInfo);
			return "callBack/UserList";
		}
	}

}
