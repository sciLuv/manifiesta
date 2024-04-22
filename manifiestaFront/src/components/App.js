import React, { useEffect, useState } from 'react';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { MusicVoteView } from './view/MusicVoteView';
import { JoinSessionView } from './view/JoinSessionView';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import SpotifyCallbackComponent from './controller/SpotifyCallbackComponent'; // Mettez à jour le chemin selon votre structure
import AccountCreationController from './controller/AccountCreationController';
import { URI_BASE } from '../env';
import { Container, Navbar, Nav, NavDropdown } from 'react-bootstrap';
import LoginView from './view/LoginView';
import LoginController from './controller/LoginController';

function App() {
  const [compteur, setCompteur] = useState(0);


  useEffect(() => {
    const socket = new SockJS(URI_BASE + '/public/websocket');
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, (frame) => {
      console.log('Connecté: ' + frame);
      stompClient.subscribe('/public/topic', (message) => {
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
    fetch(URI_BASE + '/public/incrementer')
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

  return (
    
    <div>
    <Router>
      <div>
        
        <Navbar expand="lg" className='border-bottom'>
          <Container>
            <Navbar.Brand as={Link} className='text-light' to="#home">Manifiesta</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" className="nav-custom" />
            <Navbar.Collapse  id="basic-navbar-nav">
              <Nav className="me-auto">
                <Nav.Link as={Link} className='text-light' to="/home">Accueil</Nav.Link>
                <Nav.Link as={Link} className='text-light' to="/spotify">spotify</Nav.Link>
                <Nav.Link as={Link} className='text-light' to="/account">créer un compte</Nav.Link>
                <Nav.Link as={Link} className='text-light' to="/login">Se connecter</Nav.Link>
                <NavDropdown title="Plus" id="basic-nav-dropdown">
                  <NavDropdown.Item as={Link} to="/action/3.1">Action</NavDropdown.Item>
                </NavDropdown>
              </Nav>
            </Navbar.Collapse>
          </Container>
        </Navbar>

        <Routes>
          <Route path="/home" element={<div>Accueil</div>} />
          <Route path="/spotify" element={<SpotifyCallbackComponent />} />
          <Route path="/account" element={<AccountCreationController />} />
          <Route path="/login" element={<LoginController />} />
        </Routes>
      </div>
    </Router>
      <h1  className='text-light'>Compteur: <span>{compteur}</span></h1>
      <button onClick={handleIncrement}>Incrémenter</button>
      <MusicVoteView/>
      <button onClick={handleAuthorization}>Autoriser l'accès Spotify</button>

      
      {spotifyAuthorizationUri && <p>URI de redirection Spotify : {spotifyAuthorizationUri}</p>}

      
    </div>
  );
}

export default App;
