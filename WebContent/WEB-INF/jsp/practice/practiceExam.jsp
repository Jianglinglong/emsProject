<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE HTML>
<html>

<head>
<%@include file="/common/easyui.jspf"%>
</head>
<body>



	<div class="main">
		<!--nr start-->
		<div class="test_main">
			<div class="nr_left">
				<div class="test">
					<h1 style="text-align: center">${practiceTitle }</h1>

					<form id="stu-exam-form" method="post">
						<input type="hidden" name="paperID" value="${paperID}" /> <input
							type="hidden" name="courseID" value="${course.courseId}">

						<c:if test="${choices!=null }">
							<div class="test_content">
								<div class="test_content_title">
									<h2>单选题</h2>
									<p>
										<span>共</span><i class="content_lit">${choicesNum }</i><span>题</span>
									</p>

								</div>
							</div>
							<div class="test_content_nr">
								<ul>
									<c:set value="1" var="choiceNum" />
									<c:forEach items="${choices }" var="choices">
										<li id="qu_0_${choiceNum }">
											<div class="test_content_nr_tt">
												<i>${choiceNum }</i><font>${choices.choiceTitle }</font> <span
													class="hiddensingle" style="display: none;">参考答案:&nbsp;&nbsp;${choices.answer }</span>
												<input type="hidden" name="single" class="single"
													an="${choices.answer }" value="${choices.choiceId}" />
											</div>
											<div class="test_content_nr_main">
												<ul>
													<li class="option"><input type="radio"
														class="radioOrCheck" value="A"
														name="singleChoice${choices.choiceId }"
														id="0_answer_${choiceNum }_option_1" /> <label
														for="0_answer_${choiceNum }_option_1"> A.
															<p class="ue" style="display: inline;">${choices.answera }</p>
													</label></li>

													<li class="option"><input type="radio"
														class="radioOrCheck" value="B"
														name="singleChoice${choices.choiceId }"
														id="0_answer_${choiceNum }_option_2" /> <label
														for="0_answer_${choiceNum }_option_2"> B.
															<p class="ue" style="display: inline;">${choices.answerb }</p>
													</label></li>

													<li class="option"><input type="radio"
														class="radioOrCheck" value="C"
														name="singleChoice${choices.choiceId }"
														id="0_answer_${choiceNum }_option_3" /> <label
														for="0_answer_${choiceNum }_option_3"> C.
															<p class="ue" style="display: inline;">${choices.answerc }</p>
													</label></li>

													<li class="option"><input type="radio"
														class="radioOrCheck" value="D"
														name="singleChoice${choices.choiceId }"
														id="0_answer_${choiceNum }_option_4" /> <label
														for="0_answer_${choiceNum }_option_4"> D.
															<p class="ue" style="display: inline;">${choices.answerd }</p>
													</label></li>
												</ul>
											</div>
										</li>
										<c:set value="${choiceNum+1 }" var="choiceNum" />
									</c:forEach>
								</ul>
							</div>
						</c:if>

						<c:if test="${mulChoices!=null }">
							<div class="test_content">
								<div class="test_content_title">
									<h2>多选题</h2>
									<p>
										<span>共</span><i class="content_lit">${mulChoicesNum }</i><span>题</span>
									</p>
								</div>
							</div>
							<div class="test_content_nr">
								<ul>
									<c:set value="1" var="mulNum" />
									<c:forEach items="${mulChoices }" var="mulChoices">
										<li id="qu_1_${mulNum }">
											<div class="test_content_nr_tt">
												<i>${mulNum }</i><font>${mulChoices.choiceTitle }</font> <input
													type="hidden" name="multi" class="multi"
													an="${mulChoices.answer}" value="${mulChoices.choiceId}" />
												<span class="hiddenmul" style="display: none;">参考答案:&nbsp;&nbsp;${mulChoices.answer }</span>
											</div>

											<div class="test_content_nr_main">
												<ul>
													<li class="option"><input type="checkbox"
														class="radioOrCheck" value="A"
														name="multChoice${mulChoices.choiceId }"
														id="1_answer_${mulNum }_option_1" /> <label
														for="1_answer_${mulNum }_option_1"> A.
															<p class="ue" style="display: inline;">${mulChoices.answera }</p>
													</label></li>
													<li class="option"><input type="checkbox"
														class="radioOrCheck" value="B"
														name="multChoice${mulChoices.choiceId }"
														id="1_answer_${mulNum }_option_2" /> <label
														for="1_answer_${mulNum }_option_2"> B.
															<p class="ue" style="display: inline;">${mulChoices.answerb }</p>
													</label></li>
													<li class="option"><input type="checkbox"
														class="radioOrCheck" value="C"
														name="multChoice${mulChoices.choiceId }"
														id="1_answer_${mulNum }_option_3" /> <label
														for="1_answer_${mulNum }_option_3"> C.
															<p class="ue" style="display: inline;">${mulChoices.answerc }</p>
													</label></li>
													<li class="option"><input type="checkbox"
														class="radioOrCheck" value="D"
														name="multChoice${mulChoices.choiceId }"
														id="1_answer_${mulNum }_option_4" /> <label
														for="1_answer_${mulNum }_option_4"> D.
															<p class="ue" style="display: inline;">${mulChoices.answerd }</p>
													</label></li>
												</ul>
											</div>
										</li>

										<c:set value="${mulNum+1 }" var="mulNum" />
									</c:forEach>
								</ul>
							</div>
						</c:if>

						<c:if test="${judges!=null }">
							<div class="test_content">
								<div class="test_content_title">
									<h2>判断题</h2>
									<p>
										<span>共</span><i class="content_lit">${judgesNum }</i><span>题</span>
									</p>
								</div>
							</div>
							<div class="test_content_nr">
								<ul>
									<c:set value="1" var="judgeNum" />
									<c:forEach items="${judges }" var="judges">
										<li id="qu_2_${judgeNum }">
											<div class="test_content_nr_tt">
												<i>${judgeNum }</i><font>${judges.judgeTitle }</font> 
												<input type="hidden" name="judge" class="judge" an="${judges.answer}" value="${judges.judgeId}" /><br />
												<span class="hiddenJudge" style="display: none">参考答案:&nbsp;&nbsp;${judges.answer == 1 ? "正确" : "错误" }</span>
											</div>
											<div class="test_content_nr_main">
												<ul>
													<li class="option"><input type="radio"
														class="radioOrCheck" name="judgeNum${judges.judgeId }"
														id="2_answer_${judgeNum }_option_1" value="1" /> <label
														for="2_answer_${judgeNum }_option_1">
															<p class="ue" style="display: inline;">正确</p>
													</label></li>
													<li class="option"><input type="radio"
														class="radioOrCheck" name="judgeNum${judges.judgeId }"
														id="2_answer_${judgeNum }_option_2" value="2" /> <label
														for="2_answer_${judgeNum }_option_2">
															<p class="ue" style="display: inline;">错误</p>
													</label></li>
												</ul>
											</div>
										</li>
										<c:set value="${judgeNum+1 }" var="judgeNum" />
									</c:forEach>
								</ul>
							</div>
						</c:if>

						<c:if test="${blanks!=null }">
							<div class="test_content">
								<div class="test_content_title">
									<h2>填空题</h2>
									<p>
										<span>共</span><i class="content_lit">${blanksNum }</i><span>题</span>
									</p>
								</div>
							</div>
							<div class="test_content_nr">
								<ul>
									<c:set value="1" var="blankNum" />
									<c:forEach items="${blanks }" var="blanks">
										<li id="qu_3_${blankNum }">
											<%-- <input type="hidden" name="blank" value="${blanks.blankId}"/> --%>
											<div class="test_content_nr_tt">
												<i>${blankNum }</i><font>${blanks.blankTitle }</font>
												<input type="hidden" class="blank" value="${blanks.blankId}" an="${blanks.answer}"/><br/>
												<span class="hiddenBlank" style="display: none">参考答案:&nbsp;&nbsp;${blanks.answer}</span>
											</div>
											<div class="test_content_nr_main">
												<ul>
													<li class="option" style="padding-bottom: 10px"><label
														for="3_answer_${blankNum }_option_1"> <input
															type="text" name="blankNum${blanks.blankId }"
															id="3_answer_${blankNum }_option_1"
															style="width: 100%; height: 22px; float: none;" />
													</label></li>
												</ul>
											</div></li>
										<c:set value="${blankNum+1 }" var="blankNum" />
									</c:forEach>
								</ul>
							</div>
						</c:if>

						<c:if test="${subjectives!=null }">
							<div class="test_content">
								<div class="test_content_title">
									<h2>主观题</h2>
									<p>
										<span>共</span><i class="content_lit">${subjectivesNum }</i><span>题</span>
									</p>
								</div>
							</div>
							<div class="test_content_nr">
								<ul>
									<c:set value="1" var="subjectiveNum" />
									<c:forEach items="${subjectives }" var="subjectives">
										<li id="qu_4_${subjectiveNum }"><input type="hidden"
											name="subjective" value="${subjectives.subId}" />
											<div class="test_content_nr_tt">
												<i>${subjectiveNum }</i><font>${subjectives.subTitle }</font>
											</div>
											<div class="test_content_nr_main">
												<ul>
													<li class="option"><label
														for="4_answer_${subjectiveNum }_option_1"> <textarea
																name="subjectiveNum${subjectives.subId}"
																style="width: 100%; height: 50px;"></textarea>
													</label></li>
													<li><span class="hiddenSubjective"
														style="display: none;">参考答案:&nbsp;&nbsp;${subjectives.answer }</span>
													</li>
												</ul>
											</div></li>
										<c:set value="${subjectiveNum+1 }" var="subjectiveNum" />
									</c:forEach>
								</ul>
							</div>
						</c:if>
					</form>
				</div>
			</div>
		</div>
		<div class="nr_right">
			<div class="nr_rt_main">
				<div class="rt_nr1">
					<div class="rt_nr1_title">
						<h1>答题卡</h1>
					</div>
					<c:if test="${choices!=null }">
						<div class="rt_content">
							<div class="rt_content_tt">
								<h2>单选题</h2>
								<p>
									<span>共</span><i class="content_lit">${choicesNum }</i><span>题</span>
								</p>
							</div>
							<div class="rt_content_nr answerSheet">
								<c:set value="1" var="chNum" />
								<ul>
									<c:forEach items="${choices }" var="choices">
										<li><a class="singleAn" href="#qu_0_${chNum }">${chNum }</a></li>
										<c:set value="${chNum+1 }" var="chNum" />
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:if>

					<c:if test="${mulChoices != null}">
						<div class="rt_content">
							<div class="rt_content_tt">
								<h2>多选题</h2>
								<p>
									<span>共</span><i class="content_lit">${mulChoicesNum }</i><span>题</span>
								</p>
							</div>
							<div class="rt_content_nr answerSheet">
								<ul>
									<c:set value="1" var="mNum" />
									<c:forEach items="${mulChoices }" var="mulChoices">
										<li><a class="mulAn" href="#qu_1_${mNum }">${mNum }</a></li>
										<c:set value="${mNum+1 }" var="mNum" />
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:if>

					<c:if test="${judges!=null }">
						<div class="rt_content">
							<div class="rt_content_tt">
								<h2>判断题</h2>
								<p>
									<span>共</span><i class="content_lit">${judgesNum }</i><span>题</span>
								</p>
							</div>
							<div class="rt_content_nr answerSheet">
								<ul>
									<c:set value="1" var="juNum" />
									<c:forEach items="${judges }" var="judges">
										<li><a class="judgeAn" href="#qu_2_${juNum }">${juNum }</a></li>
										<c:set value="${juNum+1 }" var="juNum" />
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:if>

					<c:if test="${blanks!=null }">
						<div class="rt_content">
							<div class="rt_content_tt">
								<h2>填空题</h2>
								<p>
									<span>共</span><i class="content_lit">${blanksNum }</i><span>题</span>
								</p>
							</div>
							<div class="rt_content_nr answerSheet">
								<ul>
									<c:set value="1" var="blNum" />
									<c:forEach items="${blanks }" var="blanks">
										<li><a class="blankAn" href="#qu_3_${blNum }">${blNum }</a></li>
										<c:set value="${blNum+1 }" var="blNum" />
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:if>

					<c:if test="${subjectives!=null }">
						<div class="rt_content">
							<div class="rt_content_tt">
								<h2>主观题</h2>
								<p>
									<span>共</span><i class="content_lit">${subjectivesNum }</i><span>题</span>
								</p>
							</div>
							<div class="rt_content_nr answerSheet">
								<ul>
									<c:set value="1" var="subNum" />
									<c:forEach items="${subjectives }" var="subjectives">
										<li><a href="#qu_4_${subNum }">${subNum }</a></li>
										<c:set value="${subNum+1 }" var="subNum" />
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<!--nr end-->
	<div class="foot"></div>


	<div class="test_title">
		<font><input type="button" name="test_jiaojuan" value="交卷"
			onclick="tijiao()"></font>
	</div>
	<link href="${basePath}/css/main.css" rel="stylesheet" type="text/css" />
	<link href="${basePath}/css/test.css" rel="stylesheet" type="text/css" />
	<style>
.hasBeenAnswer {
	background: #5d9cec;
	color: #fff;
}
</style>
	<script src="${basePath}/js/jquery.easy-pie-chart.js"></script>
	<script>
		$(function() {
			$('li.option label').click(
					function() {
						;
						var examId = $(this).closest('.test_content_nr_main')
								.closest('li').attr('id'); // 得到题目ID
						var cardLi = $('a[href=#' + examId + ']'); // 根据题目ID找到对应答题卡
						// 设置已答题
						if (!cardLi.hasClass('hasBeenAnswer')) {
							cardLi.addClass('hasBeenAnswer');
						}
					});
		});

		function tijiao() {
			var single = $(".single");
			if (single != null) {
				single.each(function(index, element) {
							var num = 'singleChoice' + $(this).val();
							var ch = $('input:radio[name=' + num + ']:checked')
									.val();
							var an = $(this).attr("an");
							if (ch == an) {
								$(".singleAn").eq(index).css(
										"background-color", "green");
								$(".test_content_nr_tt").eq(index).css(
										"background-color", "#fff");
								$(".hiddensingle").eq(index).css(
										"background-color", "green");
							} else {
								$(".singleAn").eq(index).css(
										"background-color", "red");
								$(".test_content_nr_tt").eq(index).css(
										"background-color", "red");
								$(".hiddensingle").eq(index).css(
										"background-color", "red");
							}
						});
				$(".hiddensingle").css("display", "inline-block");
			}

			var multi = $(".multi");
			if (multi != null) {
				multi.each(function(index, element) {
					var num = 'multChoice' + $(this).val();
					var ch = "";
					$('input:checkbox[name=' + num + ']:checked').each(
							function() {
								ch += $(this).val();
							});
					var an = $(this).attr("an");
					if (ch == an) {
						$(".mulAn").eq(index).css("background-color", "green");
						$(".test_content_nr_tt").eq(index).css(
								"background-color", "#fff");
						$(".hiddenmul").eq(index).css("background-color",
								"green");
					} else {
						$(".mulAn").eq(index).css("background-color", "red");
						$(".test_content_nr_tt").eq(index).css(
								"background-color", "red");
						$(".hiddenmul").eq(index)
								.css("background-color", "red");
					}
				});
				$(".hiddenmul").css("display", "inline-block");
			}

			var judge = $(".judge");
			if (judge != null) {
				judge.each(function(index, element) {
					var num = 'judgeNum' + $(this).val();
					var ch = $('input:radio[name=' + num + ']:checked').val();
					var an = $(this).attr("an");
					if (ch == an) {
						$(".judgeAn").eq(index)
								.css("background-color", "green");
						$(".test_content_nr_tt").eq(index).css(
								"background-color", "#fff");
						$(".hiddenJudge").eq(index).css("background-color",
								"green");
					} else {
						$(".judgeAn").eq(index).css("background-color", "red");
						$(".test_content_nr_tt").eq(index).css(
								"background-color", "red");
						$(".hiddenJudge").eq(index).css("background-color",
								"red");
					}
				});
				$(".hiddenJudge").css("display", "inline-block");
			}

			var blank = $(".blank");
			if (blank != null) {
				blank.each(function(index, element) {
					var num = 'blankNum' + $(this).val();
					var ch = $('input:input[name=' + num + ']').val();
					var an = $(this).attr("an");
					if (ch == an) {
						$(".blankAn").eq(index)
								.css("background-color", "green");
						$(".test_content_nr_tt").eq(index).css(
								"background-color", "#fff");
						$(".hiddenBlank").eq(index).css("background-color",
								"green");
					} else {
						$(".blankAn").eq(index).css("background-color", "red");
						$(".test_content_nr_tt").eq(index).css(
								"background-color", "red");
						$(".hiddenBlank").eq(index).css("background-color",
								"red");
					}
				});
				$(".hiddenBlank").css("display", "inline-block");
			}

			var subjective = $(".subjective");
			if (subjective != null) {
				$(".hiddenSubjective").css("display", "inline-block");
			}

		}
	</script>
	<div
		style="text-align: center; margin: 50px 0; font: normal 14px/24px 'MicroSoft YaHei';">
	</div>
</body>
</html>
