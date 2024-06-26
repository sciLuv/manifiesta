
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import SessionView from '../view/SessionView';
import { URI_BASE } from '../../env';

const SessionController = ({accessToken, refreshToken, setAccessToken, setRefreshToken}) => {
    const navigate = useNavigate();
    let stringJson = localStorage.getItem('sessionInformations');
    const [data, setData] = useState(JSON.parse(stringJson));
    const [isMusicEnded, setIsMusicEnded] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const [overlayIsOpen, setOverlayIsOpen] = useState(false);
    const [listOfParticipants, setListOfParticipants] = useState([]);
    const [showListParticipants, setShowListParticipants] = useState(false);
    const [showcodeAndPassword, setShowcodeAndPassword] = useState(false);
    const [selectedMusicId, setSelectedMusicId] = useState(null);
    let repeatRequestRefreshSession = 0;
    let repeatVoteForSong = 0;
    let repeatRequestEndSession = 0;
    let repeatLeaveSession = 0;
    let repeatRequestGetAllParticipants = 0;

    const promiseQueue = [];
    let currentlyProcessing = false;


    const processQueue = async () => {
        if (currentlyProcessing) return;
        if (promiseQueue.length === 0) return;
    
        currentlyProcessing = true;
        const currentTask = promiseQueue.shift();
        await currentTask();
        currentlyProcessing = false;
    
        processQueue(); // Appelle la prochaine tâche dans la queue
    };

    console.log(data);

    const voteForSong = async (SelectedSong) => {
        console.log("SelectedSong : " + SelectedSong);
        setSelectedMusicId(SelectedSong);
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
    
    const endedSession = async () => {
        const task = async () => {
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
                            console.log(repeatRequestEndSession);
    
                            if(repeatRequestEndSession < 5){
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
        };
        promiseQueue.push(task);
        processQueue();
    };


const getAllParticipants = async (event) => {
    try{
        const response = await fetch(URI_BASE + '/GetAllParticipantsOfSession', {
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
            repeatRequestGetAllParticipants = 0;
            const responseJson = await response.json();
            console.log(responseJson.sessionParticipantNameAndIsGuestDtos);
            setListOfParticipants(responseJson.sessionParticipantNameAndIsGuestDtos);                
        } else {
            if(response.status === 401){
                console.log("response.header :" + response.headers.get('New-Access-Token'));
                const newAccessToken = response.headers.get('New-Access-Token');
                const newRefreshToken = response.headers.get('New-Refresh-Token');
    
                // Vérifier que les deux tokens sont présents avant de mettre à jour les états
                if (newAccessToken && newRefreshToken) {
                    console.log("test refresh token");
                    repeatRequestGetAllParticipants++
                    setAccessToken(newAccessToken);
                    setRefreshToken(newRefreshToken);

                    if(repeatRequestGetAllParticipants < 2){
                        console.log("test refresh token 2");
                    return getAllParticipants();
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


const leaveSession = async (event) => {
    try{
        const response = await fetch(URI_BASE + '/leaveSession', {
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
            repeatLeaveSession = 0;
            const responseJson = await response.json();
            console.log(responseJson);
            if(responseJson.response == "session leaved"){
                navigate('/');
            } else if(responseJson.response == "problem with leaving session"){
                setErrorMessage('Problème lors de la déconnexion de la session');
            }                       
            
        } else {
            if(response.status === 401){
                console.log("response.header :" + response.headers.get('New-Access-Token'));
                const newAccessToken = response.headers.get('New-Access-Token');
                const newRefreshToken = response.headers.get('New-Refresh-Token');
    
                // Vérifier que les deux tokens sont présents avant de mettre à jour les états
                if (newAccessToken && newRefreshToken) {
                    console.log("test refresh token");
                    repeatLeaveSession++
                    setAccessToken(newAccessToken);
                    setRefreshToken(newRefreshToken);

                    if(repeatLeaveSession < 2){
                        console.log("test refresh token 2");
                    return leaveSession();
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

const refreshSession = async () => {
    const task = async () => {
        if (repeatRequestRefreshSession >= 2) {
            console.error("Nombre maximal de tentatives atteint pour rafraîchir la session");
            return;
        }
        try {
            const response = await fetch(URI_BASE + '/joinSession', {
                method: 'POST',
                headers: {
                    "Authorization": "Bearer " + accessToken,
                    "Refresh-Token": refreshToken,
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
                localStorage.setItem('sessionInformations', JSON.stringify(responseJson));
                setData(responseJson);
                if(responseJson.sessionEnded){
                    navigate('/');
                }
            } else if (response.status === 401) {
                const newAccessToken = response.headers.get('New-Access-Token');
                const newRefreshToken = response.headers.get('New-Refresh-Token');
                if (newAccessToken && newRefreshToken) {
                    setAccessToken(newAccessToken);
                    setRefreshToken(newRefreshToken);
                    repeatRequestRefreshSession++;
                    return refreshSession();
                } else {
                    navigate('/deconnexion')
                }
            } else {
                throw new Error("Problème lors de la requête");
            }
        } catch (e) {
            console.error('Erreur lors de la requête de rafraîchissement de la session', e);
        }
    };
    promiseQueue.push(task);
    processQueue();
};




    useEffect(() => {
        const handleVisibilityChange = () => {
          if (document.visibilityState === 'visible') {
            console.log('L\'utilisateur est revenu sur cet onglet!');
            refreshSession()
          }
        };
    
        document.addEventListener('visibilitychange', handleVisibilityChange);
    
        // N'oubliez pas de nettoyer l'effet !
        return () => {
          document.removeEventListener('visibilitychange', handleVisibilityChange);
        };
      }, []);
    useEffect(() => {   
        console.log("test entry in refreshSession");
        refreshSession()
    }, [isMusicEnded, accessToken, selectedMusicId]);

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
                leaveSession={leaveSession}
                overlayIsOpen={overlayIsOpen} 
                setOverlayIsOpen={setOverlayIsOpen}
                getAllParticipants={getAllParticipants}
                listOfParticipants={listOfParticipants}
                showcodeAndPassword={showcodeAndPassword}
                setShowcodeAndPassword={setShowcodeAndPassword}
                showListParticipants={showListParticipants}
                setShowListParticipants={setShowListParticipants}
                setSelectedMusicId={setSelectedMusicId}
                selectedMusicId={selectedMusicId}
            />
        </>
    );
};

export default SessionController;
