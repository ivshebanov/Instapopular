<template>
    <v-app>
        <v-app-bar app
                   color="deep-purple accent-4"
                   dense
                   dark
                   class="overflow-hidden">
            <v-toolbar-title>Instapopular</v-toolbar-title>
            <v-spacer></v-spacer>
            <span v-if="profile">Instagram: {{profile.instName}}</span>
            <v-spacer></v-spacer>
            <span v-if="profile">{{profile.usrname}}</span>
            <v-btn v-if="profile" icon href="/logout">
                <v-icon>exit_to_app</v-icon>
            </v-btn>
        </v-app-bar>

        <v-content>
            <v-container v-if="!profile">
                <a href="/login">Необходимо авторизоваться</a>
            </v-container>
            <v-container v-if="profile">
                <messages-list :messages="messages"/>
            </v-container>
        </v-content>
    </v-app>
</template>
<script>
    import MessagesList from '../components/messages/MessageList.vue'

    export default {
        components: {
            MessagesList
        },
        data() {
            return {
                messages: frontendData.messages,
                profile: frontendData.profile
            }
        }
    }
</script>

<style>
</style>