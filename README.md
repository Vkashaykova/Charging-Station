Charging Station API Server Application
This project implements a RESTful API server for managing electric car charging stations. It allows users to store and retrieve charging station data based on unique IDs, geo-coordinates, and zip codes. The application is built using Java and the Spring Framework.

Introduction
Welcome to the Charging Station API Server Application! This project aims to provide a robust and efficient solution for managing electric car charging stations. With this API server, users can perform various operations such as storing new charging stations, querying stations by ID, zip code, or geo-location, and updating/deleting existing stations.

Project Description
The project follows the requirements outlined in the task prompt. It implements a RESTful interface for data storage and retrieval of charging station information. The key components of the application include:

Data Persistence: Ability to persist charging station data.
Data Retrieval/Query Capabilities: Retrieve charging stations by unique ID, zip code, or geo-location perimeter.
Functional Requirements
Data Persistence: Charging station data should be stored persistently in the database.
Data Retrieval/Query Capabilities:
Retrieve charging stations by unique ID.
Retrieve charging stations by zip code.
Retrieve charging stations within a specified distance from a given geo-location.

Setup Instructions
To set up and run the Charging Station API Server Application locally, follow these steps:

Clone the Repository: git clone https://github.com/Vkashaykova/Hubject
Navigate to the Project Directory: cd charging-station-api
Build the Project: ./gradlew build
Run the Application: ./gradlew bootRun
Access the API: Open your web browser and go to http://localhost:8080/api

API Endpoints
The following endpoints are available in the API:

GET /api/charging-stations: Retrieve all charging stations.
GET /api/charging-stations/{chargingStationId}: Retrieve charging station by ID.
GET /api/charging-stations/zipcode/{zipcode}: Retrieve charging stations by zip code.
GET /api/charging-stations/search?latitude={latitude}&longitude={longitude}: Retrieve charging stations within a specified radius from a given geo-location.
GET /api/charging-stations/search?zipcode={zipcode}: Retrieve charging stations within a specified zipcode.
POST /api/charging-stations: Add a new charging station.
PUT /api/charging-stations/{chargingStationId}: Update an existing charging station.
DELETE /api/charging-stations/{chargingStationId}: Delete a charging station by ID.
PUT /api/charging-stations/zipcode/{zipcodeId}: Update an existing zipcode.
DELETE /api/charging-stations/zipcode{zipcodeId}: Delete a zipcode by ID.

Testing
The project includes unit tests to ensure the correctness of the implemented functionalities.

Contributing
Contributions are welcome! If you have any suggestions, improvements, or bug fixes, we'd be happy to incorporate them.