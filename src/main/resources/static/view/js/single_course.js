const vue = new Vue({
    el: "#vue_box",
    data:{
        options: {},
        title_url:'',
        quto:'',
        people:'',
        title:'',
        imgUrl:'',
        institute:'',
        introduce:'',
        professor:'',
        activate0:true,
        activate1:false,
        activate2:false,
        menuList:[]
    },
    created(){
        this.options = this.getUrlOptions()
        this.getDetail()
    },
    methods:{
        change(e){
            let index = e.target.dataset.activateindex*1
            this.activate0=false
            this.activate1=false
            this.activate2=false
            if(index==0){
                this.activate0=true
            }
            else if(index==1){
                this.activate1=true
            }
            else{
                this.activate2=true
            }
        },
        getUrlOptions() {
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
        getDetail(){
            this.$http.post(requestIp+'mooc/moocDetail',{moocId:this.options.moocid},{emulateJSON:true}).then((res)=>{
                this.title_url = res.body.data.columns.title_url
                this.quto = res.body.data.columns.quto,
                this.people = res.body.data.columns.people,
                this.title = res.body.data.columns.title,
                this.imgUrl = res.body.data.columns.picture,
                this.institute = res.body.data.columns.institute,
                this.introduce = res.body.data.columns.introduce,
                this.professor = res.body.data.columns.professor
                this.$http.post(requestIp+'mooc/moocMenu',{moocId:this.options.moocid},{emulateJSON:true}).then((res)=>{
                    this.menuList = res.body.data
                },(res)=>{

                })
            },(res)=>{

            })
        }
    }
})