package kh.pingpong.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.pingpong.dto.HobbyDTO;
import kh.pingpong.dto.JjimDTO;
import kh.pingpong.dto.LanguageDTO;
import kh.pingpong.dto.MemberDTO;
import kh.pingpong.dto.PartnerDTO;
import kh.pingpong.dto.ReportListDTO;
import kh.pingpong.dto.ReviewDTO;
import kh.pingpong.service.GroupService;
import kh.pingpong.service.MemberService;
import kh.pingpong.service.PartnerService;

@Controller
@RequestMapping("/partner/")
public class PartnerController {

	@Autowired
	private PartnerService pservice;

	@Autowired
	private MemberService mservice;

	@Autowired
	private GroupService gservice;

	@Autowired
	private HttpSession session;

	
	@Autowired 
	JavaMailSender mailSender;
	 

	// 메일 
	private Boolean mail(HttpServletRequest request, HttpServletResponse response, String pemail, String memail, String emailPassword,String contents) {
		Boolean result = false;
		System.out.println("mail start");
//		String uri = request.getRequestURI();
//		String contextPath = request.getContextPath();
//		String cmd = uri.substring(contextPath.length());

		//mail server 설정
		//String userMail = request.getParameter("mailId");
		String host = "smtp.gmail.com";
		String user = memail; //자신의 네이버 계정
		String password = emailPassword;// 자신의 패스워드
//		System.out.println("host : " + host);
//		System.out.println("user : " + user);
//		System.out.println("password : " + password);

		//메일 받을 주소
		String to_email = pemail;
		//System.out.println("to_email : " + to_email);
		
		//SMTP 서버 정보를 설정한다.
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", 465);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust",host);

		 //session 생성
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
           protected PasswordAuthentication getPasswordAuthentication() {
              return new PasswordAuthentication(user, password);
           }
        });

		String charset = "UTF-8";
		
		//email 전송
		try {
			MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(user));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to_email));
			
			//메일 제목
			msg.setSubject(user + "님이 보낸 메일입니다.",charset); 
			
			//메일 내용
			msg.setText(contents,charset);
			
			Transport.send(msg);
			System.out.println("메시지를 성공적으로 보냈습니다.");   
			return !result;
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getStackTrace().toString());
			return result;
		}
	}
	
	//이메일 보내기
	@ResponseBody
	@RequestMapping("send")
	public String send(PartnerDTO pdto, MemberDTO mdto,  Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		System.out.println("================== test ================");
		System.out.println(pdto.getEmail());
		Boolean result = this.mail(request, response, pdto.getEmail(), mdto.getMemail(), mdto.getEmailPassword(),mdto.getContents());
		System.out.println("in send: " + result);	
		//return "redirect:/partner/partnerList";
		return String.valueOf(result);
	}

	//파트너 목록 페이지
	@RequestMapping("partnerList")
	public String partnerList(String align,HttpServletRequest request, Model model,Map<String,Object> search) throws Exception{
		int cpage = 1;
		try {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}catch(Exception e) {}
		//List<PartnerDTO> plist = pservice.partnerList(cpage);
		MemberDTO loginInfo = (MemberDTO)session.getAttribute("loginInfo");
		List<HobbyDTO> hdto = pservice.selectHobby();
		List<LanguageDTO> ldto = pservice.selectLanguage();
		List<PartnerDTO> alist = pservice.searchAlign(cpage,align);
		String navi = pservice.getPageNavi(cpage,align,search);
		
		model.addAttribute("loginInfo", loginInfo);
		model.addAttribute("navi", navi);
		model.addAttribute("hdto", hdto);
		model.addAttribute("ldto", ldto);
		model.addAttribute("align", align);
		model.addAttribute("alist", alist);
		
		return "partner/partnerList";
	}

	//파트너 상세 뷰페이지
	@ResponseBody
	@RequestMapping("chatPartner")
	public List<PartnerDTO> chatPartner(HttpServletRequest request, Model model) throws Exception{
		List<PartnerDTO> plist = pservice.partnerListAll();
		return plist;
	}

	@Transactional("txManager")
	@RequestMapping("partnerView")
	public String partnerView(int seq, Model model) throws Exception{
		MemberDTO loginInfo = (MemberDTO)session.getAttribute("loginInfo");
		String id = loginInfo.getId();
		PartnerDTO pdto = pservice.selectBySeq(seq);
		
		Map<Object, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("parent_seq", seq);
		
		JjimDTO jdto = new JjimDTO();
		jdto.setId(id);
		jdto.setParent_seq(seq);
		
		boolean checkJjim = pservice.selectJjim(jdto);
		System.out.println(checkJjim);
		model.addAttribute("checkJjim",checkJjim);
		model.addAttribute("pdto", pdto);
		System.out.println("리뷰 포="+ pdto.getReview_point());

		//리뷰 리스트 출력
		List<ReviewDTO> reviewList = gservice.reviewList(seq);

		model.addAttribute("reviewList", reviewList);
		return "partner/partnerView";
	}
	
	//멤버 선택 
	@RequestMapping("selectMember")
	public String selectMember(MemberDTO mdto, Model model, HttpServletRequest request) throws Exception{
		//String id = "ddong";
		MemberDTO loginInfo = (MemberDTO)session.getAttribute("loginInfo");
		mdto = mservice.memberSelect(loginInfo);
		return "partner/partnerList";
	}

	//파트너 등록
	@RequestMapping("insertPartner")
	public String insertPartner(MemberDTO mdto, String contact, Model model) throws Exception{
		MemberDTO loginInfo = (MemberDTO)session.getAttribute("loginInfo");
		
		mdto = pservice.selectMember(loginInfo.getId());
		mdto = mservice.memberSelect(loginInfo);
		Map<String, Object> insertP = new HashMap<>();
		insertP.put("mdto", mdto);
		insertP.put("contact", contact);	
		pservice.partnerInsert(insertP,mdto);
		
		if (loginInfo.getId().contentEquals(mdto.getId()) ) {
			MemberDTO mbdto = pservice.selectMember(loginInfo.getId());
			session.setAttribute("loginInfo", mbdto);
		}		
		
		return "redirect:/partner/partnerList?align=recent";
	}

	//파트너 삭제
	@RequestMapping("deletePartner")
	public String deletePartner(Model model, String id) throws Exception{
		MemberDTO loginInfo = (MemberDTO)session.getAttribute("loginInfo");
		pservice.deletePartner(loginInfo);
		//model.addAttribute(attributeName, attributeValue)
		
		if (loginInfo.getId().contentEquals(id) ) {
			MemberDTO mbdto = pservice.selectMember(loginInfo.getId());
			session.setAttribute("loginInfo", mbdto);
		}	
		return "redirect:/partner/partnerList?align=recent";
	}

	//상세 검색
	@RequestMapping("partnerSearch")
	public String search(String align,PartnerDTO pdto, HttpServletRequest request, Model model) throws Exception{
		int cpage = 1;
		try {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}catch(Exception e) {}
                                                                               
		Map<String, Object> search = new HashMap<>();	
		List<PartnerDTO> alist = pservice.search(cpage, search, pdto);
		String navi = pservice.getPageNavi(cpage,align,search);
		List<HobbyDTO> hdto = pservice.selectHobby();
		List<LanguageDTO> ldto = pservice.selectLanguage();
		//List<PartnerDTO> alist = pservice.searchAlign(align);
		model.addAttribute("alist", alist);
		//model.addAttribute("plist", plist);
		model.addAttribute("navi", navi);
		model.addAttribute("hdto", hdto);
		model.addAttribute("ldto", ldto);

		return "/partner/partnerList";
	}

	//파트너 신고
	@RequestMapping("report")
	@ResponseBody
	public int report(ReportListDTO rldto, Model model) {
		MemberDTO mdto = (MemberDTO)session.getAttribute("loginInfo");
		rldto.setReporter(mdto.getId());
		
		int result = pservice.selectReport(rldto);
		model.addAttribute("rldto", rldto);
		
		return result;
	}
	
	@RequestMapping("reportProc")
	public String reportProc(ReportListDTO rldto, Model model) {
		System.out.println("rldto =" + rldto.getSeq());
		pservice.insertReport(rldto);
		return "redirect:/partner/partnerView?seq=" + rldto.getParent_seq();
	}
	
	//리뷰 글쓰기
	@RequestMapping("reviewWrite")
	@ResponseBody
	public String reviewWrite(ReviewDTO redto,MemberDTO mdto) throws Exception{
		
		int result = gservice.reviewWrite(redto);
		session.setAttribute("loginInfo2", mdto);
		if(result>0) {
			return String.valueOf(true);
		}else {
			return String.valueOf(false);
		}
	}
	
	
	//찜하기
	@RequestMapping("jjim")
	@ResponseBody
	public int partnerInsertJjim(JjimDTO jdto) {
		MemberDTO loginInfo = (MemberDTO)session.getAttribute("loginInfo");
		String id = loginInfo.getId();
		
		jdto.setId(id);
		
		int result = pservice.insertJjim(jdto);
		return result;
	}
	

	@RequestMapping("delJjim")
	@ResponseBody
	public int groupDeleteJjim(JjimDTO jdto) {
		MemberDTO loginInfo = (MemberDTO)session.getAttribute("loginInfo");
		String id = loginInfo.getId();
		
		jdto.setId(id);
		
		int result = pservice.deleteJjim(jdto);
		return result;
	}
	
	//최신순, 평점순
	@RequestMapping("align")
	public String align(HttpServletRequest request, Model model, int cpage) throws Exception{
		String alignType = request.getParameter("align");
		List<PartnerDTO> alist = pservice.searchAlign(cpage,alignType);
		System.out.println(alignType);
		model.addAttribute("align", alignType);
		model.addAttribute("alist", alist);
		return "/partner/partnerList";
	}


}

