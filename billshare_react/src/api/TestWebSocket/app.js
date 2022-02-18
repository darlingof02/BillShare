import Stomp from "stompjs";
import SockJS from "sockjs-client";
import jquery from "jquery";
import jQuery from "jquery";


var stompClient = null;

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
    var socket = new SockJS('http://localhost:8080/gs-guide-websocket',
    null,
   {
       transports: ['xhr-streaming'], 
       headers: {'Authorization': token }
   });

    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
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
    stompClient.send("/app/hello", {}, JSON.stringify({'name': jQuery("#name").val()}));
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
});