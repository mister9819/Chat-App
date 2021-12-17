

// signup("A", "A", 20, "Rice", "none");

var message_template_user = $("<li class=\"clearfix\"><span class='mid' style='display: none'></span>\n" +
    "                                    <div class=\"message-data\">\n" +
    "                                        <img src=\"https://bootdey.com/img/Content/avatar/avatar7.png\" alt=\"avatar\">\n" +
    "                                        <span class=\"message-user-name\"><strong>You</strong></span>\n" +
    "                                        <span class=\"message-data-time\">10:20 AM, Today</span>\n" +
    "                                    </div>\n" +
    "                                    <div class=\"message other-message my-message\">\n" +
    "                                        <span class=\"message-text\"></span>\n" +
    "                                        <div style=\"position: absolute; bottom:-15px; right: 10px\" class=\"hidden-sm text-right\">\n" +
    "                                           <button type=\"button\" class=\"btn btn-sm btn-outline-secondary dropdown-toggle dropdown-toggle-split\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n" +
    "                                            <span class=\"sr-only\">Toggle Dropdown</span>\n" +
    "                                        </button>\n" +
    "                                        <div class=\"dropdown-menu dropdown-menu-right\">\n" +
    "                                            <a class=\"dropdown-item btn-recall\" href=\"#\">Recall</a>\n" +
    "                                            <a class=\"dropdown-item btn-edit\" href=\"#\">Edit</a>\n" +
    "                                        </div>" +
    "                                        </div>\n" +
    "                                    </div>\n" +
    "                                </li>");

var message_template_admin = $("<li class=\"clearfix\"><span class='mid' style='display: none'></span>\n" +
    "                                    <div class=\"message-data\">\n" +
    "                                        <img src=\"https://bootdey.com/img/Content/avatar/avatar7.png\" alt=\"avatar\">\n" +
    "                                        <span class=\"message-user-name\"><strong class='name'>You</strong></span>\n" +
    "                                        <span class=\"message-data-time\">10:20 AM, Today</span>\n" +
    "                                    </div>\n" +
    "                                    <div class=\"message my-message\">\n" +
    "                                        <span class=\"message-text\"></span>\n" +
    "                                        <div style=\"position: absolute; bottom:-15px; right: 10px\" class=\"hidden-sm text-right\">\n" +
    "                                           <button type=\"button\" class=\"btn btn-sm btn-outline-secondary dropdown-toggle dropdown-toggle-split\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n" +
    "                                            <span class=\"sr-only\">Toggle Dropdown</span>\n" +
    "                                        </button>\n" +
    "                                        <div class=\"dropdown-menu dropdown-menu-right\">\n" +
    "                                            <a class=\"dropdown-item btn-delete\" href=\"#\">Delete</a>\n" +
    "                                        </div>" +
    "                                        </div>\n" +
    "                                    </div>\n" +
    "                                </li>");

var message_template_other = $("<li class=\"clearfix\"><span class='mid' style='display: none'></span>\n" +
    "                                    <div class=\"message-data\">\n" +
    "                                        <img src=\"https://bootdey.com/img/Content/avatar/avatar1.png\" alt=\"avatar\">\n" +
    "                                        <strong><span class='name'>Vincent Porter</span></strong>\n" +
    "                                        <span class=\"message-data-time\">10:15 AM, Today</span>\n" +
    "                                    </div>\n" +
    "                                    <div class=\"message my-message\"><span class=\"message-text\"></span></div>\n" +
    "                                </li>");

var joined_room_template = $("<li class=\"clearfix chatroom\"><span class='cid' style='display: none'></span>\n" +
    "                                <div class=\"about\">\n" +
    "                                    <div class=\"name\">Chat room 1</div>\n" +
    "                                    <div class=\"status\"><span class=\"current-size\">1</span>/<span class=\"max-size\">10</span> people</div>\n" +
    "                                </div>\n" +
    "                                <div class=\"hidden-sm text-right\">\n" +
    "                                    <button disabled class=\"btn btn-sm btn-secondary\">\n" +
    "                                        <i class=\"fa fa-key fa-sm\"></i>\n" +
    "                                    </button>\n" +
    "                                    <button class=\"btn btn-leave btn-outline-warning\"><i class=\"fa fa-sign-out\"></i></button>\n" +
    "                                </div>\n" +
    "                            </li>")

var new_room_template = $("<li class=\"clearfix\"><span class='cid' style='display: none'></span>\n" +
    "                            <div class=\"about\">\n" +
    "                                <div class=\"name\">Chat room 7</div>\n" +
    "                                <div class=\"status\"><span class=\"current-size\">1</span>/<span class=\"max-size\">10</span> people</div>\n" +
    "                            </div>\n" +
    "                            <div class=\"hidden-sm text-right\">\n" +
    "                                    <button disabled class=\"btn btn-sm btn-secondary\">\n" +
    "                                        <i class=\"fa fa-key fa-sm\"></i>\n" +
    "                                    </button>\n" +
    "                                <button class=\"btn btn-outline-success btn-join\"><i class=\"fa fa-sign-in\"></i></button>\n" +
    "                            </div>\n" +
    "                            <hr>\n" +
    "                        </li>")

var chatroom_user_template = $("<li class=\"clearfix\"><span class='uid' style='display: none'></span>\n" +
    "                                <img alt=\"avatar\">\n" +
    "                                <div class=\"about\">\n" +
    "                                    <div class=\"name\">Dean Henry</div>\n" +
    "                                </div>\n" +
    "                                <br>\n" +
    "                                <div style=\"float: right;\" class=\"hidden-sm text-right\">\n" +
    "                                    <button class=\"btn btn-direct-chat btn-outline-success\"><i class=\"fa fa-envelope\"></i></button>\n" +
    "                                    <button class=\"btn btn-report btn-outline-danger\"><i class=\"fa fa-flag\"></i></button>\n" +
    "                                </div>\n" +
    "                            </li>")

function createAdminMessage(mid, username, body, type, avatar, time) {
    // console.log(body);
    let x = $(message_template_admin).clone();
    x.find("span.message-text").text(body);
    x.find(".mid").text(mid);
    x.find(".name").text(username);
    x.find("img").attr("src", "avatars/" + avatar);
    x.find(".message-data-time").text(time);
    if (type == "image") {
        x.find(".message").css("font-size", "80px");
        x.find(".message").css("background", "white");
        x.find("span.message-text").css("margin-right", "10px");
        x.find(".my-message").addClass("without-my-message");
    } else if (type == "link") {
        x.find("span.message-text").html("<a href='https://" + body + "'>" + body + "</a>");
    }
    $("#chat-history-list").append(x);
}


function createUserMessage(mid, body, type, time) {
    // console.log(body);
    let x = $(message_template_user).clone();
    x.find("span.message-text").text(body);
    x.find(".mid").text(mid);
    x.find("img").attr("src", "avatars/" + user_avatar);
    x.find(".message-data-time").text(time);
    if (type == "image") {
        x.find(".message").css("font-size", "80px");
        x.find(".message").css("background", "white");
        x.find("span.message-text").css("margin-right", "10px");
        x.find(".my-message").addClass("without-my-message");
        x.find(".btn-edit").remove();
    } else if (type == "link") {
        x.find("span.message-text").html("<a href='https://" + body + "'>" + body + "</a>");
    }
    $("#chat-history-list").append(x);
}

function createOtherMessage(mid, username, body, type, avatar, time) {
    let x = $(message_template_other).clone();
    x.find("span.message-text").text(body);
    x.find(".mid").text(mid);
    x.find(".name").text(username);
    x.find("img").attr("src", "avatars/" + avatar);
    x.find(".message-data-time").text(time);
    if (type == "image") {
        x.find(".message").css("font-size", "80px");
        x.find(".message").css("background", "white");
        x.find(".my-message").addClass("without-my-message");
    } else if (type == "link") {
        x.find("span.message-text").html("<a href='https://" + body + "'>" + body + "</a>");
    }
    $("#chat-history-list").append(x);
}

function changePasswordVisibility() {
    let val = $('#chatroom_visibility').val();
    $('#chatroom_password').removeAttr("disabled");
    if (val === "Public") {
        $("#chatroom_password").attr("disabled", "disabled");
    }
}

// Send text
function sendTextMessage() {
    let message = $('#message-body')
    //createUserMessage(message.val(), false)
    if (message != "") {
        $.post("/sendMsg",
            {
                userId,
                receiverId: userId,
                roomId: chatroomId,
                type: "text",
                data: message.val()
            }, (msg) => {
                if (msg === '"Msg Id: -1"') {
                    $("#modal_warning").modal('show');
                } else if (msg === '"Msg Id: -2"') {
                    $("#modal_banned").modal('show');
                }
            });
        message.val("");
    }
}

// Send emoji
$('#emoji-list a').on('click', function () {
    // createUserMessage($(this).text(), true);
    $.post("/sendMsg",
        {
            userId,
            receiverId: userId,
            roomId: chatroomId,
            type: "image",
            data: $(this).text()
        });
});

// Send link
function sendLinkMessage() {
    let message = $('#message-body')
    //createUserMessage(message.val(), false)
    if (message != "") {
        $.post("/sendMsg",
            {
                userId,
                receiverId: userId,
                roomId: chatroomId,
                type: "link",
                data: message.val()
            });
        message.val("");
    }
}

function emptyPage() {
    $("#new_rooms").find("li").remove();
    $("#chatroom_users").find("li").not(':first').remove();
    $("#chat-history-list").find("li").remove();
    $("#joined_rooms").find("li").remove();
    $(".chat-about").text(" ");
    $(".chat-message").css("visibility", "hidden");
    $(".invite-container").css("visibility", "hidden");
    $("#no_joined_rooms").css("visibility", "visible");
}

$("#test_btn").click(function () {
    receiveDirectMessage("hey", 2);
})

function createMessage(data, sender) {
    // console.log(data.type)
    switch (data.type) {
        case "text":
        case "direct":
        case "image":
        case "link":
            // if (adminSet.includes(userId)) {
            //     createAdminMessage(data.id, data.text, /\p{Emoji}/u.test(data.body), data.timestamp)
            // }
            // else
            if (adminSet.includes(parseInt(userId))) {
                if (data.senderId == userId) {
                    createUserMessage(data.id, data.text, data.type, data.timestamp)
                } else {
                    // console.log("here");
                    createAdminMessage(data.id, sender.username, data.text, data.type, sender.avatar, data.timestamp)
                }
            } else {
                if (data.senderId == userId) {
                    createUserMessage(data.id, data.text, data.type, data.timestamp)
                } else {
                    createOtherMessage(data.id, sender.username, data.text, data.type, sender.avatar, data.timestamp)
                }
            }
            break
        case "action":
            // loadUserChatRoom();
            if (data.action !== "reloadroom" && data.action !== "reloaduser" && data.action !== "reloadroomlist") {
                createChatroomMessage(sender.username, data.action)
                // console.log(sender.username);
            }
            break
        default:
            break
    }
}

let webSocket, userId, chatroomId, username, admin;
let users = new Map();
let adminSet = [];
let inviteId = 0

window.onload = function () {
    //Init
    //signup("A", "A", 20, "Rice", "none");
    //signup("B", "B", 20, "Rice", "none");

    //localStorage.setItem("userId", "abc");
    userId = localStorage.getItem("userId");
    if (userId === null) {
        window.location.href = "login.html"
    }
    //console.log("User " + userId);
    webSocket = new WebSocket("wss://" + location.hostname + ":" + location.port + "/chatapp?userId=" + userId);
    //console.log(webSocket)
    webSocket.onclose = () => alert("WebSocket connection closed");
    $.get(`/downloadInfo/userList`, (data) => {
        data = JSON.parse(data);
        for (const datum of data) {
            users.set(datum.userId, datum);
            if (datum.userId == userId) {
                updateUser(datum.username, datum.avatar);
                user_avatar = datum.avatar;
            }
        }
    })

    function updateChatRoom(message) {
        // TODO: convert the data to JSON and use .append(text) on a html element to append the message to the chat area
        $('#chatArea').append(data.userMessage);
    }

    loadUserChatRoom()

    webSocket.onmessage = (msg) => {
        //console.log("onmessage")
        let data = JSON.parse(msg.data);
        //console.log(data);
        let message = JSON.parse(data.message);
        //console.log(message);
        switch (message.type) {
            case "action":
                if (message.action === "reloadroom") {
                    $("#chat-history-list").empty()
                    $.get(`/loadChatRoomMessage?userId=${userId}&chatRoomId=${chatroomId}`, (data) => {
                        data = JSON.parse(data)
                        for (const datam of data) {
                            //console.log(users)
                            createMessage(datam, users.get(datam.senderId))
                        }
                    })
                } else if (message.action === "reloaduser") {
                    //console.log("reload room user")
                    location.reload(true);
                    // $("#chatroom_users").empty();
                    $("#chatroom_users").find("li").not(".first").remove();
                    $.get(`/loadChatRoomUsers?chatRoomId=${chatroomId}`, (data) => {
                        data = JSON.parse(data);
                        //console.log(data);
                        $("#chatroom_users").find("li").not(".first").remove();

                        for (const datum of data) {
                            if (parseInt(datum.userId) === parseInt(userId)) {
                                updateUser(datum.username, datum.avatar);
                            } else {
                                createChatroomUser(datum.userId, datum.username, datum.avatar, false);
                            }
                        }
                    })
                } else {
                    // console.log(message.action)
                    let sender = JSON.parse(data.sender);
                    // console.log(sender.userId)
                    // console.log(sender.userId)

                    if (message.action === "reloadroomlist") {
                        //console.log("reload chat room")
                        loadUserChatRoom();
                    } else {
                        //console.log("some leave/join room")
                        let sender = JSON.parse(data.sender)
                        createMessage(message, sender)
                    }
                }
                break
            case "text":
            case "image":
            case "link": {
                let sender = JSON.parse(data.sender);
                createMessage(message, sender)
                break;
            }
            case "direct": {
                if (message.senderId != userId) {
                    receiveDirectMessage(message.text, message.senderId)
                }
                break;
            }
            case "invitation": {
                if (message.receiverId == userId) {
                    createInvite(users.get(message.senderId).username, message.roomId);
                    inviteId = message.roomId
                }
                break;
            }
        }
    };

    // Heartbeat message preventing connection from closing
    let heartbeat = setInterval(() => {
        webSocket.send(JSON.stringify({type: "heartbeat"}));
    }, 1000);


};

function loadUserChatRoom() {
    $("#joined_rooms").find("li").remove()
    $.get(`/loadUserChatRoom?userId=${userId}`, (data) => {
        data = JSON.parse(data);
        if (data.length !== 0) {
            for (let i = 0; i < data.length; i++) {
                let datum = data[i];
                let c = createJoinedRoom(datum.id, datum.name, datum.size, datum.type, "", datum.admin[0] == userId);
                if (i === 0) {
                    switchChatroom(c);
                }
                updateCurrentSize(c, Object.keys(datum.users).length);

            }
        }
    })
}

// TODO: redirect to login if user is not login
function generateChatroomMessage(message) {
    return $("<div class=\"separator\">" + message + "</div>");
}

function createChatroomMessage(username, message) {
    let log = username + " " + message
    let x = $("<li class=\"clearfix\"><div class=\"separator\">" + log + "</div></li>");
    $("#chat-history-list").append(x);
}

$("#logout").click(() => {
        window.location.href = "login.html"
        localStorage.clear();
    }
)

$(document).on('click', '.btn-leave', function (event) {
    event.preventDefault();
    current_private_room = $(this).parents("li");
    $("#modal_leave").modal('show');
    if (current_private_room.find(".btn-outline-danger").length === 0) {
        $('#modal_leave').find(".modal-body").text("Are you sure you want to leave the room " + current_private_room.find(".name").text() + "?");
    } else {
        $('#modal_leave').find(".modal-body").text("Are you sure you want to leave the room " + current_private_room.find(".name").text() + "? " +
            "Since you are an admin, this room will close on your action.");
    }
});

// TODO: $("#modal_banned").modal('show');
// TODO: $("#modal_warning").modal('show');

// TODO: generate invitation
function createInvite(username, roomname) {
    $("#modal_invite").find(".modal-body")
        .text("You have an invitation to join " + roomname + " by " + username)
    $("#modal_invite").modal("show")
}

// TODO: send something on invitation accept
function invitationResponse(accept) {
    if (accept) { // Invitation accepted
        // console.log("Accept invite")
        $.post("/joinChatroom", {
            chatroomId: inviteId,
            userId,
        })
    } else {
        // console.log("Decline invite")
    }
}

function leaveRoom(tx) {
    x = tx;
    let visibility = "Public"

    if (x.find(".d-none").length === 0) {
        visibility = "Private"
    }
    if (x.find(".btn-outline-danger").length === 0) { //Normal room

        if (chatroomId == x.find(".cid").text()) {
            // location.reload(true);
            $("#chat-history-list").empty()

            $("#no_joined_rooms").css("visibility", "visible");
            $("#chatroom_users").find("li").not(':first').remove();
        }


        createNewRoom(x.find(".name").text(), x.find(".max-size").text(), visibility.toLowerCase(), "", false);
        $.post("/operationToChatroom", {
            userId: userId,
            roomId: chatroomId,
            operation: "leave"
        })
    } else { // Admin leaving
        $.post("/operationToChatroom", {
            userId: userId,
            roomId: chatroomId,
            operation: "close"
        })
    }
    x.remove();
}

$("#find-chatroom-btn").click(() => {
    $("#new_rooms").find("li").remove();

    $.get(`/loadJoinChatRoom?userId=${userId}`, (data) => {
        for (const datum of JSON.parse(data)) {
            // console.log(datum);
            let c = createNewRoom(datum.id, datum.name, datum.size,
                datum.type[0].toUpperCase() + datum.type.slice(1))
            updateCurrentSize(c, Object.keys(datum.users).length);
        }
    });
});

$(document).on('click', '.btn-join', function (event) {
    event.preventDefault();
    let x = $(this).parents("li");
    let roomId = x.find(".cid").text();
    // console.log(roomId)
    if (x.find(".d-none").length === 0) { //Private room
        current_private_room = x;
        $('#modal_password').modal('show');
    } else {
        createJoinedRoom(x.find(".name").text(), x.find(".max-size").text(), "Public", "", false);
        $.post("/joinChatroom", {
            chatroomId: roomId,
            userId
        })
        x.remove();
    }

    $('#modal1').modal('hide');
});

function joinPrivateRoom() {
    createJoinedRoom(current_private_room.find(".name").text(),
        current_private_room.find(".max-size").text(),
        "Private", "", false);

    current_private_room.remove();
}

$(document).on('click', '.btn-recall', function (event) {
    event.preventDefault();
    let par = $(this).parents("li");
    let t = par.find(".mid").text();
    par.children().remove();
    // par.append(generateChatroomMessage("You recalled a message"));
    // console.log("RECALL ID: " + t);
    $.post("/operationToMsg", {
        userId: userId,
        roomId: chatroomId,
        msgId: t,
        operation: "recall",
    })
});

$(document).on('click', '.btn-delete', function (event) {
    event.preventDefault();
    let par = $(this).parents("li");
    let t = par.find(".mid").text();
    par.children().remove();
    $.post("/operationToMsg", {
        userId: userId,
        roomId: chatroomId,
        msgId: t,
        operation: "delete",
    })
});

$(document).on('click', '.btn-edit', function (event) {
    event.preventDefault();
    current_edit_element = $(this).parents("li");

    $('#modal_edit').modal({
        show: true
    });
    $("#edited_text").val(current_edit_element.find(".message-text").text());
});

function editMessage() {
    $(current_edit_element).find("span.message-text").text($("#edited_text").val());
    let t = current_edit_element.find(".mid").text();
    let val = current_edit_element.find(".message-text").text();
    // console.log("RECALL ID: " + t);
    $.post("/EditMsg", {
        userId: userId,
        roomId: chatroomId,
        msgId: t,
        value: val,
    })
}


$(document).on('click', '.btn-report', function (event) {
    event.preventDefault();
    let parent = $(this).parents("li");
    let user = parent.find(".name").text();
    let userId = parent.find(".uid").text();

    $("#modal_report").modal({
        show: true
    });

    if (parent.find(".fa-flag").length === 1) {
        //console.log("Report");
        $('#modal_report').find(".modal-body").text("Are you sure you want to report " + user + "?");
        $("#btn-report").text("Report");
        $.post("/operationToUser", {
            userId: userId,
            roomId: chatroomId,
            operation: "report"
        })
    } else {
        // console.log(userId);
        $('#modal_report').find(".modal-body").text("Are you sure you want to ban " + user + "?");
        $("#btn-report").text("Ban");
        $.post("/operationToUser", {
            userId: userId,
            roomId: chatroomId,
            operation: "block"
        })
    }
});

$(document).on('click', '.btn-direct-chat', function (event) {
    event.preventDefault();
    current_edit_element = $(this).parents("li");
    $('#modal_direct').modal({
        show: true
    });
    $("#send_to").text("Send a message to " + current_edit_element.find(".name").text());
});

function directMessage() {
    $.post("/sendMsg", {
        userId,
        roomId: chatroomId,
        receiverId: current_edit_element.find(".uid").text(),
        type: "direct",
        data: $("#direct_text").val(),
    })
    // TODO: First parameter is message, second is sender id, third is receiver id
    // console.log($("#direct_text").val(), userId, current_edit_element.find(".uid").text());

}

function leaveAllRooms() {
    $.get(`/leaveAllRoom?userId=${userId}`, (data) => {
        location.reload(true);
    });
}

function receiveDirectMessage(text, sender) {
    $('#modal_directr').modal({
        show: true
    });
    $("#direct_message").html(
        users.get(sender).username + " sent you a message: <br><strong>" + text + "</strong>"
    );
    current_edit_element = sender;
}

function directMessageR() {
    // TODO: First parameter is message, second is sender id, third is receiver id
    $.post("/sendMsg", {
        userId,
        roomId: chatroomId,
        receiverId: current_edit_element,
        type: "direct",
        data: $("#direct_text_reply").val(),
    })
    //console.log($("#direct_text_reply").val(), userId, current_edit_element);
}

function createRoomForm() {
    let name = $('#chatroom_name').val();
    let size = $('#chatroom_size').val();
    let visibility = $('#chatroom_visibility').val();
    let password = $('#chatroom_password').val();
    if (name != "" && size != "") {

        $.post("/createChatroom", {
            userId: localStorage.getItem("userId"), size: size,
            description: "no decription", name: name, type: visibility.toLowerCase(), password: password
        }, function (data) {
            // console.log(data);
            createJoinedRoom(data, name, size, visibility.toLowerCase(), password, true);
            $('#modal2').modal('hide');
        }, "json");
    }
}

function createNewRoom(cid, name, size, visibility, password, status) {
    let x = $(new_room_template).clone();
    x.find(".name").text(name);
    x.find(".cid").text(cid);
    x.find(".max-size").text(size);
    if (visibility === "Public") {
        x.find(".btn-secondary").addClass("d-none");
    } else {
        x.find(".btn-outline-success").attr("disabled", "disabled");
    }
    if (status === "full") {
        x.find(".btn-outline-success").attr("disabled", "disabled");
    } else if (status === "banned") {
        x.find(".btn-outline-success").attr("disabled", "disabled");
        x.find(".fa-sign-in").addClass("fa-ban");
        x.find(".fa-sign-in").removeClass("fa-sign-in");
        x.find(".btn-outline-success").addClass("btn-outline-danger");
    }

    $("#new_rooms").append(x);
    return x;
}

function createJoinedRoom(cid, name, size, visibility, password, admin) {
    let x = $(joined_room_template).clone();
    x.find(".name").text(name);
    x.find(".cid").text(cid);
    x.find(".max-size").text(size);
    if (visibility === "public") {
        x.find(".btn-secondary").addClass("d-none");
    }
    if (admin) {
        x.find(".btn-outline-warning").addClass("btn-outline-danger");
    }
    $("#joined_rooms").append(x);
    return x;
}

function updateCurrentSize(x, new_val) {
    x.find(".current-size").text(new_val);
    if (new_val == x.find(".max-size").text()) {
        x.find(".btn-outline-success").attr("disabled", "disabled");
    }
}

function createChatroomUser(uid, username, avatar, admin) {
    let x = $(chatroom_user_template).clone();
    x.find("img").attr("src", "avatars/" + avatar);
    x.find(".uid").text(uid);
    x.find(".name").text(username);
    if (admin) {
        x.find(".fa-flag").addClass("fa-ban");
        x.find(".fa-flag").removeClass("fa-flag");
    }
    $("#chatroom_users").append(x);
}

function updateUser(username, avatar) {
    let x = $("#chatroom_users").find("li").first();
    x.find("img").attr("src", "avatars/" + avatar);
    x.find(".name").text(username);
}

// TODO: invitation
function sendInvitation() {
    // console.log("Send invitation");
    // console.log($("#invitation_username").val());
    let username = $("#invitation_username").val();
    for (const user of Array.from(users.values())) {
        // console.log(user, username)
        if (user.username == username) {
            $.post("/invitation", {
                invitorId: userId,
                receiverId: user.userId,
                chatroomId: chatroomId
            })
            return
        }
    }


}

// GET MESSAGES AND USERLIST HERE
async function switchChatroom(x) {
    $(".invite-container").css("visibility", "hidden");
    $("#no_joined_rooms").css("visibility", "hidden");
    $(".chat-message").css("visibility", "visible");

    $(".chatroom").removeClass("active");
    $(".chat-about").text(x.find(".name").text());

    $("#chatroom_users").find("li").not(':first').remove();
    $("#chat-history-list").find("li").remove();

    // Chatroom ID to make REST calls
    let cid = x.find(".cid").text();
    chatroomId = cid;

    await Promise.resolve($.get(`/loadChatRoomUsers?chatRoomId=${cid}`, async (data) => {
        data = JSON.parse(data);
        await Promise.resolve($.get(`/loadRoomAdmin?chatRoomId=${cid}`, (data) => {
            adminSet = [];
            data = JSON.parse(data);
            for (const tmp of data) {
                adminSet.push(tmp);
            }
            //console.log(adminSet);
        }))
        //console.log(adminSet)
        let isAdmin = adminSet.includes(parseInt(userId));

        if (isAdmin) {
            $(".invite-container").css("visibility", "visible");
        }

        // console.log(userId, isAdmin);
        for (const datum of data) {
            if (datum.userId != userId) {
                createChatroomUser(datum.userId, datum.username, datum.avatar, isAdmin);
            }
        }
        //console.log(users);
    }))

    $.get(`/loadChatRoomMessage?chatRoomId=${cid}&userId=${userId}`, (data) => {
        data = JSON.parse(data);
        //console.log(data);
        for (const datam of data) {
            //console.log(datam);
            createMessage(datam, users.get(datam.senderId))
        }
    })

    x.addClass("active");
}

//Switch chatroom
$(document).on('click', '.chatroom', function () {
    switchChatroom($(this))
})

var user_avatar = "";
var current_edit_element;
var current_private_room;


// createChatroomMessage("You joined the chat");
// createUserMessage("Are we meeting today?", false, "10:12 AM, Today");
// createOtherMessage("Vincent Porter", "Yes!", false, "avatar1.png", "10:15 AM, Today");
// createUserMessage("Did you prepare for today's meeting? I heard there is going to be a lot of work!", false, "10:17 AM, Today");
// createOtherMessage("Aiden Chavez", "I am ready.", false, "avatar2.png", "10:18 AM, Today");
// createChatroomMessage("Mike Thomas deleted a message");
// createOtherMessage("Aiden Chavez", '😁', true, "avatar2.png", "10:18 AM, Today");
//
//
// c1 = createJoinedRoom(0, "Chat room 1", 10, "Private", "", false);
// c2 = createJoinedRoom(1, "Chat room 2", 15, "Public", "", false);
// c3 = createJoinedRoom(2, "Chat room 3", 10, "Public", "", true);
// c4 = createJoinedRoom(3, "Chat room 4", 10, "Public", "", false);
// c5 = createJoinedRoom(4, "Chat room 5", 10, "Public", "", false);
// c6 = createJoinedRoom(5, "Chat room 6", 10, "Public", "", false);
// switchChatroom(c2);
//
// c7 = createNewRoom(6, "Chat room 7", 10, "Public", "", "");
// c8 = createNewRoom(7, "Chat room 8", 15, "Public", "", "");
// c9 = createNewRoom(8, "Chat room 9", 10, "Public", "", "full");
// c10 = createNewRoom(9, "Chat room 10", 10, "Public", "", "banned");
// c11 = createNewRoom(10, "Chat room 11", 10, "Public", "", "");
// c12 = createNewRoom(11, "Chat room 12", 10, "Private", "", "");
//
//
// updateCurrentSize(c1, 4);
// updateCurrentSize(c2, 7);
// updateCurrentSize(c3, 2);
// updateCurrentSize(c4, 4);
// updateCurrentSize(c5, 4);
// updateCurrentSize(c6, 4);
//
// updateCurrentSize(c7, 4);
// updateCurrentSize(c8, 7);
// updateCurrentSize(c9, 10);
// updateCurrentSize(c10, 4);
// updateCurrentSize(c11, 4);
// updateCurrentSize(c12, 4);
//
// createChatroomUser("Vincent Porter", "avatar1.png", true);
// createChatroomUser("Aiden Chavez", "avatar2.png", true);
// createChatroomUser("Mike Thomas", "avatar3.png", false);
// createChatroomUser("Monica Ward", "avatar8.png", false);
// createChatroomUser("Dean  Henry", "avatar3.png", false);


var message_template_user2 = $("<li class=\"clearfix\">\n" +
    "                                    <div class=\"message-data\">\n" +
    "                                        <img src=\"https://bootdey.com/img/Content/avatar/avatar7.png\" alt=\"avatar\">\n" +
    "                                        <span class=\"message-user-name\"><strong>You</strong></span>\n" +
    "                                        <span class=\"message-data-time\">10:20 AM, Today</span>\n" +
    "                                    </div>\n" +
    "                                    <div class=\"message other-message my-message\">\n" +
    "                                        <span class=\"message-text\"></span>\n" +
    "                                        <div style=\"position: absolute; bottom:-15px; right: 10px\" class=\"hidden-sm text-right\">\n" +
    "                                       <button style=\"margin-right: -18px\" type=\"button\" class=\"btn btn-sm dropdown-toggle dropdown-toggle-split\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n" +
    "                                        <span class=\"sr-only\">Toggle Dropdown</span>\n" +
    "                                    </button>\n" +
    "                                    <div class=\"dropdown-menu dropdown-menu-right\">\n" +
    "                                        <a class=\"dropdown-item btn-delete\" href=\"#\">Delete</a>\n" +
    "                                        <a class=\"dropdown-item\" href=\"#\">Edit</a>\n" +
    "                                    </div>" +
    "                                        </div>\n" +
    "                                    </div>\n" +
    "                                </li>");