const vue = new Vue({
    el: '#vue_box',
    data:{
        inputCourse: '',
        courseList:[],
        genreList:[]
    },
    created(){

        this.$http.post(requestIp+'mooc/moocSearch',{keyword:' '},{emulateJSON:true}).then((res)=>{
            this.courseList = res.body.data
            this.getAllGenre()
        },(res)=>{

        })
    },
    methods:{
        searchCourse(){
            console.log(555)
            if(this.inputCourse!=''){
                this.$http.post(requestIp+'mooc/moocSearch',{keyword:this.inputCourse},{emulateJSON:true}).then((res)=>{
                    this.courseList = res.body.data
                },(res)=>{

                })
            }
        },
        getAllGenre(){//获取分类
            this.$http.post(requestIp+'mooc/getKnowledge',{},{emulateJSON:true}).then((res)=>{
                this.genreList = res.body.data
            },(res)=>{

            })
        },
        getAllCourse(){
            this.$http.post(requestIp+'mooc/moocSearch',{keyword:''},{emulateJSON:true}).then((res)=>{
                this.courseList = res.body.data
            },(res)=>{

            })
        },
        searchByGenre(e){//根据分类查询课程
            let genreId = e.target.dataset.genreid
            this.$http.post(requestIp+'mooc/getCourseByKnowledge',{clusterId:genreId},{emulateJSON:true}).then((res)=>{
                this.courseList = res.body.data
            },(res)=>{

            })
        },
        getDetail(e){
            let mooc_id = e.target.dataset.moocid
            console.log(mooc_id)
            window.open("../page/single_course.html?moocid="+mooc_id);
        }
    }
})