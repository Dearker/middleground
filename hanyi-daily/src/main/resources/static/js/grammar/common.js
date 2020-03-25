$(function () {
    /**
     * 基本语法
     */
    //给input标签添加value属性
    $("#i1").val("哈士奇");

    //给input标签添加点击事件
    $("#i2").click(function () {
        alert("哈哈哈")
    });

    //修改div的样式属性
    $("#d1").mouseover(function () {
        $(this).css("background-color", "mediumvioletred")
            .css("width", "400px");
    });

    //获取张三
    //dom对象获取值,value属性, jQuery获取值val()函数，如果该函数中添加参数则为设置值
    alert($("#myinput").val());

    //获取哈士奇
    //dom对象使用属性innerText,jQuery使用的是text()函数,如果该函数中添加参数则为设置值
    //alert($("#mydiv").text("柯基")); //将该id标签的值修改为柯基
    alert($("#mydiv").text());

    //获取mydiv的后的所有内容(HTML内容)
    //dom对象使用属性innerHTML,jQuery使用的是html()函数，如果该函数中添加参数则为设置值
    alert($("#mydiv").html());


    //获取属性值
    console.log($("#bj").attr("name")); //beijing
    //设置北京节点的name属性的值为dabeijing
    $("#bj").attr("name","dabeijing");

    //新增北京节点的discription属性 属性值是didu
    $("#bj").attr("discription","didu");

    //删除北京节点的name属性并检验name属性是否存在
    $("#bj").removeAttr("name");

    //获取hobby的选中状态，prop函数适用于,固有属性
    for(let e of $("input[name='hobby']")){
        //alert($(e).prop("checked"));
    }

    //修改样式
    //<input type="button" value="采用属性增加样式(改变id=one的样式)" id="b1" />
    $("#c1").click(function() {
        $("#one").addClass("second");
    });

    //<input type="button" value="removeClass" id="b3" />
    $("#c3").click(function() {
        $("#one").removeClass();
    });

    //<input type="button" value=" 切换样式" id="b4" />
    $("#c4").click(function() {
        $("#one").toggleClass("second");
    });

    //<input type="button" value=" 通过css()获得id为one背景颜色" id="b5" />
    $("#c5").click(function() {
        alert($("#one").css("background-color"));
    });

    //<input type="button" value=" 通过css()设置id为one背景颜色为绿色" id="b6" />
    $("#c6").click(function() {
        $("#one").css("background-color", "green");
    });

    /**
     * 基本选择器
     */
    //<input type="button" value="改变 id 为 one 的元素的背景色为 红色"  id="b1"/>
    // 绑定事件
    $("#b1").click(function () {
        // 获取id 为one jQuery 对象, 通过css 方法 设置背景色
        $("#one").css("background-color", "red");
    });

    //<input type="button" value=" 改变元素名为 <div> 的所有元素的背景色为 红色"  id="b2"/>
    $("#b2").click(function () {
        // 获取div 元素 ,多个div元素,无需遍历, jQuery 处理
        $("div").css("background-color", "red");
    });

    //<input type="button" value=" 改变 class 为 mini 的所有元素的背景色为 红色"  id="b3"/>
    $("#b3").click(function () {
        // 获取 class 为mini的元素,多个mini元素无需遍历, jQuery处理
        $(".mini").css("background-color", "red");
    });

    //<input type="button" value=" 改变所有的<span>元素和 id 为 two 的元素的背景色为红色"  id="b4"/>
    $("#b4").click(function () {
        // 获取span 的元素,多个span元素,无需遍历, jQuery处理
        //  , 表示并列 .
        $("span , #two").css("background-color", "red");
    });

    /**
     * 属性选择器
     */
    //<input type="button" value=" 含有属性title 的div元素背景色为红色"  id="b1"/>
    $("#b1").click(function () {
        //  标签名[属性名]
        $("div[title]").css("background-color", "red");
    });

    //<input type="button" value=" 属性title值等于test的div元素背景色为红色"  id="b2"/>
    $("#b2").click(function () {
        //  标签名[属性名='属性值']
        $("div[title='test']").css("background-color", "red");
    });

    //<input type="button" value=" 属性title值不等于test的div元素(没有属性title的也将被选中)背景色为红色"  id="b3"/>
    $("#b3").click(function () {
        //  标签名[属性名!='属性值']  不等于 , 不含title 属性 ,也算是不等于.
        $("div[title!='test']").css("background-color", "red");
    });

    //<input type="button" value=" 属性title值 以te开始 的div元素背景色为红色"  id="b4"/>
    $("#b4").click(function () {
        //  标签名[属性名^='属性值']  ^=  以属性值开始.   理解为 是 string 的  startswith()
        $("div[title ^= 'te']").css("background-color", "red");
    });

    //<input type="button" value=" 属性title值 以est结束 的div元素背景色为红色"  id="b5"/>
    $("#b5").click(function () {
        //  标签名[属性名$='属性值']  $=  以属性值结束.   理解为 是 string 的  endswith()
        $("div[title $= 'est']").css("background-color", "red");
    });

    //<input type="button" value="属性title值 含有es的div元素背景色为红色"  id="b6"/>
    $("#b6").click(function () {
        //  标签名[属性名*='属性值']  *=  包含属性值.   理解为 是 string 的  contains()
        $("div[title *= '0']").css("background-color", "red");
    });

    //<input type="button" value="选取有属性id的div元素，然后在结果中选取属性title值含有“es”的 div 元素背景色为红色"  id="b7"/>
    $("#b7").click(function () {
        // 属性的并列的形式 ,表示 与 的逻辑 .
        $("div[id][title *= 'es']").css("background-color", "red");
    });

    /**
     * 层级选择器
     * 获得A元素内部的所有的B元素：$("A B ")
     * 获得A元素下面的所有的B子元素：$("A > B")
     * 获得A元素同级下一个B元素 ：$("A + B")
     * 获得A元素同级下所有后面B元素：$("A ~ B")
     */
    $("#bt1").click(
        function () {
            $("#a1 .B").css("background-color", "red");
        }
    );

    $("#bt2").click(
        function () {
            $("#a1 > .B").css("background-color", "red");
        }
    );

    $("#bt3").click(
        function () {
            $("#b1 + .B").css("background-color", "red");
        }
    );

    $("#bt4").click(
        function () {
            $("#b1 ~ .B").css("background-color", "red");
        }
    );

    $("#bt5").click(
        function () {
            $("div").css("background-color", "#9999CC");
        }
    );


    /**
     * 过滤选择器
     */
    //<input type="button" value=" 改变第一个 div 元素的背景色为 红色"  id="b1"/>
    $("#b1").click(function () {
        //  :first  , 表示首个
        $("div:first").css("background-color", "red");
    });

    //<input type="button" value=" 改变最后一个 div 元素的背景色为 红色"  id="b2"/>
    $("#b2").click(function () {
        //  :last  , 表示最后一个
        $("div:last").css("background-color", "red");
    });

    //<input type="button" value=" 改变class不为 one 的所有 div 元素的背景色为 红色"  id="b3"/>
    $("#b3").click(function () {
        //    :not(选择)  , 表示取反
        $("div:not(.one)").css("background-color", "red");
    });

    //<input type="button" value=" 改变索引值为偶数的 div 元素的背景色为 红色"  id="b4"/>
    $("#b4").click(function () {
        //偶数
        $("div:even").css("background-color", "red");
    });

    //<input type="button" value=" 改变索引值为奇数的 div 元素的背景色为 红色"  id="b5"/>
    $("#b5").click(function () {
        //    奇数
        $("div:odd").css("background-color", "green");
    });

    //<input type="button" value=" 改变索引值为大于 3 的 div 元素的背景色为 红色"  id="b6"/>
    //<input type="button" value=" 改变索引值为等于 3 的 div 元素的背景色为 红色"  id="b7"/>
    //<input type="button" value=" 改变索引值为小于 3 的 div 元素的背景色为 红色"  id="b8"/>

    $("#b6").click(function () {
        //    gt  >
        $("div:gt(3)").css("background-color", "green");
    });
    $("#b7").click(function () {
        //    eq  =
        $("div:eq(3)").css("background-color", "green");
    });

    $("#b8").click(function () {
        //    lt <
        $("div:lt(3)").css("background-color", "green");
    });

    //<input type="button" value=" 改变所有的标题元素的背景色为 红色"  id="b9"/>
    $("#b9").click(function () {
        $(":header").css("background-color", "green");
    });

    // <input type="button" value=" 改变当前正在执行动画的所有元素的背景色为 红色"  id="b10"/>
    $("#b10").click(function () {
        $(":animated").css("background-color", "green");
    });

    //  id 为 mover  的div  动起来 .  定时器
    setInterval(function () {
        $("#mover").fadeToggle("1000", function () {
            $(this).fadeToggle("1000");
        });
    }, 2000);

    /**
     * 表单属性过滤选择器
     */
    //		<input type="button" value=" 利用 jQuery 对象的 val() 方法改变表单内可用 <input> 元素的值"  id="b1"/>
    $("#b1").click(function () {
        $("input[type='text']:enabled").val("tom");
    });

    //<input type="button" value=" 利用 jQuery 对象的 val() 方法改变表单内不可用 <input> 元素的值"  id="b2"/>
    $("#b2").click(function () {
        $("input[type='text']:disabled").val("jerry");
    });

    // <input type="button" value=" 利用 jQuery 对象的 length 属性获取多选框选中的个数"  id="b3"/>
    $("#b3").click(function () {
        let len = $("input[type='checkbox']").length;
        alert(len);
    });

    //<input type="button" value=" 利用 jQuery 对象的 text() 方法获取下拉框选中的内容"  id="b4"/>
    $("#b4").click(function () {
        // alert( $("select option").text()) ;
        let $arr = $("select option");  // 获取 JQuery对象 , 本质是数组 .

        // 1. jQuery each() ,做循环
        /*
         *    each的参数: function (){}  , 表示循环要做什么 .
         *    function(index , ele) : 循环的索引  , js 对象
         */
        //$arr.each(  function(index , ele){
        //   alert(index  +"--" + ele.innerText);
        //});

        // 2. $.each()  , 做循环
        // $.each( $arr, function(index , ele){
        //    alert(index  +"--" + ele.innerText);
        // });

        //3. jQuery的增强for循环,需要高版本的jQuery才支持该语法
        for (let ele of $arr) {
            //alert(ele.innerText); //循环中的对象为js对象，不能使用jQuery的语法，innerText为获取属性
            alert($(ele).text());   //将js对象转换成jQuery对象，获取属性
        }
    });

});

// 全选和不选，this获取的是整合input标签的内容;checked属性表示选中或未选中
function selectAll(ele){
    $("input[type='checkbox']").prop("checked" , ele.checked);
}

//反选
function reverse(){
    $(".itemSelect").click();
}