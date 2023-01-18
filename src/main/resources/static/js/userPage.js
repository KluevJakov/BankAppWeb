getCurrentUser();
getCurrentUserAccounts();
getCurrentUserTransactions();

/*
setInterval(function() {
    getCurrentUserAccounts();
    getCurrentUserTransactions();
}, 5000);
*/

transferButton.addEventListener('click', transfer);

function transfer () {
    let transferDebtorAccountId = document.getElementById('transferDebtorAccountId');
    let transferCreditorAccountId = document.getElementById('transferCreditorAccountId');
    let transferAmount = document.getElementById('transferAmount');

    let transferDTO = {
        transferDebtorAccountId : transferDebtorAccountId.value,
        transferCreditorAccountId : transferCreditorAccountId.value,
        transferAmount : transferAmount.value
    };

    let status = sendRequest('POST', '/executeTransfer', JSON.stringify(transferDTO));

    let statusMessage = document.getElementById("statusMessage");
    statusMessage.innerHTML = status.message;
    if (status.success) {
        statusMessage.style.color = 'green';
        getCurrentUserAccounts();
        getCurrentUserTransactions();
    } else {
        statusMessage.style.color = 'red';
    }
}

function sendRequest(method, mapping, data) {
    var xhr = new XMLHttpRequest();
    xhr.open(method, mapping, false);

    if (method == 'POST' && data != null) {
        xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
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

function getCurrentUser() {
    let currentUser = sendRequest('GET', '/getCurrentUser', null);

    let profileSurname = document.getElementById("profileSurname");
    let profileName = document.getElementById("profileName");
    let profilePatromynic = document.getElementById("profilePatromynic");
    let profileEmail = document.getElementById("profileEmail");

    profileSurname.innerHTML += currentUser.surname;
    profileName.innerHTML += currentUser.name;
    profilePatromynic.innerHTML += currentUser.patronymic;
    profileEmail.innerHTML += currentUser.email;
}

function getCurrentUserAccounts() {
    let currentUserAccounts = sendRequest('GET', '/getCurrentUserAccounts', null);

    let accountsList = document.getElementById('accountsList');

    accountsList.innerHTML = "";

    for (let i = 0; i < currentUserAccounts.length; i++) {
        let accountItem = document.createElement("div");
        let accountItemId = document.createElement("div");
        let accountItemName = document.createElement("div");
        let accountItemBalance = document.createElement("div");

        accountItemId.innerHTML = currentUserAccounts[i].id;
        accountItemName.innerHTML = currentUserAccounts[i].name;
        accountItemBalance.innerHTML = currentUserAccounts[i].balance;

        accountItem.className = "accountItem";
        accountItem.appendChild(accountItemId);
        accountItem.appendChild(accountItemName);
        accountItem.appendChild(accountItemBalance);

        accountsList.appendChild(accountItem);
    }
}

function getCurrentUserTransactions() {
    let currentUserTransactions = sendRequest('GET', '/getCurrentUserTransactions', null);

    let transactionsList = document.getElementById('transactionsList');

    transactionsList.innerHTML = "";

    for (let i = 0; i < currentUserTransactions.length; i++) {
        let transactionItem = document.createElement("div");
        let transactionItemSender = document.createElement("div");
        let transactionItemGetter = document.createElement("div");
        let transactionItemAmount = document.createElement("div");
        let transactionItemDate = document.createElement("div");

        let transactionTypeStyle = "";

        if (currentUserTransactions[i].transactionType == 'INTERNAL') {
            transactionTypeStyle = "trInternal";
        } else if (currentUserTransactions[i].transactionType == 'INCOME') {
            transactionTypeStyle = "trIncome";
        } else {
            transactionTypeStyle = "trOutcome";
        }

        transactionItemSender.innerHTML = currentUserTransactions[i].sender.name;
        transactionItemSender.classList.add("trItem1");
        transactionItemGetter.innerHTML = currentUserTransactions[i].getter.name;
        transactionItemGetter.classList.add("trItem2");
        transactionItemAmount.innerHTML = currentUserTransactions[i].valueOfPayment + " ₽";
        transactionItemAmount.classList.add("trItem3");
        transactionItemDate.innerHTML = dateFormatting(currentUserTransactions[i].dateOfPayment);
        transactionItemDate.classList.add("trItem4");

        transactionItem.classList.add("transactionItem", transactionTypeStyle);
        transactionItem.appendChild(transactionItemSender);
        transactionItem.appendChild(transactionItemGetter);
        transactionItem.appendChild(transactionItemAmount);
        transactionItem.appendChild(transactionItemDate);

        transactionsList.appendChild(transactionItem);
    }
}

function dateFormatting(dateString) {
    let ms = Date.parse(dateString);
    let date = new Date(ms);

    let yyyy = date.getFullYear();
    let mm = date.getMonth() + 1;
    let dd = date.getDate();
    let hh = date.getHours();
    let nn = date.getMinutes();

    if (dd < 10) dd = '0' + dd;
    if (mm < 10) mm = '0' + mm;
    if (nn < 10) nn = '0' + nn;
    if (hh < 10) hh = '0' + hh;

    return hh + ":" + nn + " " + dd + '.' + mm + '.' + yyyy;
}

