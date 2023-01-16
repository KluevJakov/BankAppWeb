getCurrentUser();
getCurrentUserAccounts();
getCurrentUserTransactions();

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
        accountItemId.className = "accountItemId";
        accountItemName.innerHTML = currentUserAccounts[i].name;
        accountItemName.className = "accountItemName";
        accountItemBalance.innerHTML = currentUserAccounts[i].balance;
        accountItemBalance.className = "accountItemBalance";

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
        let transactionItemId = document.createElement("div");
        let transactionItemName = document.createElement("div");
        let transactionItemBalance = document.createElement("div");

        transactionItemId.innerHTML = currentUserTransactions[i].sender;
        transactionItemId.className = "transactionItemId";
        transactionItemName.innerHTML = currentUserTransactions[i].valueOfPayment;
        transactionItemName.className = "transactionItemName";
        transactionItemBalance.innerHTML = currentUserTransactions[i].dateOfPayment;
        transactionItemBalance.className = "transactionItemBalance";

        transactionItem.className = "transactionItem";
        transactionItem.appendChild(transactionItemId);
        transactionItem.appendChild(transactionItemName);
        transactionItem.appendChild(transactionItemBalance);

        transactionsList.appendChild(transactionItem);
    }
}