<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/admin/aheader.jsp"/>
	<div id="main_wrap">
		<div id="table_wrap">
			<h3 class="tit">강의 삭제 신청</h3>
			<table>
				<thead>
					<tr>
						<th><input type="checkbox" id="selectAll"></th>
						<th>번호</th>
						<th>신청자</th>
						<th>유형</th>
						<th>강의 코드</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${empty ldlist}">
							<tr>
								<td colspan="7">등록된 강의 삭제 신청이 없습니다.</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="ldlist" items="${ldlist}">
								<tr>
									<td><input type="checkbox" value="${ldlist.seq}" class="deleteCheck"></td>
									<td>${ldlist.seq}</td>
									<td><a href="/admins/lessonDelView?seq=${ldlist.seq}">${ldlist.id}</a></td>
									<td>${ldlist.category}</td>
									<td>${ldlist.parent_seq}</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<div class="btn_wrap">
				<div class="btns">
					<button id="acceptAll">승인</button>
					<button id="deleteAll">삭제</button>
				</div>
			</div>
			<div class="navi">${navi}</div>
			<script>
				$('#selectAll').on('change', function(){
					if ($('#selectAll').is(':checked')) {
						$('.deleteCheck').attr('checked', 'true');
					} else {
						$('.deleteCheck').removeAttr('checked');
					}
				})
				
				$('#acceptAll').on('click', function(){
					var accCount = $('.deleteCheck:checked').length;
					
					if (accCount == 0) {
						alert('선택된 항목이 없습니다.');
					} else {
						var conf = confirm('선택한 항목들을 모두 승인하시겠습니까?');
						
						if (conf) {
							var accList = [];
							
							for (var i = 0; i < accCount; i++){
								accList[i] = $($('.deleteCheck:checked')[i]).val();
							}
							
							location.href = '/admins/acceptDeleteLessons?values=' + accList;
						}
					}
				})
			
				$('#deleteAll').on('click', function(){
					var delCount = $('.deleteCheck:checked').length;
					
					if (delCount == 0) {
						alert('선택된 항목이 없습니다.');
					} else {
						var conf = confirm('선택한 항목들을 정말 삭제하시겠습니까?');
						
						if (conf) {
							var delList = [];
							
							for (var i = 0; i < delCount; i++) {
								delList[i] = $($('.deleteCheck:checked')[i]).val();
							}
							
							location.href = '/admins/deleteAll?pageName=lessonDelList&values=' + delList;
						}
					}
				})
			</script>
		</div>
	</div>
</body>
</html>