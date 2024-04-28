
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import SessionView from '../view/SessionView';
import { URI_BASE } from '../../env';

const SessionController = (accessToken) => {

    
    let stringJson = localStorage.getItem('sessionInformations');
    const [data, setData] = useState(JSON.parse(stringJson));
    const [isMusicEnded, setIsMusicEnded] = useState(false);
    console.log(data);

    const refreshSession = async (event) => {

                try{
                    const response = await fetch(URI_BASE + '/joinSession', {
                        method: 'POST',
                        headers :{
                            "Authorization" : "Bearer " + accessToken.accessToken,
                            "Refresh-Token" : accessToken.refreshToken,
                            "Content-Type": "application/json"
                        },
                          
                        body: JSON.stringify({
                            qrCodeInfo: data.joinSessionDto.qrCodeInfo,
                            password: data.joinSessionDto.password  // Inclut le mot de passe seulement si nécessaire
                        })
                    });
                    if (response.ok) {
                        const responseJson = await response.json();
                        console.log(responseJson);
                        localStorage.setItem('sessionInformations', JSON.stringify(responseJson));
                        setData(responseJson);
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


    useEffect(() => {   
        refreshSession()
    }, [isMusicEnded]);

    // Gerer la selection du nombre de chansons
    const handleSongsNumberChange = (event) => {
        setSongsNumber(event.target.value);
    }


    return (
        <>
            <SessionView
                data={data}
                setIsMusicEnded={setIsMusicEnded}
            />
        </>
    );
};

export default SessionController;
