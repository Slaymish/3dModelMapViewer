// Initialize the map
var mymap = L.map('mapid').setView([51.505, -0.09], 13);

// Load tiles for the map background
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>',
    maxZoom: 18,
}).addTo(mymap);

function loadDataPoints() {
    fetch('/api/datapoints')
        .then(response => response.json())
        .then(data => {
            console.log(data);
            const modelsList = document.getElementById('modelsList');
            modelsList.innerHTML = ''; // Clear existing list items

            data.forEach(dp => {
                // Add marker to the map
                var marker = L.marker([dp.latitude, dp.longitude]).addTo(mymap);
                marker.bindPopup(`<b>${dp.building.author}</b><br>${dp.building.description}`).openPopup();

                // Create a new list item for the model
                const listItem = document.createElement('div');
                listItem.classList.add('p-4', 'border', 'border-gray-200', 'rounded', 'mb-2', 'bg-white');
                listItem.innerHTML = `
                    <h3 class="text-lg font-semibold">${dp.building.author}</h3>
                    <p>${dp.building.description}</p>
                    <p>Latitude: ${dp.latitude}, Longitude: ${dp.longitude}</p>
                `;

                // Append the new list item to the models list
                modelsList.appendChild(listItem);
            });
        })
        .catch(error => console.error('Error loading data points:', error));
}


let selectedLat, selectedLng;

var selectedMarker; // Holds the currently selected marker

mymap.on('click', function(e) {
    // Enable the "Upload Model" button
    document.getElementById('uploadModelButton').disabled = false;
    document.getElementById('uploadModelButton').classList.remove('opacity-50', 'cursor-not-allowed');
    document.getElementById('uploadModelButton').classList.add('opacity-100', 'cursor-pointer');

    // Update latitude and longitude values
    selectedLat = e.latlng.lat;
    selectedLng = e.latlng.lng;

    // Remove the previous marker, if any
    if (selectedMarker) {
        mymap.removeLayer(selectedMarker);
    }

    // Add a new marker to the map at the clicked location
    selectedMarker = L.marker([selectedLat, selectedLng]).addTo(mymap);
});


function showForm() {
    document.getElementById('uploadForm').style.display = 'flex'; // Use 'flex' to show the modal
}

function hideForm() {
    document.getElementById('uploadForm').style.display = 'none'; // Hide the modal
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
        })
        .catch((error) => {
            console.error('Error:', error);
            alert("Error uploading model.");
        });
}





// Call the function to load data points
loadDataPoints();
