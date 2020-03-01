<template>
    <v-card class="ma-3 pa-6" outlined tile>
        <v-card-actions>
            <p class="font-italic">({{ message.id }}) </p>
            <p class="font-weight-medium"> {{ message.myGroup }}</p>
            <v-spacer></v-spacer>

            <v-btn icon @click="edit">
                <v-icon>edit</v-icon>
            </v-btn>
            <v-btn icon @click="del" small>
                <v-icon>delete</v-icon>
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    export default {
        props: ['message', 'editMessage', 'deleteMessage', 'messages'],
        methods: {
            edit() {
                this.editMessage(this.message)
            },
            del() {
                this.deleteMessage(this.message)
            }
        }
    }
</script>

<style>
</style>