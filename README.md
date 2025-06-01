# Building Archive: Web-Based 3D Map Explorer

This project is a web application that allows users to upload information about buildings (including their name, author, description, and geographical coordinates). These buildings are then displayed as markers on an interactive 3D globe rendered in the browser using Three.js. Users can explore the globe and click on buildings to see their details.

## Features

- Upload building metadata: Users can submit building information including name, author, description, and geographical coordinates (latitude and longitude) via a simple web form.
- Interactive 3D Globe: View uploaded buildings pijn_pointed on a 3D globe rendered in the web browser using Three.js.
- View Building Details: Click on a building marker on the globe to display its name and coordinates.
- Java Backend: The application uses a Java-based backend (Jetty server with Servlets) to handle data submissions and serve the web content.

## Setup and Usage

### Prerequisites
- Java Development Kit (JDK) 17 or higher
- Apache Maven

### Running the Application
1. Clone the repository.
2. Open a terminal in the project root directory.
3. Run the command:
   ```bash
   mvn jetty:run
   ```
4. Once the server starts, you can access the application in your web browser:
   - **Upload Form:** [http://localhost:8080/](http://localhost:8080/)
   - **3D Globe Explorer:** [http://localhost:8080/explore.html](http://localhost:8080/explore.html)

## Project Structure

* `pom.xml`: The Maven project object model file, defining project dependencies (like Jetty, Servlets, Three.js via CDN, Gson, LWJGL), build plugins, and project metadata.
* `src/main/java/`: Contains the Java backend code.
  - Servlets for handling HTTP requests (e.g., for `/upload` and `/explore` endpoints).
  - `MapApp.java`: A separate desktop application using LWJGL for 3D graphics. Its direct role in the primary web application flow is not immediately clear; it may be a utility for other purposes or an alternative visualization frontend.
* `src/main/webapp/`: Contains the frontend web application files.
  - `index.html`: The main page with the form for uploading building data.
  - `explore.html`: The page that renders the 3D globe visualization using Three.js.
  - `main.css`: Stylesheets for the web pages.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).
