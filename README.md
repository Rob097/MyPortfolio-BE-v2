# MyPortfolio-BE
Hi! This is the BackEnd of MyPortfolio.

### *What is MyPortfolio?*
MyPortfolio is a place where you can tell your own stories. About your job, tour educational path, your projects and so on. 

MyPortfolio is meant to be a community for those people who want to see beyond just a completed project or simple hiring. For those who wants to know the whole story that is behind these achievements.

#### How its build
The architectural design used to create this project is based on the Microservices design.

So the technology stack used is the following:

 - Deployed on docker and managed using a Kubernetes cluster
 - Database: Relational DB using MySql.
 - BackEnd: Java with spring boot as framework.
		 - ApiGateway as entryPoint of microservices and used as LoadBalancer
		 - EurekaServer used to track every microservice status
		 - Zipkin used to trace requests and responses between microservices
		 - Security: Spring security with JWT Token
		 - Cache: Redis for in memory cache
- FrontEnd: Angular. For more information, visit the relative GitHub repository [here](https://github.com/Rob097/MyPortfolio-Frontend).

