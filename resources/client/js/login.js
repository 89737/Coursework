
function RunLoginpage(){
    login();
}

function login() {
    const form = document.getElementById("loginForm");
    const formData = new FormData(form);
    form.addEventListener("submit",event=>{
        event.preventDefault();
        let formData = new FormData(form);
        fetch("/User/login", {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {

            if (responseData.hasOwnProperty('error')) {
                alert(responseData.error);
            } else {
                Cookies.set("username", responseData.username);
                Cookies.set("token", responseData.token);

                window.location.href = '/client/index.html';
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

