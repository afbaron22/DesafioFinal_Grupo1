
![image](https://user-images.githubusercontent.com/82905697/122288452-7445ca80-ceb7-11eb-9b91-bd66cdcccef4.png)

## Authentication and Authorization procedure

1. Register. Endpoint: /register

```
{
    "username": "username1",
    "password": "admin",
    "role": "ROLE_REPRESENTATIVE",
    "idWarehouse": 1
}
```

Roles: {"ROLE_BUYER","ROLE_REPRESENTATIVE","ROLE_SELLER"}

2. Authenticate. Endopoint: /auth

```
{
    "username": "username1",
    "password": "admin",
    "idWarehouse": 1
}
```
Response:
```
{
"token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VybmFtZTEiLCJleHAiOjE2MjM3ODYzNjAsImlhdCI6MTYyMzc2ODM2MH0.VLLQzTmqLsgKvidB2ti5FvuIiSaQYaML1sZ7TqugPJC_dh8PELrrssT7OPF7CkJCeH4AGiegnCFUZgvjWNMu7A"
}
```

3. Copy obtained token and paste it on Authorization label with Authorization Type: "Bearer Token" on desired endpoint.

## H001 Endpoints
1. POST http://localhost:8080/api/v1/fresh-products/inboundorder
   Luego de logearse como un representante a un warehouse , se puede dar de alta una inboundOrder para la warehouse asociada.

```
{
    "inboundOrder":{
        "orderNumber": 1,
        "orderDate": "2020-06-11",
        "section": {
            "sectionCode": "FF",
            "warehouseCode": "2"
        },
        "batchStock": [
            {
                "batchNumber": "3",
                "productId": "2",
                "currentTemperature": 23,
                "minimunTemperature": 17,
                "initialQuantity": 20,
                "currentQuantity": 10,
                "manufacturingDate": "2018-02-27",
                "manufacturingTime": "2018-02-27 18:14:01",
                "dueDate": "2023-07-07"
            },
            {
                "batchNumber": "2",
                "productId": "2",
                "currentTemperature": 23,
                "minimunTemperature": 1,
                "initialQuantity": 5,
                "currentQuantity": 5,
                "manufacturingDate": "2021-06-10",
                "manufacturingTime": "2018-02-27 18:14:01",
                "dueDate": "2021-07-07"
            }
        ]
    }
}
```

2.  PUT http://localhost:8080/api/v1/fresh-products/inboundorder 
   En caso de necesitar editar una InboundOrder ya creada , se debe ajustar los campos necesarios y mandar nuevamente el cuerpo del request.
    
 ```
{
    "inboundOrder":{
        "orderNumber": 1,
        "orderDate": "2020-06-11",
        "section": {
            "sectionCode": "FF",
            "warehouseCode": "2"
        },
        "batchStock": [
            {
                "batchNumber": "3",
                "productId": "2",
                "currentTemperature": 23,
                "minimunTemperature": 17,
                "initialQuantity": 20,
                "currentQuantity": 10,
                "manufacturingDate": "2018-02-27",
                "manufacturingTime": "2018-02-27 18:14:01",
                "dueDate": "2023-07-07"
            },
            {
                "batchNumber": "2",
                "productId": "2",
                "currentTemperature": 23,
                "minimunTemperature": 1,
                "initialQuantity": 5,
                "currentQuantity": 5,
                "manufacturingDate": "2021-06-10",
                "manufacturingTime": "2018-02-27 18:14:01",
                "dueDate": "2021-07-07"
            }
        ]
    }
}
```
## H002 Endpoints
## H003 Endpoints
1. GET http://localhost:8080/api/v1/fresh-products/list?querytype=[ProductId]&ordBy=[orderType]
  Con este endpoint un representante puede consultar un producto en stock en el
warehouse para conocer su ubicación en un sector y los diferentes lotes en
donde se encuentre.Los posibles ordenamientos son:
Ver una lista de productos con todos
los lotes en donde aparezca.
Ordenados por por:
a. L = ordenado por lote
b. C = ordenado por cantidad actual
c. F = ordenado por fecha vencimiento

## H004 Endpoints
1. GET http://localhost:8080/api/v1/fresh-products/warehouse/querytype=[id product] 
Este endpoint permite que un representante pueda consultar un producto en todos los
warehouse para conocer el stock en cada warehouse de dicho producto

## H005 Endpoints
1. GET http://localhost:8080/api/v1/fresh-products/due-date/querytype=[cantidad días]
Este endpoint todos los lotesalmacenados en un sector de un depósito ordenados por su fecha
de vencimiento.
2. GET http://localhost:8080/api/v1/fresh-products/due-date/list?querytype=[cantidad días]querytype=[category]querytype=[asc]
Obtener una lista de los lotes ordenados por fecha de
vencimiento, que pertenecen a una determinada categoría de producto.
category:
a.FS=Fresco
b.RF=Refrigerado
c.FF=Congelado

## H006 Endpoints
Logeado como representante quiero poder ver de todos los warehouses cual es la mas vacia.
GET http://localhost:8080/api/v1/fresh-products/warehouseFewerItems
![image](https://user-images.githubusercontent.com/64280930/121260897-9025ec80-c877-11eb-86ff-02b6db9e6779.png)
