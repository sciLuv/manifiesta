import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import LoginView from '../view/LoginView';
import { URI_BASE } from '../../env';

const LoginController = (props) => {
    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    
    const [errorMessage, setErrorMessage] = useState('');
    const [showErrorMessage, setShowErrorMessage] = useState(false);

    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleLoginSubmit = async () => {
        // Initialisation des messages d'erreur
        setShowErrorMessage(false);
        setErrorMessage('');

        try {
            const response = await fetch(URI_BASE + '/public/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username, password }),
            });

            if (!response.ok) {
                throw new Error('Erreur de connexion');
            }

            const data = await response.json();
            console.log(data);
            if(data.username && data.mail){
                props.setUser(data.username);
                props.setMail(data.mail);
                props.setRole(data.role);
            }

            

            // Récupérer les tokens depuis les headers de la réponse
            const newAccessToken = response.headers.get('Access-Token');
            const newRefreshToken = response.headers.get('Refresh-Token');

            // Vérifier que les deux tokens sont présents avant de mettre à jour les états
            if (newAccessToken && newRefreshToken) {
                props.setAccessToken(newAccessToken);
                props.setRefreshToken(newRefreshToken);
            }

            
            // Rediriger l'utilisateur vers la page d'accueil
            navigate('/'); 


        } catch (error) {
            setShowErrorMessage(true);
            setErrorMessage(error.message || 'Une erreur est survenue lors de la connexion');
        }
    };

    return (

        <div>
                <LoginView
                    username={username}
                    password={password}
                    errorMessage={errorMessage}
                    showErrorMessage={showErrorMessage}
                    onUsernameChange={handleUsernameChange}
                    onPasswordChange={handlePasswordChange}
                    onLoginSubmit={handleLoginSubmit}
                />
        </div>
    );
};

export default LoginController;
