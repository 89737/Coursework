function pageLoad() {

    let now = new Date();

    let myHTML = '<div style="text-align:center;">'   // aligns the text for my main heading
        + '<h1>Flashcard Revision!</h1>' // this is the main title for my index page and will show the writing within the h1 tags
        + '<img src="/client/img/logo.jpg" align="left" width="300px" height="100px" alt="Logo"/>' // this loads in the image i'll be using and has the file path with the width being set to 300px wide
        + '<div style="font-style: italic;">' //this is for the font style that will be being set for the heading
        + 'Generated at ' + now.toLocaleTimeString() //this displays the time of which the page has been loaded
        + '</div>'
        + '</div>';

    document.getElementById("testDiv").innerHTML = myHTML; //sets the testDiv to the myHTML variable



    const canvas = document.getElementById('chartCanvas');
    const context = canvas.getContext('2d');

    let myChart = new Chart(context, {
        type: 'line',   // change this to 'pie' if wanted
        data: {
            labels: [
                'Test 1',
                'Test 2',
                'Test 3',
                'Test 4',
                'Test 5',
                'Test 6'
            ],
            datasets: [{
                label: 'Test results',
                data: [
                    12,
                    19,
                    3,
                    5,
                    2,
                    3
                ],
                backgroundColor: [
                    'red',
                    'blue',
                    'yellow',
                    'green',
                    'purple',
                    'orange'
                ]
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            },
            responsive: false
        }
    });


}

function RunLoginpage() {



    window.location.href = "/client/login.html";
}


