import React, { useEffect, useState } from 'react';
import SessionParameterView from '../view/SessionParameterView';
import { URI_BASE } from '../../env';
import SpotifyCallback from './SpotifyCallback';

const SessionParameterController = ( {accessToken, setAccessToken, refreshToken, setRefreshToken, setUser ,user, mail,setMail,role,setRole} ) => {

    const [passwordSession, setPasswordSession] = useState('');
    const [showErrorMessage, setShowErrorMessage] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const [qrCodeType, setQrCodeType] = useState('');
    const [passwordOrNot, setPasswordOrNot] = useState(false);
    const [isSpotifyConnected, setIsSpotifyConnected] = useState(false);
    const [spotifyRefreshToken, setSpotifyRefreshToken] = useState('');
    const [spotifyToken, setSpotifyToken] = useState('');
    const [musicalStyles, setMusicalStyles] = useState(1);
    const [songsNumber, setSongsNumber] = useState(2);



    useEffect(() => {
        
        if(localStorage.getItem('user') != null && localStorage.getItem('user') != undefined && localStorage.getItem('user') != ""){
            setUser(localStorage.getItem('user'));
            setMail(localStorage.getItem('mail'));
            setRole(localStorage.getItem('role'));
            setAccessToken(localStorage.getItem('accessToken'));
            setRefreshToken(localStorage.getItem('refreshToken'));
        }

        const spotifyTokenTest = localStorage.getItem('spotifyToken');
        const spotifyRefreshTokenTest = localStorage.getItem('spotifyRefreshToken');
        
        spotifyTokenTest != "" && 
        spotifyTokenTest != null && 
        spotifyTokenTest != undefined  ?  setSpotifyToken(spotifyTokenTest) : setSpotifyToken('');
        spotifyTokenTest != "" &&
        spotifyTokenTest != null && 
        spotifyTokenTest != undefined  ?  setSpotifyRefreshToken(spotifyRefreshTokenTest) : setSpotifyRefreshToken('');
        spotifyTokenTest != "" &&
        spotifyTokenTest != null && 
        spotifyTokenTest != undefined  ?  setIsSpotifyConnected(true) : setIsSpotifyConnected(false);
     
        
        if(
            (user == "" && mail == "" && role != "user" && accessToken == "" && refreshToken == "" )&&
            (localStorage.getItem('user') == null || localStorage.getItem('user') == undefined || localStorage.getItem('user') == "")
        ){
            window.location.href = "/";
        };
        
        setTimeout(() => {
            localStorage.removeItem('spotifyToken');
            localStorage.removeItem('spotifyRefreshToken');
            localStorage.removeItem('user');
            localStorage.removeItem('mail');
            localStorage.removeItem('role');
            localStorage.removeItem('accessToken');
            localStorage.removeItem('refreshToken');
        }, 1000);


        

    }, []);


    // Gérer les changements du mot de passe de la session
    const handlePasswordSessionChange = (event) => {
        setPasswordSession(event.target.value);
    };

    // Gerer la selection du nombre de chansons
    const handleSongsNumberChange = (event) => {
        setSongsNumber(event.target.value);
    }

    // Gerer la selection du nombre de styles musicaux
    const handleMusicalStylesChange = (event) => {
        setMusicalStyles(event.target.value);
    }
        

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
            localStorage.setItem('user', user);
            localStorage.setItem('mail', mail);
            localStorage.setItem('role', role);
            localStorage.setItem('accessToken', accessToken);
            localStorage.setItem('refreshToken', refreshToken);
            const response = await fetch(URI_BASE + '/spotify/authorize', {
                headers :{
                    "Authorization" : "Bearer " + accessToken,
                    "Refresh-Token" : refreshToken
                }
            });
            if (response.ok) {
                const authorizationUri = await response.text();
                setSpotifyRefreshToken(authorizationUri);
                // Rediriger l'utilisateur vers l'URI de redirection Spotify
                window.location.href = authorizationUri;
            } else {
                console.error('Erreur lors de la récupération de l\'URI de redirection Spotify');
            }
        } catch (error) {
          console.error('Erreur lors de la récupération de l\'URI de redirection Spotify', error);
        }
      };


      const handleNewSession = async () => {
        try {
            const response = await fetch(URI_BASE + '/createSession', {
                method: 'POST',
                headers :{
                    "Authorization" : "Bearer " + accessToken,
                    "Refresh-Token" : refreshToken,
                    "Content-Type": "application/json"
                },
                  
                body: JSON.stringify({
                    sessionDto: {
                        idSession: null,
                        password: passwordSession,
                        songsNumber: songsNumber,
                        musicalStylesNumber: musicalStyles
                    },
                    userLoginDto: {
                        username: user,
                        password: ""
                    },
                    tokenDto: {
                        accessToken: spotifyToken,
                        refreshToken: spotifyRefreshToken
                    }
                })
            });
            if (response.ok) {
                const responseJson = await response.json();
                console.log(responseJson);
                if(responseJson.response == "Music is not played"){
                    setErrorMessage('La musique n\'est pas jouée');
                    setShowErrorMessage(true);
                }  else {
                    setShowErrorMessage(false);
                    console.log('Session créée avec succès');
                }
            } else {
                console.error('Erreur lors de la requete de création de session');
            }
        } catch (error) {
            console.error('Erreur lors de la requete de création de session', error);
        }               
    }


    return (
        <>
            <SessionParameterView 
                passwordSession={passwordSession}
                errorMessage={errorMessage}
                showErrorMessage={showErrorMessage}
                onPasswordSessionChange={handlePasswordSessionChange}
                qrCodeType={qrCodeType}
                passwordOrNot={passwordOrNot}
                setQrCodeType={setQrCodeType}
                setPasswordOrNot={setPasswordOrNot}
                isSpotifyConnected={isSpotifyConnected}
                setIsSpotifyConnected={setIsSpotifyConnected} 
                changeCheckBoxPassword={changeCheckBoxPassword}
                handleAuthorization={handleAuthorization}
                setSpotifyRefreshToken={setSpotifyRefreshToken}
                spotifyRefreshToken={spotifyRefreshToken}
                spotifyToken={spotifyToken}
                handleSongsNumberChange={handleSongsNumberChange}
                handleMusicalStylesChange={handleMusicalStylesChange}
                setSpotifyToken={setSpotifyToken}
                handleNewSession={handleNewSession}
            />
        </>
    );
};

export default SessionParameterController;
