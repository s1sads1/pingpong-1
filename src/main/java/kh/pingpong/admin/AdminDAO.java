package kh.pingpong.admin;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kh.pingpong.dto.BlacklistDTO;
import kh.pingpong.dto.CorrectDTO;
import kh.pingpong.dto.DeleteApplyDTO;
import kh.pingpong.dto.DiscussionDTO;
import kh.pingpong.dto.FileDTO;
import kh.pingpong.dto.GroupDTO;
import kh.pingpong.dto.LanguageDTO;
import kh.pingpong.dto.LessonDTO;
import kh.pingpong.dto.LocationDTO;
import kh.pingpong.dto.MemberDTO;
import kh.pingpong.dto.NewsDTO;
import kh.pingpong.dto.PartnerDTO;
import kh.pingpong.dto.ReportListDTO;
import kh.pingpong.dto.TuteeDTO;
import kh.pingpong.dto.TutorAppDTO;

@Repository
public class AdminDAO {
	@Autowired
	private SqlSessionTemplate mybatis;
	
	// 회원 목록
	public List<MemberDTO> memberList(Map<String, Integer> page) {
		return mybatis.selectList("Admin.memberList", page);
	}
	
	// 회원 뷰
	public MemberDTO memberView(String id) {
		return mybatis.selectOne("Admin.memberView", id);
	}
	
	// 파트너 목록
	public List<PartnerDTO> partnerList(Map<String, Integer> page) {
		return mybatis.selectList("Admin.partnerList", page);
	}
	
	// 파트너 뷰
	public PartnerDTO partnerView(int seq) {
		return mybatis.selectOne("Admin.partnerView", seq);
	}
	
	// 그룹 목록
	public List<GroupDTO> groupList(Map<String, Integer> page) {
		return mybatis.selectList("Admin.groupList", page);
	}
	
	// 그룹 뷰
	public GroupDTO groupView(int seq) {
		return mybatis.selectOne("Admin.groupView", seq);
	}
	
	// 튜터 목록
	public List<MemberDTO> tutorList(Map<String, Integer> page) {
		return mybatis.selectList("Admin.tutorList", page);
	}
	
	// 튜터 뷰
	public MemberDTO tutorView(String id) {
		return mybatis.selectOne("Admin.tutorView", id);
	}
	
	// 튜터 신청 목록
	public List<TutorAppDTO> tutorAppList(Map<String, Integer> page) {
		return mybatis.selectList("Admin.tutorAppList", page);
	}
	
	// 튜터 신청 뷰
	public TutorAppDTO tutorAppView(int seq) {
		return mybatis.selectOne("Admin.tutorAppView", seq);
	}
	
	//자격증 파일 보기
	public List<FileDTO> licenseViewFile(int seq){
		return mybatis.selectList("Admin.licenseViewFile", seq);
	}
	
	// 강의 목록
	public List<LessonDTO> lessonList(Map<String, Integer> page) {
		return mybatis.selectList("Admin.lessonList", page);
	}
	
	// 강의 뷰
	public LessonDTO lessonView(int seq) {
		return mybatis.selectOne("Admin.lessonView", seq);
	}
	
	// 강의 신청 목록
	public List<LessonDTO> lessonAppList(Map<String, Integer> page) {
		return mybatis.selectList("Admin.lessonAppList", page);
	}
	
	// 강의 신청 뷰
	public LessonDTO lessonAppView(int seq) {
		return mybatis.selectOne("Admin.lessonAppView", seq);
	}
	
	// 강의 삭제 목록
	public List<DeleteApplyDTO> lessonDelList(Map<String, Integer> page) {
		return mybatis.selectList("Admin.lessonDelList", page);
	}
	
	// 강의 삭제 뷰
	public DeleteApplyDTO lessonDelView(int seq) {
		return mybatis.selectOne("Admin.lessonDelView", seq);
	}
	
	// 튜티 목록
	public List<TuteeDTO> tuteeList(Map<String, Integer> page) {
		return mybatis.selectList("Admin.tuteeList", page);
	}
	
	// 튜티 뷰, 환불 신청 뷰
	public TuteeDTO tuteeView(int seq) {
		return mybatis.selectOne("Admin.tuteeView", seq);
	}
	
	// 환불 신청 목록
	public List<TuteeDTO> refundList(Map<String, Integer> page) {
		return mybatis.selectList("Admin.refundList", page);
	}

	// 토론 게시글 목록
	public List<DiscussionDTO> discussionList(Map<String, Integer> page) {
		return mybatis.selectList("Admin.discussionList", page);
	}
	
	// 토론 게시글 뷰
	public DiscussionDTO discussionView(int seq) {
		return mybatis.selectOne("Admin.discussionView", seq);
	}
	
	// 첨삭 게시글 목록
	public List<CorrectDTO> correctList(Map<String, Integer> page) {
		return mybatis.selectList("Admin.correctList", page);
	}
	
	// 첨삭 게시글 뷰
	public CorrectDTO correctView(int seq) {
		return mybatis.selectOne("Admin.correctView", seq);
	}
	
	// 소식통 게시글 목록
	public List<NewsDTO> newsList(Map<String, Integer> page) {
		return mybatis.selectList("Admin.newsList", page);
	}
	
	// 소식통 게시글 뷰
	public NewsDTO newsView(int seq) {
		return mybatis.selectOne("Admin.newsView", seq);
	}
	
	// 신고 목록
	public List<ReportListDTO> reportList(Map<String, Integer> page) {
		return mybatis.selectList("Admin.reportList", page);
	}
	
	// 신고 뷰
	public ReportListDTO reportView(int seq) {
		return mybatis.selectOne("Admin.reportView", seq);
	}
	
	// 삭제
	public int deleteOne(Map<String, Object> param) {
		return mybatis.delete("Admin.deleteOne", param);
	}
	
	// 튜터 삭제
	public int deleteTutor(String id) {
		return mybatis.update("Admin.deleteTutor", id);
	}
	
	// 파트너 삭제
	public int deletePartner(String id) {
		return mybatis.update("Admin.deletePartner", id);
	}
	
	// 튜티 삭제
	public int deleteTutee(int seq) {
		return mybatis.update("Admin.deleteTutee", seq);
	}
	
	// 튜터 승인(tutor_app 테이블 pass 컬럼)
	public int updateTutorAppPass(int seq) {
		return mybatis.update("Admin.updateTutorAppPass", seq);
	}
	
	// 튜터 승인(member 테이블 grade 컬럼)
	public int updateTutorAppGrade(String id) {
		return mybatis.update("Admin.updateTutorAppGrade", id);
	}
	
	// 강의 신청 승인
	public int updateLessonPass(int seq) {
		return mybatis.update("Admin.updateLessonPass", seq);
	}
	
	// 강의 삭제 승인
	public int deleteApplyLesson(int parent_seq) {
		return mybatis.delete("Admin.deleteApplyLesson", parent_seq);
	}
	
	// 신고 승인(reportlist 테이블 pass 컬럼)
	public int updateReportListPass(int seq) {
		return mybatis.update("Admin.updateReportListPass", seq);
	}
	
	// 신고 승인(member 테이블 report_count 컬럼)
	public int updateReportCount(String id) {
		return mybatis.update("Admin.updateReportCount", id);
	}
	
	// 카운트
	public int selectCount(Map<String, String> param) {
		return mybatis.selectOne("Admin.selectCount", param);
	}
	
	// 체크박스로 여러 개 삭제
	public int deleteAll(Map<String, Object> param) {
		return mybatis.delete("Admin.deleteAll", param);
	}
	
	// 체크박스로 여러 튜터 삭제
	public int deleteSelectedTutor(List<String> list) {
		return mybatis.update("Admin.deleteSelectedTutor", list);
	}
	
	// 체크박스로 여러 파트너 삭제
	public int deleteSelectedPartner(Map<String, Object> param) {
		return mybatis.update("Admin.deleteSelectedPartner", param);
	}
	
	// 체크박스로 여러 튜티 삭제
	public int getLessonSeqByTutee(int seq) {
		return mybatis.selectOne("Admin.getLessonSeqByTutee", seq);
	}
	
	// 체크박스로 여러 개 승인
	public int acceptAll(Map<String, Object> param) {
		return mybatis.update("Admin.acceptAll", param);
	}
	
	// 체크박스로 여러 강의 삭제 승인
	public int acceptAllLessonDel(Map<String, Object> param) {
		return mybatis.delete("Admin.acceptAllLessonDel", param);
	}
	
	// 튜터 신청 아이디 셀렉트
	public String getIdFromTutorApp(int seq) {
		return mybatis.selectOne("Admin.getIdFromTutorApp", seq);
	}
	
	// 체크박스로 여러 튜터 신청 승인
	public int acceptAllTutorApp(Map<String, Object> param) {
		return mybatis.update("Admin.acceptAllTutorApp", param);
	}
	
	// 신고 아이디 셀렉트
	public String getIdFromReportList(int seq) {
		return mybatis.selectOne("Admin.getIdFromReportList", seq);
	}
	
	// 신고 카테고리 셀렉트
	public String getCategoryFromRep(int seq) {
		return mybatis.selectOne("Admin.getCategoryFromRep", seq);
	}
	
	// 신고 부모 시퀀스 셀렉트
	public int getParentSeqFromRep(int seq) {
		return mybatis.selectOne("Admin.getParentSeqFromRep", seq);
	}
	
	// 강의 삭제 부모 시퀀스 셀렉트
	public int getParentSeqFromDel(int seq) {
		return mybatis.selectOne("Admin.getParentSeqFromDel", seq);
	}
	
	// 언어 조회
	public List<LanguageDTO> selectFiveLang() {
		return mybatis.selectList("Admin.selectFiveLang");
	}
	
	// 지역 조회
	public List<LocationDTO> selectFiveLoc() {
		return mybatis.selectList("Admin.selectFiveLoc");
	}
	
	// 블랙리스트 추가
	public int insertBlacklist(Map<String, Object> param) {
		return mybatis.insert("Admin.insertBlacklist", param);
	}
	
	// 이름 가져오기
	public String getNameForBlack(String id) {
		return mybatis.selectOne("Admin.getNameForBlack", id);
	}
	
	// 블랙리스트 목록
	public List<BlacklistDTO> blacklistList(Map<String, Integer> param) {
		return mybatis.selectList("Admin.blacklistList", param);
	}
	
	// 블랙리스트 뷰
	public BlacklistDTO blacklistView(int seq) {
		return mybatis.selectOne("Admin.blacklistView", seq);
	}
	
	// 신고 횟수 셀렉트
	public int getReportCount(String id) {
		return mybatis.selectOne("Admin.getReportCount", id);
	}
	
	// 블랙리스트 만료
	public int doneBlacklist(String today_date) {
		return mybatis.delete("Admin.doneBlacklist", today_date);
	}
	
	// 로그인 시 블랙리스트 확인
	public boolean isBlacklist(String id) {
		boolean result = false;
		
		if (mybatis.selectOne("Admin.isBlacklist", id) != null) {
			result = true;
		}
		
		return result;
	}
	
	// 신고된 게시글인지 확인
	public List<Integer> isAlreadyReport(Map<String, Object> param) {
		return mybatis.selectList("Admin.isAlreadyReport", param);
	}
}
