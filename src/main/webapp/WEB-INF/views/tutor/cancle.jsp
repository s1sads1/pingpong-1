 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>



<script>
	$(function() {
		var layerPop_s1 = $("#layerPop_s1");
		$("#popOnBtn").on("click", function() {
			layerPop_s1.stop().fadeIn();
		})
		
		$("#back").on("click", function(){
			layerPop_s1.stop().fadeOut();
		})
	})
</script>
<style>
	#layerPop_s1 { position:fixed; left:0; top:0; width:100%; height:100%; z-index:10001; display:none;  background:rgba(0,0,0,0.5); }
	#layerPop_s1 .pop_body { position:absolute; left:50%; top:50%; transform:translate(-50%, -50%); max-width:640px; background:#fff;}
</style>



<article id="layerPop_s1">
	<div class="pop_body">
		<div class="tit_s3">
			<h4>탈퇴 신청서</h4>
		</div>
		<form action="cancleProc" method="post">
			<input type="hidden" name="id" value="${ldto.id}">
			<input type="hidden" name="parent_seq" value="${seq}">
			<input type="hidden" name="category" value="${ldto.category}" />
			id: ${ldto.id }
			<section data-seq="${seq}">
				<article>
					<div>탈퇴이유</div>
					<div class="contents">
						<textarea rows="30" cols="50" name="contents"></textarea>
					</div>
					<div>
						<button>제출하기</button>
						<input type="button" id="back" value="닫기">
					</div>

				</article>
			</section>
		</form>
	</div>
</article>