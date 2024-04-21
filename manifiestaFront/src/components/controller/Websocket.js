/* var socket = new SockJS('public/websocket');
var stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    stompClient.subscribe('public/topic/updates', function (update) {
        alert("Mise à jour reçue : " + update.body);
    });
});   */