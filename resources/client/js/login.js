
function RunLoginpage(){
    login(); //runs the login function
}

function login() {
    const form = document.getElementById("loginForm"); // makes the loginform ID a  constant
    const formData = new FormData(form);
    form.addEventListener("submit",event=>{ //on the login button click
        event.preventDefault(); //prevents default event from happening when clicked
        let formData = new FormData(form);
        fetch("/User/login", {method: 'post', body: formData} //gets the API login method
        ).then(response => response.json() //looks for a response from a JSON object and iif its's not returns an error
        ).then(responseData => {

            if (responseData.hasOwnProperty('error')) { // if the response data is located in the object then returns an error
                alert(responseData.error);
            } else {
                //if there is ano error it sets the username and token a cookie value
                Cookies.set("username", responseData.username);
                Cookies.set("token", responseData.token);

                window.location.href = '/client/index.html'; //links to the home page
            }
        });
    });


}
function logout() {

    fetch("/User/logout", {method: 'post'} //this runs the logout API method by fetching it to run
    ).then(response => response.json() // this looks for a response from the JSON object and if it is not a JSON object returns an error
    ).then(responseData => {
        if (responseData.hasOwnProperty('error')) {

            alert(responseData.error);

        } else {
            // this removes the usernames and the token from the cookies to not allow them to log back in without going through the login
            Cookies.remove("username");
            Cookies.remove("token");
            //this links the user back to the login page once they have logged out
            window.location.href = '/client/login.html';

        }
    });

}

