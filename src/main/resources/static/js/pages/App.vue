<template>
    <v-app>
        <v-app-bar app dense dark color="deep-purple accent-4" class="overflow-hidden">

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

        </v-app-bar>

        <v-content>

            <v-container v-if="isLogin">
                <login :updateData="updateData"/>
            </v-container>

            <v-container v-if="isRegistration">
                <registration :updateData="updateData"/>
            </v-container>

            <v-container v-if="profile">
                <messages-list :messages="messages"/>
            </v-container>

        </v-content>
    </v-app>
</template>
<script>
    import MessagesList from '../components/messages/MessageList.vue'
    import Login from './login/login.vue'
    import Registration from './login/registration.vue'

    export default {
        components: {
            MessagesList,
            Login,
            Registration
        },
        data() {
            return {
                messages: frontendData.messages,
                profile: frontendData.profile,
                isLogin: null,
                isRegistration: null
            }
        },
        methods: {
            updateData(isLogin, isRegistration, profile, messages) {
                this.isLogin = isLogin
                this.isRegistration = isRegistration
                this.profile = profile
                this.messages = messages
            }
        }
    }
</script>

<style>
</style>