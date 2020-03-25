$(function () {

    // 绑定多个事件 .
    $("#b1").on({
        "click": function () {
            alert("click!!!");
        },
        "mouseout": function () {
            alert("mouseout~~~~");
        }
    });

    // div 绑定两个事件 .
    $("div").on({
        "mouseover": function () {
            // 改变颜色
            $(this).css("background-color", "green");
        },
        "mouseout": function () {
            $(this).css("background-color", "yellow");
        },
        "click": function () {
            alert($(this).text());
        }
    });

    //设置子元素绑定事件
    $("#f").on("mouseover", ".z", function () {
        //   this 此时 表示当前子代元素.
        $(this).css("background-color", "green");
    });


});

//解绑事件 联动
function jiechu() {
    $("#f").off("mouseover");
}



