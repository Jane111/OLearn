// Vue.component('navigate-bar',{
//     data:function () {
//         return {
//             isActivity:false
//         }
//     },
//     template: `<div class="info-box-02">
//                 <div class="container">
//                 <div class="row">
//                 <div class="col-lg-12 text-center">
//                 <div class="main-nav__btn">
//                 <div class="icon-left"></div>
//                 <div class="icon-right"></div>
//                 </div>
//                 <div class="info-box-02__box-01">
//                 <div class="search-block">
//                 \t<button class="search-btn" v-on:click="showSearchInput">Search</button>
//                 \t<form action="" class="search-block__form">
//                 \t\t<input :class="['search-block__form-text', isActivity ? 'active':'']" type="text" name="search-name" placeholder="Search...">
//                 \t</form>
//                 </div>
//                 </div>
//                 <ul class="main-nav__list sf-js-enabled sf-arrows" style="touch-action: pan-y;">
//                 <li class="active">
//                 \t<!--
//                 \tclass="sf-with-ul"    下拉菜单
//                 \t-->
//                 <a href="#">首页</a>
//                 </li>
//                 <li class="">
//                 <a href="#">知识图谱</a>
//                 </li>
//                 <li class="">
//                 <a href="#">课程搜索</a>
//                 </li>
//                 <li>
//                 <a href="#">平台介绍</a>
//                 </li>
//                 <li>
//                 <a href="#">个人中心</a>
//                 </li>
//                 </ul>
//                 </div>
//                 </div>
//                 </div>
//                 </div>`,
//     methods:{
//         showSearchInput: function () {
//             if(this.isActivity){
//                 this.isActivity = false
//             }
//             else{
//                 this.isActivity = true
//             }
//
//         }
//     }
// })
let layer;
layui.use('layer', function(){
    layer = layui.layer;
});

const vue1 = new Vue({
    el: "#login_register_vue",
    data:{

    },
    methods: {
        login() {
            layer.open({
                type: 1,
                title:'登录',
                content: $('#loginModal'),
                area:'650px',
                shade: [0.7]
            })
        },
        register() {
            layer.open({
                type: 1,
                title:'注册',
                content: $('#registerModal'),
                area:'650px',
                shade: [0.7]
            })
        }
    }

})
const vue2 = new Vue({
    el: "#loginModal",
    data:{

    },
    methods: {
        goToRegister(){
            layer.closeAll('page');
            layer.open({
                type: 1,
                title:'注册',
                content: $('#registerModal'),
                area:'650px',
                shade: [0.7]
            })
        }
    }

})
const vue3 = new Vue({
    el: "#registerModal",
    data:{

    },
    methods: {
        goToLogin(){
            layer.closeAll('page');
            layer.open({
                type: 1,
                title:'登录',
                content: $('#loginModal'),
                area:'650px',
                shade: [0.7]
            })
        }
    }

})