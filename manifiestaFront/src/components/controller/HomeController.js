import { useEffect, useState } from "react";
import React from "react";
import HomeView from "../view/HomeView";
import { URI_BASE } from "../../env";

const HomeController = ({role, user, accessToken, refreshToken}) => {
    const [UserHasSession, setUserHasSession] = useState(false);
    const [UserParticipateToSession, setUserParticipateToSession] = useState(false);

    useEffect(() => {
        checkIfUserHasSession();
    }, []);

    //fonction qui envoie une requête à l'API pour savoir si l'utilisateur a créer une session en cours
    const checkIfUserHasSession = async () => {
        try {
            const response = await fetch(URI_BASE + '/public/getOwnSessionInformation', {
                method: 'GET',
                headers: {
                    "Authorization": "Bearer " + accessToken,
                    "Refresh-Token": refreshToken,
                },
            });
            if (response.ok) {
                const responseJson = await response.json();
                console.log(responseJson);
            } else {
                console.error('Erreur lors de la requete de création de session');
            }
        } catch (e) {
            console.log(e);
        }
    }
    
    //fonction qui envoie une requête à l'API pour savoir si l'utilisateur participe à des sessions en cours

    return (
        <HomeView 
        role={role} 
        user={user}
        UserHasSession={UserHasSession}
        UserParticipateToSession={UserParticipateToSession}
        />
    )
}

export default HomeController;