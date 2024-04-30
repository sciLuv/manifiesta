import { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom';
import React from "react";
import HomeView from "../view/HomeView";
import { URI_BASE } from "../../env";

const HomeController = ({role, user, accessToken, refreshToken, setRefreshToken, setAccessToken}) => {
    const navigate = useNavigate();
    const [userHasSession, setUserHasSession] = useState(false);
    const [userParticipateToSession, setUserParticipateToSession] = useState(false);
    const [userSession, setUserSession] = useState([]);
    const [userParticipateSession, setUserParticipateSession] = useState([]);
    const [showErrorMessage, setShowErrorMessage] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    let repeatRequestCheckIfUserHasSession = 0
    let repeatRequestCheckIfUserParticipeToSessions = 0
    let repeatRequestHandleJoinSession = 0


    useEffect(() => {
        checkIfUserHasSession();
        checkIfUserParticipeToSessions();
    }, [accessToken]);

    //fonction qui envoie une requête à l'API pour savoir si l'utilisateur a créer une session en cours
    const checkIfUserHasSession = async () => {
            if(!accessToken == "" || !accessToken == null || !accessToken == undefined){
            try {
                const response = await fetch(URI_BASE + '/getOwnSessionInformation', {
                    method: 'GET',
                    headers: {
                        "Authorization": "Bearer " + accessToken,
                        "Refresh-Token": refreshToken,
                    },
                });
                if (response.ok) {
                    const responseJson = await response.json();
                    if(responseJson !== null){
                        setUserHasSession(true);
                        setUserSession(responseJson);
                    } else {
                        setUserHasSession(false);
                        setUserSession({});
                    }
                    console.log(responseJson);
                } else {
                    if(response.status === 401){
                        console.log("response.header :" + response.headers.get('New-Access-Token'));
                        const newAccessToken = response.headers.get('New-Access-Token');
                        const newRefreshToken = response.headers.get('New-Refresh-Token');
            
                        // Vérifier que les deux tokens sont présents avant de mettre à jour les états
                        if (newAccessToken && newRefreshToken) {
                            repeatRequestCheckIfUserHasSession++
                            setAccessToken(newAccessToken);
                            setRefreshToken(newRefreshToken);

                            if(repeatRequestCheckIfUserHasSession < 2){
                            return checkIfUserHasSession();
                            }
                        } else {
                            navigate('/deconnexion')
                        }

                    }
                    console.error('Erreur lors de la requete de demande d\'information sur les sessions créer par l\'utilisateur');
                }
            } catch (e) {
                console.log(e);
            }
        }
    }
    
    //fonction qui envoie une requête à l'API pour savoir si l'utilisateur participe à des sessions en cours
    const checkIfUserParticipeToSessions = async () => {
            if(!accessToken == "" || !accessToken == null || !accessToken == undefined){
            try {
                const response = await fetch(URI_BASE + '/getParticipantSessionInformation', {
                    method: 'GET',
                    headers: {
                        "Authorization": "Bearer " + accessToken,
                        "Refresh-Token": refreshToken,
                    },
                });
                if (response.ok) {
                    repeatRequestCheckIfUserParticipeToSessions = 0
                    const responseJson = await response.json();
                    if(responseJson.length > 0){
                        setUserParticipateToSession(true);
                        setUserParticipateSession(responseJson);
                    } else {
                        setUserParticipateToSession(false);
                        setUserParticipateSession([]);
                    }
                    console.log(responseJson);
                } else {
                    if(response.status === 401){
                        console.log("response.header :" + response.headers.get('New-Access-Token'));
                        const newAccessToken = response.headers.get('New-Access-Token');
                        const newRefreshToken = response.headers.get('New-Refresh-Token');
            
                        // Vérifier que les deux tokens sont présents avant de mettre à jour les états
                        if (newAccessToken && newRefreshToken) {
                            repeatRequestCheckIfUserParticipeToSessions++
                            setAccessToken(newAccessToken);
                            setRefreshToken(newRefreshToken);

                            if(repeatRequestCheckIfUserParticipeToSessions < 2){
                            return checkIfUserParticipeToSessions();
                            }
                        } else {
                            navigate('/deconnexion')
                        }

                    }
                    console.error('Erreur lors de la requete de demande d\'information sur les sessions rejoins par l\'utilisateur');
                }
            } catch (e) {
                console.log(e);
            }
        }
    }

    const handleJoinSession = async (pass, code) => {
        console.log("test entry in handleJoinSession : " + code);
            if(!accessToken == "" || !accessToken == null || !accessToken == undefined){
            try {
                const response = await fetch(URI_BASE + '/joinSession', {
                    method: 'POST',
                    headers: {
                        "Authorization": "Bearer " + accessToken,
                        "Refresh-Token": refreshToken,
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        qrCodeInfo: code,
                        password: pass
                    })
                });

                if (response.ok) {
                    repeatRequestHandleJoinSession = 0
                    const responseJson = await response.json();
                    console.log(responseJson);
                    localStorage.setItem('sessionInformations', JSON.stringify(responseJson));
                    navigate('/session');
                    if (responseJson.response === "Music is not played") {
                        setErrorMessage('Veuillez lancer la lecture de musique sur Spotify pour continuer.');
                        setShowErrorMessage(true);
                    } else {
                        setShowErrorMessage(false);
                        console.log('Session créée avec succès');
                    }
                } else {
                    if(response.status === 401){
                        console.log("response.header :" + response.headers.get('New-Access-Token'));
                        const newAccessToken = response.headers.get('New-Access-Token');
                        const newRefreshToken = response.headers.get('New-Refresh-Token');
            
                        // Vérifier que les deux tokens sont présents avant de mettre à jour les états
                        if (newAccessToken && newRefreshToken) {
                            repeatRequestHandleJoinSession++
                            setAccessToken(newAccessToken);
                            setRefreshToken(newRefreshToken);

                            if(repeatRequestHandleJoinSession < 2){
                            return handleJoinSession();
                            }
                        } else {
                            navigate('/deconnexion')
                        }

                    }
                    console.error('Erreur lors de la connexion à la session');
                
                }
            } catch (error) {
                console.error('Erreur lors de la connexion à la session', error);
                setErrorMessage('Une erreur réseau est survenue.');
                setShowErrorMessage(true);
            }
    }
};


    return (
        <HomeView 
            role={role} 
            user={user}
            userHasSession={userHasSession}
            userParticipateToSession={userParticipateToSession}
            userSession={userSession}
            userParticipateSession={userParticipateSession}
            handleJoinSession={handleJoinSession}
        />
    )
}

export default HomeController;