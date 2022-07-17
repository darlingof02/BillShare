import Stomp from "stompjs";
import SockJS from "sockjs-client";
import jQuery from "jquery";


var stompClient = null;
// const IP = '192.168.0.125'
const IP = '192.168.86.166'
// const IP = 'localhost'

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
// const token =  "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MkB0ZXN0LmNvbSIsImV4cCI6MTY1ODYxODAwMywiaWF0IjoxNjU4MDEzMjAzfQ.cKY9MXJTpw9OOzSvVDEynrLw9VSAUohhrCxrYE1Wn5aJjqlIk1nzt1IzhWIJleTFN5-2WeabHmVic3z4WP9GhQ"
const token =  "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwiZXhwIjoxNjU4NDQ0NjYzLCJpYXQiOjE2NTc4Mzk4NjN9.CCduvnBjvq9vXkbifwYC-bgB1XYtoW7GIdtvzp_ZSfyfgwImB-V6_6ymGVFHdO6NHjaqocW6E1Bsrh5iYOwCjw"
function connect() {
    var socket = new SockJS(`http://${IP}:8080/gs-guide-websocket`,
    null,
   {
       transports: ['xhr-streaming'], 
       headers: {'Authorization': token }
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
            // showGreeting(JSON.parse(greeting.body));
            showGreeting(greeting.body);
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