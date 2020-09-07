const Login = { template: '<login></login>' }
const MainApp = { template: '<main-app></main-app>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: MainApp},
	    { path: '/login', component: Login }
	  ]
});

var app = new Vue({
	router, 
	el: "#test"
});

