import { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom';
import React from "react";
import HomeView from "../view/HomeView";
import { URI_BASE } from "../../env";

const HomeController = ({role, user, accessToken, refreshToken}) => {
    const navigate = useNavigate();
    const [userHasSession, setUserHasSession] = useState(false);
    const [userParticipateToSession, setUserParticipateToSession] = useState(false);
    const [userSession, setUserSession] = useState([]);
    const [userParticipateSession, setUserParticipateSession] = useState([]);
    const [showErrorMessage, setShowErrorMessage] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    useEffect(() => {
        checkIfUserHasSession();
        checkIfUserParticipeToSessions();
    }, []);

    //fonction qui envoie une requête à l'API pour savoir si l'utilisateur a créer une session en cours
    const checkIfUserHasSession = async () => {
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
                    console.log("ca fonctionne 1");
                } else {
                    setUserHasSession(false);
                    setUserSession({});
                }
                console.log(responseJson);
            } else {
                console.error('Erreur lors de la requete de demande d\'information sur les sessions créer par l\'utilisateur');
            }
        } catch (e) {
            console.log(e);
        }
    }
    
    //fonction qui envoie une requête à l'API pour savoir si l'utilisateur participe à des sessions en cours
    const checkIfUserParticipeToSessions = async () => {
        try {
            const response = await fetch(URI_BASE + '/getParticipantSessionInformation', {
                method: 'GET',
                headers: {
                    "Authorization": "Bearer " + accessToken,
                    "Refresh-Token": refreshToken,
                },
            });
            if (response.ok) {
                const responseJson = await response.json();
                if(responseJson.length > 0){
                    setUserParticipateToSession(true);
                    setUserParticipateSession(responseJson);
                    console.log("ca fonctionne");
                } else {
                    setUserParticipateToSession(false);
                    setUserParticipateSession([]);
                }
                console.log(responseJson);
            } else {
                console.error('Erreur lors de la requete de demande d\'information sur les sessions auquelles l\'utilisateur participe');
            }
        } catch (e) {
            console.log(e);
        }
    }

    const handleJoinSession = async (pass, code) => {
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
                console.error('Erreur lors de la requête de création de session');
                setErrorMessage('Une erreur est survenue lors de la tentative de connexion à la session.');
                setShowErrorMessage(true);
            }
        } catch (error) {
            console.error('Erreur lors de la connexion à la session', error);
            setErrorMessage('Une erreur réseau est survenue.');
            setShowErrorMessage(true);
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