import {createRouter, createWebHistory} from "vue-router";
import Pages from "../views/pages/Pages.vue";
import Home from "../views/pages/Home.vue";

const routes = [
    {
        path: '/',
        name: 'pages',
        component: Pages,
        children: [
            {
              path: '/',
              redirect: '/home'
            },
            {
              path: '/home',
              name: 'home',
              component: Home,
            },
            {
                path: '/warung',
                name: 'warung',
                component: () => import('../views/pages/Warung.vue'),
            },
        ]
    },
]

export const router = createRouter({
    history: createWebHistory(),
    routes: routes
});