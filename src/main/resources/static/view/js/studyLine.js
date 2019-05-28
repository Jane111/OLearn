function getUrlOptions() {
    let url = location.search; //获取url中"?"符后的字串
    let theRequest = new Object();
    if (url.indexOf("?") != -1) {
        let str = url.substr(1);
        let strs = str.split("&");
        for (let i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = decodeURIComponent(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}
let layer;
layui.use('layer', function(){
    layer = layui.layer;
});
// const vue1 = new Vue({
//     el: "#primaryLine",
//     data:{
//         primaryLine:[],
//         clusterId: -1
//     },
//     created(){
//         this.clusterId = getUrlOptions().clusterId
//         this.showprimaryLine()
//     },
//     methods:{
//         showprimaryLine(){
//             this.$http.post(requestIp+'user/showMySchedule',{userId:1,clusterId:this.clusterId,rank:0},{emulateJSON:true}).then((res)=>{
//                 this.primaryLine = res.body.data
//             },(res)=>{
//
//             })
//         },
//         lighten(e){
//             let moocId = e.target.dataset.moocid
//             let sequence = e.target.dataset.sequence
//             this.$http.post(requestIp+'user/setMyMoocPlan',{userId:1,moocId:moocId,sequence:sequence},{emulateJSON:true}).then((res)=>{
//                 this.showprimaryLine()
//             })
//         }
//     }
// })
// const vue2 = new Vue({
//     el: "#moderateLine",
//     data:{
//         moderateLine :[],
//         clusterId: -1
//     },
//     created(){
//         this.clusterId = getUrlOptions().clusterId
//         this.showmoderateLine()
//     },
//     methods:{
//         showmoderateLine(){
//             this.$http.post(requestIp+'user/showMySchedule',{userId:1,clusterId:this.clusterId,rank:1},{emulateJSON:true}).then((res)=>{
//                 this.moderateLine = res.body.data
//             },(res)=>{
//
//             })
//         },
//         lighten(e){
//             let moocId = e.target.dataset.moocid
//             let sequence = e.target.dataset.sequence
//             this.$http.post(requestIp+'user/setMyMoocPlan',{userId:1,moocId:moocId,sequence:sequence},{emulateJSON:true}).then((res)=>{
//                 this.showmoderateLine()
//             })
//         }
//     }
// })
// const vue3 = new Vue({
//     el: "#seniorLine",
//     data:{
//         seniorLine: [],
//         clusterId: -1
//     },
//     created(){
//         this.clusterId = getUrlOptions().clusterId
//         this.showseniorLine()
//     },
//     methods:{
//         showseniorLine(){
//             this.$http.post(requestIp+'user/showMySchedule',{userId:1,clusterId:this.clusterId,rank:2},{emulateJSON:true}).then((res)=>{
//                 this.seniorLine = res.body.data
//             },(res)=>{
//
//             })
//         },
//         lighten(e){
//             let moocId = e.target.dataset.moocid
//             let sequence = e.target.dataset.sequence
//             this.$http.post(requestIp+'user/setMyMoocPlan',{userId:1,moocId:moocId,sequence:sequence},{emulateJSON:true}).then((res)=>{
//                 this.$http.post(requestIp+'user/showMySchedule',{userId:1,clusterId:this.clusterId,rank:2},{emulateJSON:true}).then((res)=>{
//                     this.seniorLine = res.body.data
//                     if(sequence == this.seniorLine.length){
//                         this.$http.post(requestIp+'user/setUserPointFinish',{pNameId:this.clusterId},{emulateJSON:true}).then((res)=>{
//
//                         })
//                     }
//                 },(res)=>{
//
//                 })
//             })
//         }
//     }
// })
// const vue4 = new Vue({
//     el: "#studyNote",
//     data:{
//
//     },
//     methods:{
//         addNote(){
//             layer.open({
//                 type: 1,
//                 title:'学习笔记',
//                 content: $('#noteModal'),
//                 area:'400px',
//                 shade: [0.7]
//             })
//         }
//     }
// })
//
// const vue5 = new Vue({
//     el:"#editNoteModal",
//     date:{
//         noEdit: true
//     },
//     methods:{
//         goEdit(){
//             this.noEdit = false
//         },
//         cancel(){
//             this.noEdit = true
//         }
//     }
// })

//***************************knowledgeGraph
//拓扑
var network;
// 创建节点对象
var nodes;
// 创建连线对象
var edges;
// 已扩展的节点
var nodeExtendArr = new Array();
let timer = null
function drawGraphUser(userObj,elementId) {
    let userObjs = userObj
    initTag(elementId);
    //修改初始缩放
    network.moveTo({scale: 0.6});
    //先初始化一个节点
    $.ajax({
        url:'/user/getMapByAreaUser?areaId='+userObj.areaId + '&uId='+userObj.uId,
        async:false,
        success: function(ret) {
            if(ret){
                createNetworkTag({nodes:ret.nodeList,edges:ret.edgeList});
            }else{
                layer.msg("查询失败");
            }
        }
    });
    network.on("click", function (params) {//单击进入该知识点的具体内容
//            params.event = "[original event]";
//            document.getElementById('eventSpan').innerHTML = '<h2>Click event:</h2>' + JSON.stringify(params, null, 4);
//            console.log('click event, getNodeAt returns: ' + this.getNodeAt(params.pointer.DOM));

        if(params.nodes.length>0&&params.nodes[0]<10000){
            const pNameId = params.nodes[0];
            clearTimeout(timer)
            timer = setTimeout(function () {
                window.location.href="/view/page/studyLine.html?clusterId="+pNameId
            },300)
        }


    });
    network.on("doubleClick", function (params) {//双击将该知识点加入学习
//            params.event = "[original event]";
//            document.getElementById('eventSpan').innerHTML = '<h2>doubleClick event:</h2>' + JSON.stringify(params, null, 4);
//            console.log()
        if(params.nodes.length>0&&params.nodes[0]<10000){
            clearTimeout(timer)
            var pNameId = params.nodes[0];
            $.ajax({
                url:'/user/getUserPreviousPoint?pNameId='+pNameId,
                async:false,
                success: function(ret) {
                    if(ret){
                        layer.confirm("您还未完成前导知识："+ret+"暂时无法加入学习",{
                                btn:["我知道了"],
                                title:"提示"
                            }
                        )
                    }else{
                        $.ajax({
                            url:'/user/addPointToUserMap?uId=1&pNameId='+pNameId,
                            async:false,
                            success: function(ret) {
                                if(ret){
                                    layer.msg("加入学习成功");
                                    drawGraphUser(userObjs)
                                }else{
                                    layer.msg("查询失败");
                                }
                            }
                        });
                    }
                }
            });
        }


    });
};

function initTag(elementId){
    // 创建节点对象
    nodes = new vis.DataSet([]);
    // 创建连线对象
    edges = new vis.DataSet([]);
    // 创建一个网络拓扑图  不要使用jquery获取元素
    var container = document.getElementById(elementId.id);
    // 符号表示说明
    var x = -container.clientWidth/1.5;
    var y = -550;
    var step = 150;
    nodes.add({id: 10000, x: x, y: y, label: '学习完成', group: 'finished', value: 1, fixed: true, physics:false});
    nodes.add({id: 10001, x: x + 1*step, y: y , label: '正在学习', group: 'learning', value: 1, fixed: true,  physics:false});
    nodes.add({id: 10002, x: x + 2*step, y: y , label: '推荐学习', group: 're', value: 1, fixed: true,  physics:false});
    nodes.add({id: 10003, x: x + 3*step, y: y, label: '未学习', group: 'not', value: 1, fixed: true,  physics:false});

    var data = {nodes: nodes, edges: edges};
    //全局设置，每个节点和关系的属性会覆盖全局设置
    var options = {
        //设置节点形状
        nodes:{
            shape: 'dot',//采用远点的形式
            size: 25,
            font:{
                face:'verdana',
                size:25//字体大小
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
        },
        groups: {
            finished: {
                shape: 'dot',
                color: '#FF9900' // orange
            },
            learning: {
                shape: 'dot',
                color: "#FF4040" //
            },
            re: {
                shape: 'dot',
                color: "#A4D3EE" //
            },
            not: {
                shape: 'dot',
                color: "#C4C4C4" //
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
function createNetworkTag(param) {
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
                //
                var pointGroup = "not"

                if(node.nodeStatus==0){//学习完成
                    pointGroup = "finished";
                }
                if(node.nodeStatus && node.nodeStatus==1){//正在学习
                    pointGroup = "learning";
                }
                if(node.nodeStatus && node.nodeStatus==2){//推荐学习
                    pointGroup = "re";
                }
                if(node.nodeStatus && node.nodeStatus==3){//未学习
                    pointGroup = "not";
                }
//                 //人
//                 if(node.nodeName && node.nodeName!="人工智能"){
//                     background = "#A0CE4E";
//                 }
// //                    //电影
// //                    else if(node.title && node.title!=""){
// //                        background = "#6DCE9E";
// //                    }
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
                    group:pointGroup,
                    title:"双击加入学习",
                    // color:{
                    //     background:background,
                    //     border:background,
                    //     hover:{
                    //         background:'#94c860',
                    //         border:"#A0CE4E"
                    //     },z
                    //     highlight:{
                    //         background:'#94c860',
                    //         border:"#A0CE4E"
                    //     }
                    // }
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
                        // color:"#A0CE4E",
                        // highlight:"#94c860",
                        // hover:"#94c860",
//                            opacity:0.3,
                        inherit:'both'//颜色的继承
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
//***************************knowledgeGraph

const mainVue = new Vue({
    el: "#vueBlock",
    created(){
        this.clusterId = getUrlOptions().clusterId
        this.showseniorLine()
        this.showmoderateLine()
        this.showprimaryLine()
    },
    data:{
        seniorLine: [],
        moderateLine :[],
        primaryLine:[],
        noEdit: true,
        showNote:true,
        showPrimary:false,
        showModerate: false,
        showSenior: false,
        exercise:[{topic:'面向对象的三大特性是',answer:'封装、继承、多态'},{topic:'JAVA中的输出方式是',answer:'system.out.println()'}],
        exerciseIndex: 0,
        isAnswerShow: false,
        showThisNote:{
            display:'none'
        },
        isShow:true,
        isShow2: true,
        index:0,
        sign:false
    },
    methods:{
        setSign(){
            layer.msg("系统处理中，请稍后")
            setTimeout(function () {
                drawGraphUser({areaId:1,uId:1},{id:'network_id'})
            },2000)
            this.sign=true
        },
        saveNote(){
            layer.closeAll("page")
            if(this.index==0){
                this.isShow = false
                this.index++
            }
            else{
                this.isShow2 = false
            }
            this.sign = false
        },
        closeAnalysis(){
            layer.closeAll("page")
        },
        showMePrimary(){
            this.showPrimary = true
            this.showModerate = false
            this.showSenior = false
            this.showNote = false
        },
        showMeModerate(){
            this.showPrimary = false
            this.showModerate = true
            this.showSenior = false
            this.showNote = false
        },
        showMeSenior(){
            this.showPrimary = false
            this.showModerate = false
            this.showSenior = true
            this.showNote = false
        },
        showMeNote(){
            this.showPrimary = false
            this.showModerate = false
            this.showSenior = false
            this.showNote = true
        },
        showprimaryLine(){
            this.$http.post(requestIp+'user/showMySchedule',{userId:1,clusterId:this.clusterId,rank:0},{emulateJSON:true}).then((res)=>{
                this.primaryLine = res.body.data
            },(res)=>{

            })
        },
        showmoderateLine(){
            this.$http.post(requestIp+'user/showMySchedule',{userId:1,clusterId:this.clusterId,rank:1},{emulateJSON:true}).then((res)=>{
                this.moderateLine = res.body.data
            },(res)=>{

            })
        },
        showseniorLine(){
            this.$http.post(requestIp+'user/showMySchedule',{userId:1,clusterId:this.clusterId,rank:2},{emulateJSON:true}).then((res)=>{
                this.seniorLine = res.body.data
            },(res)=>{

            })
        },
        lighten(e){
            let moocId = e.target.dataset.moocid
            let sequence = e.target.dataset.sequence
            let rank = e.target.dataset.rank
            console.log(rank)
            this.$http.post(requestIp+'user/setMyMoocPlan',{userId:1,moocId:moocId,sequence:sequence},{emulateJSON:true}).then((res)=>{
                if(rank==0){
                    this.showprimaryLine()
                }
                else if(rank==1){
                    this.showmoderateLine()
                }
                else{
                    this.$http.post(requestIp+'user/showMySchedule',{userId:1,clusterId:this.clusterId,rank:2},{emulateJSON:true}).then((res)=>{
                        this.seniorLine = res.body.data
                        if(sequence == this.seniorLine.length){
                            this.$http.post(requestIp+'user/setUserPointFinish',{pNameId:this.clusterId},{emulateJSON:true}).then((res)=>{

                            })
                        }
                    },(res)=>{

                    })
                }
            })
        },
        addNote(){
            // layer.open({
            //     type: 1,
            //     title:'学习笔记',
            //     content: $('#noteModal'),
            //     area:'400px',
            //     shade: [0.7]
            // })
            $('#network_id').empty()
            layer.open({
                type: 1,
                title:'新建卡片',
                content: $('#card_analysis'),
                area:['800px','754px'],
                shade: [0.7],
                end: function () {
                    mainVue.sign = false
                }
            })
        },
        getDetail(){
            $('#network_id_edit').empty()
            layer.open({
                type: 1,
                title:'编辑卡片',
                content: $('#cardEdit_analysis'),
                area:['800px','754px'],
                shade: [0.7],
                success:function () {
                    layer.msg("系统处理中，请稍后")
                    drawGraphUser({areaId:1,uId:1},{id:'network_id_edit'})
                }
            })
        },
        goEdit(){
            this.noEdit = false
        },
        cancel(){
            this.noEdit = true
        },
        showExercise(e){
            this.isAnswerShow = false
            layer.open({
                type: 1,
                title:'自我测试',
                content: $('#exerciseModal'),
                area:'500px',
                shade: [0.7]
            })
        },
        showAnswer(){
            this.isAnswerShow = true
        },
        lastTopic(){
            this.isAnswerShow = false
            this.exerciseIndex--
        },
        nextTopic(){
            this.isAnswerShow = false
            this.exerciseIndex++
        }
}
})