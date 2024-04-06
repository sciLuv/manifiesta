import React, { useEffect, useState } from 'react';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import Incrementer from './controller/Incrementer'; // Assurez-vous que le chemin d'importation est correct
import { MusicVoteView } from './view/MusicVoteView';
import { JoinSessionView } from './view/JoinSessionView';

function App() {
  const [compteur, setCompteur] = useState(0);

  useEffect(() => {
    const socket = new SockJS('http://127.0.0.1:8080/websocket');
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, (frame) => {
      console.log('Connecté: ' + frame);
      stompClient.subscribe('/topic/compteur', (message) => {
        console.log("test");
        setCompteur(JSON.parse(message.body));
      });
    });

    return () => {
      if (stompClient) {
        stompClient.disconnect();
      }
    };
  }, []);

  const handleIncrement = () => {
    // Utilisation de l'API fetch pour envoyer une requête GET à /incrementer
    fetch('http://127.0.0.1:8080/incrementer')
      .then(response => {
        if (!response.ok) {
          throw new Error('La requête a échoué');
        }
        return response.text();
      })
      .then(data => console.log(data))
      .catch(error => console.error('Erreur lors de l\'incrémentation:', error));
  };

  return (
    <div>
      <h1>Compteur: <span>{compteur}</span></h1>
      <button onClick={handleIncrement}>Incrémenter</button>
      <MusicVoteView/>
      <JoinSessionView/>
    </div>
  );
}

export default App;
