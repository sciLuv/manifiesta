
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import SessionView from '../view/SessionView';

const SessionController = () => {

    
    let stringJson = localStorage.getItem('sessionInformations');
    const [data, setData] = useState(JSON.parse(stringJson));
    console.log(data);


    const handleJoinSession = async (event) => {
        event.preventDefault(); // Empêche le rechargement de la page
                try{
                    const response = await fetch(URI_BASE + '/joinSession', {
                        method: 'POST',
                        headers :{
                            "Authorization" : "Bearer " + accessToken.accessToken,
                            "Refresh-Token" : accessToken.refreshToken,
                            "Content-Type": "application/json"
                        },
                          
                        body: JSON.stringify({
                            qrCodeInfo: accessCode,
                            password: includePassword ? password : ""  // Inclut le mot de passe seulement si nécessaire
                        })
                    });
                    if (response.ok) {
                        const responseJson = await response.json();
                        console.log(responseJson);
                        localStorage.setItem('sessionInformations', JSON.stringify(responseJson));
                        navigate('/session'); 
                        if(responseJson.response == "Music is not played"){
                            setErrorMessage('Veuillez lancer la lecture de musique sur Spotify pour continuer.');
                            setShowErrorMessage(true);
                        }  else {
                            setShowErrorMessage(false);
                            console.log('Session créée avec succès');
                        }
                    } else {
                        console.error('Erreur lors de la requete de création de session');
                    }
                }
                catch(e){
                    console.log(e);
                }
        // Vous pourriez appeler une API ou gérer la connexion à une session ici
    };

    // Gerer la selection du nombre de chansons
    const handleSongsNumberChange = (event) => {
        setSongsNumber(event.target.value);
    }


    return (
        <>
            <SessionView
                stringJson={data}
            />
        </>
    );
};

export default SessionController;
