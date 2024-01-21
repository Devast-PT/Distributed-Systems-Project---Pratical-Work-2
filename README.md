# Distributed Systems Project

## Introduction

In the pursuit of enhancing the previous project's implementation, which involved the development of a server, general client, and administrator components, this practical work aimed to apply techniques and concepts learned in class. Building upon the previous use of Java RMI and Spring Data for server development, this iteration delved into exploring alternative solutions to meet the professor's requirements.

### Objectives

The objectives remain similar to the previous project, with some nuanced changes in data modeling and service functionalities.

#### Data Modeling Objectives

- Provide GPS coordinates as location.
- Maintain a list of all locations for an artist, representing past and future performances.
- Enable the option to send donations to artists.
- Implement user authentication for applications.



#### General Client Objectives

- Register in the system with a username/email/password.
- Authenticate with a username and password.
- Request registration of a new artist.
- List artists with location or art filters.
- List locations where artists are performing.
- Display dates of past and future performances for an artist.
- Send donations to an artist.
- List donations to an artist.
- Rate an artist.
- View a list of artists and their ratings.


  
#### Administrator Objectives:

- Authenticate as an administrator.
- Grant administrator permissions to a user.
- List artists by status (approval).
- Approve an artist who has not yet been approved.
- Consult and modify artist information.
- Perform all actions available to a general client.



#### Server Objectives

- Support redundancy and replication to tolerate failures.
- Implement a publish-subscribe mechanism.
- The implementation should allow for flexibility in technologies and languages, ensuring the system can continue functioning when switching these components.

### Development

To achieve the goals of this project, various new techniques and concepts were employed. The server was designed as a REST API, and the client as a consumer of the REST API, with JWT tokens used for authentication. Microservices were utilized for a horizontally scalable solution. The project was developed with the separation of each microservice's responsibilities in mind. Although I changed several stuff (and not for the best in most cases). I only had 5 days to learn and deploy all this concepts in my spare time.

Some Of The Technologies Used:
Spring Eureka (Netflix) for service discovery.
Spring Gateway for service filtering and load balancing.
Docker for database containerization.
Zipkin for distributed tracing with micrometer instead of sleught.
Zookeeper and Apache Kafka for pub/sub service.
The implementation also incorporated a JWT-based authentication service, a database per microservice using Docker, and replication through synchronous and asynchronous methods.
Config server that I deleted cause my computer could not handle all the services up and running at some point. :'D

### Conclusion
Despite the challenges posed by time constraints and concurrent academic and professional commitments (I work as a machinist while trying to learn computer science to change field), this project provided valuable insights into various cloud solutions and the Spring framework. While the desired outcome may not have been fully achieved, the effort invested in meeting deadlines was worthwhile. The exploration of different cloud solutions and technologies significantly contributed to a deeper understanding of distributed systems and cloud architectures. Having made some mistakes, one can gain a broader perspective on all the options and understand why certain solutions are chosen and the possibilities they bring. If I were to start over (something I intend to do to try a different approach), I would change some of the decisions I made.

