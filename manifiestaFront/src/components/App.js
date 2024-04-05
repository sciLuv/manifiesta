import React, { useEffect, useState } from 'react';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

function App() {
  const [compteur, setCompteur] = useState(0);

  useEffect(() => {
    const socket = new SockJS('http://localhost:8080/websocket');
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, frame => {
      console.log('Connecté: ' + frame);
      
      stompClient.subscribe('/topic/compteur', message => {
        setCompteur(message.body);
      });
    });

    return () => {
      if (stompClient) {
        stompClient.disconnect();
      }
    };
  }, []);

  return (
    <div>
      <h1>Compteur: <span>{compteur}</span></h1>
    </div>
  );
}

export default App;
