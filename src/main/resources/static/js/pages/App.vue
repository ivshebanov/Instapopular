<template>
    <v-app>
        <div>
            <v-toolbar color="deep-purple accent-4" dark tabs>
                <v-toolbar-title>Instapopular</v-toolbar-title>

                <v-spacer></v-spacer>
                <span v-if="profile">Instagram: {{profile.instName}}</span>

                <v-spacer></v-spacer>
                <span v-if="profile">{{profile.usrname}}</span>

                <v-btn v-if="profile" icon href="/logout">
                    <v-icon>exit_to_app</v-icon>
                </v-btn>

                <v-btn v-if="!profile" icon @click="updateData(true)">
                    <v-icon>account_box</v-icon>
                </v-btn>


                <template v-if="profile" v-slot:extension>
                    <v-tabs v-model="tab" color="cyan" align-with-title>
                        <v-tabs-slider color="yellow"></v-tabs-slider>

                        <v-tab v-for="item in items" :key="item">
                            {{ item }}
                        </v-tab>
                    </v-tabs>
                </template>
            </v-toolbar>

            <v-tabs-items v-if="profile" v-model="tab">
                <v-tab-item v-for="item in items" :key="item">
                    <v-card flat>
                        <v-container v-if="item==='Главная'">
                            <info/>
                        </v-container>
                        <v-container v-if="item==='Группы'">
                            <keep-alive>
                                <component v-bind:is="getMyGroups"></component>
                            </keep-alive>
                            <groupe :group="group" :getMyGroups="getMyGroups"/>
                        </v-container>
                        <v-container v-if="item==='Хештеги'">
                            <hashtag/>
                        </v-container>
                        <v-container v-if="item==='Анализ'">
                            <analysis/>
                        </v-container>
                        <v-container v-if="item==='Отписка'">
                            <unsubscribe/>
                        </v-container>
                    </v-card>
                </v-tab-item>
            </v-tabs-items>
        </div>

        <v-content>
            <v-container v-if="isLogin">
                <login :updateData="updateData"/>
            </v-container>

            <v-container v-if="isRegistration">
                <registration :updateData="updateData"/>
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
    import MessagesList from '../components/messages/MessageList.vue'
    import Login from './login/login.vue'
    import Registration from './login/registration.vue'
    import Analysis from './actions/analysis.vue'
    import Info from "./actions/info.vue";
    import Groupe from "./groupe.vue";
    import Hashtag from "./actions/hashtag.vue";
    import Unsubscribe from "./actions/unsubscribe.vue";

    export default {
        components: {
            Unsubscribe,
            Hashtag,
            Groupe,
            Info,
            MessagesList,
            Login,
            Registration,
            Analysis
        },
        data() {
            return {
                messages: frontendData.messages,
                profile: frontendData.profile,
                isLogin: null,
                isRegistration: null,
                tab: null,
                items: [
                    'Главная', 'Группы', 'Хештеги', 'Анализ', 'Отписка'
                ],
                group: null
            }
        },
        methods: {
            getMyGroups() {
                this.$http.get('/group1').then(result => {
                        if (result.ok) {
                            result.json().then(data => {
                                this.group = data
                            })
                        }
                    })
            },
            updateData(isLogin, isRegistration, profile, messages) {
                this.isLogin = isLogin
                this.isRegistration = isRegistration
                this.profile = profile
                this.messages = messages
            }
        }
    }
</script>

<style scoped>

</style>