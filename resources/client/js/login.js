function Loginpage() {

    let now = new Date();

    let myHTML = '<div style="text-align:center;">'   // aligns the text for my main heading
        + '<h1>Flashcard Revision!</h1>' // this is the main title for my index page and will show the writing within the h1 tags
        + '<img src="/client/img/logo.jpg" align="left" width="300px" height="100" alt="Logo"/>' // this loads in the image i'll be using and has the file path with the width being set to 300px wide
        + '<div style="font-style: italic;">' //this is for the font style that will be being set for the heading
        + 'login at ' + now.toLocaleTimeString() //this displays the time of which the page has been loaded
        + '</div>'
        + '</div>';

    document.getElementById("testDiv").innerHTML = myHTML; //sets the testDiv to the myHTML variable

}
/* function logout() {

    fetch("/user/logout", {method: 'post'}
    ).then(response => response.json()
).then(responseData => {
        if (responseData.hasOwnProperty('error')) {

        alert(responseData.error);

    } else {

        Cookies.remove("username");
        Cookies.remove("token");

        window.location.href = '/client/index.html';

    }
});

}
*/