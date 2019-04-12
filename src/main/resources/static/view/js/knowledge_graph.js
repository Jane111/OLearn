//拓扑
var timer = null
var network;
// 创建节点对象
var nodes;
// 创建连线对象
var edges;
// 已扩展的节点
var nodeExtendArr = new Array();

function drawGraph(areaId) {
    init();
    //修改初始缩放
    network.moveTo({scale: 0.6});
    //先初始化一个节点
    $.ajax({
        url:'/user/getMapByArea?areaId='+areaId,
        async:false,
        success: function(ret) {
            if(ret){
                createNetwork({nodes:ret.nodeList,edges:ret.edgeList});
            }else{
                layer.msg("查询失败");
            }
        }
    });
    network.on("click", function (params) {//单击进入该知识点的具体内容
//            params.event = "[original event]";
//            document.getElementById('eventSpan').innerHTML = '<h2>Click event:</h2>' + JSON.stringify(params, null, 4);
//            console.log('click event, getNodeAt returns: ' + this.getNodeAt(params.pointer.DOM));
        if(params.nodes.length>0){
            const pNameId = params.nodes[0];
            clearTimeout(timer)
            timer = setTimeout(function () {
                window.location.href="http://localhost:8080/view/page/chooseCourse.html?clusterId="+pNameId
            },300)
        }


    });
    network.on("doubleClick", function (params) {//双击将该知识点加入学习
//            params.event = "[original event]";
//            document.getElementById('eventSpan').innerHTML = '<h2>doubleClick event:</h2>' + JSON.stringify(params, null, 4);
//            console.log()
        if(params.nodes.length>0){
            clearTimeout(timer)
            var pNameId = params.nodes[0];
            $.ajax({
                url:'/user/getPreviousPoint?pNameId='+pNameId,
                async:false,
                success: function(ret) {
                    if(ret){
                        layer.confirm("加入学习前，请确定已经学习其前导内容："+ret, {
                                btn:["确认","取消"],
                                title:"提示"
                            },
                            function() {
                                $.ajax({
                                    url:'/user/addPointToUserMap?uId=1&pNameId='+pNameId,
                                    async:false,
                                    success: function(ret) {
                                        if(ret){
                                            layer.msg("加入学习成功");
                                        }else{
                                            layer.msg("查询失败");
                                        }
                                    }
                                });
                            }
                        )

                    }else{
                        layer.msg("查询失败");
                    }
                }
            });
        }


    });
};

function drawGraphByWork(workId) {
    init();
    //修改初始缩放
    network.moveTo({scale: 0.6});
    //先初始化一个节点
    $.ajax({
        url:'/user/getMapByJob?jId='+workId,
        async:false,
        success: function(ret) {
            if(ret){
                createNetwork({nodes:ret.nodeList,edges:ret.edgeList});
            }else{
                layer.msg("查询失败");
            }
        }
    });
    network.on("click", function (params) {//单击进入该知识点的具体内容
//            params.event = "[original event]";
//            document.getElementById('eventSpan').innerHTML = '<h2>Click event:</h2>' + JSON.stringify(params, null, 4);
//            console.log('click event, getNodeAt returns: ' + this.getNodeAt(params.pointer.DOM));
        if(params.nodes.length>0){
            const pNameId = params.nodes[0];
            console.log(params)
            clearTimeout(timer)
            timer = setTimeout(function () {
                window.location.href="http://localhost:8080/view/page/chooseCourse.html?clusterId="+pNameId
            },300)
        }

    });
    network.on("doubleClick", function (params) {//双击将该知识点加入学习
//            params.event = "[original event]";
//            document.getElementById('eventSpan').innerHTML = '<h2>doubleClick event:</h2>' + JSON.stringify(params, null, 4);
//            console.log()
        if(params.nodes.length>0){
            clearTimeout(timer)
            var pNameId = params.nodes[0];
            $.ajax({
                url:'/user/getPreviousPoint?pNameId='+pNameId,
                async:false,
                success: function(ret) {
                    if(ret){
                        layer.confirm("加入学习前，请确定已经学习其前导内容："+ret, {
                                btn:["确认","取消"],
                                title:"提示"
                            },
                            function() {
                                $.ajax({
                                    url:'/user/addPointToUserMap?uId=1&pNameId='+pNameId,
                                    async:false,
                                    success: function(ret) {
                                        if(ret){
                                            layer.msg("加入学习成功");
                                        }else{
                                            layer.msg("查询失败");
                                        }
                                    }
                                });
                            }
                        )

                    }else{
                        layer.msg("查询失败");
                    }
                }
            });
        }


    });
};

function init(){
    // 创建节点对象
    nodes = new vis.DataSet([]);
    // 创建连线对象
    edges = new vis.DataSet([]);
    // 创建一个网络拓扑图  不要使用jquery获取元素
    var container = document.getElementById('network_id');

    var data = {nodes: nodes, edges: edges};
    //全局设置，每个节点和关系的属性会覆盖全局设置
    var options = {
        //设置节点形状
        nodes:{
            shape: 'dot',//采用远点的形式
            size: 40,
            font:{
                face:'verdana',
                size:20//字体大小
            }
        },
        // 设置关系连线
        edges:{
            font:{
                face:'Microsoft YaHei'
            }
        },
        //设置节点的相互作用
        interaction: {
            //鼠标经过改变样式
            hover: true,
//                navigationButtons: true,
//                keyboard: true
            //设置禁止缩放
            //zoomView:false
        },
        //力导向图效果
        physics: {
            enabled: true,
            barnesHut: {
                gravitationalConstant: -4000,
                centralGravity: 0.3,
                springLength: 120,
                springConstant: 0.04,
                damping: 0.09,
                avoidOverlap: 0
            }
        }
//            layout: {//按树形结构显示
//                hierarchical: {
//                    direction: "LR",
//                    sortMethod: "hubsize"
//                }
//            }
    };
    network = new vis.Network(container, data, options);
}

//扩展节点 param nodes和relation集合
function createNetwork(param) {
    //可以试试注释掉去重的方法看看效果
    console.log(param.nodes);
    console.log(param.edges);
    if(param.nodes && param.nodes.length>0){
        //去除已存在的节点  以“id”属性为例删除重复节点，根据具体的属性自行修改
        for(var i in network.body.data.nodes._data){
            var nodeTemp = network.body.data.nodes._data[i];
            param.nodes = deleteValueFromArr(param.nodes,"nodeId",nodeTemp.id);
        }
        if(param.nodes && param.nodes.length>0){
            console.log("this is nodes")
            //添加节点
            for(var i=0;i<param.nodes.length;i++){
                var node = param.nodes[i];
                //控制背景色 不同类型的节点颜色不同
                var background = "red";
                //人
                if(node.nodeName && node.nodeName!="人工智能"){
                    background = "#A0CE4E";
                }
//                    //电影
//                    else if(node.title && node.title!=""){
//                        background = "#6DCE9E";
//                    }
                //拼接返回的结果显示在图上
                var label = "";
                for(var n in node){
                    if(n=="nodeName")
                    {
                        label += node[n];
                    }
                }
                nodes.add({
                    id: node.pNameId,//id为
                    label: label,
                    title:"双击加入学习",
                    color:{
                        background:background,
                        border:background,
                        hover:{
                            background:'#94c860',
                            border:"#A0CE4E"
                        },
                        highlight:{
                            background:'#94c860',
                            border:"#A0CE4E"
                        }
                    }
                });
            }
        }else{
            layer.msg("无扩展信息");
        }
    }
    if(param.edges && param.edges.length>0){
        //去除已存在的关系
        for(var i in network.body.data.edges._data){
            var edgeTemp = network.body.data.edges._data[i];
            param.edges = deleteValueFromArr(param.edges,"edgeId",edgeTemp.id);
        }
        if(param.edges && param.edges.length>0) {
            console.log("this is edges");
            //添加关系
            for (var i = 0; i < param.edges.length; i++) {
                var edge = param.edges[i];
                //拼接返回的结果显示在图上  根据数据库属性存储的格式进行调整
                var label = "";
                for(var n in edge.roles){
                    label += edge.roles[n] + " ";
                }
                edges.add({
                    id: edge.edgeId,
                    arrows: 'to',
                    from: edge.edgeFrom,
                    to: edge.edgeTo,
                    label: label,
                    font: {align: "middle"},
                    length: 150,
                    width:3,
                    color:{
                        color:"#A0CE4E",
                        highlight:"#94c860",
                        hover:"#94c860",
//                            opacity:0.3,
//                            inherit:'from'//颜色的继承
                    }
                });
            }
        }
    }
}

//根据对象组数中的某个属性值进行过滤删除
//arrName数组名  field过滤的字段   keyValue字段值
function deleteValueFromArr(arrName,field,keyValue){
    if(arrName==null || arrName.length==0){
        return null;
    }
    for (var i =0;i< arrName.length;i++){
        if(arrName[i][field]==keyValue){
            arrName.splice(i,1);
        }
    }
    return arrName;
}
const vue = new Vue({
    el: "#vue_box",
    data:{
        knowledgeLabels:[],
        jobLabels:[],
        doneFirstStep: false
    },
    created(){
        this.getKnowledgeLabels();

    },
    methods:{
        getKnowledgeLabels(){
            this.$http.post(requestIp+'mooc/showLabel',{},{emulateJSON:true}).then((result)=>{
                this.knowledgeLabels = result.body.data
                this.getJobLabels()
            },(result)=>{

            })
        },
        getJobLabels(){
            this.$http.post(requestIp+'work/showLabel',{},{emulateJSON:true}).then((result)=>{
                this.jobLabels = result.body.data
            },(result)=>{

            })
        },
        drawGraph(e){
            let areaId = e.target.dataset.areaid*1
            drawGraph(areaId)
            this.doneFirstStep = true
        },
        drawGraphByWork(e){
            let workId = e.target.dataset.workid*1
            drawGraphByWork(workId)
            this.doneFirstStep = true
        },
        backToChoose(){
            this.doneFirstStep = false
        }
    }
})