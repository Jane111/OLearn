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
const vue1 = new Vue({
    el: "#primaryLine",
    data:{
        primaryLine:[],
        clusterId: -1
    },
    created(){
        this.clusterId = getUrlOptions().clusterId
        this.showprimaryLine()
    },
    methods:{
        showprimaryLine(){
            this.$http.post(requestIp+'user/showMySchedule',{userId:1,clusterId:this.clusterId,rank:0},{emulateJSON:true}).then((res)=>{
                this.primaryLine = res.body.data
            },(res)=>{

            })
        },
        lighten(e){
            let moocId = e.target.dataset.moocid
            let sequence = e.target.dataset.sequence
            this.$http.post(requestIp+'user/setMyMoocPlan',{userId:1,moocId:moocId,sequence:sequence},{emulateJSON:true}).then((res)=>{
                this.showprimaryLine()
            })
        }
    }
})
const vue2 = new Vue({
    el: "#moderateLine",
    data:{
        moderateLine :[],
        clusterId: -1
    },
    created(){
        this.clusterId = getUrlOptions().clusterId
        this.showmoderateLine()
    },
    methods:{
        showmoderateLine(){
            this.$http.post(requestIp+'user/showMySchedule',{userId:1,clusterId:this.clusterId,rank:1},{emulateJSON:true}).then((res)=>{
                this.moderateLine = res.body.data
            },(res)=>{

            })
        },
        lighten(e){
            let moocId = e.target.dataset.moocid
            let sequence = e.target.dataset.sequence
            this.$http.post(requestIp+'user/setMyMoocPlan',{userId:1,moocId:moocId,sequence:sequence},{emulateJSON:true}).then((res)=>{
                this.showmoderateLine()
            })
        }
    }
})
const vue3 = new Vue({
    el: "#seniorLine",
    data:{
        seniorLine: [],
        clusterId: -1
    },
    created(){
        this.clusterId = getUrlOptions().clusterId
        this.showseniorLine()
    },
    methods:{
        showseniorLine(){
            this.$http.post(requestIp+'user/showMySchedule',{userId:1,clusterId:this.clusterId,rank:2},{emulateJSON:true}).then((res)=>{
                this.seniorLine = res.body.data
            },(res)=>{

            })
        },
        lighten(e){
            let moocId = e.target.dataset.moocid
            let sequence = e.target.dataset.sequence
            this.$http.post(requestIp+'user/setMyMoocPlan',{userId:1,moocId:moocId,sequence:sequence},{emulateJSON:true}).then((res)=>{
                this.$http.post(requestIp+'user/showMySchedule',{userId:1,clusterId:this.clusterId,rank:2},{emulateJSON:true}).then((res)=>{
                    this.seniorLine = res.body.data
                    if(sequence == this.seniorLine.length){
                        this.$http.post(requestIp+'user/setUserPointFinish',{pNameId:this.clusterId},{emulateJSON:true}).then((res)=>{

                        })
                    }
                },(res)=>{

                })
            })
        }
    }
})
