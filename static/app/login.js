Vue.component("login", {
    template: ` 
	<form name="login_form">
		<label>Username:</label> 
		<input type="text" v-model="user.username" name="username" /> <br /> 
		<label>Password:</label>
		<input type="password" v-model="user.password" name="password" />
		<br />
		<input type="button" v-on:click="login(user)" value="Login"/>
    </form> 
    `,
    data: function () {
        return {
            user: { username: '', password: '' }
        };
    },
    methods: {
        login(user) {
            axios
                .post('rest/user/login', user)
                .then(response => {
                    window.localStorage.setItem('user', JSON.stringify(response.data));
                    window.location = '#/';
                }).catch(
                    error => {
                        console.log('Login failed.');
                        toast('Login failed: ' + error.response.data.message, true);
                    })
        }
    }
});