const nameRe = /[^a-zA-Z]/;
const usernameRe = /[^a-zA-Z0-9_]/;
let validForm = false;
let validFirstNameInput = false;
let validLastNameInput = false;
let validUsernameInput = false;
let validPasswordInput = false;
let validConfirmPasswordInput = false;

const form = document.querySelector("form");
form.addEventListener("input", validateForm);

const firstNameInput = document.getElementById("firstName");
firstNameInput.addEventListener("input", validateFirstName);

const lastNameInput = document.getElementById("lastName");
lastNameInput.addEventListener("input", validateLastName);

const usernameInput = document.getElementById("username");
usernameInput.addEventListener("input", validateUsername);

const passwordInput = document.getElementById("password");
passwordInput.addEventListener("input", validatePassword);

const confirmPasswordInput = document.getElementById("passwordConfirm");
confirmPasswordInput.addEventListener("input", validateConfirmPassword);

passwordInput.addEventListener("input", function() {
    if (confirmPasswordInput.value) {
        validateConfirmPassword.call(confirmPasswordInput);
    }
});

function validateFirstName() {
    const existingError = document.getElementById("firstNameError");
    if (existingError) {
        existingError.remove();
    }

    // Name contains digits or other special charachters
    if (nameRe.test(this.value)) {
        this.style.color = "red";
        const errorMessage = document.createElement("span");
        errorMessage.id = "firstNameError";
        errorMessage.style.color = "red";
        errorMessage.textContent = "Error, invalid characters detected";
        this.parentNode.insertBefore(errorMessage, this.nextSibling);
        validFirstNameInput = false;
    } else {
        this.style.color = "black";
        validFirstNameInput = true;
    }
}

function validateLastName() {
    const existingError = document.getElementById("lastNameError");
    if (existingError) {
        existingError.remove();
    }

    // Name contains digits or other special charachters
    if (nameRe.test(this.value)) {
        this.style.color = "red";
        const errorMessage = document.createElement("span");
        errorMessage.id = "lastNameError";
        errorMessage.style.color = "red";
        errorMessage.textContent = "Error, invalid characters detected";
        this.parentNode.insertBefore(errorMessage, this.nextSibling);
        validLastNameInput = false;
    } else {
        this.style.color = "black";
        validLastNameInput = true;
    }
}

function validateUsername() {
    const existingError = document.getElementById("usernameError");
    if (existingError) {
        existingError.remove();
    }

    // Username contains invalid symbols
    if (usernameRe.test(this.value)) {
        this.style.color = "red";
        const errorMessage = document.createElement("span");
        errorMessage.id = "usernameError";
        errorMessage.style.color = "red";
        errorMessage.textContent = "Error, invalid characters detected";
        this.parentNode.insertBefore(errorMessage, this.nextSibling);
        validUsernameInput = false;
    } else {
        this.style.color = "black";
        validUsernameInput = true;
    }
}

function validatePassword() {
    const existingError = document.getElementById("passwordError");
    if (existingError) {
        existingError.remove();
    }

    if (usernameRe.test(this.value) || this.value.length <= 8 || this.value.length >= 15) {
        this.style.color = "red";
        const errorMessage = document.createElement("span");
        errorMessage.id = "passwordError";
        errorMessage.style.color = "red";
        errorMessage.textContent = "Error, invalid password";
        this.parentNode.insertBefore(errorMessage, this.nextSibling);
        validPasswordInput = false;
    } else {
        this.style.color = "black";
        validPasswordInput = true;
    }
}

function validateConfirmPassword() {
    const existingError = document.getElementById("confirmPasswordError");
    if (existingError) {
        existingError.remove();
    }

    if (this.value !== passwordInput.value) {
        this.style.color = "red";
        const errorMessage = document.createElement("span");
        errorMessage.id = "confirmPasswordError";
        errorMessage.style.color = "red";
        errorMessage.textContent = "Error, confirm doesn't match";
        this.parentNode.insertBefore(errorMessage, this.nextSibling);
        validConfirmPasswordInput = false;
    } else {
        this.style.color = "black";
        validConfirmPasswordInput = true;
    }
}

function validateForm() {
    const confirmButton = document.getElementById("confirmButton");
    if (confirmButton) {
        confirmButton.remove();
    }

    if (validFirstNameInput && validLastNameInput && validUsernameInput && validPasswordInput && validConfirmPasswordInput) {
        validForm = true;
        // Create button
        const confirmButton = document.createElement("button");
        confirmButton.textContent = "Create Account";
        confirmButton.id = "confirmButton";
        confirmButton.addEventListener("click", () => {
            alert("Account creation in progress.");
            window.location.href = "/";
        });
        form.appendChild(confirmButton);
    } else {
        validForm = false;
    }
}