<template>
    <v-layout column>
        <message-form :messages="messages"
                      :messageAttr="message"
                      :save="save"/>
        <message-row v-for="message in messages"
                     :key="message.id"
                     :message="message"
                     :editMessage="editMessage"
                     :deleteMessage="deleteMessage"
                     :messages="messages"/>
    </v-layout>
</template>

<script>
    import MessageRow from 'components/messages/MessageRow.vue'
    import MessageForm from 'components/messages/MessageForm.vue'

    export default {
        props: ['messages', 'deleteMessage', 'save'],
        components: {
            MessageRow,
            MessageForm
        },
        data() {
            return {
                message: null
            }
        },
        computed: {
            sortedMessages() {
                return this.messages.sort((a, b) => -(a.id - b.id))
            }
        },
        methods: {
            editMessage(message) {
                this.message = message
            }
        }
    }
</script>

<style>
</style>