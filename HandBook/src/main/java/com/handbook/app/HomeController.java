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
import org.springframework.security.web.util.matcher.IpAddressMatcher;
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
import com.zaxxer.hikari.util.SuspendResumeLock;

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



	// 濡쒓렇�씤
	@PostMapping("user_login")
	public String user_login(S_USERINFO userinfo, HttpSession session, Model model) {
		S_USERINFO login = s_service.userlogin(userinfo);
		S_USERINFO info = s_service.userlogin(userinfo);
		model.addAttribute("info",info);
		boolean pwdMatch = pwdEncoder.matches(userinfo.getUser_pwd(), login.getUser_pwd());
		if (login != null && pwdMatch == true) {
			session.setAttribute("session_id", userinfo.getUser_id());

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
	public String user_join(S_USERINFO userinfo,S_ImgPathName img) {
		int result = s_service.idChk(userinfo);
		if (result == 1) {
			return "S_login.s";
		} else if (result == 0) {
			String inputPass = userinfo.getUser_pwd();
			String pwd = pwdEncoder.encode(inputPass);
			userinfo.setUser_pwd(pwd);
			s_service.userJoin(userinfo);
			File realUserDir = new File(uploadPath + File.separator + "imgUpload\\"+userinfo.getUser_id());
			File subUserDir = new File(uploadPath + File.separator + "imgUpload\\"+userinfo.getUser_id() + "\\profile");
			//저장하는 날자로 폴더 생성 
			UploadFileUtils.mkDir(subUserDir,realUserDir);
		}
		return "S_login.s";
	}

	// �쑀���젙蹂�
	@PostMapping("S_myPage")
	public String S_myPage(S_USERINFO userinfo, Model model) {
		S_USERINFO info = s_service.userlogin(userinfo);
		model.addAttribute("info", info);
		return "S_myPage.c";
	}
	
	@PostMapping("user_Update")
	public String userUpdate(S_USERINFO userinfo, S_ImgPathName img, HttpSession session, MultipartFile file, HttpServletRequest req
			) throws IIOException, Exception {
		System.out.println("개인정보수정"+img.getSubName());
		String inputPass = userinfo.getUser_pwd();
		String pwd = pwdEncoder.encode(inputPass);
		userinfo.setUser_pwd(pwd);
		String imgPath = img.getRealAddress() +img.getRealName();
		System.out.println(imgPath);
		System.out.println("file    ==========" + file);
		if (file.getOriginalFilename() != null && file.getOriginalFilename() != "") {
			new File(uploadPath + req.getParameter("user_img")).delete();
			File sub = new File(uploadPath + "\\imgUpload\\" + userinfo.getUser_id() + "\\profile"+ "\\sub");
			File real = new File(uploadPath + "\\imgUpload\\" + userinfo.getUser_id() + "\\profile");
			String path = img.getImgPath() +img.getSubAddress();
			File folder = new File(path);
			UploadFileUtils.copyFile(sub,real);
			UploadFileUtils.subDireCtoryDelete(folder);
			userinfo.setUser_img(imgPath);
		} else {
			userinfo.setUser_img(req.getParameter("user_img"));
		}
		s_service.userUpdate(userinfo);
		session.invalidate();
		return "redirect:/";
	}

	
	//게시글 insert
	@PostMapping("BoardInsert")
	public String BoardInsert(S_BOARD board,S_ImgPathName img, MultipartFile file,HttpServletRequest req)
			throws IOException, Exception {
		System.out.println("보드in");
		if (file.getOriginalFilename() != null && file.getOriginalFilename() != "") {
			//img 컬럼에 데이터값 저장을 위한 setter
			//board.setB_img(img.getRealAddress()+img.getRealName());
			//게시물번호를 불러와서 
			String path = img.getImgPath() +img.getSubAddress();
			File folder = new File(path);
			File subAddress = new File (img.getImgPath() + img.getSubAddress());
			File realAddress = new File(img.getImgPath()+img.getRealAddress()); // + 아이디 + 게시묿번호 
			System.out.println("아이디"+board.getB_user_id());
			UploadFileUtils.copyFile(subAddress,realAddress);
			UploadFileUtils.subDireCtoryDelete(folder);
		}else {//파일이 선택되지 않았다면 
			board.setB_num("");
		}
		s_service.BoardInsert(board);
		return "redirect:S_index.c";
	}
	
	@PostMapping("BoardDetail")
	@ResponseBody
	public S_BOARD BoardView(S_BOARD board,String session_id,Model model) {
		System.out.println("업데이트 넘버 ="+board.getB_num());
		System.out.println("세션아이디 = "+session_id);
		board.setB_user_id(session_id);
		S_BOARD Detail = s_service.BoardDetail(board);
		System.out.println("디테일 b_content 값 = " + Detail.getB_content());
		model.addAttribute("Detail",Detail);
		System.out.println("끝");
		return Detail;
	}
	
	@PostMapping("Board_Update")
	public String BoardUpdatee(S_BOARD board, S_ImgPathName img, MultipartFile file, HttpServletRequest req ) throws IOException {
		System.out.println("업데이트"+board.getB_user_id());
		System.out.println(img.getImgPath());
		System.out.println(img.getRealAddress());
		System.out.println(img.getSubAddress());
		if(file.getOriginalFilename() != null && file.getOriginalFilename() != "") {
			new File(uploadPath + req.getParameter("b_content")).delete();
			String path = img.getImgPath() +img.getSubAddress();
			File folder = new File(path);
			File subAddress = new File (img.getImgPath() + img.getSubAddress());
			File realAddress = new File(img.getImgPath()+img.getRealAddress()); // + 아이디 + 게시묿번호 
			System.out.println("업데이트 서브 ="+subAddress);
			System.out.println("업데이트 리얼 ="+realAddress);
			System.out.println("아이디"+board.getB_user_id());
			UploadFileUtils.copyFile(subAddress,realAddress);
			UploadFileUtils.subDireCtoryDelete(folder);
			s_service.BoardUpdate(board);
		}
		return "S_index.c";
	}

	@PostMapping("subName")
	@ResponseBody
	public S_ImgPathName subName(MultipartFile file,String UploadFlag,String b_user_id) throws Exception {
		S_ImgPathName img = new S_ImgPathName();
		System.out.println("subName 플래그" + UploadFlag);
		System.out.println("아이디="+b_user_id);
		String imgUploadPath = uploadPath + File.separator + "imgUpload";
		//저장하는 날자로 폴더 생성 
		String ymdPath = UploadFileUtils.calcPath(b_user_id,imgUploadPath);
		//저장하는 파일의 이름을 생성
		img = UploadFileUtils.fileUpload(file.getOriginalFilename(), ymdPath,UploadFlag,b_user_id);

		//파일이 저장되는 로컬주소 및 폴더주소
		if(UploadFlag.equals("1")) {//타임라인 글쓸때
			//파일이 저장되는 로컬주소 
			String imgUploadPath1 = uploadPath;
			img.setImgPath(imgUploadPath1);
			img = UploadFileUtils.subFileUpload(img.getImgPath(),  file.getBytes(),img);
		}else if(UploadFlag.equals("2")) {// 회원정보수정 이미지
			img = UploadFileUtils.fileUpload(file.getOriginalFilename(), ymdPath,UploadFlag,b_user_id);

			//파일이 저장되는 로컬주소 
			String imgUploadPath1 = uploadPath;
			img.setImgPath(imgUploadPath1);
			img = UploadFileUtils.subFileUpload(img.getImgPath(),  file.getBytes(),img);
		}
		return img;
	}
	
	@PostMapping("deleteImg")
	@ResponseBody
	public S_ImgPathName deleteImg(String DeleteNum ,S_ImgPathName img, MultipartFile File) {
		System.out.println("Delete Address  = "+ img.getImgPath()+img.getSubAddress());
		File subAddress = new File(img.getImgPath()+img.getSubAddress());
		UploadFileUtils.subDireCtoryDelete(subAddress);
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
