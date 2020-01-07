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

    let l = [];
    let d = [];

    fetch('/Tests/get/1', {method: 'get'}
    ).then(response => response.json()
    ).then(testScores => {

        for (let score of testScores) {
            l.push(score.TestName);
            d.push(score.TestScore);
        }

        const canvas = document.getElementById('chartCanvas');
        const context = canvas.getContext('2d');

        let myChart = new Chart(context, {
            type: 'line',   // change this to 'pie' if wanted

            data: {
                labels: l,
                datasets: [{
                    label: 'Test results',
                    data: d,
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

    });

}

function RunLoginpage() {



    window.location.href = "/client/login.html";
}

function checkLogin() {

    let username = Cookies.get("username");

    let logInHTML = '';

    if (username === undefined) {

        let editButtons = document.getElementsByClassName("editButton");
        for (let button of editButtons) {
            button.style.visibility = "hidden";
        }

        let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            button.style.visibility = "hidden";
        }

        logInHTML = "Not logged in. <a href='/client/login.html'>Log in</a>";
    } else {

        let editButtons = document.getElementsByClassName("editButton");
        for (let button of editButtons) {
            button.style.visibility = "visible";
        }

        let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            button.style.visibility = "visible";
        }

        logInHTML = "Logged in as " + username + ". <a href='/client/login.html?logout'>Log out</a>";

    }

    document.getElementById("loggedInDetails").innerHTML = logInHTML;

}

