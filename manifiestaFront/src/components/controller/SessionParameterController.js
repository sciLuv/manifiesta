
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import SessionParameterView from '../view/SessionParameterView';
import { URI_BASE } from '../../env';

const SessionParameterController = ( {accessToken, setAccessToken, refreshToken, setRefreshToken, setUser ,user, mail,setMail,role,setRole} ) => {

    const navigate = useNavigate();

    console.log("prout");

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
    const [isQRCodeGlobal, setIsQRCodeGlobal] = useState(false);
    const [spotifyError, setSpotifyError] = useState(false);

    let RepeatHandleAuthorization = 0;
    let repeatRequestNewSession = 0;

    console.log(spotifyToken);
    console.log(spotifyRefreshToken);


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
        if(localStorage.getItem('spotifyConnectionError') === null ){
                        setSpotifyError(false);
                        spotifyTokenTest != "" && 
                        spotifyTokenTest != null && 
                        spotifyTokenTest != undefined  ?  setSpotifyToken(spotifyTokenTest) : setSpotifyToken('');
                        spotifyTokenTest != "" &&
                        spotifyTokenTest != null && 
                        spotifyTokenTest != undefined  ?  setSpotifyRefreshToken(spotifyRefreshTokenTest) : setSpotifyRefreshToken('');
                        spotifyTokenTest != "" &&
                        spotifyTokenTest != null && 
                        spotifyTokenTest != undefined  ?  setIsSpotifyConnected(true) : setIsSpotifyConnected(false);
        }
        if (
            (user == "" && mail == "" && role != "user" && accessToken == "" && refreshToken == "") &&
            (localStorage.getItem('user') == null || localStorage.getItem('user') == undefined || localStorage.getItem('user') == "")
        ) {
            navigate("/spotify-error");
        }
        
        setTimeout(() => {
            localStorage.removeItem('spotifyToken');
            localStorage.removeItem('spotifyRefreshToken');
            localStorage.removeItem('user');
            localStorage.removeItem('mail');
            localStorage.removeItem('role');
            localStorage.removeItem('accessToken');
            localStorage.removeItem('refreshToken');
            localStorage.removeItem('spotifyConnectionError');
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
                RepeatHandleAuthorization = 0;
                const authorizationUri = await response.text();
                setSpotifyRefreshToken(authorizationUri);
                // Rediriger l'utilisateur vers l'URI de redirection Spotify
                window.location.href = authorizationUri;
            } else {
                if((response.status === 401)|| (response.status === 500)){
                    console.log("response.header :" + response.headers.get('New-Access-Token'));
                    const newAccessToken = response.headers.get('New-Access-Token');
                    const newRefreshToken = response.headers.get('New-Refresh-Token');
        
                    // Vérifier que les deux tokens sont présents avant de mettre à jour les états
                    if (newAccessToken && newRefreshToken) {
                        RepeatHandleAuthorization++
                        setAccessToken(newAccessToken);
                        setRefreshToken(newRefreshToken);

                        if(RepeatHandleAuthorization < 2){
                        return handleAuthorization();
                        }
                    } else {
                        navigate('/deconnexion')
                    }

                }
                console.error('Erreur lors l\'obtention de l\'URI de redirection Spotify');
            }
        } catch (error) {
          console.error('Erreur lors de la récupération de l\'URI de redirection Spotify', error);
        }
      };


      const handleNewSession = async () => {
        try {
            const createSessionResponse = await fetch(URI_BASE + '/createSession', {
                method: 'POST',
                headers: {
                    "Authorization": "Bearer " + accessToken,
                    "Refresh-Token": refreshToken,
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
                    },
                    qrCodeGlobal: isQRCodeGlobal,
                })
            });
    
            if (!createSessionResponse.ok) {
                if (createSessionResponse.status === 401) {
                    const newAccessToken = createSessionResponse.headers.get('New-Access-Token');
                    const newRefreshToken = createSessionResponse.headers.get('New-Refresh-Token');
    
                    if (newAccessToken && newRefreshToken && repeatRequestNewSession < 2) {
                        setAccessToken(newAccessToken);
                        setRefreshToken(newRefreshToken);
                        repeatRequestNewSession++;
                        return handleNewSession();
                    } else {
                        navigate('/deconnexion');
                        return;
                    }
                }
                throw new Error('Failed to create session');
            }
    
            const createSessionJson = await createSessionResponse.json();
            if (createSessionJson.response === "Music is not played") {
                setErrorMessage('Veuillez lancer la lecture de musique sur Spotify pour continuer.');
                setShowErrorMessage(true);
                return;
            }
    
            const joinSessionResponse = await fetch(URI_BASE + '/joinSession', {
                method: 'POST',
                headers: {
                    "Authorization": "Bearer " + accessToken,
                    "Refresh-Token": refreshToken,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    qrCodeInfo: createSessionJson.sessionCode,
                    password: createSessionJson.SessionPassword
                })
            });
    
            if (!joinSessionResponse.ok) {
                throw new Error('Failed to join session');
            }
    
            const joinSessionJson = await joinSessionResponse.json();
            localStorage.setItem('sessionInformations', JSON.stringify(joinSessionJson));
            navigate('/session');
    
            if (joinSessionJson.response === "Music is not played") {
                setErrorMessage('Veuillez lancer la lecture de musique sur Spotify pour continuer.');
                setShowErrorMessage(true);
            } else {
                setShowErrorMessage(false);
                console.log('Session créée avec succès');
            }
        } catch (error) {
            console.error('Erreur lors de la requête de création ou de connexion à la session', error);
            setErrorMessage(error.message);
            setShowErrorMessage(true);
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
                setIsQRCodeGlobal={setIsQRCodeGlobal}
                spotifyError={spotifyError}
            />
        </>
    );
};

export default SessionParameterController;
