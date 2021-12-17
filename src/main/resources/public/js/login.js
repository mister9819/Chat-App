'use strict';

/**
 * A post request to login
 */

$("#login").submit((event) => {
    event.preventDefault();
    $.post("/login", {"username": $("#username").val(), "password": $("#password").val()}, (data) => {
        //console.log(data);
        if (parseInt(data) !== -1) {
            localStorage.setItem("userId", data);
            window.location.href = "index.html"
        } else {
            $("#wrong_credentials").css("display", "block");
        }
    });
})

/**
 * A post request to signup
 */
function signup(username, password, cpassword, age, school, interests) {
    if (password == cpassword) {
        let avatarImage = $("#plist li.clearfix.border").find("img")[0].src.split("/").at(-1)
        let obj = {
            avatar: avatarImage,
            username: username,
            password: password,
            age: age,
            school: school,
            interests: interests
        }
        $.post("/signup", obj, function (data) {
            if (data === -1) {
                $("#usernameTaken").css("display", "block");
            }
            else {
                localStorage.setItem("userId", data);
                window.location.href = "index.html"
            }
        }, "json");
    } else
        {
            $("#wrong_passwords").css("display", "block");
        }
    }
