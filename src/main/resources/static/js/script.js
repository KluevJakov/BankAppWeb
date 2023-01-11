let cooldown = false;

let idClientForUpdating = document.getElementById('idClientForUpdating');
let clientForUpdatingName = document.getElementById('clientForUpdatingName');
let clientForUpdatingSurname = document.getElementById('clientForUpdatingSurname');
let clientForUpdatingPatr = document.getElementById('clientForUpdatingPatr');
let clientForUpdatingButton = document.getElementById('clientForUpdatingButton');

idClientForUpdating.addEventListener('input', autoUploadFIO);

function autoUploadFIO(e) {
    if (cooldown == false) {
        cooldown = true;
        setTimeout(cooldownOff(e), 3000);
    }
}

function cooldownOff (e) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/getClient?id=' + e.target.value, false);
    xhr.send();
    if (xhr.status == 200) {
        let client = JSON.parse(xhr.responseText);

        clientForUpdatingName.disabled = false;
        clientForUpdatingSurname.disabled = false;
        clientForUpdatingPatr.disabled = false;
        clientForUpdatingButton.disabled = false;

        clientForUpdatingName.value = client.name;
        clientForUpdatingSurname.value = client.surname;
        clientForUpdatingPatr.value = client.patronymic;
    } else {
        clientForUpdatingName.value = '';
        clientForUpdatingSurname.value = '';
        clientForUpdatingPatr.value = '';

        clientForUpdatingName.disabled = true;
        clientForUpdatingSurname.disabled = true;
        clientForUpdatingPatr.disabled = true;
        clientForUpdatingButton.disabled = true;
    }
    cooldown = false;
}