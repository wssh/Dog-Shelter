# Dog Shelter RESTful API
 Simple RESTful API, built with Spring Boot, that helps to store data about dogs in animal shelters. This RESTful API follows the HATEOAS principles where hyperlinks to available actions, based on the current state, are referenced in json representations.
 
Endpoint: `domain:port/dog`, e.g: `localhost:8090/dog`

# Mappings
### `/dog`

#### Method: `GET`
Gets a list of all dogs, regardless of their status, in the shelter database.

#### Example Response
```json
{
    "id": 1,
    "name": "Ruby",
    "breed": "French Bulldog",
    "age": "puppy",
    "gender": "male",
    "status": "AVAILABLE",
    "_links": {
        "self": {
            "href": "http://localhost:8090/dog/1"
        },
        "dogs": {
            "href": "http://localhost:8090/dog"
        },
        "hold": {
            "href": "http://localhost:8090/dog/1/hold"
        }
    }
```

#### Method: `POST`
Add a new dog to the database with the following parameters:
| Parameter | Description | Example |
| --------- | --------- | ---------|
| name | the name of the dog | `Ruby` |
| breed | breed of the dog | `French Bulldog` |
| age | age of the dog. i.e: puppy/young/adult/senior | `puppy` |
| gender | gender of the dog | `male` |

### `/dog/{id}`

#### Method: `GET`
Gets the data of one dog corresponding to the id registered in the database

#### Method: `DELETE`
Deletes the dog corresponding to the id in the database.

#### Method: `PUT`
Edit an existing dog with the corresponding id in the database. If the id of the dog doesn't exist, then add a new dog with the given parameters:
| Parameter | Description | Example |
| --------- | --------- | ---------|
| name | the name of the dog | `Buster` |
| breed | breed of the dog | `Boxer` |
| age | age of the dog. i.e: puppy/young/adult/senior | `adult` |
| gender | gender of the dog | `female` |

### `dog/{id}/adopt`

### Method: `PUT`
Changes the status of the dog corresponding to the id to the `ADOPTED` status. This hyperlink will dynamically display reference itself when the dog's `STATUS` is `AVAILABLE` or `ON_HOLD`. 

### `dog/{id}/hold`

### Method: `PUT`
Changes the status of the dog corresponding to the id from the `AVAILABLE` status to the `ON_HOLD` status. This hyperlink will dynamically display reference itself when the dog's `STATUS` is `AVAILABLE`.

### `/dog/{id}/cancel`

### Method: `DELETE`
Changes the status of the dog corresponding to the id from the `ON_HOLD` status to the `AVAILABLE` status. This hyperlink will dynamically display reference itself when the dog's `STATUS` is `ON_HOLD`

### `/dog/available`

#### Method: `GET`
Gets a list of all dogs that are in the AVAILABLE status.

### `/dog/foreverhomed`

#### Method: `GET`
Gets a list of all dogs that are in the ADOPTED status. 
