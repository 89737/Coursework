
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

    fetch("/User/logout", {method: 'post'}
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

