# TURING HEALTH
## Aplikasi Pengelolaan Kesehatan Pribadi


***

### ALAN PRATAMA RUSFI (MANAGER 1)
![img.png](img.png)

### AXEL BARLIAN (MANAGER 2)
![img_1.png](img_1.png)

### TOMMY HARYANTO (MANAGER 3)
![img_2.png](img_2.png)

### IRSYAD FAUZI ADIARSA (MANAGER 4)
![img_3.png](img_3.png)

## Main Feature
### Doctor Service
* Consult with the doctor in real-time, access by rile member and doctor
* Choose doctor
* Choose consultation date
* Get a link for consultation via online like (whatsapp, telegram, zoom, google meet, etc)
* Medicine recipes, information and education from doctor itself
* Get information and education you need
* Doc AI (Frontend)
* Emergency Button (Frontend)
* Health Article

### Buy Product
* Canceled: When the buyer cancels the transaction.
* Pending: When the buyer has made a transaction but has not paid yet.
* Packed: When the buyer has paid the bill, and the admin packs the order.
* Sent: When the admin sends the order to the buyer’s address.
* Accepted: When the buyer confirms the product has been received.

### Hospital Book
* Users can view a list of hospitals filtered by closest location and obtain their map locations.
* Users can access the contact information of active hospitals.

#### Minor Feature
* Authentication (login and register)
* Update profile 
* Change password

#### Future Feature
* MAKE PREMIUM CONSULTATION

## How to run this project

Go to src/main/resources/application.properties and copy paste below replace {{something}} with your config in your
database

```text
# Spring Config
server.port=8080

# Database Config
spring.datasource.username={{dbUsername}}
spring.datasource.password={{dbPassword}}
spring.datasource.url=jdbc:postgresql://localhost:5432/{{dbName}}

# JPA Config
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

# API Reference
***
## AUTHENTICATION
### Register 
````
POST: /api/auth/register
````
###### Request:
````JSON
{
  "name": "Irsyad Fauzi",
  "phone": "0888877776655",
  "email": "irsyad@gmail.com",
  "password": "tomiss"
}
````

###### Response:
````JSON
{
    "message": "Register Successfully!",
    "status": "OK",
    "data": {
        "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiTUVNQkVSIiwic3ViIjoiaXJzeWFkQGdtYWlsLmNvbSIsImlhdCI6MTcyMjUyMTE2MCwiZXhwIjoxNzIyNjA3NTYwfQ._gZmnO8KnhmXnaybzQdjMbeCl7f3Gjncni6j0F2k3rk",
        "refresh_token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiTUVNQkVSIiwic3ViIjoiaXJzeWFkQGdtYWlsLmNvbSIsImlhdCI6MTcyMjUyMTE2MCwiZXhwIjoxNzIzMTI1OTYwfQ.CSHysHuynX67SoPKD5Ro7rjrAuSMgOisTcgoY89Eokg"
    }
}
````

### Login
````
POST: /api/auth/login
````
###### Request:
````JSON
{
  "email": "irsyad@gmail.com",
  "password": "tomiss"
}
````

###### Response:
````JSON
{
  "message": "Login Successfully!",
  "status": "OK",
  "data": {
    "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiTUVNQkVSIiwic3ViIjoiaXJzeWFkQGdtYWlsLmNvbSIsImlhdCI6MTcyMjUyMTIxNSwiZXhwIjoxNzIyNjA3NjE1fQ.W4dqZcnXVJOBZR75pvck7GOR61NSgkjgyM9720VayIc",
    "refresh_token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiTUVNQkVSIiwic3ViIjoiaXJzeWFkQGdtYWlsLmNvbSIsImlhdCI6MTcyMjUyMTIxNSwiZXhwIjoxNzIzMTI2MDE1fQ.iDmGUJFp2mOBNSOeF0Hu_vYsTrJAv-ya5EgsrOFelPc"
  }
}
````

***


## PROFILE
### Get Info Profile
````
GET: /api/profile
````

###### Response:
````JSON
{
  "message": "Get Info Profile Successfully!",
  "status": "OK",
  "data": {
    "id": 9,
    "name": "Irsyad Fauzi",
    "nik": null,
    "phone": "0888877776655",
    "address": null,
    "email": "irsyad@gmail.com",
    "imageLink": null,
    "role": "MEMBER",
    "region": null
  }
}
````

### Update Info Profile
````
PUT: /api/profile
````
###### Request:
![img_5.png](img_5.png)

###### Response:
````JSON
{
  "message": "Profile Updated Successfully!",
  "status": "OK",
  "data": {
    "id": 9,
    "name": "Irsyad F.",
    "nik": "317503091200055",
    "phone": "085817000942",
    "address": "Jl. Kemana aja la",
    "email": "irsyad@gmail.com",
    "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722521758/profileIrsyad%20F._e6eee9a3-1075-4152-bd3f-1299d54eb826.gif",
    "role": "MEMBER",
    "region": {
      "id": 1,
      "name": "KOTA BANDA ACEH, ACEH"
    }
  }
}
````

### Profile Change Password
````
PATCH: /api/profile/change-password
````
###### Request:
````JSON
{
    "oldPassword": "tomiss",
    "newPassword": "irsyad",
    "confirmPassword": "irsyad"
}
````

###### Response:
````JSON
{
    "message": "Password Changed Successfuly!",
    "status": "OK",
    "data": {
        "id": 9,
        "name": "Irsyad F.",
        "nik": "317503091200055",
        "phone": "085817000942",
        "address": "Jl. Kemana aja la",
        "email": "irsyad@gmail.com",
        "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722521758/profileIrsyad%20F._e6eee9a3-1075-4152-bd3f-1299d54eb826.gif",
        "role": "MEMBER",
        "region": {
            "id": 1,
            "name": "KOTA BANDA ACEH, ACEH"
        }
    }
}
````


## ADDRESS USER
### Create Address User
````
POST: /api/address
````

###### Request:
````JSON
{
    "buyerName": "Axel",
    "buyerPhone": "0858288483",
    "city": "Jakarta Timur",
    "posCode": "1340",
    "addressDetail": "Jl. Turing Lalala",
    "fixPoint": "Samping enigma",
    "type": "House",
    "message": "hati hati di jalan"

}
````

###### Response:
````JSON
{
    "message": "Address Created Successfully!",
    "status": "OK",
    "data": {
        "id": 8,
        "buyerName": "Axel",
        "buyerPhone": "0858288483",
        "city": "Jakarta Timur",
        "posCode": "1340",
        "addressDetail": "Jl. Turing Lalala",
        "fixPoint": "Samping enigma",
        "type": "House",
        "message": "hati hati di jalan"
    }
}
````

### Update Info User
````
PUT: /api/address/{id}
````
###### Request:
````JSON
{
    "buyerName": "Alan Turing",
    "buyerPhone": "08582884831",
    "city": "Jakarta Timur1",
    "region": "DKI Jakarta1",
    "posCode": "13401",
    "addressDetail": "Jl. Turing Lalala1",
    "fixPoint": "Samping enigma1",
    "type": "House1",
    "message": "hati hati di jalan1"
}
````

###### Response:
````JSON
{
    "message": "Address Updated Successfully!",
    "status": "OK",
    "data": {
        "id": 4,
        "buyerName": "Alan Turing",
        "buyerPhone": "08582884831",
        "city": "Jakarta Timur1",
        "posCode": "13401",
        "addressDetail": "Jl. Turing Lalala1",
        "fixPoint": "Samping enigma1",
        "type": "House1",
        "message": "hati hati di jalan1"
    }
}
````

### Get Address User
````
GET: /api/address
````

###### Response:
````JSON
{
    "message": "Address Fetched Successfully!",
    "status": "OK",
    "data": [
        {
            "id": 8,
            "buyerName": "Axel",
            "buyerPhone": "0858288483",
            "city": "Jakarta Timur",
            "posCode": "1340",
            "addressDetail": "Jl. Turing Lalala",
            "fixPoint": "Samping enigma",
            "type": "House",
            "message": "hati hati di jalan"
        },
        {
            "id": 9,
            "buyerName": "Irsyad",
            "buyerPhone": "0858288483",
            "city": "Jakarta Selatan",
            "posCode": "1340",
            "addressDetail": "Jl. Semangka Lalala",
            "fixPoint": "Samping enigma",
            "type": "House",
            "message": "hati hati di jalan"
        }
    ]
}
````


### Get One Address User
````
GET: /api/address/{id}
````

###### Response:
````JSON
{
    "message": "Address Fetched Successfully!",
    "status": "OK",
    "data": {
        "id": 8,
        "buyerName": "Axel",
        "buyerPhone": "0858288483",
        "city": "Jakarta Timur",
        "posCode": "1340",
        "addressDetail": "Jl. Turing Lalala",
        "fixPoint": "Samping enigma",
        "type": "House",
        "message": "hati hati di jalan"
    }
}
````


### Delete One Address User
````
DELETE: /api/address/{id}
````

###### Response:
````JSON
{
  "message": "Address Deleted Successfully!",
  "status": "OK",
  "data": null
}
````

***

## Consultation
### Start Consultation
#### Only MEMBER
```
POST /api/consultations/start
```

###### Request :
```JSON
{
  "consultationDate": "2024-08-07",
  "doctorId": 8
}
```

###### Response :
```JSON
{
  "message": "Consultation Created Successfully! Wait For Doctor To Accept Your Consultation!",
  "status": "OK",
  "data": {
    "id": 3,
    "consultationDate": "2024-08-07T00:00:00.000+00:00",
    "consultationUrl": null,
    "accepted": false,
    "member": {
      "name": "Irsyad F.",
      "userImageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722521758/profileIrsyad%20F._e6eee9a3-1075-4152-bd3f-1299d54eb826.gif",
      "role": "MEMBER"
    },
    "doctor": {
      "name": "Alex Pratama",
      "userImageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722511300/profileAlex%20Pratama_fc037fe4-a178-4b68-9e21-f6475dc2b3a6.png",
      "role": "DOCTOR"
    }
  }
}
```

### Accept Consultation
#### Only DOCTOR
```
POST /api/consultations/accept-consultation/{id}
```

###### Request :
```JSON
{
  "consultationUrl": "https://alanpratama.com"
}
```

###### Response :
```JSON
{
  "message": "Consultation Created Successfully! Wait For Doctor To Accept Your Consultation!",
  "status": "OK",
  "data": {
    "id": 3,
    "consultationDate": "2024-08-07",
    "consultationUrl": "https://alanpratama.com",
    "accepted": true,
    "member": {
      "name": "Irsyad F.",
      "userImageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722521758/profileIrsyad%20F._e6eee9a3-1075-4152-bd3f-1299d54eb826.gif",
      "role": "MEMBER"
    },
    "doctor": {
      "name": "Alex Pratama",
      "userImageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722511300/profileAlex%20Pratama_fc037fe4-a178-4b68-9e21-f6475dc2b3a6.png",
      "role": "DOCTOR"
    }
  }
}
```

### Get All Consultations
#### Only MEMBER and DOCTOR
```
GET /api/consultations
```

##### Response :
```JSON
{
    "message": "Consultation Fetched Successfully!",
    "status": "OK",
    "data": [
        {
            "id": 3,
            "consultationDate": "2024-08-07",
            "consultationUrl": "https://alanpratama.com",
            "accepted": true,
            "member": {
                "name": "Irsyad F.",
                "userImageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722521758/profileIrsyad%20F._e6eee9a3-1075-4152-bd3f-1299d54eb826.gif",
                "role": "MEMBER"
            },
            "doctor": {
                "name": "Alex Pratama",
                "userImageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722511300/profileAlex%20Pratama_fc037fe4-a178-4b68-9e21-f6475dc2b3a6.png",
                "role": "DOCTOR"
            }
        }
    ]
}
```

***
## Hospitals
### Get All Hospitals
```
GET /api/hospitals/all
```

#### Response
```JSON
{
    "message": "Got All Hospitals",
    "status": "FOUND",
    "data": {
        "content": [
            {
                "id": 1,
                "name": "RS UMUM DAERAH  DR. ZAINOEL ABIDIN",
                "address": "JL. TGK DAUD BEUREUEH, NO. 108 B. ACEH",
                "region": {
                    "id": 1,
                    "name": "KOTA BANDA ACEH, ACEH"
                },
                "phone": "(0651) 34565",
                "province": "Aceh",
                "gmap": "https://www.google.com/maps/place?q=RS UMUM DAERAH  DR. ZAINOEL ABIDIN"
            },
            {
                "id": 2,
                "name": "RS UMUM DAERAH CUT MEUTIA KAB. ACEH UTARA",
                "address": "JL. BANDA ACEH-MEDAN KM.6 BUKET RATA LHOKSEUMAWE",
                "region": {
                    "id": 2,
                    "name": "KOTA LHOKSEUMAWE, ACEH"
                },
                "phone": "(0645) 46334",
                "province": "Aceh",
                "gmap": "https://www.google.com/maps/place?q=RS UMUM DAERAH CUT MEUTIA KAB. ACEH UTARA"
            },
            {
                "id": 3,
                "name": "RSUP SANGLAH",
                "address": "JL. DIPONEGORO DENPASAR BALI",
                "region": {
                    "id": 3,
                    "name": "KOTA DENPASAR, BALI"
                },
                "phone": "(0361) 227912",
                "province": "Bali",
                "gmap": "https://www.google.com/maps/place?q=RSUP SANGLAH"
            }
        ],
        "totalElements": 132,
        "size": 3,
        "totalPage": 44,
        "page": 0
    }
}
```

### Get One Hospital
```
GET /api/hospitals/{id}
```

#### Response
```JSON
{
  "message": "Hospital found",
  "status": "FOUND",
  "data": {
    "id": 69,
    "name": "RS UMUM DAERAH DR. KANUJOSO DJATIWIBOWO",
    "address": "JL. M.T. HARYONO RING ROAD BALIKPAPAN NO.656",
    "phone": "(0542) 873901",
    "province": "Kalimantan Timur",
    "gmap": "https://www.google.com/maps/place?q=RS UMUM DAERAH DR. KANUJOSO DJATIWIBOWO",
    "region": {
      "id": 60,
      "name": "KOTA BALIKPAPAN, KALIMANTAN TIMUR"
    }
  }
}
```

### Create Hospital
#### Only ADMIN
```
POST /api/hospitals/create
```

#### Request
```JSON
{
    "name": "RS Test",
    "address": "Jl Melati 59",
    "regionId": 1,
    "phone": "099999999",
    "province": "Banda Aceh"
}
```

#### Response
```JSON
{
    "message": "Created Hospital",
    "status": "CREATED",
    "data": {
        "id": 135,
        "name": "RS Test",
        "address": "Jl Meati 59",
        "phone": "099999999",
        "province": "Banda Aceh",
        "gmap": "https://www.google.com/maps/place?q=RS Test",
        "region": {
            "id": 1,
            "name": "KOTA BANDA ACEH, ACEH"
        }
    }
}
```

### Update Hospital
#### Only ADMIN
```
PUT /api/hospitals/{id}
```

#### Request
```JSON
{
    "name": "RS MANA AJA",
    "address": "Jl Melati 0",
    "regionId": 2,
    "phone": "099999991",
    "province": "Bandar Gacor"
}
```

#### Response
```JSON
{
    "message": "Hospital Updated",
    "status": "OK",
    "data": {
        "id": 135,
        "name": "RS MANA AJA",
        "address": "Jl Melati 0",
        "phone": "099999991",
        "province": "Bandar Gacor",
        "gmap": "https://www.google.com/maps/place?q=RS MANA AJA",
        "region": {
            "id": 2,
            "name": "KOTA LHOKSEUMAWE, ACEH"
        }
    }
}
```

### Delete Hospital
#### Only ADMIN
```
DELETE /api/hospitals/{id}
```

#### Response
```JSON
{
    "message": "Hospital Deleted",
    "status": "OK",
    "data": null
}
```

***

## Category
### Get All Category
```             
GET /api/categories
```

#### Response
```JSON
{
  "message": "Category Fetched Successfully!",
  "status": "OK",
  "data": {
    "content": [
      {
        "id": 1,
        "category": "Health Drink"
      },
      {
        "id": 4,
        "category": "Tablet"
      },
      {
        "id": 5,
        "category": "Drops"
      }
    ],
    "totalElements": 3,
    "size": 15,
    "totalPage": 1,
    "page": 0
  }
}
```

### Get One Category
```
GET /api/categories/{id}
```

#### Response
```JSON
{
    "message": "Category Fetched Successfully!",
    "status": "OK",
    "data": {
        "id": 1,
        "category": "Health Drink"
    }
}
```

### Create Category
#### Only ADMIN
```
POST /api/categories/
```

#### Request
```JSON
{
    "category": "Vitamins"
}
```

#### Response
```JSON
{
    "message": "Category Created Successfully!",
    "status": "OK",
    "data": {
        "id": 6,
        "category": "Vitamins"
    }
}
```

### Update Category
#### Only ADMIN
```
PUT /api/categories/{id}
```

#### Request
```JSON
{
    "category": "Suplement"
}
```

#### Response
```JSON
{
    "message": "Category Updated Successfully!",
    "status": "OK",
    "data": {
        "id": 5,
        "category": "Suplement"
    }
}
```

### Delete Category
#### Only ADMIN
```
DELETE /api/categories/{id}
```

#### Response
```JSON
{
    "message": "Category Deleted Successfully!",
    "status": "OK",
    "data": null
}
```

***

## Product
### Get All Products
```
GET /api/products
```

#### Response
```JSON
{
  "message": "Product Fetched Successfully!",
  "status": "OK",
  "data": {
    "content": [
      {
        "id": 1,
        "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437103/profileObat%20Pelangsing_6368f368-8c6c-4fa1-a2ff-b6ac43c444e0.jpg",
        "name": "Obat Pelangsing",
        "price": 50000,
        "description": "Ini sangat mantap looo",
        "available": true,
        "category": {
          "id": 1,
          "category": "Health Drink"
        }
      },
      {
        "id": 5,
        "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437807/profilePanadol%20Lalala_a8e81cc8-7761-4315-8ff7-69881a15e63b.png",
        "name": "Panadol Lalala",
        "price": 20000,
        "description": "Ini sangat mantap looo",
        "available": true,
        "category": {
          "id": 1,
          "category": "Health Drink"
        }
      }
    ],
    "totalElements": 2,
    "size": 20,
    "totalPage": 1,
    "page": 0
  }
}
```

### Get One Product
```
GET /api/products/{id}
```

#### Response
```JSON
{
    "message": "Product Fetched Successfully!",
    "status": "OK",
    "data": {
        "id": 1,
        "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437103/profileObat%20Pelangsing_6368f368-8c6c-4fa1-a2ff-b6ac43c444e0.jpg",
        "name": "Obat Pelangsing",
        "price": 50000,
        "description": "Ini sangat mantap looo",
        "available": true,
        "category": {
            "id": 1,
            "category": "Health Drink"
        }
    }
}
```

### Create Product
#### Only ADMIN
```
POST /api/products/
```

#### Request
![img_6.png](img_6.png)

#### Response
```JSON
{
    "message": "Product Created Successfully!",
    "status": "OK",
    "data": {
        "id": 8,
        "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722525933/profilePainKiller_5fad29eb-b96b-483b-bb0b-60c50c6b12a3.gif",
        "name": "PainKiller",
        "price": 20000,
        "description": "So good",
        "available": true,
        "category": {
            "id": 4,
            "category": "Tablet"
        }
    }
}
```

### Update Product
#### Only ADMIN
```
PUT /api/products/{id}
```

#### Request
![img_7.png](img_7.png)

#### Response
```JSON
{
    "message": "Product Updated Successfully!",
    "status": "OK",
    "data": {
        "id": 5,
        "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722526201/profileCounter%20Bacteria_bf273241-a5d1-4bb7-9a31-9952f0482045.gif",
        "name": "Counter Bacteria",
        "price": 20000,
        "description": "It works",
        "available": true,
        "category": {
            "id": 1,
            "category": "Health Drink"
        }
    }
}
```

### Delete Product
```
DELETE /api/products/{id}
```

#### Response
```JSON
{
    "message": "Product Deleted Successfully!",
    "status": "OK",
    "data": null
}
```

***

## Transaction
### Get All Transaction
```
GET /api/transactions
```

#### Response
```JSON
{
    "message": "Transaction Fetched Successfully!",
    "status": "OK",
    "data": [
        {
            "id": 5,
            "user": {
                "name": "Lalalan1",
                "userImageLink": null,
                "role": "DOCTOR"
            },
            "addressUser": {
                "id": 4,
                "buyerName": "Alan Turing",
                "buyerPhone": "08582884831",
                "city": "Jakarta Timur1",
                "posCode": "13401",
                "addressDetail": "Jl. Turing Lalala1",
                "fixPoint": "Samping enigma1",
                "type": "House1",
                "message": "hati hati di jalan1"
            },
            "total": 150000,
            "message": "minta yang terbaik",
            "paymentType": "bca",
            "resiCode": null,
            "vaNumber": "51327206119",
            "expiryTime": "2024-08-02 16:47:09",
            "status": "CANCELED",
            "createdAt": "2024-08-01",
            "products": [
                {
                    "id": 1,
                    "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437103/profileObat%20Pelangsing_6368f368-8c6c-4fa1-a2ff-b6ac43c444e0.jpg",
                    "name": "Obat Pelangsing",
                    "price": 50000,
                    "description": "Ini sangat mantap looo",
                    "available": true,
                    "category": {
                        "id": 1,
                        "category": "Health Drink"
                    }
                },
                {
                    "id": 1,
                    "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437103/profileObat%20Pelangsing_6368f368-8c6c-4fa1-a2ff-b6ac43c444e0.jpg",
                    "name": "Obat Pelangsing",
                    "price": 50000,
                    "description": "Ini sangat mantap looo",
                    "available": true,
                    "category": {
                        "id": 1,
                        "category": "Health Drink"
                    }
                },
                {
                    "id": 1,
                    "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437103/profileObat%20Pelangsing_6368f368-8c6c-4fa1-a2ff-b6ac43c444e0.jpg",
                    "name": "Obat Pelangsing",
                    "price": 50000,
                    "description": "Ini sangat mantap looo",
                    "available": true,
                    "category": {
                        "id": 1,
                        "category": "Health Drink"
                    }
                }
            ]
        },
        {
            "id": 6,
            "user": {
                "name": "Lalalan1",
                "userImageLink": null,
                "role": "DOCTOR"
            },
            "addressUser": {
                "id": 4,
                "buyerName": "Alan Turing",
                "buyerPhone": "08582884831",
                "city": "Jakarta Timur1",
                "posCode": "13401",
                "addressDetail": "Jl. Turing Lalala1",
                "fixPoint": "Samping enigma1",
                "type": "House1",
                "message": "hati hati di jalan1"
            },
            "total": 170000,
            "message": "minta yang terbaik",
            "paymentType": "bca",
            "resiCode": null,
            "vaNumber": "51327464992",
            "expiryTime": "2024-08-02 16:47:33",
            "status": "PACKED",
            "createdAt": "2024-08-01",
            "products": [
                {
                    "id": 1,
                    "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437103/profileObat%20Pelangsing_6368f368-8c6c-4fa1-a2ff-b6ac43c444e0.jpg",
                    "name": "Obat Pelangsing",
                    "price": 50000,
                    "description": "Ini sangat mantap looo",
                    "available": true,
                    "category": {
                        "id": 1,
                        "category": "Health Drink"
                    }
                },
                {
                    "id": 1,
                    "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437103/profileObat%20Pelangsing_6368f368-8c6c-4fa1-a2ff-b6ac43c444e0.jpg",
                    "name": "Obat Pelangsing",
                    "price": 50000,
                    "description": "Ini sangat mantap looo",
                    "available": true,
                    "category": {
                        "id": 1,
                        "category": "Health Drink"
                    }
                },
                {
                    "id": 1,
                    "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437103/profileObat%20Pelangsing_6368f368-8c6c-4fa1-a2ff-b6ac43c444e0.jpg",
                    "name": "Obat Pelangsing",
                    "price": 50000,
                    "description": "Ini sangat mantap looo",
                    "available": true,
                    "category": {
                        "id": 1,
                        "category": "Health Drink"
                    }
                },
                {
                    "id": 5,
                    "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437807/profilePanadol%20Lalala_a8e81cc8-7761-4315-8ff7-69881a15e63b.png",
                    "name": "Panadol Lalala",
                    "price": 20000,
                    "description": "Ini sangat mantap looo",
                    "available": true,
                    "category": {
                        "id": 1,
                        "category": "Health Drink"
                    }
                }
            ]
        }
    ]
}
```

### Get One Transaction
```
GET /api/transactions/{id}
```

#### Response
```JSON
{
    "message": "Transaction Fetched Successfully!",
    "status": "OK",
    "data": [
        {
            "id": 5,
            "user": {
                "name": "Lalalan1",
                "userImageLink": null,
                "role": "DOCTOR"
            },
            "addressUser": {
                "id": 4,
                "buyerName": "Alan Turing",
                "buyerPhone": "08582884831",
                "city": "Jakarta Timur1",
                "posCode": "13401",
                "addressDetail": "Jl. Turing Lalala1",
                "fixPoint": "Samping enigma1",
                "type": "House1",
                "message": "hati hati di jalan1"
            },
            "total": 150000,
            "message": "minta yang terbaik",
            "paymentType": "bca",
            "resiCode": null,
            "vaNumber": "51327206119",
            "expiryTime": "2024-08-02 16:47:09",
            "status": "CANCELED",
            "createdAt": "2024-08-01",
            "products": [
                {
                    "id": 1,
                    "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437103/profileObat%20Pelangsing_6368f368-8c6c-4fa1-a2ff-b6ac43c444e0.jpg",
                    "name": "Obat Pelangsing",
                    "price": 50000,
                    "description": "Ini sangat mantap looo",
                    "available": true,
                    "category": {
                        "id": 1,
                        "category": "Health Drink"
                    }
                },
                {
                    "id": 1,
                    "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437103/profileObat%20Pelangsing_6368f368-8c6c-4fa1-a2ff-b6ac43c444e0.jpg",
                    "name": "Obat Pelangsing",
                    "price": 50000,
                    "description": "Ini sangat mantap looo",
                    "available": true,
                    "category": {
                        "id": 1,
                        "category": "Health Drink"
                    }
                },
                {
                    "id": 1,
                    "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437103/profileObat%20Pelangsing_6368f368-8c6c-4fa1-a2ff-b6ac43c444e0.jpg",
                    "name": "Obat Pelangsing",
                    "price": 50000,
                    "description": "Ini sangat mantap looo",
                    "available": true,
                    "category": {
                        "id": 1,
                        "category": "Health Drink"
                    }
                }
            ]
        },
        {
            "id": 6,
            "user": {
                "name": "Lalalan1",
                "userImageLink": null,
                "role": "DOCTOR"
            },
            "addressUser": {
                "id": 4,
                "buyerName": "Alan Turing",
                "buyerPhone": "08582884831",
                "city": "Jakarta Timur1",
                "posCode": "13401",
                "addressDetail": "Jl. Turing Lalala1",
                "fixPoint": "Samping enigma1",
                "type": "House1",
                "message": "hati hati di jalan1"
            },
            "total": 170000,
            "message": "minta yang terbaik",
            "paymentType": "bca",
            "resiCode": null,
            "vaNumber": "51327464992",
            "expiryTime": "2024-08-02 16:47:33",
            "status": "PACKED",
            "createdAt": "2024-08-01",
            "products": [
                {
                    "id": 1,
                    "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437103/profileObat%20Pelangsing_6368f368-8c6c-4fa1-a2ff-b6ac43c444e0.jpg",
                    "name": "Obat Pelangsing",
                    "price": 50000,
                    "description": "Ini sangat mantap looo",
                    "available": true,
                    "category": {
                        "id": 1,
                        "category": "Health Drink"
                    }
                },
                {
                    "id": 1,
                    "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437103/profileObat%20Pelangsing_6368f368-8c6c-4fa1-a2ff-b6ac43c444e0.jpg",
                    "name": "Obat Pelangsing",
                    "price": 50000,
                    "description": "Ini sangat mantap looo",
                    "available": true,
                    "category": {
                        "id": 1,
                        "category": "Health Drink"
                    }
                },
                {
                    "id": 1,
                    "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437103/profileObat%20Pelangsing_6368f368-8c6c-4fa1-a2ff-b6ac43c444e0.jpg",
                    "name": "Obat Pelangsing",
                    "price": 50000,
                    "description": "Ini sangat mantap looo",
                    "available": true,
                    "category": {
                        "id": 1,
                        "category": "Health Drink"
                    }
                },
                {
                    "id": 5,
                    "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437807/profilePanadol%20Lalala_a8e81cc8-7761-4315-8ff7-69881a15e63b.png",
                    "name": "Panadol Lalala",
                    "price": 20000,
                    "description": "Ini sangat mantap looo",
                    "available": true,
                    "category": {
                        "id": 1,
                        "category": "Health Drink"
                    }
                }
            ]
        }
    ]
}
```

### Create Transaction
#### Only MEMBER and DOCTOR
```
POST /api/transactions/create-transaction
```

#### Request
```JSON
{
    "addressId": 9,
    "message": "for the best",
    "paymentType": "bca",
    "products": [1,1]
}
```

#### Response
```JSON
{
    "message": "Transaction Created Successfully!",
    "status": "OK",
    "data": {
        "id": 9,
        "user": {
            "name": "Irsyad F.",
            "userImageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722521758/profileIrsyad%20F._e6eee9a3-1075-4152-bd3f-1299d54eb826.gif",
            "role": "MEMBER"
        },
        "addressUser": {
            "id": 9,
            "buyerName": "Irsyad",
            "buyerPhone": "0858288483",
            "city": "Jakarta Selatan",
            "posCode": "1340",
            "addressDetail": "Jl. Semangka Lalala",
            "fixPoint": "Samping enigma",
            "type": "House",
            "message": "hati hati di jalan"
        },
        "total": 100000,
        "message": "for the best",
        "paymentType": "bca",
        "resiCode": null,
        "vaNumber": "51327548417",
        "expiryTime": "2024-08-02 22:39:14",
        "status": "PENDING",
        "createdAt": "2024-08-01T15:39:14.416+00:00",
        "products": [
            {
                "id": 1,
                "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437103/profileObat%20Pelangsing_6368f368-8c6c-4fa1-a2ff-b6ac43c444e0.jpg",
                "name": "Obat Pelangsing",
                "price": 50000,
                "description": "Ini sangat mantap looo",
                "available": true,
                "category": {
                    "id": 1,
                    "category": "Health Drink"
                }
            },
            {
                "id": 1,
                "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437103/profileObat%20Pelangsing_6368f368-8c6c-4fa1-a2ff-b6ac43c444e0.jpg",
                "name": "Obat Pelangsing",
                "price": 50000,
                "description": "Ini sangat mantap looo",
                "available": true,
                "category": {
                    "id": 1,
                    "category": "Health Drink"
                }
            }
        ]
    }
}
```

### Already Pay Transactions
#### Only MEMBER and DOCTOR
```
POST /api/transactions/change-status/packed/{id}
```

#### Response
```JSON
{
    "message": "Transaction Status Changed Successfully!",
    "status": "OK",
    "data": {
        "id": 9,
        "user": {
            "name": "Irsyad F.",
            "userImageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722521758/profileIrsyad%20F._e6eee9a3-1075-4152-bd3f-1299d54eb826.gif",
            "role": "MEMBER"
        },
        "addressUser": {
            "id": 9,
            "buyerName": "Irsyad",
            "buyerPhone": "0858288483",
            "city": "Jakarta Selatan",
            "posCode": "1340",
            "addressDetail": "Jl. Semangka Lalala",
            "fixPoint": "Samping enigma",
            "type": "House",
            "message": "hati hati di jalan"
        },
        "total": 100000,
        "message": "for the best",
        "paymentType": "bca",
        "resiCode": null,
        "vaNumber": "51327548417",
        "expiryTime": "2024-08-02 22:39:14",
        "status": "PACKED",
        "createdAt": "2024-08-01",
        "products": [
            {
                "id": 1,
                "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437103/profileObat%20Pelangsing_6368f368-8c6c-4fa1-a2ff-b6ac43c444e0.jpg",
                "name": "Obat Pelangsing",
                "price": 50000,
                "description": "Ini sangat mantap looo",
                "available": true,
                "category": {
                    "id": 1,
                    "category": "Health Drink"
                }
            },
            {
                "id": 1,
                "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437103/profileObat%20Pelangsing_6368f368-8c6c-4fa1-a2ff-b6ac43c444e0.jpg",
                "name": "Obat Pelangsing",
                "price": 50000,
                "description": "Ini sangat mantap looo",
                "available": true,
                "category": {
                    "id": 1,
                    "category": "Health Drink"
                }
            }
        ]
    }
}
```

### To Sent Transactions
#### Only ADMIN
```
POST /api/transactions/change-status/sent/{id}
```

#### Request
```JSON
{
    "resiCode": "dasbjadofhwbsajksahdsahdos"
}
```

#### Response
```JSON
{
    "message": "Transaction Status Changed Successfully!",
    "status": "OK",
    "data": {
        "id": 11,
        "user": {
            "name": "Irsyad F.",
            "userImageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722521758/profileIrsyad%20F._e6eee9a3-1075-4152-bd3f-1299d54eb826.gif",
            "role": "ADMIN"
        },
        "addressUser": {
            "id": 9,
            "buyerName": "Irsyad",
            "buyerPhone": "0858288483",
            "city": "Jakarta Selatan",
            "posCode": "1340",
            "addressDetail": "Jl. Semangka Lalala",
            "fixPoint": "Samping enigma",
            "type": "House",
            "message": "hati hati di jalan"
        },
        "total": 40000,
        "message": "for the best",
        "paymentType": "bca",
        "resiCode": "dasbjadofhwbsajksahdsahdos",
        "vaNumber": "51327646709",
        "expiryTime": "2024-08-02 22:57:21",
        "status": "SENT",
        "createdAt": "2024-08-01",
        "products": [
            {
                "id": 5,
                "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722526201/profileCounter%20Bacteria_bf273241-a5d1-4bb7-9a31-9952f0482045.gif",
                "name": "Counter Bacteria",
                "price": 20000,
                "description": "It works",
                "available": true,
                "category": {
                    "id": 1,
                    "category": "Health Drink"
                }
            },
            {
                "id": 5,
                "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722526201/profileCounter%20Bacteria_bf273241-a5d1-4bb7-9a31-9952f0482045.gif",
                "name": "Counter Bacteria",
                "price": 20000,
                "description": "It works",
                "available": true,
                "category": {
                    "id": 1,
                    "category": "Health Drink"
                }
            }
        ]
    }
}
```

### Accepted Transaction
#### Only MEMBER and DOCTOR
```
POST /api/transactions/change-status/accepted/{id}
```

#### Response
```JSON
{
    "message": "Transaction Status Changed Successfully!",
    "status": "OK",
    "data": {
        "id": 9,
        "user": {
            "name": "Irsyad F.",
            "userImageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722521758/profileIrsyad%20F._e6eee9a3-1075-4152-bd3f-1299d54eb826.gif",
            "role": "MEMBER"
        },
        "addressUser": {
            "id": 9,
            "buyerName": "Irsyad",
            "buyerPhone": "0858288483",
            "city": "Jakarta Selatan",
            "posCode": "1340",
            "addressDetail": "Jl. Semangka Lalala",
            "fixPoint": "Samping enigma",
            "type": "House",
            "message": "hati hati di jalan"
        },
        "total": 100000,
        "message": "for the best",
        "paymentType": "bca",
        "resiCode": "dasbjadofhwbsajksahdsahdos",
        "vaNumber": "51327548417",
        "expiryTime": "2024-08-02 22:39:14",
        "status": "ACCEPTED",
        "createdAt": "2024-08-01",
        "products": [
            {
                "id": 1,
                "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437103/profileObat%20Pelangsing_6368f368-8c6c-4fa1-a2ff-b6ac43c444e0.jpg",
                "name": "Obat Pelangsing",
                "price": 50000,
                "description": "Ini sangat mantap looo",
                "available": true,
                "category": {
                    "id": 1,
                    "category": "Health Drink"
                }
            },
            {
                "id": 1,
                "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437103/profileObat%20Pelangsing_6368f368-8c6c-4fa1-a2ff-b6ac43c444e0.jpg",
                "name": "Obat Pelangsing",
                "price": 50000,
                "description": "Ini sangat mantap looo",
                "available": true,
                "category": {
                    "id": 1,
                    "category": "Health Drink"
                }
            }
        ]
    }
}
```

### Canceled Transaction
#### Only MEMBER and DOCTOR
```
POST /api/transactions/change-status/canceled/{id}
```

#### Response
```JSON
{
    "message": "Transaction Status Changed Successfully!",
    "status": "OK",
    "data": {
        "id": 10,
        "user": {
            "name": "Irsyad F.",
            "userImageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722521758/profileIrsyad%20F._e6eee9a3-1075-4152-bd3f-1299d54eb826.gif",
            "role": "MEMBER"
        },
        "addressUser": {
            "id": 9,
            "buyerName": "Irsyad",
            "buyerPhone": "0858288483",
            "city": "Jakarta Selatan",
            "posCode": "1340",
            "addressDetail": "Jl. Semangka Lalala",
            "fixPoint": "Samping enigma",
            "type": "House",
            "message": "hati hati di jalan"
        },
        "total": 100000,
        "message": "for the best",
        "paymentType": "bca",
        "resiCode": null,
        "vaNumber": "51327401409",
        "expiryTime": "2024-08-02 22:54:00",
        "status": "CANCELED",
        "createdAt": "2024-08-01",
        "products": [
            {
                "id": 1,
                "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437103/profileObat%20Pelangsing_6368f368-8c6c-4fa1-a2ff-b6ac43c444e0.jpg",
                "name": "Obat Pelangsing",
                "price": 50000,
                "description": "Ini sangat mantap looo",
                "available": true,
                "category": {
                    "id": 1,
                    "category": "Health Drink"
                }
            },
            {
                "id": 1,
                "imageLink": "http://res.cloudinary.com/dtmkuridm/image/upload/v1722437103/profileObat%20Pelangsing_6368f368-8c6c-4fa1-a2ff-b6ac43c444e0.jpg",
                "name": "Obat Pelangsing",
                "price": 50000,
                "description": "Ini sangat mantap looo",
                "available": true,
                "category": {
                    "id": 1,
                    "category": "Health Drink"
                }
            }
        ]
    }
}
```