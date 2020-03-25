$(function () {
    //失去焦点事件
    $("#loginnameId").blur(function () {
        let obj = $(this).val();
        getResult(obj);
    });

});

//发送ajax请求到后台
function getResult(obj) {
    //定义请求参数对象，[]为参数数组
    let params = {};
    $.ajax({
        type: "get",
        url: "/hanyi-daily/common",
        //传递的参数
        data: obj,
        success: function (data) {
            if (data.code === 200) {
                $.each(data.data, function (index, ele) {
                    if (index === 0) {
                        $("#loginnameId").val(ele.userName);
                    }
                    if (index === 1) {
                        $("#userNameId").val(ele.userName);
                    }
                });
            }
        },
        error: function (msg) {
            console.log(msg)
        }
    });

}

//确认框
function show_confirm() {
    let r = confirm("Press a button!");
    if (r === true) {
        alert("You pressed OK!");
    } else {
        alert("You pressed Cancel!");
    }
}

//提示框
function disp_prompt() {
    let name = prompt("请输入您的名字", "Bill Gates");
    if (name != null && name !== "") {
        document.write("你好！" + name + " 今天过得怎么样？")
    }
}

//location对象跳转地址
function local_href() {
    //解析浮点数
    parseFloat("1.23");
    //解析数组类型
    parseInt("123");
    location.href = "http://www.baidu.com"
}

//二级联动
let citys = [["昌平", "海淀", "东城", "西城"], ["吉林", "长春", "松原"], ["无锡", "南京", "苏州"]];

function selectCity(index) {
    let city = citys[index];

    // 1. 获取 cityid  , 城市下拉框.
    let $cityid = $("#cityId");
    // 2. 遍历数组 city

    /*
     *   jQuery创建标签 时 , $("<标签名></标签名>");
     */

    // 清空子标签
    $cityid.empty();

    // 添加第一个子标签
    $cityid.append($("<option>----请-选-择-市----</option>"))
    // 2. 遍历数组 city
    for (let ele of city) {
        // * 3. 添加子标签
        // * $("<option></option>") 创建子标签
        // * text 添加文本 . ele 表示 城市
        let $o = $("<option></option>").text(ele);
        $cityid.append($o);
    }
}