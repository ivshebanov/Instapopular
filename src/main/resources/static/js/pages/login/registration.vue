<template>
    <v-app id="inspire">
        <v-container fluid fill-height>
            <v-layout align-center justify-center>
                <v-flex xs12 sm8 md4>
                    <v-card class="elevation-12">
                        <v-toolbar color="deep-purple accent-4" dark flat>
                            <v-toolbar-title>Регистрация</v-toolbar-title>
                            <v-spacer></v-spacer>
                            <v-tooltip bottom>
                                <template v-slot:activator="{ on }">
                                    <v-btn @click="loginFunc"
                                           icon large target="_blank" v-on="on">
                                        <v-icon>exit_to_app</v-icon>
                                    </v-btn>
                                </template>
                                <span>Войти</span>
                            </v-tooltip>
                        </v-toolbar>

                        <v-form @submit.prevent="registrationFunc" method="POST">
                            <v-card-text>
                                <v-text-field v-model="username"
                                              label="Логин"
                                              name="username"
                                              prepend-icon="person"
                                              type="text"/>

                                <v-text-field v-model="password"
                                              label="Пароль"
                                              name="password"
                                              prepend-icon="lock"
                                              type="password"/>

                                <v-text-field v-model="email"
                                              label="Почта"
                                              name="email"
                                              prepend-icon="mail"
                                              type="email"/>

                                <v-text-field v-model="instName"
                                              label="Логин от инстаграма"
                                              name="instName"
                                              prepend-icon="person"
                                              type="text"/>

                                <v-text-field v-model="instPassword"
                                              label="Пароль от инстаграма"
                                              name="instPassword"
                                              prepend-icon="lock"
                                              type="text"/>
                            </v-card-text>
                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn type="submit" color="deep-purple accent-4" dark>Зарегистрировать</v-btn>
                            </v-card-actions>
                        </v-form>

                    </v-card>
                </v-flex>
            </v-layout>
        </v-container>
    </v-app>
</template>

<script>
    export default {
        props: ['updateData'],
        data() {
            return {
                username: "",
                password: "",
                email: "",
                instName: "",
                instPassword: ""
            }
        },
        methods: {
            registrationFunc() {
                var formData = new FormData();
                formData.append('usrname', this.username);
                formData.append('password', this.password);
                formData.append('email', this.email);
                formData.append('instName', this.instName);
                formData.append('instPassword', this.instPassword);

                this.$http.post('/registration', formData).then(result => {
                        if (result.ok) {
                            this.username = '';
                            this.password = '';
                            this.email = '';
                            this.instName = '';
                            this.instPassword = '';

                            this.updateData(true, false)
                        }
                    }
                )
            },
            loginFunc() {
                this.updateData(true, false)
            }
        }
    }
</script>

<style scoped>

</style>