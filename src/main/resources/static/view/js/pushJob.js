const vue = new Vue({
    el: "#job_vue_box",
    data:{
        labelList:[],
        jobList:[]
    },
    created(){
        this.getMyLabels()
    },
    methods:{
        getMyLabels(){
            this.$http.post(requestIp+"user/showMyLabel",{userId:1},{emulateJSON:true}).then((res)=>{
                this.labelList = res.body.data
                this.getMyJobs()
            },(res)=>{

            })
        },
        getMyJobs(){
            this.$http.post(requestIp+"work/workRecommend",{userId:1},{emulateJSON:true}).then((res)=>{
                this.jobList = res.body.data
            },(res)=>{

            })
        }
    }
})