var examTime =0;
var time =examTime*60;
function timer() {
    // 计算小时
    var hour =  handlerTime(Math.floor(time/3600));
    // 计算分钟
    var minutes =  handlerTime(Math.floor(time%3600/60));
    // 计算秒
    var second =  handlerTime(Math.floor(time%3600%60));

    // 判断考试时间到自动提交试卷
    if (time == 0) {
        stu_submit_exam()
        clearInterval(timers);
    }

    $(".timer").html(hour+":"+minutes+":"+second);
    time--;
}

function handlerTime(t) {
    var t1 = t;
    if (t<10) {
        t1 = "0"+t;
    }

    return t1;
}

var timers = setInterval("timer();", 1000);