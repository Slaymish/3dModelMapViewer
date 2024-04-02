# 3D Model Map Viewer

This project is a web application that allows users to view and interact with 3D models on a map. It provides a user-friendly interface for exploring and discovering 3D models based on their geographic location.

## Features

- [X] Interactive map interface for browsing 3D models
- [ ] Display 3D models using Babylon.js
- [X] View model details such as author and description
- [X] Upload new 3D models and associate them with a specific location on the map
- [ ] Retrieve address information for selected locations using reverse geocoding

## Prerequisites

- Docker
- Docker Compose

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/Slaymish/3dModelMapViewer.git
   cd 3dModelMapViewer
   ```

2. Set the required environment variables:
    - Create a `.env` file in the project root directory.
    - Add the following variables to the `.env` file:
      ```
      SPRING_DATASOURCE_PASSWORD=your-postgres-password
      ```
    - Replace `your-postgres-password` with your actual PostgreSQL password.

3. Run the application using Docker Compose:
   ```bash
   docker-compose up --build
   ```

   This command will build the application image, start the PostgreSQL service, and start the application service. The database will be automatically created based on the environment variables.

4. Access the application in your web browser at `http://localhost:8080`.

## Configuration

The application uses the following environment variables for configuration:

- `SPRING_DATASOURCE_URL`: The URL of the PostgreSQL database (default: `jdbc:postgresql://db:5432/map3dmodels`).
- `SPRING_DATASOURCE_USERNAME`: The username for the PostgreSQL database (default: `postgres`).
- `SPRING_DATASOURCE_PASSWORD`: The password for the PostgreSQL database (set in the `.env` file).

You can modify these variables in the `docker-compose.yaml` file if needed.

## Data Persistence

The PostgreSQL data is persisted using a Docker volume named `postgres-data`. This ensures that the database data is preserved across container restarts.

## Troubleshooting

If you encounter any issues during the setup or running of the application, try the following:

- Make sure you have the latest versions of Docker and Docker Compose installed.
- Ensure that the required ports (8080 for the application and 5432 for PostgreSQL) are not being used by other applications.
- Check the logs of the containers using `docker-compose logs` to identify any error messages.

If the issue persists, please open an issue on the GitHub repository with details about the problem.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).
