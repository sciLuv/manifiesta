import React, { useState } from 'react';
import { URI_BASE } from '../../env';
import JoinSessionView from '../view/JoinSessionView'; // Assurez-vous que le chemin d'importation est correct
import { useNavigate } from 'react-router-dom';

function JoinSessionController({accessToken, refreshToken, setRefreshToken, setAccessToken}) {
    const navigate = useNavigate();
    // États pour le code d'accès et le mot de passe
    const [accessCode, setAccessCode] = useState('');
    const [password, setPassword] = useState('');
    const [includePassword, setIncludePassword] = useState(false);
    let repeatRequestJoinSession = 0;

    // Gestion de la soumission du formulaire
    const handleJoinSession = async (event) => {
        event.preventDefault(); // Empêche le rechargement de la page
                try{
                    const response = await fetch(URI_BASE + '/joinSession', {
                        method: 'POST',
                        headers :{
                            "Authorization" : "Bearer " + accessToken,
                            "Refresh-Token" : refreshToken,
                            "Content-Type": "application/json"
                        },
                          
                        body: JSON.stringify({
                            qrCodeInfo: accessCode,
                            password: includePassword ? password : ""  // Inclut le mot de passe seulement si nécessaire
                        })
                    });
                    if (response.ok) {
                        repeatRequestJoinSession = 0;
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
                        if(response.status === 401){
                            console.log("response.header :" + response.headers.get('New-Access-Token'));
                            const newAccessToken = response.headers.get('New-Access-Token');
                            const newRefreshToken = response.headers.get('New-Refresh-Token');
                
                            // Vérifier que les deux tokens sont présents avant de mettre à jour les états
                            if (newAccessToken && newRefreshToken) {
                                repeatRequestJoinSession++
                                setAccessToken(newAccessToken);
                                setRefreshToken(newRefreshToken);
    
                                if(repeatRequestJoinSession < 2){
                                return handleJoinSession();
                                }
                            } else {
                                navigate('/deconnexion')
                            }
    
                        }
                        console.error('Erreur lors de la connexion à la session');
                    }
                }
                catch(e){
                    console.log(e);
                }
                
                

        // Vous pourriez appeler une API ou gérer la connexion à une session ici
    };

    // Fonctions pour mettre à jour les états lors des changements d'entrées
    const handleAccessCodeChange = (event) => {
        setAccessCode(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleIncludePasswordChange = (event) => {
        setIncludePassword(event.target.checked);
        // Réinitialise le mot de passe si la checkbox est décochée
        if (!event.target.checked) {
            setPassword('');
        }
    };

    // Rendu du composant Vue avec des props appropriés
    return (
        <JoinSessionView
            accessCode={accessCode}
            password={password}
            includePassword={includePassword}
            onAccessCodeChange={handleAccessCodeChange}
            onPasswordChange={handlePasswordChange}
            onIncludePasswordChange={handleIncludePasswordChange}
            onJoinSessionSubmit={handleJoinSession}
        />
    );
}

export default JoinSessionController;
