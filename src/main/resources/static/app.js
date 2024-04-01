// Initialize the map
var mymap = L.map('mapid').setView([51.505, -0.09], 13);

// Load tiles for the map background
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>',
    maxZoom: 18,
}).addTo(mymap);

// Function to load data points and add to map
function loadDataPoints() {
    fetch('/api/datapoints')
        .then(response => response.json())
        .then(data => {
            console.log(data);
            data.forEach(dp => {
                var marker = L.marker([dp.latitude, dp.longitude]).addTo(mymap);
                marker.bindPopup(`<b>${dp.building.author}</b><br>${dp.building.description}`).openPopup();
            });
        })
        .catch(error => console.error('Error loading data points:', error));
}

let selectedLat, selectedLng;

mymap.on('click', function(e) {
    selectedLat = e.latlng.lat;
    selectedLng = e.latlng.lng;
    document.getElementById('uploadForm').style.display = 'block';
});

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
