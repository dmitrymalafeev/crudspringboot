$(document).ready(function () {

    $('.table .editBtn').on("click", function (event) {
        event.preventDefault();
        const href = $(this).attr('href');
        $.get(href, function (user, status){
            $('#editId').val(user.id);
            $('#editName').val(user.name);
            $('#editLastName').val(user.lastName);
            $('#editAge').val(user.age);
            $('#editEmail').val(user.email);
            $('#editPassword').val(user.password);
            $('#editForm').attr('action', '/admin/' + user.id + '/update');
        });
        $('#editModal').modal();
    });

    $('.table .delBtn').on("click", function (event) {
        event.preventDefault();
        const href = $(this).attr('href');
        $.get(href, function (user, status){
            $('#deleteId').val(user.id);
            $('#deleteName').val(user.name);
            $('#deleteLastName').val(user.lastName);
            $('#deleteAge').val(user.age);
            $('#deleteEmail').val(user.email);
            $('#deletePassword').val(user.password);
            $('#delRef').attr('href','/admin/' + user.id + '/delete');
        });
        $('#delModal').modal();
    });
});