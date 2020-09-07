function jwt_config() {
    var user = JSON.parse(window.localStorage.getItem('user'));
    if (user) {
        const token = user.jwt;
        return {
            headers: {
                Authorization: 'Bearer ' + token,
            }
        };
    } else {
        return {};
    }
}
function splitLines(sMessage) {
    var retVal = '';
    var lines = sMessage.split('\n');
    for (var line of lines) {
        if (line !== '') {
            retVal += line;
        } else
            break;
        retVal += '<br />';
    }
    return retVal;
}


Vue.component("main-app", {
    template: ` 
    <div id="studenti">
    <input type="button" value="Logout" v-on:click="logout()" /><br/>
    <label>Id</label>
    <input type="text" v-model="selektovaniStudent.id" v-bind:disabled="mod !== 2"/>
    <br/>
    <label>Ime</label>
    <input type="text" v-model="selektovaniStudent.ime" v-bind:disabled="mod === 0"/>
    <br/>
    <label>Prezime</label>
    <input type="text" v-model="selektovaniStudent.prezime" v-bind:disabled="mod === 0"/>
    <br/>
    <label>Mesto rodjenja</label>
    <select v-model="selektovaniStudent.mestoRodjenja" v-bind:disabled="mod === 0">
        <option v-for="m in mesta" v-bind:value="m" v-bind:selected="m.id===selektovaniStudent.mestoRodjenja.id">{{m.naziv}}</option>
    </select>
    <br/>

    <button v-on:click="snimi()" v-bind:disabled="mod===0">Save</button>
    <button v-on:click="odustani()" v-bind:disabled="mod===0">Cancel</button>

    <table class="students">
        <tr>
            <th>id</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Birth Place</th> 
            <th></th>
        </tr>
        <tr v-for="s in studenti" v-on:click="select(s)" v-bind:class="{selected:selektovaniStudent.id===s.id}">
            <td>{{s.id}}</td>
            <td>{{s.ime}}</td>
            <td>{{s.prezime}}</td>
            <td>{{s.mestoRodjenja.naziv}}</td>
            <td><button v-on:click="obrisi(s)">Delete</button></td>
        </tr>
    </table>

    <button v-on:click="novi()" v-bind:disabled="mod != 0">New</button>
    <button v-on:click="edit()" v-bind:disabled="mod != 0 || !selektovaniStudent.id">Edit</button>

</div>
    `,
    data: function () {
        return {
            studenti: null,
            selektovaniStudent: { mestoRodjenja: { id: 0 } },
            mesta: null,
            mod: 0 // 0 browse, 1 edit existing, 2 insert new
        }
    },
    mounted() {
        var user = window.localStorage.getItem('user');
        if (user) {
            this.ucitajMesta();
            this.ucitaj(0, 0);
        } else {
            window.location = '#/login';
        }
    },
    methods: {
        select: function (s) {
            if (this.mod === 0) {
                this.selektovaniStudent = s;
            }
        },
        snimi: function () {
            if (this.mod === 2) {
                this.insert();
            }
            else {
                this.update();
            }
        },
        insert: function () {
            axios
                .post('rest/student/insert', this.selektovaniStudent, jwt_config())
                .then(response => {
                    toast('Student snimljen');
                    this.ucitaj(0, 2);
                }).catch(
                    error => {
                        console.log('Insert failed.');
                        toast('Insert failed: ' + splitLines(error.response.data.message), true);
                        //this.ucitaj(2, 2);
                    })
        },
        update: function () {
            axios
                .put('rest/student/update', this.selektovaniStudent, jwt_config())
                .then(response => {
                    toast('Student updated');
                    this.ucitaj(0, 1);
                }).catch(
                    error => {
                        console.log('Update failed.');
                        toast('Update failed: ' + splitLines(error.response.data.message), true);
                    })
        },
        obrisi: function (s) {
            if (confirm('Are you sure?')) {
                axios
                    .delete('rest/student/delete/' + s.id, jwt_config())
                    .then(response => {
                        this.ucitaj(0, 0);
                        toast('Student deleted: ' + s.ime + ' ' + s.prezime);
                    })
                    .catch(
                        error => {
                            console.log('Delete student failed.');
                            toast('Delete student failed: ' + splitLines(error.response.data.message), true);
                        })
            }
        },
        findSelektovaniStudent: function () {
            if (!this.selektovaniStudent.id)
                return { mestoRodjenja: this.mesta[0] };
            for (s of this.studenti) {
                if (s.id === this.selektovaniStudent.id) {
                    return s;
                }
            }
            return { mestoRodjenja: this.mesta[0] };
        },
        ucitaj: function (noviMod, tekuciMod) {
            axios
                .get('rest/student/getall', jwt_config())
                .then(response => {
                    this.studenti = response.data;
                    if (noviMod === 0 && tekuciMod === 0) {
                        this.selektovaniStudent = { mestoRodjenja: this.mesta[0] };
                    } else {
                        this.selektovaniStudent = this.findSelektovaniStudent();
                    }
                    this.mod = noviMod;
                })
                .catch(
                    error => {
                        console.log('Get all students failed.');
                        toast('Get all students failed: ' + error.response.data.message, true);
                    })
        },
        ucitajMesta: function () {
            axios
                .get('rest/mesto/getall', jwt_config())
                .then(response => this.mesta = response.data)
                .catch(
                    error => {
                        console.log('Get places failed.');
                        toast('Get all students failed: ' + error.response.data.message, true);
                    })
        },
        edit: function () {
            this.mod = 1;
        },
        novi: function () {
            this.selektovaniStudent = { mestoRodjenja: this.mesta[0] };
            this.mod = 2;
        },
        odustani: function () {
            this.ucitaj(0, this.selektovaniStudent);
        },
        logout: function () {
            window.localStorage.removeItem('user');
            window.location = '#/login';
        }
    }
});