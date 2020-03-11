<template>
    <div>
        <v-toolbar color="cyan" dark tabs>
            <v-toolbar-side-icon></v-toolbar-side-icon>

            <v-toolbar-title>Page title</v-toolbar-title>

            <v-spacer></v-spacer>

            <template v-slot:extension>
                <v-tabs v-model="tab" color="cyan" grow>
                    <v-tabs-slider color="yellow"></v-tabs-slider>

                    <v-tab v-for="item in items" :key="item">
                        {{ item }}
                    </v-tab>
                </v-tabs>
            </template>
        </v-toolbar>

        <v-tabs-items v-model="tab">
            <v-tab-item
                    v-for="item in items"
                    :key="item"
            >
                <v-card flat>
                    <v-card-text>{{ text }}</v-card-text>
                </v-card>
            </v-tab-item>
        </v-tabs-items>
    </div>
</template>

<script>
    export default {
        data () {
            return {
                tab: null,
                items: [
                    'web', 'shopping', 'videos', 'images', 'news'
                ],
                text: 'qw'
            }
        }
    }
</script>

<style scoped>

</style>