(function () {
    let button = document.querySelector("#cookieConsent button[data-cookie-string]");
    if (document.cookie.includes("cc")){
        let alert = button.parentElement;
        alert.parentElement.removeChild(alert);
    }
    if (button) {
        button.addEventListener("click", function (event) {
            document.cookie = button.dataset.cookieString;
        }, false);
    }
})();