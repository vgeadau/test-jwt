### Project's target

- Create a books management application using Java, Spring, REST API and JWT basic authentication.

### Main objectives

- Create a User model having: username, password and pseudonym fields.
- Create a Book model having: title, description, author (User), cover image and price fields.
- Create an endpoint to authenticate using username + password.
- Create REST endpoints for `/books`.
    - No authentication is required for the GET operations.
    - Basic Authentication is required for the CRUD operations.
- Create Unit tests for all endpoints.

### Some details

- Make sure that currently logged users may delete only their own books.
- Make sure that banned users are not allowed to authenticate in the application.
- Make sure that some `/books` endpoints accepts/returns a JSON/XML based on the headers settings.
- Make sure that the password is encrypted.
- User some in memory database and Spring/JPA.
- Time limit for POC is 8 hours so prioritize functionality.