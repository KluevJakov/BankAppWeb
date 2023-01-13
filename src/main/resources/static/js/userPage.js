let currentUser = sendRequest('GET', '/getCurrentUser', null);

let profileSurname = document.getElementById("profileSurname");
let profileName = document.getElementById("profileName");
let profilePatromynic = document.getElementById("profilePatromynic");
let profileEmail = document.getElementById("profileEmail");

profileSurname.innerHTML += currentUser.surname; //['surname']
profileName.innerHTML += currentUser.name;
profilePatromynic.innerHTML += currentUser.patronymic;
profileEmail.innerHTML += currentUser.email;

let currentUserAccounts = sendRequest('GET', '/getCurrentUserAccounts', null);

let accountsList = document.getElementById('accountsList');

for (let i = 0; i < currentUserAccounts.length; i++) {
    accountsList.innerHTML += JSON.stringify(currentUserAccounts[i]) + "<br>";
}

transferButton.addEventListener('click', transfer);

function transfer () {
    //sendRequest('POST', '');
}

function sendRequest(method, mapping, data) {
    var xhr = new XMLHttpRequest();
    xhr.open(method, mapping, false);

    if (method == 'POST' && data != null) {
        xhr.send(data);
    } else {
        xhr.send();
    }

    if (xhr.status == 200) {
        return JSON.parse(xhr.responseText);
    } else {
        console.log(xhr.status);
    }
}