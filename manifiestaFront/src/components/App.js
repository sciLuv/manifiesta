import React, { useEffect, useState } from 'react';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import Incrementer from './controller/Incrementer'; // Assurez-vous que le chemin d'importation est correct
import { MusicVoteView } from './view/MusicVoteView';
import { JoinSessionView } from './view/JoinSessionView';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import SpotifyCallbackComponent from './controller/SpotifyCallbackComponent'; // Mettez à jour le chemin selon votre structure
import AccountCreationController from './controller/AccountCreationController';
import { URI_BASE } from '../env';


function App() {
  const [compteur, setCompteur] = useState(0);


  useEffect(() => {
    const socket = new SockJS(URI_BASE + '/websocket');
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
    fetch(URI_BASE + '/incrementer')
      .then(response => {
        if (!response.ok) {
          throw new Error('La requête a échoué');
        }
        return response.text();
      })
      .then(data => console.log(data))
      .catch(error => console.error('Erreur lors de l\'incrémentation:', error));
  };

  const [spotifyAuthorizationUri, setSpotifyAuthorizationUri] = useState('');

  const handleAuthorization = async () => {
    try {
      const response = await fetch(URI_BASE + '/spotify/authorize');
      if (response.ok) {
        const authorizationUri = await response.text();
        setSpotifyAuthorizationUri(authorizationUri);
        // Rediriger l'utilisateur vers l'URI de redirection Spotify
        window.location.href = authorizationUri;
      } else {
        console.error('Erreur lors de la récupération de l\'URI de redirection Spotify');
      }
    } catch (error) {
      console.error('Erreur lors de la récupération de l\'URI de redirection Spotify', error);
    }
  };
  const TestComponent = () => <div>Route Test</div>;
  return (
    <div>
      <h1>Compteur: <span>{compteur}</span></h1>
      <button onClick={handleIncrement}>Incrémenter</button>
      <MusicVoteView/>
      <JoinSessionView/>
      <h1>App React</h1>
      <button onClick={handleAuthorization}>Autoriser l'accès Spotify</button>
      <AccountCreationController />

      
      {spotifyAuthorizationUri && <p>URI de redirection Spotify : {spotifyAuthorizationUri}</p>}
      <Router>
        <Routes>
          <Route path="/spotify" element={<SpotifyCallbackComponent/>} />
          <Route path="/test" element={<TestComponent />} />
          <Route path="/account" element={<AccountCreationController />} />
        </Routes>
      </Router>
      
    </div>
  );
}

export default App;
