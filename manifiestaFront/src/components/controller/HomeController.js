import { useState } from "react";
import React from "react";
import HomeView from "../view/HomeView";

const HomeController = ({role, user}) => {
    const [UserHasSession, setUserHasSession] = useState(false);
    const [UserParticipateToSession, setUserParticipateToSession] = useState(false);

    //fonction qui envoie une requête à l'API pour savoir si l'utilisateur a créer une session en cours
    function checkIfUserHasSession(){
        //fetch
    }

    //fonction qui envoie une requête à l'API pour savoir si l'utilisateur participe à des sessions en cours
    function checkIfUserParticipateToSession(){
        //fetch
    }

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