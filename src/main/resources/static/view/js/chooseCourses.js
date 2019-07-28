const vue = new Vue({
    el: '#vueBox',
    data:{
        options: {},
        courseList:[],
        primary_course: -1,
        moderate_course: -1,
        senior_course: -1
    },
    created(){
        this.options = this.getUrlOptions()
        this.getCourse()
    },
    methods:{
        addSeniorCourse(e){
            this.senior_course = e.target.dataset.courseindex * 1
            let index = e.target.dataset.courseindex * 1
            let moocId = this.courseList[index].columns.mooc_id
            this.$http.post(requestIp+'user/addCourseToPath',{userId:1, moocId:[moocId], clusterId:this.options.clusterId,theMinRank:2},{emulateJSON: true}).then((res)=>{

            },(res)=>{

            })
        },
        delSeniorCourse(){
            this.senior_course = -1
        },
        addModerateCourse(e){
            this.moderate_course = e.target.dataset.courseindex * 1
            let index = e.target.dataset.courseindex * 1
            let moocId = this.courseList[index].columns.mooc_id
            this.$http.post(requestIp+'user/addCourseToPath',{userId:1, moocId:[moocId], clusterId:this.options.clusterId, theMinRank:1},{emulateJSON: true}).then((res)=>{

            },(res)=>{

            })
        },
        delModerateCourse(){
            this.moderate_course = -1
        },
        addPrimaryCourse(e){
            this.primary_course = e.target.dataset.courseindex * 1
            let index = e.target.dataset.courseindex * 1
            let moocId = this.courseList[index].columns.mooc_id
            this.$http.post(requestIp+'user/addCourseToPath',{userId:1, moocId:[moocId], clusterId:this.options.clusterId, theMinRank:0},{emulateJSON: true}).then((res)=>{

            },(res)=>{

            })
        },
        delPrimaryCourse(){
            this.primary_course = -1
        },
        getUrlOptions(){
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
        },
        getCourse(){
            this.$http.post(requestIp+'mooc/showCourse',{clusterId: this.options.clusterId},{emulateJSON:true}).then((res)=>{
                this.courseList = res.body.data
            },(res)=>{

            })
        },
        showDetail(e){

            const index = e.target.dataset.courseindex *1
            console.log(index)
            vue_page.course_introduce = this.courseList[index].columns.introduce
            vue_page.course_title = this.courseList[index].columns.title
            layer.open({
                type: 1
                ,title: false //不显示标题栏
                ,closeBtn: false
                ,area: ['600px','700px']
                ,shade: 0.8
                ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
                ,btn: ['我知道了']
                ,btnAlign: 'c'
                ,moveType: 1 //拖拽模式，0或者1
                ,content: $("#courseDetail")
            });
        },
        addCourse(){

        }
    }
})

const vue_page = new Vue({
    el: "#courseDetail",
    data:{
        course_title:'',
        course_introduce:''
    }
})