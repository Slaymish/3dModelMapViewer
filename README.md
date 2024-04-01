# 3D Models Map Viewer
The 3D Models Map Viewer is a web application that allows users to upload 3D models with specific geographic locations and view these models on an interactive map. This project utilizes a Java Spring Boot backend for handling model uploads and metadata management, with a Leaflet-powered frontend for map interaction.

## Features
- Upload 3D Models: Users can upload 3D model files, specifying their geographic location (latitude and longitude) along with metadata such as author and description.
- View Models on Map: Uploaded models are represented as markers on an interactive map. Users can click these markers to view model details.
- Model Details: A popup or a dedicated viewer allows users to explore further information about the model, including a possible preview or link to view the model in 3D.

## Getting Started
###Prerequisites
- Java JDK 11 or newer
- Maven
- PostgreSQL
- Node.js and npm (for running the frontend)

### Backend Setup
```bash
git clone https://your-repository-url.git
cd your-repository-directory
```

Configure the database connection in src/main/resources/application.properties:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/yourDatabase
spring.datasource.username=yourUsername
spring.datasource.password=yourPassword
```

Run the application:
```bash
mvn spring-boot:run
```

The backend server will start, by default on http://localhost:8080.

### Frontend Setup
Navigate to the frontend directory:
Assuming your frontend code is in the frontend directory within the project.

```bash
cd static/
```
Install dependencies:
```bash
npm install
```
Run the frontend:
```bash
npm start
```
The frontend should now be accessible at http://localhost:3000, depending on your configuration.

## Usage
Uploading a Model: Navigate to the upload page via the interface, fill in the details for your model, select the file, and submit.
Viewing Models: On the map page, click any model marker to view its details and access the model viewer if available.

## Contributing
Contributions are welcome! Please feel free to submit pull requests or open issues to discuss proposed changes or report bugs.
