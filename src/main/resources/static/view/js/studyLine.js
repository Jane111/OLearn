const vue1 = new Vue({
    el: "#primaryLine",
    data:{
        primaryLine:[]
    },
    created(){
        this.showprimaryLine()
    },
    methods:{
        showprimaryLine(){
            this.$http.post(requestIp+'user/showMySchedule',{userId:1,clusterId:6,rank:0},{emulateJSON:true}).then((res)=>{
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
        moderateLine :[]
    },
    created(){
        this.showmoderateLine()
    },
    methods:{
        showmoderateLine(){
            this.$http.post(requestIp+'user/showMySchedule',{userId:1,clusterId:6,rank:1},{emulateJSON:true}).then((res)=>{
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
        seniorLine: []
    },
    created(){
        this.showseniorLine()
    },
    methods:{
        showseniorLine(){
            this.$http.post(requestIp+'user/showMySchedule',{userId:1,clusterId:6,rank:2},{emulateJSON:true}).then((res)=>{
                this.seniorLine = res.body.data
            },(res)=>{

            })
        },
        lighten(e){
            let moocId = e.target.dataset.moocid
            let sequence = e.target.dataset.sequence
            this.$http.post(requestIp+'user/setMyMoocPlan',{userId:1,moocId:moocId,sequence:sequence},{emulateJSON:true}).then((res)=>{
                this.showseniorLine()
            })
        }
    }
})
