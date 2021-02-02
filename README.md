## 1. What is SmartChoice?

SmartChoice is a website that can compare a product's price from different resources (Tiki, Lazada, Shopee, etc).

## 2. Requirements and Goals of the System

### Functional Requirements
1. A Rest API to support their customer compare a product price, the API should return the product name, current price, the discount rate, promotion..
2. The customer can click on the product to see more details or view the product's images
3. For audit support, the company wants to keep track of the customer's searching history. 
4. Getting the product information from many different resources (Tiki, Lazada, Shopee, etc.) takes a lot of time in a high traffic period. All the data must be completely returned from 3rd parties before the API can return to the website.	
5. As the user types in their query, our service should suggest the top 10 terms starting with whatever the user has typed.		
### Non-functional Requirements
1. Our service needs to be highly available. 
2. Users should have a real-time search experience with minimum latency.
3. The acceptable latency of the system is 200ms for searching products
4. Consistency can take a hit (in the interest of availability). If a user doesn't search a product for a while, it should be fine.
5. Failure to store customer activity should have no impact on the function or performance of the Rest APIs
6. Each request sent to 3rd will be charged a fee. The company want to decrease the cost and improve the effectiveness of the application
7. The suggestions should appear in real-time. The user should be able to see the suggestions within 200ms.

## 3. Capacity Estimation and Constraints
Traffic estimation: Let's assume that our service has 30 million page views per month and query 1 million products pricing a month.
Storage estimates: Let's assume each product info needs 3kb (product info, pricing) to store in the database. We would also need to keep merchant and brands; let's assume it'll take 100 bytes with 100000 brands and ten merchant brands. 
Let assume that each user's action needs 100 Bytes. Each user session has 5 actions.
So total storage on next five years will be:
- User logging: 30,000,000 * 12 * 5 * 5 * 100 ~ 9,000,000,000,000 ~ 8.1 TB 
- Product Storage: 1,000,000 * *12 * 5 * 3 * 1024 ~ 171 Gbs
- Merchange Storage: 100 * (10000 + 10) ~ 1MB

## High Level Design
We follow Netflix OSS Microservices for our application. It's familiar and supports a lot by Spring Boot (required by this assessment). We support gateway, discovery, config servers as base services as well ). For other patters, this solution is event-driven for broadcasting and flows. We split our system to follow CQRS strictly, which can support a high load for both the read and write side.
![software_architect.png](/software_architect.png)
## Detailed Component Design
### Query Side:
- Product Server: This component provides data for merchant, brand, product detail, and category if needed. All data is cached query first. In case data is missed, SKU request event will be broadcasting and SKU construct flow is initialized
- Pricing Server: For product pricing, we split them into the separated server for the fasted query. We put logic loading into the FE side, the FE side must query product first and price later. In the pricing server, all data are cached query only.
- Search Server: Use elastic search for fast queries.
- Suggestion Server: It's complicated. We base on Trie solutions with Redis. With this solution, we can provide a response time below 50ms for each request.

### Command Side:
- Data Consumer Server: a heart of the system, broadcasting updated data to the whole picture, received trigger events, collect and aggressive data. We can split them into multi-modular with aggressive design. 
- Suggestion Consumer Server: check broadcasting data from the queue, extract and update suggestion keywords
- Search Consumer Server: check broadcasting data from the queue, extract and update search data with full-text search

### Utilities
Image Processor Server: We split image storage into an isolated system. This processor extracts all images from 3rd API, scales and standard them, puts them on our CDN Storage and gives back access URL to the queue.
Schedule Update Scheduler: For faster queries, we can schedule 10000 products with high access every day.
## Fault Tolerance
Whenever Redis Cache crashes, we can read all the active data again from Mongo Database. Another option is to have a master-slave configuration so that, when the master crashes, the slave can take over. 	
Similarly, we'll have a master-slave setup for databases to make them fault-tolerant.
## Data Partitioning
Database partitioning: We partition Redis by Product or Pricing with SKU, then all the data will be a partition on SKU. It's better. This way, the load gets distributed among different servers with what we can define.
## Cache
Varnish cached is essential. Because 3rd API is charging, all requests must be combined and merged before requestion. For duplicated request, Varnish fit perfectly.

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
In this assessment, I focus on the overall picture, how the system is scalable, and how to adapt extensive data from predicting. So I try to visualize all of them and build a solution for scalability, stability and business adapt first. With implementation, I am just building the core module and making it work together so the team can join quickly ( as the beginning of a new product ). In case your team needs me more involvement in code like logic, test, convention, devops. Feel free to tell me. I will choose one feature and building up. With the limited time of assessment, I am focusing on system architects only.
