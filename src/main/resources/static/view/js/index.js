// let layer;
// layui.use('layer', function(){
//     layer = layui.layer;
// });
//
// const vue1 = new Vue({
//     el: "#login_register_vue",
//     data:{
//
//     },
//     methods: {
//         login() {
//             layer.open({
//                 type: 1,
//                 title:'登录',
//                 content: $('#loginModal'),
//                 area:'650px',
//                 shade: [0.7]
//             })
//         },
//         register() {
//             layer.open({
//                 type: 1,
//                 title:'注册',
//                 content: $('#registerModal'),
//                 area:'650px',
//                 shade: [0.7]
//             })
//         }
//     }
//
// })
// const vue2 = new Vue({
//     el: "#loginModal",
//     data:{
//
//     },
//     methods: {
//         goToRegister(){
//             layer.closeAll('page');
//             layer.open({
//                 type: 1,
//                 title:'注册',
//                 content: $('#registerModal'),
//                 area:'650px',
//                 shade: [0.7]
//             })
//         }
//     }
//
// })
// const vue3 = new Vue({
//     el: "#registerModal",
//     data:{
//
//     },
//     methods: {
//         goToLogin(){
//             layer.closeAll('page');
//             layer.open({
//                 type: 1,
//                 title:'登录',
//                 content: $('#loginModal'),
//                 area:'650px',
//                 shade: [0.7]
//             })
//         }
//     }
//
// })
