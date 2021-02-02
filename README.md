## 1. What is SmartChoice?

SmartChoice is a website that can compare the price of a product from different resources (Tiki, Lazada, Shopee...).

## 2. Requirements and Goals of the System

### Functional Requirements
1. A Rest API to support their customer compare a product price, the API should return the product name, current price, the discount rate, promotion..
2. The customer can click on the product to see more details or view the product's images
3. For audit support, the company wants to keep track of the searching history of the customer. 
4. Getting the product information from many different resources (Tiki, Lazada, Shopee...) take a lot of time in high traffic period. All the data must be completely returned from 3rd parties before the API can return to the website.	
5. As the user types in their query, our service should suggest top 10 terms starting with whatever the user has typed.		
### Non-functional Requirements
1. Our service needs to be highly available. 
2. Users should have a real-time search experience with minimum latency.
3. The acceptable latency of the system is 200ms for searching products
4. Consistency can take a hit (in the interest of availability), if a user doesn’t search a product for a while; it should be fine.
5. Failure to store customer activity should have no impact on the function or performance of the Rest APIs
6. Each request sent to 3rd will be charged for a fee, the company want to decrease the cost and improve the effectiveness of the application
7. The suggestions should appear in real-time. The user should be able to see the suggestions within 200ms.

## 3. Capacity Estimation and Constraints
Traffic estimates: Let’s assume that our service has 30 millions page views per month and query 1 million products pricing a month.
Storage estimates: Let’s assume each product info needs 3kb (product info, pricing) to store in the database. We would also need to store information about merchant and brands; let’s assume it’ll take 100 bytes with total 100000 brands and 10 merchant. 
Let assume that each user's action need 100 Bytes. Each user session have 5 actions.
So total storage on next five years will be:
- User logging: 30,000,000 * 12 * 5 * 5 * 100 ~ 9,000,000,000,000 ~ 8.1 TB 
- Product Storage: 1,000,000 * *12 * 5 * 3 * 1024 ~ 171 Gbs
- Merchange Storage: 100 * (10000 + 10) ~ 1MB

## High Level Design
We follow Netflix OSS Microservices for our application. It's common and support a lot by Spring Boot ( which is required. We support gateway, discovery, config servers as bases services as well ). For other patters, this solution follow event-driven for broadcasting and flows. We split our system follow CQRS strictly which can suppor high load for both read and write side.
![software_architect.png](/software_architect.png)
## Detailed Component Design
### Query Side:
- Product Server: This component provide data for merchant, brand, product detail and category if needed. All data is cached query first. In case data is missed, sku request event will be broadcasting and sku contruct flow is initialize
- Pricing Server: For product pricing, we split them into separated server for fasted query. We put logic loading into FE side, FE side must query product first and price later. In pricing server, all data are cached query only.
- Search Server: Use elasticsearch for fast query.
- Suggestion Server: It's complicated. We base on Trie solutions with Redis, with this solution we can provide response time below 50ms each request.

### Command Side:
- Data Consumer Server: heart of system, broadcasting updated data to whole picture, received trigger event, collect and aggressive data. We can split them into multi modulars with aggressive design. 
- Suggestion Consumer Server: check broadcasting data from queue, extract and update suggestion keywords
- Search Consumer Server: check broadcasting data from queue, extract and update search data with full-text search

### Utilities
Image Processor Server: We split image storage into isolated system, this processor extract all images from 3rd API, scale and standard them, put on our CDN Storage and giveback access url to queue.
Schedule Update Scheduler: For faster query, we can schedule to load 10000 product with high access everyday.
## Fault Tolerance
Whenever Redis Cache crashes, we can read all the active data again from Mongo Database. Another option is to have a master-slave configuration so that, when the master crashes, the slave can take over. 	
Similarly, we’ll have a master-slave setup for databases to make them fault tolerant.
## Data Partitioning
Database partitioning: We partition Redis by Product or Pricing with SKU, then all the data will be partition on SKU. It's better. This way, the load gets distributed among different servers with we can defined.
## Cache
Varnish cached is important. Because 3rd API is charging, all request must be combined and merged before requestion. For duplicated request, Varnish fit perfectly.

## System Port Collections
| Service | Port |
|---------|------|
|gateway-server|8080|
|config-server|8081|
|discovery-server|8082|
|product-server|8091|
|pricing-server|8092|
|data-consumer-server|8093|
|search-consumer-server|8094|
|search-server|8095|
|suggestion-consumer-server|8096|
|suggestion-server|8097|
|image-processor-server|8098|

## Conclusion
In this assessment, I am focusing on overall picture, how system be scale and adapt large data from predict. From implementaiton, I am just builing the core module, make it works together. In case your team need more involve on code, feel free to tell me, I will choose on feature and builing up.
