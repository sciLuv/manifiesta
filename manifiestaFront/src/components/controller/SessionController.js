
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import SessionView from '../view/SessionView';
import { URI_BASE } from '../../env';

const SessionController = ({accessToken, refreshToken, setAccessToken, setRefreshToken}) => {
    const navigate = useNavigate();
    let stringJson = localStorage.getItem('sessionInformations');
    const [data, setData] = useState(JSON.parse(stringJson));
    const [isMusicEnded, setIsMusicEnded] = useState(false);
    let repeatRequestRefreshSession = 0;

    console.log(data);


    const refreshSession = async (event) => {
                console.log("test entry in refreshSession");
                try{
                    const response = await fetch(URI_BASE + '/joinSession', {
                        method: 'POST',
                        headers :{
                            "Authorization" : "Bearer " + accessToken,
                            "Refresh-Token" : refreshToken,
                            "Content-Type": "application/json"
                        },
                          
                        body: JSON.stringify({
                            qrCodeInfo: data.joinSessionDto.qrCodeInfo,
                            password: data.joinSessionDto.password  // Inclut le mot de passe seulement si nécessaire
                        })
                    });
                    if (response.ok) {
                        repeatRequestRefreshSession = 0;
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
                        if(response.status === 401){
                            console.log("response.header :" + response.headers.get('New-Access-Token'));
                            const newAccessToken = response.headers.get('New-Access-Token');
                            const newRefreshToken = response.headers.get('New-Refresh-Token');
                
                            // Vérifier que les deux tokens sont présents avant de mettre à jour les états
                            if (newAccessToken && newRefreshToken) {
                                console.log("test refresh token");
                                repeatRequestRefreshSession++
                                setAccessToken(newAccessToken);
                                setRefreshToken(newRefreshToken);
        
                                if(repeatRequestRefreshSession < 2){
                                    console.log("test refresh token 2");
                                return refreshSession();
                                }
                            } else {
                                navigate('/deconnexion')
                            }
                        }
                        console.error('Erreur lors de la requete de demande d\'information sur les sessions créer par l\'utilisateur');
                    }
                }
                catch(e){
                    console.log(e);
                }
        // Vous pourriez appeler une API ou gérer la connexion à une session ici
    };


    useEffect(() => {   
        refreshSession()
    }, [isMusicEnded, accessToken]);

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
