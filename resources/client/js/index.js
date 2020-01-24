function pageLoad() {

    let now = new Date();

    let myHTML = '<div style="text-align:center;">'   // aligns the text for my main heading
        + '<div style="font-style: italic;">' //this is for the font style that will be being set for the heading
        + '</div>'
        + '</div>';

    document.getElementById("testDiv").innerHTML = myHTML; //sets the testDiv to the myHTML variable

    let l = []; //this sets the label value for the testscores to an empty array so i can populate it
    let d = []; //this sets an array for the testscores to be empty to be populated by the scores as a chart

    fetch('/Tests/get/', {method: 'get'} //this line runs the get method to add the values from it to the graph
    ).then(response => response.json()
    ).then(testScores => {

        for (let score of testScores) {  //this runs until there are no more testscores to add to the graph
            l.push(score.TestName); //this adds the name of the test to the graph
            d.push(score.TestScore); //this adds the score of the test to the graph
        }

        const canvas = document.getElementById('chartCanvas'); //this finds the element of chartCanvas in the html and makes it constant
        const context = canvas.getContext('2d'); // this makes a drawing of the canvas in 2d

        let myChart = new Chart(context, {   // creates a chart with the drawing properties of the context (the drawing)
            type: 'line',   //this draws the graph as a line chart
            //this data populates the data of the graph with the test results of d containing the testscores
            data: {
                labels: l,
                datasets: [{
                    label: 'Test results',
                    data: d,
                }]
            },
            //the options set what scales i want my graph to have so i set it to begin at 0 so that it can still account for scores of 0 and increment up
            //responsive being set to false means that the graph will not respond to any page changes such as being resized
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

