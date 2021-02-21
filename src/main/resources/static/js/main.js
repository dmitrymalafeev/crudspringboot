let requestUrl = 'http://localhost:8080/admin/api/users'

function deleteModal(id) {
    fetch(requestUrl + '/' + id)
        .then(response => response.json())
        .then(result => fillFields(result))
    function fillFields(user) {
        $('#deleteId').val(user.id);
        $('#deleteName').val(user.name);
        $('#deleteLastName').val(user.lastName);
        $('#deleteAge').val(user.age);
        $('#deleteEmail').val(user.email);
        $('#deletePassword').val(user.password);
        $('#acceptDelete').attr('onclick','deleteUser(' + user.id + ')')
        $('#delModal').modal()
    }
}

function deleteUser(id) {
    fetch(requestUrl + '/' + id , {
        method : 'DELETE'
    })
        .then(() => {
            $('#delModal').modal("hide")
            refreshData();
        })
}

function editModal(id) {
    fetch(requestUrl + '/' + id)
        .then(response => response.json())
        .then(result => fillFields(result))
    function fillFields(user) {
        $('#editId').val(user.id);
        $('#editName').val(user.name);
        $('#editLastName').val(user.lastName);
        $('#editAge').val(user.age);
        $('#editEmail').val(user.email);
        $('#editPassword').val(user.password);
        $('#editModal').modal()
        $('#acceptEdit').attr('onclick','editUser(' + user.id + ')')
    }
}

function editUser(id) {
    fetch(requestUrl + '/' + id,
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: "PUT",
            body: JSON.stringify(
                {
                    name: $('#editName').val(),
                    lastName: $('#editLastName').val(),
                    age: $('#editAge').val(),
                    email: $('#editEmail').val(),
                    password: $('#editPassword').val(),
                    rolesStrings: $('#editRoles').val()
                })
        }).then((re) => {
            $('#editModal').modal("hide")
            refreshData()
    })
}

function addUser() {
    fetch(requestUrl,
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify(
                {
                    name: $('#newName').val(),
                    lastName: $('#newLastName').val(),
                    age: $('#newAge').val(),
                    email: $('#newEmail').val(),
                    password: $('#newPassword').val(),
                    rolesStrings: $('#newRoles').val()
                 })
        }).then((r) => {
            if (r.ok) {
                refreshData()
                // $('#successModal').modal()
            }
    })
}

function refreshData() {
    fetch(requestUrl)
        .then(response => response.json())
        .then(result => refreshTable(result))

    function refreshTable(users) {
        let tBody =''
        $('#usersTableBody').find('tr').remove();
        $.each(users, function(key, object){
            let roles ='';
            $.each(object.roles, function (k, o) {
                roles += o.name + ' '
            })
            tBody += ('<tr>');
            tBody += ('<td>' + object.id + '</td>');
            tBody += ('<td>' + object.name + '</td>');
            tBody += ('<td>' + object.lastName + '</td>');
            tBody += ('<td>' + object.age + '</td>');
            tBody += ('<td>' + object.email + '</td>');
            tBody += ('<td>' + roles + '</td>');
            tBody += ('<td><button type="button" onclick="editModal(' + object.id + ')" class="btn btn-primary">Edit</button></td>');
            tBody += ('<td><button type="button" onclick="deleteModal(' + object.id + ')" class="btn btn-danger">Delete</button></td>');
            tBody += ('</tr>');
        });
        $('#usersTableBody').html(tBody);
    }
}

refreshData()


