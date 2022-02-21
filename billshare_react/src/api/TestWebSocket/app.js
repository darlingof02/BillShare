import Stomp from "stompjs";
import SockJS from "sockjs-client";
import jquery from "jquery";
import jQuery from "jquery";


var stompClient = null;
const IP = '192.168.86.74'

function setConnected(connected) {
    jQuery("#connect").prop("disabled", connected);
    jQuery("#disconnect").prop("disabled", !connected);
    if (connected) {
        jQuery("#conversation").show();
    }
    else {
        jQuery("#conversation").hide();
    }
    jQuery("#greetings").html("");
}
const token =  "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJZVU5JTlgxQFVDSS5FRFUiLCJleHAiOjE2NDU3NDUwMTcsImlhdCI6MTY0NTE0MDIxN30.T95E3hsi0EVfqHl9OgyGhE4NSRv-27fgYrgP9JdKO7e8XLL4u3sCQ9y8LE87GsLks2mug1rso50zVyIe6aNUag"
function connect() {
    var socket = new SockJS(`http://${IP}:8080/gs-guide-websocket`,
    null,
   {
       transports: ['xhr-streaming'], 
    //    headers: {'Authorization': token }
   });

    stompClient = Stomp.over(socket);
    stompClient.connect({"Authorization":token}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        stompClient.subscribe('/user/topic/private-greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    console.log("call send name")
    stompClient.send("/app/hello", {}, JSON.stringify({'name': jQuery("#name").val()}));
    //     stompClient.send("/topic/greetings", {}, JSON.stringify({'name': jQuery("#name").val()}));

}

function sendPrivateName() {
    stompClient.send("/app/private-hello", {}, JSON.stringify({'name': jQuery("#private-name").val()}));
}

function showGreeting(message) {
    console.log("ggggggggg")
    jQuery("#greetings").append("<tr><td>" + message + "</td></tr>");
}

jQuery(function () {
    jQuery("form").on('submit', function (e) {
        e.preventDefault();
    });
    jQuery( "#connect" ).click(function() { connect(); });
    jQuery( "#disconnect" ).click(function() { disconnect(); });
    jQuery( "#send" ).click(function() { sendName(); });
    jQuery( "#send-private" ).click(function() { sendPrivateName(); });
});