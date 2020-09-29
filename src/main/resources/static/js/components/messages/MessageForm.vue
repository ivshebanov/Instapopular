<template>
    <v-layout row>
        <v-text-field label="New message" placeholder="Write something" v-model="text"/>
        <v-btn @click="sav">
            Save
        </v-btn>
    </v-layout>
</template>

<script>
    function getIndex(list, id) {
        for (var i = 0; i < list.length; i++) {
            if (list[i].id === id) {
                return i
            }
        }
        return -1
    }

    export default {
        props: ['messages', 'messageAttr', 'save'],
        data() {
            return {
                text: '',
                id: ''
            }
        },
        watch: {
            messageAttr(newVal, oldVal) {
                this.text = newVal.myGroup
                this.id = newVal.id
            }
        },
        methods: {
            sav() {
                this.save(this.text, this.id)
            }
        }
    }
</script>

<style>
</style>