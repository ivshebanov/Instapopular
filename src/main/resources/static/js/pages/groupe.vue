<template>
    <v-content>
        <v-container>
            <messages-list :messages="group"
                           :deleteMessage="deleteGroupe"
                           :save="saveGroupe"/>
        </v-container>
    </v-content>
</template>

<script>
    import MessagesList from '../components/messages/MessageList.vue'

    export default {
        components: {
            MessagesList
        },
        props: ['group', 'getMyGroups'],
        data() {
            return {

            }
        },
        methods: {
            deleteGroupe(message) {
                this.$resource('/group1{/id}').remove({id: message.id}).then(result => {
                    if (result.ok) {
                        this.group.splice(this.group.indexOf(message), 1)
                    }
                })
            },
            saveGroupe(text, id) {
                const message = {'myGroup': text}

                var formData = new FormData();
                formData.append('myGroup', text);


                if (id) {
                    this.$resource('/group1{/id}').update({id: id}, {'myGroup': text}).then(result =>
                        result.json().then(data => {
                            const index = getIndex(this.group, data.id)
                            this.group.splice(index, 1, data)
                            text = ''
                            id = ''
                        })
                    )
                } else {
                    this.$resource('/group1{/id}').save({}, {'myGroup': text}).then(result =>
                        result.json().then(data => {
                            this.group.push(data)
                            text = ''
                        })
                    )

                    // this.$http.post('/group1', {'myGroup': text}).then(result => {
                    //         result.json().then(data => {
                    //             this.group.push(data)
                    //             text = ''
                    //             text = ''
                    //         })
                    //     }
                    // )
                }
            }
        }
    }
</script>

<style scoped>

</style>