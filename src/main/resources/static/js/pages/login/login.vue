<template>
    <v-app id="inspire">
        <v-container fluid fill-height>
            <v-layout align-center justify-center>
                <v-flex xs12 sm8 md4>
                    <v-card class="elevation-12">
                        <v-toolbar color="deep-purple accent-4" dark flat>
                            <v-toolbar-title>Войти</v-toolbar-title>
                            <v-spacer></v-spacer>
                            <v-tooltip bottom>
                                <template v-slot:activator="{ on }">
                                    <v-btn @click="registrationFunc"
                                           icon large target="_blank" v-on="on">
                                        <v-icon>fiber_new</v-icon>
                                    </v-btn>
                                </template>
                                <span>Регистрация</span>
                            </v-tooltip>
<!--                            <v-tooltip right>-->
<!--                                <template v-slot:activator="{ on }">-->
<!--                                    <v-btn icon large href="https://codepen.io/johnjleider/pen/pMvGQO" target="_blank" v-on="on">-->
<!--                                        <v-icon>mdi-codepen</v-icon>-->
<!--                                    </v-btn>-->
<!--                                </template>-->
<!--                                <span>Codepen</span>-->
<!--                            </v-tooltip>-->
                        </v-toolbar>

                        <v-form @submit.prevent="loginFunc" method="POST">
                            <v-card-text>
                                <v-text-field
                                        v-model="username"
                                        label="Логин"
                                        name="username"
                                        prepend-icon="person"
                                        type="text"
                                />

                                <v-text-field
                                        v-model="password"
                                        label="Пароль"
                                        name="password"
                                        prepend-icon="lock"
                                        type="password"
                                />
                            </v-card-text>
                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn type="submit" color="deep-purple accent-4" dark>Войти</v-btn>
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
            }
        },
        methods: {
            loginFunc() {
                var formData = new FormData();
                formData.append('username', this.username);
                formData.append('password', this.password);

                this.$http.post('/login', formData).then(result => {
                        if (result.ok) {
                            this.username = ''
                            this.password = ''
                            this.$resource('/group1/profile').get().then(data => {
                                data.json().then(inf => {
                                    this.updateData(false, false, inf.profile, inf.messages)
                                })
                            })
                        }
                    }
                )
            },
            registrationFunc() {
                this.updateData(false, true)
            }
        }
    }
</script>

<style>
</style>