// app.js

// Initialize the map
var mymap = L.map('mapid').setView([51.505, -0.09], 13);
let markers = {}; // Object to store markers

// Load tiles for the map background
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>',
    maxZoom: 18,
}).addTo(mymap);

function initializeModelViewer(containerId, modelPath) {
    var canvas = document.createElement("canvas");
    canvas.style.width = "100%";
    canvas.style.height = "100%";
    document.getElementById(containerId).appendChild(canvas);

    var engine = new BABYLON.Engine(canvas, true);
    var scene = new BABYLON.Scene(engine);

    // Setting up camera
    var camera = new BABYLON.ArcRotateCamera("camera", -Math.PI / 2, Math.PI / 2, 10, new BABYLON.Vector3(0, 0, 0), scene);
    camera.attachControl(canvas, true);

    // Setting up lights
    var light = new BABYLON.HemisphericLight("light", new BABYLON.Vector3(1, 1, 0), scene);

    // Extracting directory and filename from the modelPath
    var modelDirectory = modelPath.substring(0, modelPath.lastIndexOf("/") + 1);
    var modelFilename = modelPath.substring(modelPath.lastIndexOf("/") + 1);

    // Loading the model
    BABYLON.SceneLoader.ImportMesh("", modelDirectory, modelFilename, scene, function (meshes) {
        // After the model is loaded, adjust the scale and position
        meshes.forEach(function (mesh) {
            mesh.scaling.scaleInPlace(1.0); // Adjust scale as needed, 1.0 means no scaling
            mesh.position = BABYLON.Vector3.Zero(); // Center the mesh
        });

        // Adjust the camera to frame the mesh
        if (meshes.length) {
            camera.target = meshes[0].getBoundingInfo().boundingBox.centerWorld;
            camera.radius = meshes[0].getBoundingInfo().boundingSphere.radius * 3; // Adjust as needed
        }

        scene.createDefaultCameraOrLight(true, true, true);
    }, null, function (scene, message, exception) {
        console.error("Error loading model:", message, exception);
    });

    engine.runRenderLoop(function () {
        scene.render();
    });

    window.addEventListener("resize", function () {
        engine.resize();
    });
}

function getDistanceFromLatLonInKm(lat1, lon1, lat2, lon2) {
    var R = 6371; // Radius of the earth in km
    var dLat = deg2rad(lat2 - lat1);
    var dLon = deg2rad(lon2 - lon1);
    var a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    var d = R * c; // Distance in km
    return d;
}

function deg2rad(deg) {
    return deg * (Math.PI / 180);
}

// Load data points
function loadDataPoints() {
    fetch('/api/datapoints')
        .then(response => response.json())
        .then(data => {
            displayDataPoints(data); // Display the data points
        })
        .catch(error => console.error('Error loading data points:', error));
}

function displayDataPoints(dataPoints) {
    dataPoints.forEach(dp => {
        // Add marker to the map
        var marker = L.marker([dp.latitude, dp.longitude]).addTo(mymap);
        marker.bindPopup(`<b>${dp.building.author}</b><br>${dp.building.description}`);
        markers[dp.id] = marker; // Store the marker

        // Create a new list item for the model and append to modelsList
        const modelsList = document.getElementById('modelsList');
        const listItem = document.createElement('div');
        listItem.classList.add('model-item');
        listItem.setAttribute('data-id', dp.id);
        listItem.innerHTML = `
            <h3>${dp.building.author}</h3>
            <p>${dp.building.description}</p>
            <div class="model-viewer" id="modelViewer-${dp.id}"></div>
            <p class="address" id="address-${dp.id}">Loading address...</p>
        `;
        modelsList.appendChild(listItem);

        // Initialize the model viewer
        initializeModelViewer(`modelViewer-${dp.id}`, dp.building.modelPath);

        // Listener for listItem click
        listItem.addEventListener('click', function() {
            mymap.flyTo([dp.latitude, dp.longitude], 16);
            marker.openPopup();
        });

        reverseGeocode(dp.latitude, dp.longitude, `address-${dp.id}`);
    });
}

let selectedLat, selectedLng;
var selectedMarker; // Holds the currently selected marker

mymap.on('click', function(e) {
    // Enable the "Upload Model" button
    document.getElementById('uploadModelButton').disabled = false;
    document.getElementById('uploadModelButton').classList.remove('disabled');

    // Update latitude and longitude values
    selectedLat = e.latlng.lat;
    selectedLng = e.latlng.lng;

    reverseGeocode(selectedLat, selectedLng, 'selectedAddress'); // Reverse geocode the selected location

    // Remove the previous marker, if any
    if (selectedMarker) {
        mymap.removeLayer(selectedMarker);
    }

    // Add a new marker to the map at the clicked location
    selectedMarker = L.marker([selectedLat, selectedLng]).addTo(mymap);
});

function shortenAddress(fullAddress) {
    let addressComponents = fullAddress.split(',').map(component => component.trim());
    let shortenedAddress = addressComponents.slice(0, 3).join(', '); // Start with the first 3 components

    // Check if the shortened address exceeds 50 characters
    if (shortenedAddress.length > 50) {
        // Try removing components from the start (usually the most specific part)
        while (shortenedAddress.length > 50 && addressComponents.length > 1) {
            addressComponents.shift(); // Remove the first component
            shortenedAddress = addressComponents.join(', ');
        }

        // If still too long, truncate to fit
        if (shortenedAddress.length > 50) {
            shortenedAddress = shortenedAddress.slice(0, 47) + '...'; // Ensure it doesn't exceed 50 characters
        }
    }

    return shortenedAddress;
}

let selectedAddress = ''; // Variable to store the selected address

function reverseGeocode(lat, lng, elementId) {
    const url = `https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lng}`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            let address = data.display_name || 'Address not found';
            address = shortenAddress(address); // Apply the shortening function

            selectedAddress = address; // Store the shortened, readable address
            document.getElementById(elementId).innerText = address; // Update the element's inner text
        })
        .catch(error => console.error('Error fetching address:', error));
}

function showForm() {
    document.getElementById('uploadForm').style.display = 'flex'; // Show the form
    document.getElementById('formAddress').innerText = selectedAddress; // Set the address in the form
}

function hideForm() {
    // Hide the form by setting display to 'none'
    document.getElementById('uploadForm').style.display = 'none';
    // Reset the form fields
    document.getElementById('modelForm').reset();
    document.getElementById('formAddress').innerText = 'Select a location on the map';
    document.getElementById('selectedAddress').innerText = 'Select a location on the map';
    // Reset selected latitude and longitude
    selectedLat = undefined;
    selectedLng = undefined;
    // Remove the selected marker from the map, if it exists
    if (selectedMarker) {
        mymap.removeLayer(selectedMarker);
    }
    // Re-disable the "Upload Model" button and adjust its styling to indicate it's disabled
    var uploadButton = document.getElementById('uploadModelButton');
    uploadButton.disabled = true;
    uploadButton.classList.add('disabled'); // Add class to visually indicate it's disabled
}

function uploadModel() {
    var formData = new FormData(document.getElementById('modelForm'));
    formData.append("latitude", selectedLat.toString());
    formData.append("longitude", selectedLng.toString());

    fetch('/api/uploadModel', {
        method: 'POST',
        body: formData,
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // Parse JSON response body
        })
        .then(dataPoint => {
            console.log('Success:', dataPoint);
            alert("Model uploaded successfully!");

            // Since the response is a single data point object, add it directly
            var marker = L.marker([dataPoint.latitude, dataPoint.longitude]).addTo(mymap);
            marker.bindPopup(`<b>${dataPoint.building.author}</b><br>${dataPoint.building.description}`).openPopup();

            // Optionally, hide the upload form and clear the selection
            document.getElementById('uploadForm').style.display = 'none';
            selectedLat = undefined;
            selectedLng = undefined;

            // Reload the data points
            loadDataPoints();
        })
        .catch((error) => {
            console.error('Error:', error);
            alert("Error uploading model.");
        });
}

// Initialization
loadDataPoints(); // Load data points