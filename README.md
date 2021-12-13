Software Stack:
---------------------------------
1. Java 1.8
2. Spring Boot 2.5.7
3. Gradle 7.3.1
4. MySql
5. Any IDE example Intellij IDEA 2021.3




Prerequisites:
---------------------------------
1. Java Minimum 1.8
2. MySql




Steps for running project:
---------------------------------
1. Install softwares from the Prerequisites section above.
2. Download / Clone myretail project from the GIT.
3. Create "myretaildb" database in MySQL and import the product_info.sql script which is present in the project root directory.
4. Open command prompt in the project root directory and run below,

gradlew clean build

This will create a jar file named myretail-0.0.1-SNAPSHOT.jar

5. Go to "<Project Root>\build\libs\" location in command prompt and run below command to start the server at port 8080,

java -jar myretail-0.0.1-SNAPSHOT.jar

6. Instead of running step 4 and step 5, alternatively we can import the project in IDE using "Import Existing Gradle Project" option and then configure the application appropriately to start the server.




API Details:
---------------------------------
There are 2 API's created in the project.

1.  
Method Type : GET
Request URL : /products/{id}
Description: This API is used to retrieve product details based on the id.
Response: 
{
    "id": 13860428,
    "name": "TestProductName",
    "current_price": {
        "value": 13.49,
        "currency_code": "USD"
    }
}


Example URL: 
http://localhost:8080/products/13860428

Example Response: 
{
    "id": 13860428,
    "name": "TestProductName",
    "current_price": {
        "value": 13.49,
        "currency_code": "USD"
    }
}



2.
Method Type: PUT
Request URL : /products/{id}
Request Body : 
{
    "id": 138604289,
    "name": "TestProductName",
    "current_price": {
        "value": 15.60,
        "currency_code": "EUR"
    }
}
Description: This API is used to update product price based on ID. It is only used to update the existing Product ID. If the provided Product ID doesn't exist, then it returns appropriate error message.

Example URL:  localhost:8080/products/13860428
Example CURL request:
curl --location --request PUT 'localhost:8080/products/13860428' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 138604289,
    "name": "TestProductName",
    "current_price": {
        "value": 13.49,
        "currency_code": "USD"
    }
}'

Example Response: 
Success



