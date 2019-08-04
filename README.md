# planet-service

## Create
````
POST: /rs/planets

Body:
{
    "name": "Tatooine",
    "climate": "arid",
    "terrain": "desert"
}

Success Response
Status: 200
Body:
{
    "id": "5d4704dd1fb7973104959988",
    "name": "Tatooine",
    "climate": "arid",
    "terrain": "desert",
    "appearances-quantity": 5
}

Error Responses
Status: 409
Body: 
{
    "code": 409001,
    "message": "Planet already registered"
}

Status: 400
Body:
{
    "code": 400002,
    "message": "It's not a Star Wars planet' "
}

````

## Update
````
PUT /rs/planets/{planet-id}
Body:
{
    "name": "Geonosis",
    "climate": "temperate, arid",
    "terrain": "rock, desert, mountain, barren",
    "appearances-quantity": 1
}

Success Response
Status: 204

Error Response
Status: 404
Body:
{
    "code": 404001,
    "message": "Planet not found"
}
````

## Delete Planet
````
DELETE: /rs/planets/{planet-id}

Success Response
Status: 204

Error Response
Status: 404
Body:
{
    "code": 404001,
    "message": "Planet not found"
}
````

## Get a Planet
````
GET /rs/planets/{planet-id} or /rs/planets;name={planet-name}

Sucess Response
Status: 200
Body:
{
    "id": "5c633aaa0db0365b7d0e000f",
    "name": "Bespin",
    "climate": "temperate",
    "terrain": "gas giant",
    "appearances-quantity": 1
}

Error Response
Status: 404
Body:
{
    "code": 404001,
    "message": "Planet not found"
}
````

## Get all Planets
````
GET /rs/planets
Header: origin | value: 'database' or 'swapi'

Response
Status: 200
Body: 
{
    [
        {
            "id": "5d47073d1fb7973c20d6392c",
            "name": "Tatooine",
            "climate": "arid",
            "terrain": "desert",
            "appearances-quantity": 5
        },
        {
            "id": "5d47073e1fb7973c20d6392d",
            "name": "Alderaan",
            "climate": "temperate",
            "terrain": "grasslands, mountains",
            "appearances-quantity": 2
        }
    ]
}
````