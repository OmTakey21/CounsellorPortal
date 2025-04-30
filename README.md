# CounsellorPortal

This is a Counsellor Portal web application built using Spring Boot and Thymeleaf to manage student enquiries efficiently. The application provides features like logging in, logging out, viewing and filtering student enquiries, and updating enquiry information. It also allows counsellors to manage enquiries through an intuitive dashboard.

🚀 Features
Login & Logout: Secure login/logout functionality to ensure user authentication.

Dashboard: A user-friendly dashboard to view and manage enquiries.

Enquiry Management:

Add Enquiries: Counsellors can add new student enquiries to the system.

View Enquiries: View the list of all student enquiries.

Filter Enquiries: Easily filter enquiries based on different criteria.

Update Enquiries: Counsellors can edit existing enquiries.

🛠 Tech Stack
Backend:

Spring Boot: Used to build the backend of the application.

Spring MVC: Handles the HTTP requests and controllers.

Spring Data JPA: Used for database operations and ORM mapping.

Frontend:

Thymeleaf: A modern server-side Java template engine for rendering HTML views.

HTML/CSS: Basic front-end technologies used for structuring and styling the UI.

Database:

MySQL: Used for storing and managing the application data.

Tools:

Git: Version control system to manage the project source code.

GitHub: Repository hosting the source code.

Git Bash: Command-line tool for interacting with Git.

IDE: IntelliJ IDEA or Eclipse (for Java development).

🏗️ Project Setup
Prerequisites
Before you begin, ensure you have the following tools installed:

Java 8+

Spring Boot

MySQL

IDE (STS)
Installation Steps
1) Clone the repository:
git clone https://github.com/yourusername/counsellor-portal.git

2)Navigate to the project folder:
cd counsellor-portal

3)Set up MySQL database:
Create a MySQL database (e.g., counsellor_portal).

4)Update the application.properties file in src/main/resources with your MySQL credentials:
spring.datasource.url=jdbc:mysql://localhost:3306/counsellor_portal
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

5)Run the application:
Use your IDE to run the Application.java class (located in src/main/java/com/yourpackage).
Alternatively, you can build the project using Maven or Gradle and run it from the terminal:
mvn spring-boot:run

The application should now be running on http://localhost:8080.

How to Use
Login Page: Access the login page via http://localhost:8080/login. Enter valid credentials to access the portal.

Dashboard: Once logged in, the dashboard will display all the current enquiries. You can add, update, and filter enquiries from here.

📄 License
This project is licensed under the MIT License - see the LICENSE file for details.
