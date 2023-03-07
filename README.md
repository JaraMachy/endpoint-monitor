# ENDPOINT-MONITOR

Project was created according to task documentation file `Java_Kotlin_task.pdf`

## Project set-up

1. To be able tu run the project, you need to have installed:
   * **JAVA 11**
   * **MySQL**
   * **Maven**

2. Database creation:
   * It's necessary to create the database according to settings in this file `application-dev-local.yml`
   * That means:
     * Database name: **endpoint-monitor_db**
     * Username: **endpoint-monitor-admin**
     * User password: **endpoint-password**

3. Run project in **IntelliJ**

## How to use the service

1. The easiest way to use the app is using swagger web ui **http://localhost:8080/swagger-ui/index.html#/**
2. First you need to authenticate yourself using the endpoint **/authenticate**
   * For this purpose use one of the hardcoded credentials listed below
   * In return you will get **access token** (jwtToken)
3. Copy the returned access token, click the **Authorize** button on the top of the right side and paste it there
4. Now you can use all the other endpoints and the access token is automatically sent in the HTTP headers in each request

### Hard coded user credentials
* **Username** &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Password**
* Albus Brumbál &nbsp; &nbsp; &nbsp; brumby007
* Profesor Snape &nbsp; &nbsp; princdvojikrve123
* Harry Potter &nbsp; &nbsp; &nbsp;&nbsp;&nbsp; &nbsp; famfrpaljebest

#Contact
* In case of some problems, you can contact me via email **jarda.machovec@gmail.com**
