import React, { useEffect, useState } from 'react';
import SessionParameterView from '../view/SessionParameterView';
import { URI_BASE } from '../../env';
import SpotifyCallback from './SpotifyCallback';

const SessionParameterController = () => {
    const [username, setUsername] = useState('');
    const [passwordSession, setPasswordSession] = useState('');
    const [showErrorMessage, setShowErrorMessage] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const [qrCodeType, setQrCodeType] = useState('');
    const [passwordOrNot, setPasswordOrNot] = useState(false);
    const [isSpotifyConnected, setIsSpotifyConnected] = useState(false);
    const [spotifyAuthorizationUri, setSpotifyAuthorizationUri] = useState('');

    //useeffect quand spotifyAuthorizationUri change
    useEffect(() => {
        localStorage.getItem('spotifyToken') ? setSpotifyAuthorizationUri(localStorage.getItem('spotifyToken')) : setSpotifyAuthorizationUri('');
        localStorage.getItem('spotifyToken') ? setIsSpotifyConnected(true) : setIsSpotifyConnected(false);
    }, []);

    useEffect(() => {
       console.log(spotifyAuthorizationUri);
    }, [isSpotifyConnected]);

    // Gérer les changements de l'username
    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    };

    // Gérer les changements du mot de passe de la session
    const handlePasswordSessionChange = (event) => {
        setPasswordSession(event.target.value);
    };

    // Gérer la soumission des données pour la connexion
    const handleLoginSubmit = () => {
        // Simulez une validation ou une authentification
        if (!username || !passwordSession) {
            setErrorMessage('Le nom d’utilisateur et le mot de passe sont requis.');
            setShowErrorMessage(true);
        } else {
            setShowErrorMessage(false);
            // Effectuez la logique de connexion ou de validation ici
            console.log('Connexion en cours avec : ', username, passwordSession);
            // Redirection ou autre logique
        }
    };

    //gerer la selection d'un mot de passe ou non
    const changeCheckBoxPassword = (event) => {
        if(event.target.checked){
            setPasswordOrNot(true)
        } else {
            setPasswordOrNot(false)
        }
        console.log(passwordOrNot);
    }

    const handleAuthorization = async () => {
        try {
          const response = await fetch(URI_BASE + '/public/spotify/authorize');
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
        <>
                <SessionParameterView 
            username={username}
            passwordSession={passwordSession}
            errorMessage={errorMessage}
            showErrorMessage={showErrorMessage}
            onUsernameChange={handleUsernameChange}
            onPasswordSessionChange={handlePasswordSessionChange}
            onLoginSubmit={handleLoginSubmit}
            qrCodeType={qrCodeType}
            passwordOrNot={passwordOrNot}
            setQrCodeType={setQrCodeType}
            setPasswordOrNot={setPasswordOrNot}
            isSpotifyConnected={isSpotifyConnected}
            setIsSpotifyConnected={setIsSpotifyConnected} 
            changeCheckBoxPassword={changeCheckBoxPassword}
            handleAuthorization={handleAuthorization}
            spotifyAuthorizationUri={spotifyAuthorizationUri}
            setSpotifyAuthorizationUri={setSpotifyAuthorizationUri}
        />
        </>
    );
};

export default SessionParameterController;
