const urlApi = {
    auth: {
        register: 'http://localhost:8080/api/v1/auth/register',
        login: 'http://localhost:8080/api/v1/auth/login',
        logout: 'http://localhost:8080/api/v1/auth/logout',
    },
    stakeholder: {
        list: 'http://localhost:8080/api/v1/stakeholder/fetch',
        detail: (id) => `http://localhost:8080/api/v1/stakeholder/${id}/detail`,
    }
}

export { urlApi }