# DesafioFinal_Grupo1
![image](https://user-images.githubusercontent.com/64280930/121260897-9025ec80-c877-11eb-86ff-02b6db9e6779.png)

## Authentication and Authorization procedure

1. Register. Endpoint: /register

```
{
    "username": "username1",
    "password": "admin",
    "role": "ROLE_REPRESENTATIVE"
}
```

Roles: {"ROLE_BUYER","ROLE_REPRESENTATIVE","ROLE_SELLER"}

2. Authenticate. Endopoint: /auth

```
{
    "username": "username1",
    "password": "admin"
}
```
Response:
```
{
"token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VybmFtZTEiLCJleHAiOjE2MjM3ODYzNjAsImlhdCI6MTYyMzc2ODM2MH0.VLLQzTmqLsgKvidB2ti5FvuIiSaQYaML1sZ7TqugPJC_dh8PELrrssT7OPF7CkJCeH4AGiegnCFUZgvjWNMu7A"
}
```

3. Copy obtained token and paste it on Authorization label with Authorization Type: "Bearer Token" on desired endpoint.
