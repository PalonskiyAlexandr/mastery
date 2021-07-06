function checkPasswordMatch() {
        var password = $("#NewPassword").val();
        var confirmPassword = $("#ConfirmPassword").val();
        if (password != confirmPassword)
            $("#CheckPasswordMatch").html("Passwords does not match!");
        else
            $("#CheckPasswordMatch").html("Passwords match.");
    }
    $(document).ready(function () {
       $("#ConfirmPassword").keyup(checkPasswordMatch);
    });