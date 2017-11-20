<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../../common/jstl.jspf"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link href="${basePath }/css/main.css" rel="stylesheet" type="text/css" />
<link href="${basePath }/css/iconfont.css" rel="stylesheet" type="text/css" />
<link href="${basePath }/css/test.css" rel="stylesheet" type="text/css" />
<style>
.hasBeenAnswer {
	background: #5d9cec;
	color:#fff;
}
</style>
</head>
<body>


<div class="main">
	<!--nr start-->
	<div class="test_main">
		<div class="nr_left">
			<div class="test">
				<div class="test_content">
					<div class="test_content_title">
						<h2>单选题</h2>
						<p>
							<span>共</span><i class="content_lit">${singleChoiceNum }</i><span>题，</span><span>合计</span><i class="content_fs">${singleScore }</i><span>分</span>
						</p>
					</div>
				</div>
				<div class="test_content_nr">
					<ul>
						<c:set value="1" var="choiceNum"/>
						<c:forEach items="${choices }" var="choices">
							<li id="qu_0_${choiceNum }">
								<div class="test_content_nr_tt">
									<c:forEach items="${choiceQues }" var="choiceQue">
										<c:if test="${choiceQue.id eq choices.choiceId}">
											<i>${choiceNum }</i><span>(${choiceQue.score }分)</span><font>${choices.choiceTitle }</font>
										</c:if>
									</c:forEach>
									
								</div>

								<div class="test_content_nr_main">
									<ul>
										<li class="option">
											<input type="radio" class="radioOrCheck" name="answer${choiceNum }" id="0_answer_${choiceNum }_option_1"/>
											<label for="0_answer_${choiceNum }_option_1">
												A.<p class="ue" style="display: inline;">${choices.answera }</p>
											</label>
										</li>
									
										<li class="option">
											<input type="radio" class="radioOrCheck" name="answer${choiceNum }" id="0_answer_${choiceNum }_option_2"/>
											<label for="0_answer_${choiceNum }_option_2">
												B.<p class="ue" style="display: inline;">${choices.answerb }</p>
											</label>
										</li>
									
										<li class="option">
											<input type="radio" class="radioOrCheck" name="answer${choiceNum }" id="0_answer_${choiceNum }_option_3"/>
											<label for="0_answer_${choiceNum }_option_3">
												C.<p class="ue" style="display: inline;">${choices.answerc }</p>
											</label>
										</li>
									
										<li class="option">
											<input type="radio" class="radioOrCheck" name="answer${choiceNum }" id="0_answer_${choiceNum }_option_4"/>
											<label for="0_answer_${choiceNum }_option_4">
												D.<p class="ue" style="display: inline;">${choices.answerd }</p>
											</label>
										</li>
									</ul>
								</div>
							</li>
							<c:set value="${choiceNum+1 }" var="choiceNum"/>
						</c:forEach>
					</ul>
				</div>
				
				<div class="test_content">
					<div class="test_content_title">
						<h2>多选题</h2>
						<p>
							<span>共</span><i class="content_lit">${mulChoiceNum }</i><span>题，</span><span>合计</span><i class="content_fs">${mulScore }</i><span>分</span>
						</p>
					</div>
				</div>
				<div class="test_content_nr">
					<ul>
						<c:set value="1" var="mulNum"/>
						<c:forEach items="${mulChoices }" var="mulChoices">
							<li id="qu_1_${mulNum }">
								<div class="test_content_nr_tt">
									<c:forEach items="${mulChoiceQues }" var="mulChoiceQues">
										<c:if test="${mulChoiceQues.id == mulChoices.choiceId }">
											<i>${mulNum }</i><span>(${mulChoiceQues.score }分)</span><font>${mulChoices.choiceTitle }</font>
										</c:if>
									</c:forEach>
								</div>

								<div class="test_content_nr_main">
									<ul>
										<li class="option">
											<input type="checkbox" class="radioOrCheck" name="answer1" id="1_answer_${mulNum }_option_1"/>
											<label for="1_answer_${mulNum }_option_1">
												A.<p class="ue" style="display: inline;">${mulChoices.answera }</p>
											</label>
										</li>
										<li class="option">
											<input type="checkbox" class="radioOrCheck" name="answer1" id="1_answer_${mulNum }_option_2"/>
											<label for="1_answer_${mulNum }_option_2">
												B.<p class="ue" style="display: inline;">${mulChoices.answerb }</p>
											</label>
										</li>
										<li class="option">
											<input type="checkbox" class="radioOrCheck" name="answer1" id="1_answer_${mulNum }_option_3"/>
											<label for="1_answer_${mulNum }_option_3">
												C.<p class="ue" style="display: inline;">${mulChoices.answerc }</p>
											</label>
										</li>
										<li class="option">
											<input type="checkbox" class="radioOrCheck" name="answer1" id="1_answer_${mulNum }_option_4"/>
											<label for="1_answer_${mulNum }_option_4">
												D.<p class="ue" style="display: inline;">${mulChoices.answerd }</p>
											</label>
										</li>
									</ul>
								</div>
							</li>
							<c:set value="${mulNum+1 }" var="mulNum"/>
						</c:forEach>
					</ul>
				</div>
				
				<div class="test_content">
					<div class="test_content_title">
						<h2>判断题</h2>
						<p>
							<span>共</span><i class="content_lit">${judgeChoiceNum }</i><span>题，</span><span>合计</span><i class="content_fs">${judgeScore }</i><span>分</span>
						</p>
					</div>
				</div>
				<div class="test_content_nr">
					<ul>
						<c:set value="1" var="judgeNum"/>
						<c:forEach items="${judges }" var="judges">
							<li id="qu_2_${judgeNum }">
								<div class="test_content_nr_tt">
									<c:forEach items="${judgeQues }" var="judgeQues">
										<c:if test="${judgeQues.id == judges.judgeId }">
											<i>${judgeNum }</i><span>(${judgeQues.score }分)</span><font>${judges.judgeTitle }</font>
										</c:if>		
									</c:forEach>
								</div>
								<div class="test_content_nr_main">
									<ul>
										<li class="option">
											<input type="radio" class="radioOrCheck" name="answer${judgeNum }" id="2_answer_${judgeNum }_option_1" value="1"/>
											<label for="2_answer_${judgeNum }_option_1">
												<p class="ue" style="display: inline;">正确</p>
											</label>
										</li>
										<li class="option">
											<input type="radio" class="radioOrCheck" name="answer${judgeNum }" id="2_answer_${judgeNum }_option_2" value="0"/>
											<label for="2_answer_${judgeNum }_option_2">
												<p class="ue" style="display: inline;">错误</p>
											</label>
										</li>
									</ul>
								</div>
							</li>
							<c:set value="${judgeNum+1 }" var="judgeNum"/>
						</c:forEach>
					</ul>
				</div>
				
				<div class="test_content">
					<div class="test_content_title">
						<h2>填空题</h2>
						<p>
							<span>共</span><i class="content_lit">${blankChoiceNum }</i><span>题，</span><span>合计</span><i class="content_fs">${blankScore }</i><span>分</span>
						</p>
					</div>
				</div>
				<div class="test_content_nr">
					<ul>
						<c:set value="1" var="blankNum"/>
						<c:forEach items="${blanks }" var="blanks">
							<li id="qu_3_${blankNum }">
								<div class="test_content_nr_tt">
									<c:forEach items="${blankQues}" var="blankQues">
										<c:if test="${blankQues.id == blanks.blankId}">
											<i>${blankNum }</i><span>(${blankQues.score }分)</span><font>${blanks.blankTitle }</font>
										</c:if>
									</c:forEach>
								</div>
								<div class="test_content_nr_main">
									<ul>
										<li class="option" style="padding-bottom:10px">
											<label for="3_answer_${blankNum }_option_1">
												<input type="text"  name="answer${blankNum }" id="3_answer_${blankNum }_option_1" style="width:100%;height:22px;float:none;"/>
											</label>
										</li>
									</ul>
								</div>
							</li>
							<c:set value="${blankNum+1 }" var="blankNum"/>
						</c:forEach>
					</ul>
				</div>
				
				<div class="test_content">
					<div class="test_content_title">
						<h2>主观题</h2>
						<p>
							<span>共</span><i class="content_lit">${subChoiceNum }</i><span>题，</span><span>合计</span><i class="content_fs">${subScore }</i><span>分</span>
						</p>
					</div>
				</div>
				<div class="test_content_nr">
					<ul>
						<c:set value="1" var="subjectiveNum"/>
						<c:forEach items="${subjectives }" var="subjectives">
							<li id="qu_4_${subjectiveNum }">
								<div class="test_content_nr_tt">
									<c:forEach items="${subQues }" var="subQues">
										<c:if test="${subQues.id == subjectives.subId}">
											<i>${subjectiveNum }</i><span>(${subQues.score }分)</span><font>${subjectives.subTitle }</font>
										</c:if>
									</c:forEach>
								</div>
								<div class="test_content_nr_main">
									<ul>
										<li class="option">
											<label for="4_answer_${subjectiveNum }_option_1">
												<textarea name="answer${subjectiveNum }" style="width:100%;height:50px;"></textarea>
											</label>
										</li>
									</ul>
								</div>
							</li>
							<c:set value="${subjectiveNum+1 }" var="subjectiveNum"/>
						</c:forEach>
					</ul>
				</div>
				
			</div>

		</div>
		<div class="nr_right">
			<div class="nr_rt_main">
				<div class="rt_nr1">
					<div class="rt_nr1_title">
						<h1>
							答题卡
						</h1>
					</div>
					
					<div class="rt_content">
						<div class="rt_content_tt">
							<h2>单选题</h2>
							<p>
								<span>共</span><i class="content_lit">${singleChoiceNum }</i><span>题</span>
							</p>
						</div>
						<div class="rt_content_nr answerSheet">
							<c:set value="1" var="chNum"/>
							<ul>
								<c:forEach items="${choices }" var="choices">
									<li><a href="#qu_0_${chNum }">${chNum }</a></li>
									<c:set value="${chNum+1 }" var="chNum"/>
								</c:forEach>
							</ul>
						</div>
					</div>
				
					<div class="rt_content">
						<div class="rt_content_tt">
							<h2>多选题</h2>
							<p>
								<span>共</span><i class="content_lit">${mulChoiceNum }</i><span>题</span>
							</p>
						</div>
						<div class="rt_content_nr answerSheet">
							<ul>
								<c:set value="1" var="mNum"/>
								<c:forEach items="${mulChoices }" var="mulChoices">
									<li><a href="#qu_1_${mNum }">${mNum }</a></li>
									<c:set value="${mNum+1 }" var="mNum"/>
								</c:forEach>
							</ul>
						</div>
					</div>
					
					<div class="rt_content">
						<div class="rt_content_tt">
							<h2>判断题</h2>
							<p>
								<span>共</span><i class="content_lit">${judgeChoiceNum }</i><span>题</span>
							</p>
						</div>
						<div class="rt_content_nr answerSheet">
							<ul>
								<c:set value="1" var="juNum"/>
								<c:forEach items="${judges }" var="judges">
									<li><a href="#qu_2_${juNum }">${juNum }</a></li>
									<c:set value="${juNum+1 }" var="juNum"/>
								</c:forEach>
							</ul>
						</div>
					</div>
					
					<div class="rt_content">
						<div class="rt_content_tt">
							<h2>填空题</h2>
							<p>
								<span>共</span><i class="content_lit">${blankChoiceNum }</i><span>题</span>
							</p>
						</div>
						<div class="rt_content_nr answerSheet">
							<ul>
								<c:set value="1" var="blNum"/>
								<c:forEach items="${blanks }" var="blanks">
									<li><a href="#qu_3_${blNum }">${blNum }</a></li>
									<c:set value="${blNum+1 }" var="blNum"/>
								</c:forEach>
							</ul>
						</div>
					</div>
					
					<div class="rt_content">
						<div class="rt_content_tt">
							<h2>主观题</h2>
							<p>
								<span>共</span><i class="content_lit">${subChoiceNum }</i><span>题</span>
							</p>
						</div>
						<div class="rt_content_nr answerSheet">
							<ul>
								<c:set value="1" var="subNum"/>
								<c:forEach items="${subjectives }" var="subjectives">
									<li><a href="#qu_4_${subNum }">${subNum }</a></li>
									<c:set value="${subNum+1 }" var="subNum"/>
								</c:forEach>
							</ul>
						</div>
					</div>
					
				</div>

			</div>
		</div>
	</div>
	<!--nr end-->
	<div class="foot"></div>
</div>

<script src="${basePath }plugin/uimaker/js/jquery.min.js"></script>
<script src="${basePath }/js/jquery.easy-pie-chart.js"></script>
<!--时间js-->
<script src="${basePath }/js/jquery.countdown.js"></script>
<script>
	window.jQuery(function($) {
		"use strict";
		
		$('time').countDown({
			with_separators : false
		});
		$('.alt-1').countDown({
			css_class : 'countdown-alt-1'
		});
		$('.alt-2').countDown({
			css_class : 'countdown-alt-2'
		});
		
	});
	
	
	$(function() {
		$('li.option label').click(function() {
		;
			var examId = $(this).closest('.test_content_nr_main').closest('li').attr('id'); // 得到题目ID
			var cardLi = $('a[href=#' + examId + ']'); // 根据题目ID找到对应答题卡
			// 设置已答题
			if(!cardLi.hasClass('hasBeenAnswer')){
				cardLi.addClass('hasBeenAnswer');
			}
			
		});
	});
</script>

</body>
</html>
