
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
    let repeatVoteForSong = 0;
    let repeatRequestEndSession = 0;

    console.log(data);

    const voteForSong = async (SelectedSong) => {
        try {
            console.log("Envoi de la requête de vote");
            const response = await fetch(URI_BASE + '/vote', {
                method: 'POST',
                headers: {
                    "Authorization" : "Bearer " + accessToken,
                    "Refresh-Token" : refreshToken,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    suggestedMusicId: SelectedSong,
                    qrCode: data.joinSessionDto.qrCodeInfo,
                })
            });
            if (response.ok) {
                console.log('Vote enregistré avec succès');
            } else {
                if(response.status === 401){
                    console.log("response.header :" + response.headers.get('New-Access-Token'));
                    const newAccessToken = response.headers.get('New-Access-Token');
                    const newRefreshToken = response.headers.get('New-Refresh-Token');
        
                    // Vérifier que les deux tokens sont présents avant de mettre à jour les états
                    if (newAccessToken && newRefreshToken) {
                        repeatVoteForSong++
                        setAccessToken(newAccessToken);
                        setRefreshToken(newRefreshToken);

                        if(repeatVoteForSong < 2){
                        return refreshSession();
                        }
                    } else {
                        navigate('/deconnexion')
                    }
                }
                console.error('Erreur lors de la requete de demande d\'information sur les sessions créer par l\'utilisateur');
            }
        } catch (error) {
            console.error('Exception lors de l\'envoi du vote', error);
        }
    };
    
    // Supposons que vous ayez un bouton dans votre interface qui appelle cette fonction quand on clique dessus
    // Vous devriez ajouter un gestionnaire d'événements qui utilise cette fonction `voteForSong`
    // Par exemple:
    // <button onClick={() => voteForSong(selectedSongId, data.qrCode, data.password)}>Vote for this song</button>
    const endedSession = async (event) => {
        console.log("test entry in refreshSession");
        try{
            const response = await fetch(URI_BASE + '/endSession', {
                method: 'POST',
                headers :{
                    "Authorization" : "Bearer " + accessToken,
                    "Refresh-Token" : refreshToken,
                    "Content-Type": "application/json"
                },
                  
                body: JSON.stringify({
                    qrCodeInfo: data.joinSessionDto.qrCodeInfo  // Inclut le mot de passe seulement si nécessaire
                })
            });
            if (response.ok) {
                repeatRequestEndSession = 0;
                const responseJson = await response.json();
                if(responseJson.response == "Session ended"){
                    navigate('/');
                }
  
            } else {
                if(response.status === 401){
                    console.log("response.header :" + response.headers.get('New-Access-Token'));
                    const newAccessToken = response.headers.get('New-Access-Token');
                    const newRefreshToken = response.headers.get('New-Refresh-Token');
        
                    // Vérifier que les deux tokens sont présents avant de mettre à jour les états
                    if (newAccessToken && newRefreshToken) {
                        console.log("test refresh token");
                        repeatRequestEndSession++
                        setAccessToken(newAccessToken);
                        setRefreshToken(newRefreshToken);

                        if(repeatRequestRefreshSession < 2){
                            console.log("test refresh token 2");
                        return endedSession();
                        }
                    } else {
                        navigate('/deconnexion')
                    }
                }
                console.error('Erreur lors de la fermeture de la session');
            }
        }
        catch(e){
            console.log(e);
        }
// Vous pourriez appeler une API ou gérer la connexion à une session ici
};
    

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
                voteForSong={voteForSong}
                endedSession={endedSession}
            />
        </>
    );
};

export default SessionController;
